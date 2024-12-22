package org.chiches.storecherepitsamvn.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class TileEntity extends BaseEntity {

    private String name;

    private Double price;

    private String material;

    private TileCategoryEntity category;

    private String description;

    private List<OrderItemEntity> orderItemEntities;

    @Column(nullable = false)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(nullable = false)
    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    @Column(nullable = false)
    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    public TileCategoryEntity getCategory() {
        return category;
    }

    public void setCategory(TileCategoryEntity category) {
        this.category = category;
    }

    @Column(nullable = false)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    @OneToMany(mappedBy = "tile")
    public List<OrderItemEntity> getOrderItemEntities() {
        return orderItemEntities;
    }

    public void setOrderItemEntities(List<OrderItemEntity> orderItemEntities) {
        this.orderItemEntities = orderItemEntities;
    }

    @Override
    public String toString() {
        return "TileEntity{" +
                "id=" + super.getId() + '\''  +
                "name='" + name + '\'' +
                ", price=" + price +
                ", material='" + material + '\'' +
                ", category=" + category +
                ", description='" + description + '\'' +
                '}';
    }
}
