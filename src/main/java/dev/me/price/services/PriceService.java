package dev.me.price.services;

import dev.me.price.models.ProductPrice;

import java.util.Optional;
import java.util.UUID;

public interface PriceService {
    Optional<ProductPrice> getPrice(UUID uuid, int quantity);
}
