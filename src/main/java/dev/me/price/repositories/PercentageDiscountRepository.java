package dev.me.price.repositories;

import dev.me.price.entities.PercentageDiscount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PercentageDiscountRepository extends JpaRepository<PercentageDiscount, UUID> {
    @Query("""
            select v from PercentageDiscount v
            where (v.productId is null or v.productId = ?1) and v.quantity <= ?2
            order by v.amount DESC""")
    List<PercentageDiscount> findByProductIdAndQuantityOrderedByAmountDesc(UUID productId, int quantity);
}
