package dev.me.price.services;

import dev.me.price.entities.Discount;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Interface for {@link org.springframework.stereotype.Service}s that should implement the logic to manage
 * the discount policies.
 *
 * @param <T> type discount policy
 */
public interface DiscountService<T extends Discount<?>> {

    /**
     * Retrieve all configured discount policies
     *
     * @return all configured discount policies
     */
    List<T> findAll();

    /**
     * Retrieve a specific, existing discount based on an identifier
     *
     * @param uuid policy identifier
     * @return an optional with the discount policy or empty optional if the specified policy was not found
     */
    Optional<T> findById(UUID uuid);

    /**
     * Create a new discount policy.
     *
     * @param entity new discount policy
     * @return identifier of the created policy
     */
    UUID create(T entity);

    /**
     * Update a specific, existing discount policy.
     *
     * @param uuid   policy identifier
     * @param entity updated discount policy
     */
    void updateById(UUID uuid, T entity);

    /**
     * Delete a specific, existing discount policy.
     *
     * @param uuid policy identifier
     */
    void deleteById(UUID uuid);

    /**
     * Find the best discount available for the passed product take into account the quantity of items added.
     *
     * @param productId the product identifier
     * @param quantity  amount of products added in the order
     * @return Optional containing the discount instance, or empty optional if there aren't any discount available
     */
    Optional<T> findBestDiscountFor(UUID productId, int quantity);
}
