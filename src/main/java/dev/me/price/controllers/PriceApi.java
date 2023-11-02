package dev.me.price.controllers;

import dev.me.price.models.ProductPrice;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

/**
 *
 * Interface for {@link org.springframework.web.bind.annotation.RestController}s for retrieve the product prices.
 */
@Validated
@Tag(name = "Price", description = "APIs to retrieve the product prices")
public interface PriceApi {

    /**
     * Returns the final price of the given product considering the amount of items added in the order.
     * @param productId product identified
     * @param quantity amount of products added in the order
     * @return instance containing both stock and final prices
     * @throws ResponseStatusException if the specified discount was not found
     */
    @Operation(
            summary = "Get the product price",
            description = "Returns the final price of the given product considering the amount of items added in the order."
    )
    @ApiResponse(responseCode = "200", description = "Successfully retrieve the product price ")
    @ApiResponse(responseCode = "404", description = "The specified discount was not found", content = @Content(schema = @Schema()))
    @Tag(name = "Price")
    ProductPrice getPrice(
            @Parameter(name = "productId", description = "product identifier", required = true) UUID productId,
            @Parameter(name = "quantity", description = "amount of products added in the order") @Positive @Valid int quantity
    );
}
