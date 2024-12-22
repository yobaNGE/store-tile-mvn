package org.chiches.storecherepitsamvn.controller;

import org.chiches.storecherepitsacontracs.controllers.TileController;
import org.chiches.storecherepitsacontracs.dto.tile.CreateTileForm;
import org.chiches.storecherepitsacontracs.dto.tile.TileForm;
import org.chiches.storecherepitsacontracs.dto.tile.UpdateTileForm;
import org.chiches.storecherepitsamvn.dto.TileDTO;
import org.chiches.storecherepitsamvn.entity.TileEntity;
import org.chiches.storecherepitsamvn.service.TileCategoryService;
import org.chiches.storecherepitsamvn.service.TileService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import java.util.List;

@Controller
public class TileControllerImpl implements TileController {

    private final TileService tileService;
    private final TileCategoryService tileCategoryService;

    public TileControllerImpl(TileService tileService, TileCategoryService tileCategoryService) {
        this.tileService = tileService;
        this.tileCategoryService = tileCategoryService;
    }

    @Override
    public String create(Model model) {
        model.addAttribute("tileForm", new CreateTileForm());
        model.addAttribute("categories", tileCategoryService.getAllCategories());
        return "tiles/tile-create-page.html";
    }

    @Override
    public String create(CreateTileForm tileForm, BindingResult bindingResult, Model model) {
        System.out.println(tileForm);
        if (bindingResult.hasErrors()) {
            model.addAttribute("categories", tileCategoryService.getAllCategories());
            return "tiles/tile-create-page.html";
        }
        tileService.createTile(tileForm);
        return "redirect:/tiles";
    }

    @Override
    public String getAll(Model model) {
        List<TileDTO> tiles = tileService.findAll();

        model.addAttribute("tiles", tiles);

        return "tiles/tile-list-page.html";
    }

    @Override
    public String update(Long id, Model model) {
        TileEntity tile = tileService.getTile(id);
        UpdateTileForm form = new UpdateTileForm();
        form.setId(tile.getId());
        form.setName(tile.getName());
        form.setPrice(tile.getPrice());
        form.setMaterial(tile.getMaterial());
        form.setCategoryId(tile.getCategory().getId());
        form.setDescription(tile.getDescription());

        model.addAttribute("updateTileForm", form);
        model.addAttribute("categories", tileCategoryService.getAllCategories());
        return "tiles/tile-update-page.html";
    }

    @Override
    public String update(UpdateTileForm updateTileForm, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("categories", tileCategoryService.getAllCategories());
            return "tiles/tile-update-page.html";
        }
        tileService.updateTile(updateTileForm);
        return "redirect:/tiles";
    }
    @Override
    public String getTileDetails(Long id, Model model) {
        TileDTO tile = tileService.getTileById(id);
        model.addAttribute("tile", tile);
        return "tiles/tile-details.html";
    }
}
