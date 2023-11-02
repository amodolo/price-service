package dev.me.price.controllers;

import dev.me.price.models.ProductPrice;
import dev.me.price.services.PriceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/price")
public class PriceController implements PriceApi {
    private final PriceService service;

    @Override
    @GetMapping(value = "/{productId}", produces = APPLICATION_JSON_VALUE)
    public ProductPrice getPrice(@PathVariable UUID productId,  @RequestParam(value = "quantity", required = false, defaultValue = "1") int quantity) {
        return service.getPrice(productId, quantity)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "The specified product was not found"));
    }
}
