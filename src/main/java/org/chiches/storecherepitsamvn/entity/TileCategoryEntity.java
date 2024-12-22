package org.chiches.storecherepitsamvn.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;

import java.util.List;

@Entity
public class TileCategoryEntity extends BaseEntity {

    private String name;

    private List<TileEntity> tileEntities;

    @Column(nullable = false, unique = true)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @OneToMany(mappedBy = "category")
    public List<TileEntity> getTiles() {
        return tileEntities;
    }

    public void setTiles(List<TileEntity> tileEntities) {
        this.tileEntities = tileEntities;
    }

    @Override
    public String toString() {
        return "TileCategoryEntity{" +
                "id=" + super.getId() + '\'' +
                "name='" + name + '\'' +
                '}';
    }
}
