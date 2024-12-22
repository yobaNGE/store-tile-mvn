package org.chiches.storecherepitsamvn.service.impl;

import org.chiches.storecherepitsamvn.dto.OrderDTO;
import org.chiches.storecherepitsamvn.dto.OrderItemDTO;
import org.chiches.storecherepitsamvn.entity.CartEntity;
import org.chiches.storecherepitsamvn.entity.OrderEntity;
import org.chiches.storecherepitsamvn.entity.OrderItemEntity;
import org.chiches.storecherepitsamvn.entity.consts.OrderStatus;
import org.chiches.storecherepitsamvn.repository.CartRepository;
import org.chiches.storecherepitsamvn.repository.OrderRepository;
import org.chiches.storecherepitsamvn.repository.UserRepository;
import org.chiches.storecherepitsamvn.service.OrderService;
import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    private final CartRepository cartRepository;
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public OrderServiceImpl(CartRepository cartRepository, OrderRepository orderRepository, UserRepository userRepository, ModelMapper modelMapper) {
        this.cartRepository = cartRepository;
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    @CacheEvict(value = {"ordersForUser", "allOrders", "ordersByStatus"}, allEntries = true)
    public void createOrderFromCart(Principal principal) {
        CartEntity cart = cartRepository.findByUserUsername(principal.getName())
                .orElseThrow(() -> new IllegalArgumentException("Cart not found for user"));

        if (cart.getItems().isEmpty()) {
            throw new IllegalStateException("Cannot create order. Cart is empty.");
        }

        OrderEntity order = new OrderEntity();
        order.setUser(cart.getUser());
        order.setOrderDate(LocalDateTime.now());
        order.setStatus(OrderStatus.CREATED);

        List<OrderItemEntity> orderItems = cart.getItems().stream().map(cartItem -> {
            OrderItemEntity orderItem = new OrderItemEntity();
            orderItem.setOrder(order);
            orderItem.setTile(cartItem.getTile());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setPrice(cartItem.getTile().getPrice() * cartItem.getQuantity());
            return orderItem;
        }).collect(Collectors.toList());

        double totalPrice = orderItems.stream()
                .mapToDouble(OrderItemEntity::getPrice)
                .sum();

        order.setOrderItems(orderItems);
        order.setTotalPrice(totalPrice);

        orderRepository.save(order);
        cart.getItems().clear();
        cartRepository.save(cart);
    }

    @Override
    @Cacheable(value = "ordersForUser", key = "#principal.name")
    public List<OrderDTO> getOrdersForUser(Principal principal) {
        List<OrderEntity> orders = orderRepository.findByUserUsername(principal.getName());
        orders.sort(Comparator.comparing(OrderEntity::getOrderDate).reversed());
        return orders.stream()
                .map(order -> {
                    OrderDTO dto = modelMapper.map(order, OrderDTO.class);
                    dto.setOrderItems(order.getOrderItems().stream()
                            .map(item -> modelMapper.map(item, OrderItemDTO.class))
                            .collect(Collectors.toList()));
                    return dto;
                })
                .collect(Collectors.toList());
    }

    @Override
    @Cacheable("allOrders")
    public List<OrderDTO> getAllOrders() {
        List<OrderEntity> orders = orderRepository.findAll();
        orders.sort(Comparator.comparing(OrderEntity::getOrderDate).reversed());
        return orders.stream()
                .map(order -> modelMapper.map(order, OrderDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    @Cacheable(value = "ordersByStatus", key = "#status")
    public List<OrderDTO> getOrdersByStatus(String status) {
        OrderStatus orderStatus = parseOrderStatus(status);
        return orderRepository.findByStatus(orderStatus).stream()
                .map(order -> modelMapper.map(order, OrderDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    @CacheEvict(value = {"ordersForUser", "allOrders", "ordersByStatus"}, allEntries = true)
    public void updateOrderStatus(Long orderId, String status) {
        OrderStatus orderStatus = parseOrderStatus(status);
        OrderEntity order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Order not found"));
        order.setStatus(orderStatus);
        orderRepository.save(order);
    }
    private OrderStatus parseOrderStatus(String status) {
        try {
            return status != null ? OrderStatus.valueOf(status.toUpperCase()) : null;
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid status: " + status);
        }
    }

}