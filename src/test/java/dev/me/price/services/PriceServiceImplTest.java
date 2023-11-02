package dev.me.price.services;


import dev.me.price.entities.PercentageDiscount;
import dev.me.price.entities.Product;
import dev.me.price.entities.ValueDiscount;
import dev.me.price.models.ProductPrice;
import dev.me.price.repositories.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class PriceServiceImplTest {
    @InjectMocks
    private PriceServiceImpl sut;
    @Mock
    private ProductRepository productRepository;
    @Mock
    private ValueDiscountService valueDiscountService;
    @Mock
    private PercentageDiscountService percentageDiscountService;
    @Spy
    @SuppressWarnings("unused")
    private DiscountCalculator discountCalculator;

    @Test
    void getPrice_givenUnknownProductId_returnEmptyOptional() {
        // given
        var uuid = UUID.randomUUID();
        // when
        var actual = sut.getPrice(uuid, 1);
        // then
        assertThat(actual).isEmpty();
    }

    @Test
    void getPrice_givenProductIdHavingNoDiscountPolicy_returnStockPriceEqualsToFinalPrice() {
        // given
        var uuid = UUID.randomUUID();
        var product = new Product(uuid, "", 100);
        doReturn(Optional.of(product)).when(productRepository).findById(uuid);
        var expected = new ProductPrice(product.getPrice(), product.getPrice());
        // when
        var actual = sut.getPrice(uuid, 1);
        // then
        assertThat(actual).isNotEmpty()
                .contains(expected);
    }

    @ParameterizedTest
    @MethodSource("discountCases")
    void getPrice_givenStockPriceAndQuantityAndDiscounts_returnCorrectDiscountedPrice(int stockPrice, int quantity, float valueDiscountValue, int valueDiscountQuantity, int percentageDiscountValue, int percentageDiscountQuantity, int finalPrice) {
        // given
        var uuid = UUID.randomUUID();
        var product = new Product(uuid, "", stockPrice);
        doReturn(Optional.of(product)).when(productRepository).findById(uuid);
        var valueDiscount = new ValueDiscount(UUID.randomUUID(), null, valueDiscountQuantity, valueDiscountValue);
        doReturn(Optional.of(valueDiscount)).when(valueDiscountService).findBestDiscountFor(uuid, quantity);
        var percentageDiscount = new PercentageDiscount(UUID.randomUUID(), null, percentageDiscountQuantity, percentageDiscountValue);
        doReturn(Optional.of(percentageDiscount)).when(percentageDiscountService).findBestDiscountFor(uuid, quantity);
        var expected = new ProductPrice(stockPrice, finalPrice);
        // when
        var actual = sut.getPrice(uuid, quantity);
        // then
        assertThat(actual).isNotEmpty()
                .contains(expected);
    }

    private static Stream<Arguments> discountCases() {
        // stock price, order quantity, amount discount amount, amount discount min quantity, perc. discount amount, perc. discount min quantity
        return Stream.of(
                Arguments.of(200, 1, 0F, 1, 0, 1, 200),
                Arguments.of(200, 1, 50F, 1, 10, 1, 150),
                Arguments.of(200, 1, 10F, 1, 10, 1, 180),
                Arguments.of(200, 1, 10F, 1, 5, 1, 190),
                Arguments.of(200, 2, 0F, 1, 0, 1, 400),
                Arguments.of(200, 2, 10F, 1, 0, 1, 390),
                Arguments.of(200, 2, 0F, 1, 10, 1, 360),
                Arguments.of(200, 2, 50F, 1, 50, 1, 200),
                Arguments.of(200, 2, 50F, 1, 50, 2, 200),
                Arguments.of(100, 1, 110F, 1, 0, 1, 0),
                Arguments.of(100, 1, 0F, 1, 200, 1, 0)
        );
    }


    @Test
    void getPrice_givenProductIdHavingValueDiscountPolicyForAnotherProduct_returnStockPrice() {
        // given
        var uuid = UUID.randomUUID();
        var product = new Product(uuid, "", 100);
        doReturn(Optional.of(product)).when(productRepository).findById(uuid);
        doReturn(Optional.empty()).when(valueDiscountService).findBestDiscountFor(uuid, 1);
        var expected = new ProductPrice(100, 100);
        // when
        var actual = sut.getPrice(uuid, 1);
        // then
        assertThat(actual).isNotEmpty()
                .contains(expected);
        verify(productRepository).findById(uuid);
        verify(valueDiscountService).findBestDiscountFor(uuid, 1);
    }

    @Test
    void getPrice_givenProductIdHavingPercentageDiscountPolicyForAnotherProduct_returnStockPrice() {
        // given
        var uuid = UUID.randomUUID();
        var product = new Product(uuid, "", 200);
        doReturn(Optional.of(product)).when(productRepository).findById(uuid);
        doReturn(Optional.empty()).when(percentageDiscountService).findBestDiscountFor(uuid, 1);
        var expected = new ProductPrice(200, 200);
        // when
        var actual = sut.getPrice(uuid, 1);
        // then
        assertThat(actual).isNotEmpty()
                .contains(expected);
        verify(productRepository).findById(uuid);
        verify(percentageDiscountService).findBestDiscountFor(uuid, 1);
    }

}