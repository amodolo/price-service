package dev.me.price.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.server.ResponseStatusException;

import java.net.URL;
import java.util.List;
import java.util.UUID;

/**
 * Interface for {@link org.springframework.web.bind.annotation.RestController}s that should implement
 * a configurable discount policies' endpoint.
 *
 * @param <T> type of the DTO
 * @param <C> type of the DTO used as create/update payload
 */
@Validated
public interface DiscountApi<T, C> {

    /**
     * Retrieve all configured discount policies
     *
     * @return all configured discount policies
     */
    @Operation(summary = "Find all", description = "Retrieve all configured discounts")
    @ApiResponse(responseCode = "200", description = "Success")
    List<T> findAll();

    /**
     * Retrieve a specific, existing discount based on an identifier
     *
     * @param uuid policy identifier
     * @return the discount policy
     * @throws ResponseStatusException if the specified discount was not found
     */
    @Operation(summary = "Find by id", description = "Retrieve a specific, existing discount based on an identifier")
    @ApiResponse(responseCode = "200", description = "Success")
    @ApiResponse(responseCode = "404", description = "The specified discount was not found", content = @Content(schema = @Schema()))
    T findById(UUID uuid);

    /**
     * Create a new discount policy.
     * By specifying the productId in the policy, the discount is applied only to a specific product,while for all
     * others such a discount will not be available.
     * Instead, if no productId is indicated in the policy, the discount will be applicable to any product.
     *
     * @param discount new discount policy
     * @return empty response with HTTP code 201
     */
    @Operation(summary = "Create", description = """
            Create a new discount policy.
            By specifying the productId in the policy, the discount is applied only to a specific product,while for all
            others such a discount will not be available.
            Instead, if no productId is indicated in the policy, the discount will be applicable to any product.
            """)
    @ApiResponse(
            responseCode = "201",
            description = "Policy correctly created",
            headers = @Header(name = "Location", description = "URL to get the promo", schema = @Schema(implementation = URL.class))
    )
    @ApiResponse(responseCode = "400", description = "Invalid request")
    ResponseEntity<Void> create(@Valid C discount);

    /**
     * Update a specific, existing discount policy.
     * By specifying the productId in the policy, the discount is applied only to a specific product,while for all
     * others such a discount will not be available.
     * Instead, if no productId is indicated in the policy, the discount will be applicable to any product.
     *
     * @param uuid     policy identifier
     * @param discount updated discount policy
     * @throws ResponseStatusException if the specified discount was not found
     */
    @Operation(summary = "Update by id", description = """
            Update a specific, existing discount policy.
            By specifying the productId in the policy, the discount is applied only to a specific product,while for all
            others such a discount will not be available.
            Instead, if no productId is indicated in the policy, the discount will be applicable to any product.
            """)
    @ApiResponse(responseCode = "204", description = "Correctly updated")
    @ApiResponse(responseCode = "404", description = "The specified discount was not found")
    void updateById(UUID uuid, @Valid C discount);

    /**
     * Delete a specific, existing discount policy.
     *
     * @param uuid policy identifier
     * @throws ResponseStatusException if the specified discount was not found
     */
    @Operation(summary = "Delete by id", description = "Delete a specific, existing discount policy.")
    @ApiResponse(responseCode = "200", description = "Policy correctly removed")
    @ApiResponse(responseCode = "404", description = "The specified policy was not found")
    void deleteById(UUID uuid);
}
