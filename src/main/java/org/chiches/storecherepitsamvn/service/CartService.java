package org.chiches.storecherepitsamvn.service;

import org.chiches.storecherepitsamvn.dto.CartDTO;

public interface CartService {
    CartDTO getCartByUsername(String username);
    void addItemToCart(String username, Long tileId, Integer quantity);
    void removeItemFromCart(String username, Long tileId);
    void clearCart(String username);
    void updateItemQuantity(String name, Long tileId, Integer quantity);
}
