package by.delaidelo.tests.testworks.services.warehouse;

import by.delaidelo.tests.testworks.dto.SelectListItemDto;
import by.delaidelo.tests.testworks.mappers.ContractorEntityMapper;
import by.delaidelo.tests.testworks.mappers.WarehouseEntityMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import by.delaidelo.tests.testworks.dao.WarehouseRepositry;
import by.delaidelo.tests.testworks.dto.WarehouseDto;
import by.delaidelo.tests.testworks.mappers.WarehouseMapper;
import jakarta.validation.constraints.NotNull;

import java.util.List;

@Service
public class WarehousesService {
    private final WarehouseRepositry warehouseRepositry;
    private final WarehouseEntityMapper entityMapper;
    private final WarehouseMapper mapper;
    
    public WarehousesService(WarehouseRepositry warehouseRepositry, WarehouseEntityMapper entityMapper, WarehouseMapper mapper) {
        this.warehouseRepositry = warehouseRepositry;
        this.entityMapper = entityMapper;
        this.mapper = mapper;
    }

    public Page<WarehouseDto> findWarehouses(String query, Pageable pageable) {
        return warehouseRepositry.findByTitleContainingIgnoreCase(query, pageable)
                .map(mapper::toDto);
    }

    @Transactional(readOnly = true)
    public List<SelectListItemDto> findSimple(String query) {
        return warehouseRepositry.findByQuery(query, Pageable.ofSize(20))
                .map(entityMapper::fromWarehouse)
                .toList();
    }

    @Transactional
    public Long create(WarehouseDto dto) {
        final var w = mapper.fromDto(dto);
        warehouseRepositry.save(w);
        return w.getId();
    }

    @Transactional
    public void update(@NotNull Long id, @NotNull WarehouseDto dto) {
        final var warehouse = warehouseRepositry.findById(id).orElseThrow();
        mapper.update(warehouse, dto);
        warehouseRepositry.save(warehouse);
    }

    @Transactional
    public void switchState(@NotNull Long id) {
        final var warehouse = warehouseRepositry.findById(id).orElseThrow();
        warehouse.setEnabled(!warehouse.isEnabled());
    }

    @Transactional
    public void delete(@NotNull Long id) {
        final var warehouse = warehouseRepositry.findById(id).orElseThrow();
        warehouseRepositry.delete(warehouse);
    }

    public WarehouseDto findById(Long id) {
        return warehouseRepositry.findById(id)
            .map(mapper::toDto)
            .orElseThrow();
    }
}
