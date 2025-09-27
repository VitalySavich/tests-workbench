package by.delaidelo.tests.testworks.services.warehouse;

import by.delaidelo.tests.testworks.dao.WarehouseItemRepository;
import by.delaidelo.tests.testworks.dto.WarehouseItemDto;
import by.delaidelo.tests.testworks.mappers.WarehouseItemMapper;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class WarehouseItemService {
    private final WarehouseItemRepository repository;
    private final WarehouseItemMapper mapper;

    public WarehouseItemService(WarehouseItemRepository repository, WarehouseItemMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Transactional(readOnly = true)
    public WarehouseItemDto findById(@NotNull Long id) {
        return repository.findById(id)
                .map(mapper::toDto)
                .orElseThrow(EntityNotFoundException::new);
    }

    @Transactional(readOnly = true)
    public Page<WarehouseItemDto> find(String query, Pageable pageable) {
        return repository.findAll(pageable)
                .map(mapper::toDto);
    }

    @Transactional
    public Long create(@NotNull WarehouseItemDto dto) {
        final var item = mapper.fromDto(dto);
        repository.save(item);
        return item.getId();
    }

    @Transactional
    public void update(@NotNull Long id, @NotNull WarehouseItemDto dto) {
        final var item = repository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
        mapper.update(item, dto);
        repository.save(item);
    }

    @Transactional
    public void deleteById(@NotNull Long id) {
        repository.deleteById(id);
    }
}
