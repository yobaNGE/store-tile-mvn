package org.chiches.storecherepitsamvn.controller;

import org.chiches.storecherepitsacontracs.controllers.CatalogueController;
import org.chiches.storecherepitsamvn.dto.TileCategoryDTO;
import org.chiches.storecherepitsamvn.dto.TileDTO;
import org.chiches.storecherepitsamvn.service.CatalogueService;
import org.chiches.storecherepitsamvn.service.TileCategoryService;
import org.chiches.storecherepitsamvn.service.TileService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class CatalogueControllerImpl implements CatalogueController {

    private final TileService tileService;
    private final TileCategoryService tileCategoryService;
    private final CatalogueService catalogueService;
    public CatalogueControllerImpl(TileService tileService, TileCategoryService tileCategoryService, CatalogueService catalogueService) {
        this.tileService = tileService;
        this.tileCategoryService = tileCategoryService;
        this.catalogueService = catalogueService;
    }

    @Override
    public String getTilesByCategory(@PathVariable Long id, Model model) {
        model.addAttribute("tiles", tileService.getTilesByCategory(id));
        model.addAttribute("categories", tileCategoryService.getAllCategories());
        return "catalogue-page";
    }
    @Override
    public String getCatalogue(int page, Double minPrice, Double maxPrice, String material, Long category, String sortBy, Model model) {
        Page<TileDTO> tiles = catalogueService.getFilteredTiles(page, minPrice, maxPrice, material, category, sortBy);
        List<String> materials = catalogueService.getAllMaterials();
        List<TileCategoryDTO> categories = tileCategoryService.getAllCategories();

        model.addAttribute("tiles", tiles.getContent());
        model.addAttribute("currentPage", tiles.getNumber() + 1);
        model.addAttribute("totalPages", tiles.getTotalPages() == 0 ? 1 : tiles.getTotalPages());
        model.addAttribute("materials", materials);
        model.addAttribute("categories", categories);
        model.addAttribute("minPrice", minPrice);
        model.addAttribute("maxPrice", maxPrice);
        model.addAttribute("material", material);
        model.addAttribute("category", category);
        model.addAttribute("sortBy", sortBy);
        return "catalogue-page";
    }
}