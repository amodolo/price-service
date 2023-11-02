package dev.me.price.services;

import dev.me.price.entities.Discount;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface DiscountService<T extends Discount<?>> {
    List<T> findAll();

    Optional<T> findById(UUID uuid);

    UUID create(T entity);

    void updateById(UUID uuid, T entity);

    void deleteById(UUID uuid);
}
