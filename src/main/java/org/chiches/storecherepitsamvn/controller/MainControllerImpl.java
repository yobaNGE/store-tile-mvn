package org.chiches.storecherepitsamvn.controller;

import org.chiches.storecherepitsacontracs.controllers.MainController;
import org.chiches.storecherepitsamvn.dto.TileCategoryDTO;
import org.chiches.storecherepitsamvn.dto.TileDTO;
import org.chiches.storecherepitsamvn.service.TileCategoryService;
import org.chiches.storecherepitsamvn.service.TileService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import java.util.List;

@Controller
public class MainControllerImpl implements MainController {
    private final TileCategoryService tileCategoryService;
    private final TileService  tileService;
    public MainControllerImpl(TileCategoryService tileCategoryService, TileService tileService) {
        this.tileCategoryService = tileCategoryService;
        this.tileService = tileService;
    }

    @Override
    public String getMainPage(Model model) {
        List<TileCategoryDTO> popularCategories = tileCategoryService.findTopCategoriesByOrders(3);
        model.addAttribute("popularCategories", popularCategories);

        List<TileDTO> mostPurchasedTiles = tileService.findMostPurchasedTiles(6);
        model.addAttribute("mostPurchasedTiles", mostPurchasedTiles);
        return "main-page";
    }
}
