package org.chiches.storecherepitsamvn.repository;

import org.chiches.storecherepitsamvn.entity.OrderEntity;
import org.chiches.storecherepitsamvn.entity.consts.OrderStatus;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends BaseRepository<OrderEntity, Long> {
    List<OrderEntity> findByUserUsername(String username);
    List<OrderEntity> findByStatus(OrderStatus status);
}
