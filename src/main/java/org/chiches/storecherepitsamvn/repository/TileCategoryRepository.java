package org.chiches.storecherepitsamvn.repository;

import org.chiches.storecherepitsamvn.entity.TileCategoryEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

public interface TileCategoryRepository extends BaseRepository<TileCategoryEntity, Long> {
    @Query("SELECT c FROM TileCategoryEntity c LEFT JOIN c.tiles t LEFT JOIN t.orderItemEntities oi GROUP BY c ORDER BY COALESCE(SUM(oi.quantity), 0) DESC")
    Page<TileCategoryEntity> findTopCategoriesByOrders(Pageable pageable);

}
