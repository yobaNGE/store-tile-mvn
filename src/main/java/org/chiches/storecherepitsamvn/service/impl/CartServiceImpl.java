package org.chiches.storecherepitsamvn.service.impl;

import org.chiches.storecherepitsamvn.dto.CartDTO;
import org.chiches.storecherepitsamvn.dto.CartItemDTO;
import org.chiches.storecherepitsamvn.entity.CartEntity;
import org.chiches.storecherepitsamvn.entity.CartItemEntity;
import org.chiches.storecherepitsamvn.entity.TileEntity;
import org.chiches.storecherepitsamvn.repository.CartRepository;
import org.chiches.storecherepitsamvn.repository.TileRepository;
import org.chiches.storecherepitsamvn.repository.UserRepository;
import org.chiches.storecherepitsamvn.service.CartService;
import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final TileRepository tileRepository;
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;

    public CartServiceImpl(CartRepository cartRepository, TileRepository tileRepository, ModelMapper modelMapper, UserRepository userRepository) {
        this.cartRepository = cartRepository;
        this.tileRepository = tileRepository;
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
    }

    @Override
    @Cacheable(value = "cartByUsername", key = "#username")
    public CartDTO getCartByUsername(String username) {
        CartEntity cart = cartRepository.findByUserUsername(username)
                .orElseGet(() -> createCartForUser(username));
        CartDTO cartDTO = modelMapper.map(cart, CartDTO.class);

        List<CartItemDTO> items = cart.getItems().stream()
                .map(item -> modelMapper.map(item, CartItemDTO.class))
                .collect(Collectors.toList());

        double totalPrice = cart.getItems().stream()
                .mapToDouble(item -> item.getTile().getPrice() * item.getQuantity())
                .sum();

        cartDTO.setItems(items);
        cartDTO.setTotalPrice(totalPrice);

        return cartDTO;
    }

    @Override
    @CacheEvict(value = "cartByUsername", key = "#username")
    public void addItemToCart(String username, Long tileId, Integer quantity) {
        CartEntity cart = cartRepository.findByUserUsername(username).orElseThrow();
        TileEntity tile = tileRepository.findById(tileId).orElseThrow();
        Optional<CartItemEntity> existingItem = cart.getItems().stream()
                .filter(item -> item.getTile().getId().equals(tileId))
                .findFirst();

        if (existingItem.isPresent()) {
            existingItem.get().setQuantity(existingItem.get().getQuantity() + quantity);
        } else {
            CartItemEntity newItem = new CartItemEntity();
            newItem.setCart(cart);
            newItem.setTile(tile);
            newItem.setQuantity(quantity);
            cart.getItems().add(newItem);
        }

        cartRepository.save(cart);
    }

    @Override
    @CacheEvict(value = "cartByUsername", key = "#username")
    public void removeItemFromCart(String username, Long tileId) {
        CartEntity cart = cartRepository.findByUserUsername(username).orElseThrow();
        cart.getItems().removeIf(item -> item.getTile().getId().equals(tileId));
        cartRepository.save(cart);
    }

    @Override
    @CacheEvict(value = "cartByUsername", key = "#username")
    public void clearCart(String username) {
        CartEntity cart = cartRepository.findByUserUsername(username).orElseThrow();
        cart.getItems().clear();
        cartRepository.save(cart);
    }

    private CartEntity createCartForUser(String username) {
        CartEntity newCart = new CartEntity();
        newCart.setUser(userRepository.findByUsername(username).orElseThrow(() ->
                new IllegalArgumentException("User not found: " + username)));
        newCart.setItems(new ArrayList<>());
        return cartRepository.save(newCart);
    }

    @Override
    @CacheEvict(value = "cartByUsername", key = "#username")
    public void updateItemQuantity(String username, Long tileId, Integer quantity) {
        CartEntity cart = cartRepository.findByUserUsername(username).orElseThrow();
        cart.getItems().stream()
                .filter(item -> item.getTile().getId().equals(tileId))
                .findFirst()
                .ifPresent(item -> item.setQuantity(quantity));
        cartRepository.save(cart);
    }
}