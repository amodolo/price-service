package dev.me.price.controllers;

import dev.me.price.models.ProductPrice;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.springframework.validation.annotation.Validated;

import java.util.UUID;

@Validated
@Tag(name = "Price", description = "APIs to retrieve the product prices")
public interface PriceApi {

    @Operation(
            summary = "Get the product price",
            description = "Returns the final price of the given product considering the amount of items added in the order."
    )
    @ApiResponse(
            responseCode = "200",
            description = "Successfully retrieve the product price "
    )
    @Tag(name = "Price")
    ProductPrice getPrice(
            @Parameter(name = "productId", description = "product identifier", required = true) UUID productId,
            @Parameter(name = "quantity", description = "amount of products added in the order") @Positive @Valid int quantity
    );
}
