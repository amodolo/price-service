package dev.me.price.services;

import dev.me.price.entities.PercentageDiscount;
import dev.me.price.entities.Product;
import dev.me.price.entities.ValueDiscount;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
public class DiscountCalculator {
    public float calculateFinalPrice(@NotNull Product product, int quantity, ValueDiscount discount) {
        Objects.requireNonNull(product);
        var price = product.getPrice() * quantity;
        var discountPrice = Optional.ofNullable(discount).map(ValueDiscount::getAmount).orElse(0F);
        return Math.max(price - discountPrice, 0);
    }

    public float calculateFinalPrice(@NotNull Product product, int quantity, PercentageDiscount discount) {
        Objects.requireNonNull(product);
        var price = product.getPrice() * quantity;
        var discountPrice = price * Optional.ofNullable(discount).map(PercentageDiscount::getAmount).orElse(0) / 100;
        return Math.max(price - discountPrice, 0);
    }
}
