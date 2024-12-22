package org.chiches.storecherepitsamvn.repository;

import org.chiches.storecherepitsamvn.entity.TileEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TileRepository extends BaseRepository<TileEntity,Long> {

    List<TileEntity> findByCategoryId(Long id);

    @Query("SELECT t FROM TileEntity t LEFT JOIN t.orderItemEntities oi GROUP BY t ORDER BY COALESCE(SUM(oi.quantity), 0) DESC")
    Page<TileEntity> findMostPurchasedTiles(Pageable pageable);

    @Query("SELECT DISTINCT t.material FROM TileEntity t")
    List<String> findDistinctMaterials();

    @Query("""
    SELECT t FROM TileEntity t
    WHERE (:minPrice IS NULL OR t.price >= :minPrice)
      AND (:maxPrice IS NULL OR t.price <= :maxPrice)
      AND (:material IS NULL OR t.material = :material)
""")
    Page<TileEntity> findTilesWithFilters(Double minPrice, Double maxPrice, String material, Pageable pageable);


    @Query("""
    SELECT COUNT(t) FROM TileEntity t
    WHERE (:minPrice IS NULL OR t.price >= :minPrice)
      AND (:maxPrice IS NULL OR t.price <= :maxPrice)
      AND (:material IS NULL OR t.material = :material)
""")
    int countTilesWithFilters(Double minPrice, Double maxPrice, String material);

    @Query("""
        SELECT t FROM TileEntity t
        WHERE (:minPrice IS NULL OR t.price >= :minPrice)
          AND (:maxPrice IS NULL OR t.price <= :maxPrice)
          AND (:material IS NULL OR t.material = :material)
          AND (:category IS NULL OR t.category.id = :category)
    """)
    Page<TileEntity> findTilesWithFiltersAndCategory(
            Double minPrice,
            Double maxPrice,
            String material,
            Long category,
            Pageable pageable);
}
