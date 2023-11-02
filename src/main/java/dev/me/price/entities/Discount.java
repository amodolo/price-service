package dev.me.price.entities;

import java.util.UUID;

public interface Discount<T extends Number> {
    UUID getId();
    void setId(UUID id);
    UUID getProductId();
    int getQuantity();
    T getAmount();

}
