package org.chiches.storecherepitsamvn.service.impl;

import org.chiches.storecherepitsamvn.dto.TileDTO;
import org.chiches.storecherepitsamvn.entity.TileEntity;
import org.chiches.storecherepitsamvn.repository.TileCategoryRepository;
import org.chiches.storecherepitsamvn.repository.TileRepository;
import org.chiches.storecherepitsamvn.service.CatalogueService;
import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CatalogueServiceImpl implements CatalogueService {


    private final TileRepository tileRepository;
    private final ModelMapper modelMapper;
    private final TileCategoryRepository tileCategoryRepository;
    public CatalogueServiceImpl(TileRepository tileRepository, ModelMapper modelMapper, TileCategoryRepository tileCategoryRepository) {
        this.tileRepository = tileRepository;
        this.modelMapper = modelMapper;
        this.tileCategoryRepository = tileCategoryRepository;
    }

    @Override
    @Cacheable(value = "filteredTiles", key = "{#page, #minPrice, #maxPrice, #material, #category, #sortBy}")
    public Page<TileDTO> getFilteredTiles(int page, Double minPrice, Double maxPrice, String material, Long category, String sortBy) {
        System.out.println("all inputs " + page + " " + minPrice + " " + maxPrice + " " + material + " " + category + " " + sortBy);
        if (material != null && material.isEmpty()) {
            material = null;
        }
        Sort sort;
        switch (sortBy) {
            case "price":
                sort = Sort.by(Sort.Direction.ASC, "price");
                break;
            case "name":
                sort = Sort.by(Sort.Direction.ASC, "name");
                break;
            case "orders":
                sort = Sort.unsorted();
                break;
            default:
                sort = Sort.by(Sort.Direction.ASC, "name");
        }
        Pageable pageable = PageRequest.of(page - 1, 20, sort);
        Page<TileEntity> tilesPage = tileRepository.findTilesWithFiltersAndCategory(minPrice, maxPrice, material, category, pageable);
        return tilesPage.map(tile -> modelMapper.map(tile, TileDTO.class));
    }

    @Override
    @Cacheable("allTileMaterials")
    public List<String> getAllMaterials() {
        return tileRepository.findDistinctMaterials();
    }

    @Override
    @Cacheable("allTileCategories")
    public List<String> getAllCategories() {
        List<String> categories = tileCategoryRepository.findAll().stream().map(category -> category.getName()).collect(Collectors.toList());
        return categories;
    }
}