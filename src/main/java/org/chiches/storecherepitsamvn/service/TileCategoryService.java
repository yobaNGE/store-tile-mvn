package org.chiches.storecherepitsamvn.service;

import org.chiches.storecherepitsacontracs.dto.tilecategory.TileCategoryForm;
import org.chiches.storecherepitsamvn.dto.TileCategoryDTO;

import java.util.List;

public interface TileCategoryService {

    List<TileCategoryDTO> getAllCategories();

    TileCategoryDTO getCategoryById(Long id);

    void createCategory(TileCategoryForm tileCategoryForm);

    void updateCategory(Long id, TileCategoryForm tileCategoryForm);

    List<TileCategoryDTO> findTopCategoriesByOrders(int limit);

}
