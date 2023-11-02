package dev.me.price.models;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Positive;

import java.util.UUID;

@Schema(description = "Define a policy that applies a unit discount to the final price")
public record NewValueDiscount(
        @Schema(description = "Product identifier. If specified, the policy may apply only to the specific product")
        UUID productId,

        @Schema(description = "Minimum quantity of items for the discount to be applied", example = "1")
        @Positive int quantity,

        @Schema(description = "Discount amount expressed as a currency", example = "50")
        @Positive float amount) {
}
