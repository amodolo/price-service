package dev.me.price.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;

import java.net.URL;
import java.util.List;
import java.util.UUID;

@Validated
public interface DiscountApi<T, C> {

    @Operation(summary = "Find all", description = "Retrieve all configured discounts")
    @ApiResponse(responseCode = "200", description = "Success")
    List<T> findAll();

    @Operation(summary = "Find by id", description = "Retrieve a specific, existing discount based on an identifier")
    @ApiResponse(responseCode = "200", description = "Success")
    @ApiResponse(responseCode = "404", description = "The specified discount was not found", content = @Content(schema = @Schema()))
    T findById(UUID uuid);

    @Operation(summary = "Create", description = "Create a new discount policy")
    @ApiResponse(
            responseCode = "201",
            description = "Promo correctly created",
            headers = @Header(name = "Location", description = "URL to get the promo", schema = @Schema(implementation = URL.class))
    )
    @ApiResponse(responseCode = "400", description = "Invalid request")
    ResponseEntity<Void> create(@Valid C discount);

    @Operation(summary = "Update by id", description = "Update a specific, existing discount policy.")
    @ApiResponse(responseCode = "204", description = "Correctly updated")
    @ApiResponse(responseCode = "404", description = "The specified discount was not found")
    void updateById(UUID uuid, @Valid C discount);

    @Operation(summary = "Delete by id", description = "Delete a specific, existing discount policy.")
    @ApiResponse(responseCode = "200", description = "Policy correctly removed")
    @ApiResponse(responseCode = "404", description = "The specified policy was not found")
    void deleteById(UUID uuid);
}
