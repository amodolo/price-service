package dev.me.price.repositories;

import dev.me.price.entities.ValueDiscount;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest()
class ValueDiscountRepositoryTest {
    @Autowired
    private ValueDiscountRepository sut;

    @Test
    void findByProductIdNullOrProductId_havingNoRecords_returnEmptyList() {
        // given
        var uuid = UUID.randomUUID();
        // when
        var actual = sut.findByProductIdAndQuantityOrderedByAmountDesc(uuid, 1);
        // then
        assertThat(actual).isEmpty();
    }

    @Test
    void findByProductIdNullOrProductId_havingOneRecordMatching_returnListWithExpectedRecord() {
        // given
        var productId = UUID.fromString("a670eb1d-4cbd-4a9f-aecc-42e5ab42ce46");
        var expected = new ValueDiscount(null, productId, 1, 10F);
        sut.saveAndFlush(expected);
        // when
        var actual = sut.findByProductIdAndQuantityOrderedByAmountDesc(productId, 1);
        // then
        assertThat(actual).isNotEmpty();
        assertThat(actual.get(0)).hasFieldOrPropertyWithValue("productId", productId)
                .hasFieldOrPropertyWithValue("quantity", 1)
                .hasFieldOrPropertyWithValue("amount", 10F);
    }

    @Test
    void findByProductIdNullOrProductId_havingOneGenericRecordMatching_returnListWithExpectedRecord() {
        // given
        var productId = UUID.fromString("a670eb1d-4cbd-4a9f-aecc-42e5ab42ce46");
        var expected = new ValueDiscount(null, null, 1, 10F);
        sut.saveAndFlush(expected);
        // when
        var actual = sut.findByProductIdAndQuantityOrderedByAmountDesc(productId, 1);
        // then
        assertThat(actual).isNotEmpty();
        assertThat(actual.get(0))
                .hasFieldOrPropertyWithValue("productId", null)
                .hasFieldOrPropertyWithValue("quantity", 1)
                .hasFieldOrPropertyWithValue("amount", 10F);
    }

    @Test
    void findByProductIdNullOrProductId_havingMoreThanOneGenericRecordMatching_returnListWithExpectedRecord() {
        // given
        var productId = UUID.fromString("a670eb1d-4cbd-4a9f-aecc-42e5ab42ce46");
        var expected1 = new ValueDiscount(null, null, 1, 10F);
        var expected2 = new ValueDiscount(null, null, 2, 20F);
        sut.saveAllAndFlush(List.of(expected1, expected2));
        // when
        var actual = sut.findByProductIdAndQuantityOrderedByAmountDesc(productId, 2);
        // then
        assertThat(actual).hasSize(2);
        assertThat(actual.get(0))
                .hasFieldOrPropertyWithValue("productId", null)
                .hasFieldOrPropertyWithValue("quantity", 2)
                .hasFieldOrPropertyWithValue("amount", 20F);
        assertThat(actual.get(1))
                .hasFieldOrPropertyWithValue("productId", null)
                .hasFieldOrPropertyWithValue("quantity", 1)
                .hasFieldOrPropertyWithValue("amount", 10F);
    }

    @Test
    void findByProductIdNullOrProductId_havingOneSpecificRecordNotMatching_returnListWithExpectedRecord() {
        // given
        var productId = UUID.fromString("a670eb1d-4cbd-4a9f-aecc-42e5ab42ce46");
        var expected = new ValueDiscount(null, productId, 1, 10F);
        sut.saveAndFlush(expected);
        // when
        var actual = sut.findByProductIdAndQuantityOrderedByAmountDesc(UUID.randomUUID(), 1);
        // then
        assertThat(actual).isEmpty();
    }
}