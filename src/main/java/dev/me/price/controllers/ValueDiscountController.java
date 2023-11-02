package dev.me.price.controllers;

import dev.me.price.models.NewValueDiscount;
import dev.me.price.models.ValueDiscount;
import dev.me.price.services.ValueDiscountService;
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
@RequestMapping("/v1/value-discount")
@Tag(name = "Discount - by value", description = "APIs to create and configure new discounts policy based on value")
public class ValueDiscountController implements DiscountApi<ValueDiscount, NewValueDiscount> {
    private final ValueDiscountService service;

    @Override
    @GetMapping(produces = APPLICATION_JSON_VALUE)
    @Tag(name = "Discount - by value")
    public List<ValueDiscount> findAll() {
        return service.findAll()
                .stream()
                .map(this::toDTO)
                .toList();
    }

    @Override
    @GetMapping(value = "{uuid}", produces = APPLICATION_JSON_VALUE)
    @Tag(name = "Discount - by value")
    public ValueDiscount findById(@PathVariable("uuid") UUID uuid) {
        return service.findById(uuid)
                .map(this::toDTO)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "The specified discount was not found"));
    }

    @Override
    @PostMapping
    @Tag(name = "Discount - by value")
    public ResponseEntity<Void> create(@Valid @RequestBody NewValueDiscount discount) {
        var uuid = service.create(toEntity(discount));
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(uuid)
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @Override
    @PutMapping("{uuid}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Tag(name = "Discount - by value")
    public void updateById(@PathVariable("uuid") UUID uuid, @Valid @RequestBody NewValueDiscount discount) {
        service.updateById(uuid, toEntity(discount));
    }

    @Override
    @DeleteMapping("{uuid}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Tag(name = "Discount - by value")
    public void deleteById(@PathVariable("uuid") UUID uuid) {
        service.deleteById(uuid);
    }

    private ValueDiscount toDTO(dev.me.price.entities.ValueDiscount entity) {
        return new ValueDiscount(entity.getId(), entity.getProductId(), entity.getQuantity(), entity.getAmount());
    }

    private dev.me.price.entities.ValueDiscount toEntity(NewValueDiscount dto) {
        return new dev.me.price.entities.ValueDiscount(null, dto.productId(), dto.quantity(), dto.amount());
    }
}
