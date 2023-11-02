package dev.me.price.repositories;

import dev.me.price.entities.PercentageDiscount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PercentageDiscountRepository extends JpaRepository<PercentageDiscount, UUID> {
}
