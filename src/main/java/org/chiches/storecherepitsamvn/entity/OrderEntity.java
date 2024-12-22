package org.chiches.storecherepitsamvn.entity;

import jakarta.persistence.*;
import org.chiches.storecherepitsamvn.entity.consts.OrderStatus;

import java.time.LocalDateTime;
import java.util.List;

@Entity
public class OrderEntity extends BaseEntity {

    private UserEntity user;

    private List<OrderItemEntity> orderItemEntities;

    private LocalDateTime orderDate;

    private Double totalPrice;

    private OrderStatus status;

    @ManyToOne
    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity userEntity) {
        this.user = userEntity;
    }

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    public List<OrderItemEntity> getOrderItems() {
        return orderItemEntities;
    }

    public void setOrderItems(List<OrderItemEntity> orderItemEntities) {
        this.orderItemEntities = orderItemEntities;
    }

    @Column(nullable = false)
    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    @Column(nullable = false)
    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }
}
