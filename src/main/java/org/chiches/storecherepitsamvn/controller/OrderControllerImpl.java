package org.chiches.storecherepitsamvn.controller;

import org.chiches.storecherepitsacontracs.controllers.OrderController;
import org.chiches.storecherepitsamvn.dto.OrderDTO;
import org.chiches.storecherepitsamvn.entity.consts.OrderStatus;
import org.chiches.storecherepitsamvn.service.OrderService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import java.security.Principal;
import java.util.List;

@Controller
public class OrderControllerImpl implements OrderController {

    private final OrderService orderService;

    public OrderControllerImpl(OrderService orderService) {
        this.orderService = orderService;
    }

    @Override
    public String createOrder(Principal principal) {
        orderService.createOrderFromCart(principal);
        return "redirect:/orders";
    }

    @Override
    public String getOrders(Principal principal, Model model) {
        model.addAttribute("orders", orderService.getOrdersForUser(principal));
        return "orders/orders-page";
    }
    @Override
    public String getAllOrders(String status, Model model) {
        List<OrderDTO> orders = status == null || status.isEmpty()
                ? orderService.getAllOrders()
                : orderService.getOrdersByStatus(status);

        model.addAttribute("orders", orders);
        model.addAttribute("statuses", OrderStatus.values());
        model.addAttribute("selectedStatus", status);
        return "orders/orders-page-admin";
    }
    @Override
    public String updateOrderStatus(Long orderId, String status) {
        orderService.updateOrderStatus(orderId, status);
        return "redirect:/orders/admin";
    }
}