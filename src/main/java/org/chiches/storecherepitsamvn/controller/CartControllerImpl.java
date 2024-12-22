package org.chiches.storecherepitsamvn.controller;

import org.chiches.storecherepitsacontracs.controllers.CartController;
import org.chiches.storecherepitsamvn.service.CartService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import java.security.Principal;

@Controller
public class CartControllerImpl implements CartController {
    private static final Logger logger = LoggerFactory.getLogger(CartControllerImpl.class);
    private final CartService cartService;

    public CartControllerImpl(CartService cartService) {
        this.cartService = cartService;
    }

    @Override
    public String viewCart(Principal principal, Model model) {
        logger.info("Viewing cart for user: {}", principal.getName());
        model.addAttribute("cart", cartService.getCartByUsername(principal.getName()));
        return "cart/view";
    }

    @Override
    public String addItem(Principal principal, Long tileId, Integer quantity) {
        logger.info("User {} added item {} with quantity {} to cart.", principal.getName(), tileId, quantity);
        cartService.addItemToCart(principal.getName(), tileId, quantity);
        return "redirect:/catalogue";
    }

    @Override
    public String removeItem(Principal principal, Long tileId) {
        logger.info("User {} removed item {} from cart.", principal.getName(), tileId);
        cartService.removeItemFromCart(principal.getName(), tileId);
        return "redirect:/cart";
    }

    @Override
    public String clearCart(Principal principal) {
        logger.info("User {} cleared their cart.", principal.getName());
        cartService.clearCart(principal.getName());
        return "redirect:/cart";
    }

    @Override
    public String updateItemQuantity(Principal principal, Long tileId, Integer quantity) {
        logger.info("User {} updated item {} to quantity {}.", principal.getName(), tileId, quantity);
        cartService.updateItemQuantity(principal.getName(), tileId, quantity);
        return "redirect:/cart";
    }
}