package dev.me.price.repositories;

import dev.me.price.entities.ValueDiscount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ValueDiscountRepository extends JpaRepository<ValueDiscount, UUID> {
}
