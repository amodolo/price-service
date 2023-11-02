package dev.me.price.services;

import dev.me.price.entities.PercentageDiscount;
import dev.me.price.repositories.PercentageDiscountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PercentageDiscountService implements DiscountService<PercentageDiscount> {
    private final PercentageDiscountRepository repository;

    @Override
    public List<PercentageDiscount> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<PercentageDiscount> findById(UUID uuid) {
        return repository.findById(uuid);
    }

    @Override
    public UUID create(PercentageDiscount entity) {
        var created = repository.saveAndFlush(entity);
        return created.getId();
    }

    @Override
    public void updateById(UUID uuid, PercentageDiscount entity) {
        entity.setId(uuid);
        repository.saveAndFlush(entity);
    }

    @Override
    public void deleteById(UUID uuid) {
        repository.deleteById(uuid);
    }
}
