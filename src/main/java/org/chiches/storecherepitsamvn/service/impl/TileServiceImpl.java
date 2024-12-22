package org.chiches.storecherepitsamvn.service.impl;

import org.chiches.storecherepitsacontracs.dto.tile.CreateTileForm;
import org.chiches.storecherepitsacontracs.dto.tile.UpdateTileForm;
import org.chiches.storecherepitsamvn.dto.TileDTO;
import org.chiches.storecherepitsamvn.entity.TileEntity;
import org.chiches.storecherepitsamvn.entity.TileCategoryEntity;
import org.chiches.storecherepitsamvn.repository.TileCategoryRepository;
import org.chiches.storecherepitsamvn.repository.TileRepository;
import org.chiches.storecherepitsamvn.service.TileService;
import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TileServiceImpl implements TileService {

    private final TileRepository tileRepository;
    private final TileCategoryRepository tileCategoryRepository;
    private final ModelMapper modelMapper;

    public TileServiceImpl(TileRepository tileRepository, TileCategoryRepository tileCategoryRepository, ModelMapper modelMapper) {
        this.tileRepository = tileRepository;
        this.tileCategoryRepository = tileCategoryRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    @Transactional
    @CacheEvict(value = {"allTiles", "tilesById", "tilesByCategory", "mostPurchasedTiles"}, allEntries = true)
    public TileDTO createTile(CreateTileForm createTileForm) {
        TileEntity tileEntity = modelMapper.map(createTileForm, TileEntity.class);
        tileEntity.setId(null);
        System.out.println("titleEntity id is: " + tileEntity.getId());
        System.out.println(tileEntity);

        TileCategoryEntity tileCategoryEntity = tileCategoryRepository.findById(createTileForm.getCategoryId())
                .orElseThrow(() -> new IllegalArgumentException("Category not found: " + createTileForm.getCategoryId()));
        tileEntity.setCategory(tileCategoryEntity);
        System.out.println(tileEntity);

        tileEntity = tileRepository.save(tileEntity);

        return modelMapper.map(tileEntity, TileDTO.class);
    }

    @Override
    @CacheEvict(value = {"allTiles", "tilesById", "tilesByCategory", "mostPurchasedTiles"}, allEntries = true)
    public TileDTO updateTile(UpdateTileForm updateTileForm) {
        TileEntity existingTile = tileRepository.findById(updateTileForm.getId())
                .orElseThrow(() -> new IllegalArgumentException("Tile not found: " + updateTileForm.getId()));

        existingTile.setDescription(updateTileForm.getDescription());
        existingTile.setPrice(updateTileForm.getPrice());
        existingTile.setMaterial(updateTileForm.getMaterial());
        existingTile.setName(updateTileForm.getName());
        TileCategoryEntity tileCategoryEntity = tileCategoryRepository.findById(updateTileForm.getCategoryId())
                .orElseThrow(() -> new IllegalArgumentException("Category not found: " + updateTileForm.getCategoryId()));
        existingTile.setCategory(tileCategoryEntity);

        existingTile = tileRepository.save(existingTile);

        return modelMapper.map(existingTile, TileDTO.class);
    }

    @Override
    @Cacheable(value = "tilesById", key = "#id")
    public TileDTO findById(Long id) {
        // Find the tile entity
        TileEntity tileEntity = tileRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Tile not found: " + id));

        // Map the entity to a DTO
        return modelMapper.map(tileEntity, TileDTO.class);
    }

    @Override
    @Cacheable("allTiles")
    public List<TileDTO> findAll() {
        List<TileDTO> tileDTOS = tileRepository.findAll().stream()
                .map(tileEntity -> modelMapper.map(tileEntity, TileDTO.class))
                .collect(Collectors.toList());
        return tileDTOS;
    }

    @Override
    public TileEntity getTile(Long id) {
        return tileRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Tile not found: " + id));
    }

    @Override
    @Cacheable(value = "tilesByCategory", key = "#categoryId")
    public List<TileDTO> getTilesByCategory(Long categoryId) {
        return tileRepository.findByCategoryId(categoryId)
                .stream()
                .map(tile -> modelMapper.map(tile, TileDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    @Cacheable(value = "mostPurchasedTiles", key = "#limit")
    public List<TileDTO> findMostPurchasedTiles(int limit) {
        return tileRepository.findMostPurchasedTiles(Pageable.ofSize(limit)).stream()
                .map(tile -> modelMapper.map(tile, TileDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    @Cacheable(value = "tilesById", key = "#id")
    public TileDTO getTileById(Long id) {
        TileEntity tileEntity = tileRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Tile not found with id: " + id));
        System.out.println(modelMapper.map(tileEntity, TileDTO.class));
        return modelMapper.map(tileEntity, TileDTO.class);
    }
}
