package dev.me.price.models;

import java.util.UUID;

public record ValueDiscount(UUID id, UUID productId, int quantity, float amount) {
}
