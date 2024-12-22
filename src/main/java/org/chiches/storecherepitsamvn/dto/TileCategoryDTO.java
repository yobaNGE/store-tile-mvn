package org.chiches.storecherepitsamvn.dto;

import java.util.List;

public class TileCategoryDTO {
    private Long id;
    private String name;
    private List<TileDTO> tiles;

    public TileCategoryDTO() {
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

    public List<TileDTO> getTiles() {
        return tiles;
    }

    public void setTiles(List<TileDTO> tiles) {
        this.tiles = tiles;
    }

    @Override
    public String toString() {
        return "TileCategoryDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
