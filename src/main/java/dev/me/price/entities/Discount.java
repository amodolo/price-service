package dev.me.price.entities;

import java.util.UUID;

/**
 * Entity interface describing a discount policy
 * @param <T> discount amount type
 */
public interface Discount<T extends Number> {
    /**
     * @return the policy identifier
     */
    UUID getId();

    /**
     * Set the policy Identifier
     * @param id policy identifier
     */
    void setId(UUID id);

    /**
     * @return the product identifier. If specified, the policy may apply only to the specific product
     */
    UUID getProductId();

    /**
     * @return minimum quantity of items for the discount to be applied
     */
    int getQuantity();

    /**
     * @return discount amount
     */
    T getAmount();

}
