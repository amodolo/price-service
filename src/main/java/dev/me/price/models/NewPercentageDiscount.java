package dev.me.price.models;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

import java.util.UUID;

public record NewPercentageDiscount(UUID productId, @Min(1) int quantity, @Min(1) @Max(100) int percentage) {
}
