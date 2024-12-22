package org.chiches.storecherepitsamvn.service;

import org.chiches.storecherepitsamvn.dto.TileDTO;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CatalogueService {
    Page<TileDTO> getFilteredTiles(int page, Double minPrice, Double maxPrice, String material, Long category, String sortBy);
    List<String> getAllMaterials();
    List<String> getAllCategories();
    //int getTotalPages(Double minPrice, Double maxPrice, String material, String category);
}
