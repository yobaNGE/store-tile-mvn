package org.chiches.storecherepitsamvn.repository;

import org.chiches.storecherepitsamvn.entity.RoleEntity;
import org.chiches.storecherepitsamvn.entity.consts.Roles;

import java.util.Optional;

public interface RoleRepository extends BaseRepository<RoleEntity, Long> {
    Optional<RoleEntity> findByRole(Roles role);
    boolean existsByRole(Roles role);
}
