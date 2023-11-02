package dev.me.price.models;

import jakarta.validation.constraints.Positive;

import java.util.UUID;

public record NewValueDiscount(UUID productId, @Positive int quantity, @Positive float amount) {
}
