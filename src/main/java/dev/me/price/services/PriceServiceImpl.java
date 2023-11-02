package dev.me.price.services;

import dev.me.price.entities.PercentageDiscount;
import dev.me.price.entities.Product;
import dev.me.price.entities.ValueDiscount;
import dev.me.price.models.ProductPrice;
import dev.me.price.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PriceServiceImpl implements PriceService {
    private final ProductRepository productRepository;
    private final ValueDiscountService valueDiscountService;
    private final PercentageDiscountService percentageDiscountService;
    private final DiscountCalculator calculator;

    @Override
    public Optional<ProductPrice> getPrice(UUID uuid, int quantity) {
        return productRepository.findById(uuid)
                .map(product -> applyDiscount(product, quantity));
    }

    private ProductPrice applyDiscount(Product product, int quantity) {
        var finalPrice = computeFinalPrice(product, quantity);
        return new ProductPrice(product.getPrice(), finalPrice);
    }

    private float computeFinalPrice(Product product, int quantity) {
        var valueDiscount = findBestValueDiscountFor(product, quantity);
        var percentageDiscount = findBestPercentageDiscountFor(product, quantity);

        var finalPrice1 = calculator.calculateFinalPrice(product, quantity, valueDiscount);
        var finalPrice2 = calculator.calculateFinalPrice(product, quantity, percentageDiscount);
        return Math.min(finalPrice1, finalPrice2);
    }

    private ValueDiscount findBestValueDiscountFor(Product product, int quantity) {
        return valueDiscountService.findBestDiscountFor(product.getId(), quantity).orElse(null);
    }

    private PercentageDiscount findBestPercentageDiscountFor(Product product, int quantity) {
        return percentageDiscountService.findBestDiscountFor(product.getId(), quantity).orElse(null);
    }
}
