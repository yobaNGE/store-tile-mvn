package org.chiches.storecherepitsamvn.dto;

import org.chiches.storecherepitsamvn.entity.CartEntity;
import org.chiches.storecherepitsamvn.entity.TileEntity;

public class CartItemDTO {
    private CartDTO cart;
    private TileDTO tile;
    private Integer quantity;

    public CartItemDTO() {
    }

    public CartDTO getCart() {
        return cart;
    }

    public void setCart(CartDTO cart) {
        this.cart = cart;
    }

    public TileDTO getTile() {
        return tile;
    }

    public void setTile(TileDTO tile) {
        this.tile = tile;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
