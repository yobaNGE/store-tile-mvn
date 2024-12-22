package org.chiches.storecherepitsamvn.repository;

import org.chiches.storecherepitsamvn.entity.UserEntity;
import org.springframework.boot.autoconfigure.security.SecurityProperties;

import java.util.Optional;

public interface UserRepository extends BaseRepository<UserEntity, Long> {
    Optional<UserEntity> findByUsername(String username);
}
