package by.delaidelo.tests.testworks.mappers;

import by.delaidelo.tests.testworks.dao.WarehouseItemTypeRepository;
import by.delaidelo.tests.testworks.domain.WarehouseItemType;
import by.delaidelo.tests.testworks.dto.SelectListItemDto;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

@Component
public class WarehouseItemTypeEntityMapper {

    private final WarehouseItemTypeRepository warehouseItemTypeRepository;

    public WarehouseItemTypeEntityMapper(WarehouseItemTypeRepository warehouseItemTypeRepository) {
        this.warehouseItemTypeRepository = warehouseItemTypeRepository;
    }

    public SelectListItemDto fromWarehouseItemType(WarehouseItemType warehouseItemType) {
        if (Objects.isNull(warehouseItemType))
            return null;
        return new SelectListItemDto(warehouseItemType.getId(), warehouseItemType.getTitle());
    }

    public WarehouseItemType toWarehouseItemType(SelectListItemDto dto) {
        return Optional.ofNullable(dto)
                .map(SelectListItemDto::id)
                .flatMap(warehouseItemTypeRepository::findById)
                .orElse(null);
    }

}
