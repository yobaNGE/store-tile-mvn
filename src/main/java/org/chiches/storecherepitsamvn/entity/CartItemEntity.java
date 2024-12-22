package org.chiches.storecherepitsamvn.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;

@Entity
public class CartItemEntity extends BaseEntity {
    private CartEntity cart;
    private TileEntity tile;
    private Integer quantity;

    @ManyToOne
    public CartEntity getCart() {
        return cart;
    }

    public void setCart(CartEntity cart) {
        this.cart = cart;
    }

    @ManyToOne
    public TileEntity getTile() {
        return tile;
    }

    public void setTile(TileEntity tile) {
        this.tile = tile;
    }

    @Column(nullable = false)
    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}