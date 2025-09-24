package by.delaidelo.tests.testworks.mappers;

import by.delaidelo.tests.testworks.dao.WarehouseRepositry;
import by.delaidelo.tests.testworks.domain.Warehouse;
import by.delaidelo.tests.testworks.dto.SelectListItemDto;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

@Component
public class WarehouseEntityMapper {

    private final WarehouseRepositry warehouseRepositry;

    public WarehouseEntityMapper(WarehouseRepositry warehouseRepositry) {
        this.warehouseRepositry = warehouseRepositry;
    }

    public SelectListItemDto fromWarehouse(Warehouse warehouse) {
        if (Objects.isNull(warehouse))
            return null;
        return new SelectListItemDto(warehouse.getId(), warehouse.getTitle());
    }

    public Warehouse toContractor(SelectListItemDto dto) {
        return Optional.ofNullable(dto)
                .map(SelectListItemDto::id)
                .flatMap(warehouseRepositry::findById)
                .orElse(null);
    }

}
