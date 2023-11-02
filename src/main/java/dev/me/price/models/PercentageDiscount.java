package dev.me.price.models;

import java.util.UUID;

public record PercentageDiscount(UUID id, UUID productId, int quantity, int percentage) {
}
