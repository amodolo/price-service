package dev.me.price.services;

import dev.me.price.entities.ValueDiscount;
import dev.me.price.repositories.ValueDiscountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ValueDiscountService implements DiscountService<ValueDiscount> {
    private final ValueDiscountRepository repository;

    @Override
    public List<ValueDiscount> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<ValueDiscount> findById(UUID uuid) {
        return repository.findById(uuid);
    }

    @Override
    public UUID create(ValueDiscount entity) {
        var created = repository.saveAndFlush(entity);
        return created.getId();
    }

    @Override
    public void updateById(UUID uuid, ValueDiscount entity) {
        entity.setId(uuid);
        repository.saveAndFlush(entity);
    }

    @Override
    public void deleteById(UUID uuid) {
        repository.deleteById(uuid);
    }

    @Override
    public Optional<ValueDiscount> findBestDiscountFor(UUID productId, int quantity) {
        return repository.findByProductIdAndQuantityOrderedByAmountDesc(productId, quantity)
                .stream()
                .findFirst();
    }
}
