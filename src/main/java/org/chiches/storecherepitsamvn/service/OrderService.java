package org.chiches.storecherepitsamvn.service;

import org.chiches.storecherepitsamvn.dto.OrderDTO;
import org.chiches.storecherepitsamvn.entity.consts.OrderStatus;

import java.security.Principal;
import java.util.List;

public interface OrderService {
    void createOrderFromCart(Principal principal);
    List<OrderDTO> getOrdersForUser(Principal principal);
    List<OrderDTO> getAllOrders();
    List<OrderDTO> getOrdersByStatus(String status);
    void updateOrderStatus(Long orderId, String status);
}
