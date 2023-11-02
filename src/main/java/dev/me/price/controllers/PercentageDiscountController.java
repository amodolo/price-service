package dev.me.price.controllers;

import dev.me.price.models.NewPercentageDiscount;
import dev.me.price.models.PercentageDiscount;
import dev.me.price.services.PercentageDiscountService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/percentage-discount")
@Tag(name = "Discount - by percentage", description = "APIs to create and configure new discounts policy based on percentage")
public class PercentageDiscountController implements DiscountApi<PercentageDiscount, NewPercentageDiscount> {
    private final PercentageDiscountService service;

    @Override
    @GetMapping(produces = APPLICATION_JSON_VALUE)
    @Tag(name = "Discount - by percentage")
    public List<PercentageDiscount> findAll() {
        return service.findAll()
                .stream()
                .map(this::toDTO)
                .toList();
    }

    @Override
    @GetMapping(value = "{uuid}", produces = APPLICATION_JSON_VALUE)
    @Tag(name = "Discount - by percentage")
    public PercentageDiscount findById(@PathVariable("uuid") UUID uuid) {
        return service.findById(uuid)
                .map(this::toDTO)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "The specified discount was not found"));
    }

    @Override
    @PostMapping
    @Tag(name = "Discount - by percentage")
    public ResponseEntity<Void> create(@Valid @RequestBody NewPercentageDiscount discount) {
        var uuid = service.create(toEntity(discount));
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{uuid}")
                .buildAndExpand(uuid)
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @Override
    @PutMapping("{uuid}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Tag(name = "Discount - by percentage")
    public void updateById(@PathVariable("uuid") UUID uuid, @Valid @RequestBody NewPercentageDiscount discount) {
        service.updateById(uuid, toEntity(discount));
    }

    @Override
    @DeleteMapping("{uuid}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Tag(name = "Discount - by percentage")
    public void deleteById(@PathVariable("uuid") UUID uuid) {
        service.deleteById(uuid);
    }

    private PercentageDiscount toDTO(dev.me.price.entities.PercentageDiscount entity) {
        return new PercentageDiscount(entity.getId(), entity.getProductId(), entity.getQuantity(), entity.getAmount());
    }

    private dev.me.price.entities.PercentageDiscount toEntity(NewPercentageDiscount dto) {
        return new dev.me.price.entities.PercentageDiscount(null, dto.productId(), dto.quantity(), dto.percentage());
    }
}
