package org.chiches.storecherepitsamvn.repository;

import org.chiches.storecherepitsamvn.entity.CartEntity;

import java.util.Optional;

public interface CartRepository extends BaseRepository<CartEntity, Long> {
    Optional<CartEntity> findByUserUsername(String username);
}
