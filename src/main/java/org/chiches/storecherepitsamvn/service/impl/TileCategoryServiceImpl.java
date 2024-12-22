package org.chiches.storecherepitsamvn.service.impl;

import org.chiches.storecherepitsacontracs.dto.tilecategory.TileCategoryForm;
import org.chiches.storecherepitsamvn.dto.TileCategoryDTO;
import org.chiches.storecherepitsamvn.entity.TileCategoryEntity;
import org.chiches.storecherepitsamvn.repository.TileCategoryRepository;
import org.chiches.storecherepitsamvn.service.TileCategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TileCategoryServiceImpl implements TileCategoryService {

    private final TileCategoryRepository tileCategoryRepository;
    private final ModelMapper modelMapper;

    public TileCategoryServiceImpl(TileCategoryRepository tileCategoryRepository, ModelMapper modelMapper) {
        this.tileCategoryRepository = tileCategoryRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    @Cacheable("allTileCategories")
    public List<TileCategoryDTO> getAllCategories() {
        return tileCategoryRepository.findAll().stream()
                .map(entity -> modelMapper.map(entity, TileCategoryDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    @Cacheable(value = "tileCategoryById", key = "#id")
    public TileCategoryDTO getCategoryById(Long id) {
        TileCategoryEntity entity = tileCategoryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Category not found: " + id));
        return modelMapper.map(entity, TileCategoryDTO.class);
    }

    @Override
    @CacheEvict(value = {"allTileCategories", "tileCategoryById", "topCategoriesByOrders"}, allEntries = true)
    public void createCategory(TileCategoryForm tileCategoryForm) {
        TileCategoryEntity entity = modelMapper.map(tileCategoryForm, TileCategoryEntity.class);
        tileCategoryRepository.save(entity);
    }

    @Override
    @CacheEvict(value = {"allTileCategories", "tileCategoryById", "topCategoriesByOrders"}, allEntries = true)
    public void updateCategory(Long id, TileCategoryForm tileCategoryForm) {
        TileCategoryEntity entity = tileCategoryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Category not found: " + id));
        modelMapper.map(tileCategoryForm, entity);
        tileCategoryRepository.save(entity);
    }

    @Override
    @Cacheable(value = "topCategoriesByOrders", key = "#limit")
    public List<TileCategoryDTO> findTopCategoriesByOrders(int limit) {
        return tileCategoryRepository.findTopCategoriesByOrders(Pageable.ofSize(limit)).stream()
                .map(category -> modelMapper.map(category, TileCategoryDTO.class))
                .collect(Collectors.toList());
    }
}
