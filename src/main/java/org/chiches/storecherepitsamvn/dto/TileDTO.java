package org.chiches.storecherepitsamvn.dto;

public class TileDTO {
    private Long id;
    private String name;
    private Double price;
    private String material;
    private TileCategoryDTO tileCategoryDTO;
    private String description;

    public TileDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public TileCategoryDTO getTileCategoryDTO() {
        return tileCategoryDTO;
    }

    public void setTileCategoryDTO(TileCategoryDTO tileCategoryDTO) {
        this.tileCategoryDTO = tileCategoryDTO;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "TileDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", material='" + material + '\'' +
                ", tileCategoryDTO=" + tileCategoryDTO +
                ", description='" + description + '\'' +
                '}';
    }
}
