package org.chiches.storecherepitsamvn.dto;

import org.chiches.storecherepitsamvn.entity.CartItemEntity;
import org.chiches.storecherepitsamvn.entity.UserEntity;

import java.util.List;

public class CartDTO {
    private UserDTO user;
    private List<CartItemDTO> items;
    private Double totalPrice;
    public CartDTO() {
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    public List<CartItemDTO> getItems() {
        return items;
    }

    public void setItems(List<CartItemDTO> items) {
        this.items = items;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }
}
