package dev.me.price.services;

import dev.me.price.entities.ValueDiscount;
import dev.me.price.repositories.ValueDiscountRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ValueDiscountServiceTest {
    @InjectMocks
    private ValueDiscountService sut;

    @Mock
    private ValueDiscountRepository repository;

    @Test
    void findAll_givenRepositoryWithARecord_returnExpectedRecord() {
        // given
        var id = UUID.randomUUID();
        var productId = UUID.randomUUID();
        var expected = List.of(new ValueDiscount(id, productId, 1, 100F));
        doReturn(expected).when(repository).findAll();
        // when
        var actual = sut.findAll();
        // then
        assertThat(actual).containsExactlyElementsOf(expected);
        verify(repository).findAll();
    }

    @Test
    void findAll_givenEmptyRepository_returnEmptyList() {
        // given
        doReturn(List.of()).when(repository).findAll();
        // when
        var actual = sut.findAll();
        // then
        assertThat(actual).isEmpty();
        verify(repository).findAll();
    }

    @Test
    void findById_givenIdAndRepositoryContainsARecordWithThatId_returnThatRecord() {
        // given
        var id = UUID.randomUUID();
        var productId = UUID.randomUUID();
        var expected = new ValueDiscount(id, productId, 1, 100F);
        doReturn(Optional.of(expected)).when(repository).findById(id);
        // when
        var actual = sut.findById(id);
        // then
        assertThat(actual).isNotEmpty()
                .contains(expected);
        verify(repository).findById(id);
    }

    @Test
    void create_givenValidEntity_saveItAndReturnGeneratedId() {
        // given
        var entity = new ValueDiscount(UUID.randomUUID(), null, 1, 100F);
        doReturn(entity).when(repository).saveAndFlush(entity);
        // when
        var actual = sut.create(entity);
        // then
        assertThat(actual).isEqualTo(entity.getId());
        verify(repository).saveAndFlush(entity);
    }

    @Test
    void updateById_givenIdAndValidEntity_updateEntity() {
        // given
        var id = UUID.randomUUID();
        var entity = new ValueDiscount(null, null, 1, 100F);
        // when
        sut.updateById(id, entity);
        // then
        assertThat(entity).hasFieldOrPropertyWithValue("id", id);
        verify(repository).saveAndFlush(entity);
    }

    @Test
    void deleteById_givenId_RemoveEntityIt() {
        // given
        var expected = UUID.randomUUID();
        // when
        sut.deleteById(expected);
        // then
        verify(repository).deleteById(expected);
    }

    @Test
    void findByProductId_givenId_delegateToRepository() {
        // given
        var expected = UUID.randomUUID();
        // when
        sut.findBestDiscountFor(expected, 1);
        // then
        verify(repository).findByProductIdAndQuantityOrderedByAmountDesc(expected, 1);
    }
}