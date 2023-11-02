package dev.me.price.models;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Positive;

import java.util.UUID;

@Schema(description = "Define a policy that applies a proportional discount to the final price")
public record NewPercentageDiscount(
        @Schema(description = "Product identifier. If specified, the policy may apply only to the specific product")
        UUID productId,

        @Schema(description = "Minimum quantity of items for the discount to be applied", example = "1")
        @Positive int quantity,

        @Schema(description = "Discount amount expressed as a percentage", example = "10")
        @Positive @Max(100) int percentage) {
}
