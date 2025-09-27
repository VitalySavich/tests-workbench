package by.delaidelo.tests.testworks.mappers;

import by.delaidelo.tests.testworks.domain.WarehouseItem;
import by.delaidelo.tests.testworks.dto.WarehouseItemDto;
import org.mapstruct.Mapper;

@Mapper(uses = {WarehouseItemTypeEntityMapper.class})
public interface WarehouseItemMapper extends MappableEntity<WarehouseItem, WarehouseItemDto> {
    
}