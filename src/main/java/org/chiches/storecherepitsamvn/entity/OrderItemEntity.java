package org.chiches.storecherepitsamvn.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
@Entity
public class OrderItemEntity extends BaseEntity{

    private OrderEntity order;

    private TileEntity tile;

    private Integer quantity;

    private Double price;

    @ManyToOne
    public OrderEntity getOrder() {
        return order;
    }

    public void setOrder(OrderEntity orderEntity) {
        this.order = orderEntity;
    }

    @ManyToOne
    public TileEntity getTile() {
        return tile;
    }

    public void setTile(TileEntity tileEntity) {
        this.tile = tileEntity;
    }
    @Column(nullable = false)
    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
    @Column(nullable = false)
    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
