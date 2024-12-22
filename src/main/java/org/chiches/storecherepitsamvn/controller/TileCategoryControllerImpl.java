package org.chiches.storecherepitsamvn.controller;

import org.chiches.storecherepitsacontracs.controllers.TileCategoryController;
import org.chiches.storecherepitsacontracs.dto.tilecategory.TileCategoryForm;
import org.chiches.storecherepitsamvn.dto.TileCategoryDTO;
import org.chiches.storecherepitsamvn.service.TileCategoryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class TileCategoryControllerImpl implements TileCategoryController {

    private final TileCategoryService tileCategoryService;

    public TileCategoryControllerImpl(TileCategoryService tileCategoryService) {
        this.tileCategoryService = tileCategoryService;
    }

    @Override
    public String getAll(Model model) {
        List<TileCategoryDTO> categories = tileCategoryService.getAllCategories();
        model.addAttribute("categories", categories);
        return "tile-categories/tile-category-list-page.html";
    }

    @Override
    public String create(Model model) {
        model.addAttribute("tileCategoryForm", new TileCategoryForm());
        return "tile-categories/tile-category-create-page.html";
    }

    @Override
    public String create(TileCategoryForm tileCategoryForm, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "tile-categories/tile-category-create-page.html";
        }
        tileCategoryService.createCategory(tileCategoryForm);
        return "redirect:/tile-categories";
    }

    @Override
    public String update(Long id, Model model) {
        TileCategoryDTO categoryDTO = tileCategoryService.getCategoryById(id);

        TileCategoryForm form = new TileCategoryForm();
        form.setName(categoryDTO.getName());

        model.addAttribute("tileCategoryForm", form);
        model.addAttribute("categoryId", id);
        return "tile-categories/tile-category-update-page.html";
    }

    @Override
    public String update(@RequestParam("id") Long id, @ModelAttribute("tileCategoryForm") TileCategoryForm tileCategoryForm, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("categoryId", id);
            return "tile-categories/tile-category-update-page.html";
        }
        tileCategoryService.updateCategory(id, tileCategoryForm);
        return "redirect:/tile-categories";
    }

}