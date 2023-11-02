package dev.me.price.models;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Positive;

public record ProductPrice(
        @Schema(description = "Product stock price")
        @Positive float stockPrice,

        @Schema(description = "Discounted product price")
        @Positive float finalPrice) {
}
