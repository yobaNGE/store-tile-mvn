package org.chiches.storecherepitsamvn.dto;

public class OrderItemDTO {
    private Long id;
    private OrderDTO orderDTO;
    private TileDTO tileDTO;
    private Integer quantity;
    private Double price;

    public OrderItemDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public OrderDTO getOrderDTO() {
        return orderDTO;
    }

    public void setOrderDTO(OrderDTO orderDTO) {
        this.orderDTO = orderDTO;
    }

    public TileDTO getTileDTO() {
        return tileDTO;
    }

    public void setTileDTO(TileDTO tileDTO) {
        this.tileDTO = tileDTO;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "OrderItemDTO{" +
                "price=" + price +
                ", tileDTO=" + tileDTO +
                ", quantity=" + quantity +
                ", id=" + id +
                '}';
    }
}
