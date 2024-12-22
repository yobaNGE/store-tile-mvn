package org.chiches.storecherepitsamvn.service;

import org.chiches.storecherepitsacontracs.dto.tile.CreateTileForm;
import org.chiches.storecherepitsacontracs.dto.tile.UpdateTileForm;
import org.chiches.storecherepitsamvn.dto.TileDTO;
import org.chiches.storecherepitsamvn.entity.TileEntity;

import java.util.List;

public interface TileService {
    TileDTO createTile(CreateTileForm createTileForm);

    TileDTO updateTile(UpdateTileForm updateTileForm);

    TileDTO findById(Long id);

    List<TileDTO> findAll();

    TileEntity getTile(Long id);

    List<TileDTO> getTilesByCategory(Long categoryId);

    List<TileDTO> findMostPurchasedTiles(int limit);

    TileDTO getTileById(Long id);
}
