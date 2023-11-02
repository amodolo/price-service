package dev.me.price.services;

import dev.me.price.models.ProductPrice;

import java.util.Optional;
import java.util.UUID;

/**
 * Interface for {@link org.springframework.stereotype.Service}s that should implement the logic to calculate
 * the product's price
 */
public interface PriceService {

    /**
     * Returns the final price of the given product considering the amount of items added in the order.
     *
     * @param uuid     product identified
     * @param quantity amount of products added in the order
     * @return Optional of instance containing both stock and final prices, or empty optional if the specified product was not found
     */
    Optional<ProductPrice> getPrice(UUID uuid, int quantity);
}
