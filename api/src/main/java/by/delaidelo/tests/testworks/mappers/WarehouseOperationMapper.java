package by.delaidelo.tests.testworks.mappers;

import by.delaidelo.tests.testworks.domain.WarehouseOperation;
import by.delaidelo.tests.testworks.dto.WarehouseOperationDto;
import org.mapstruct.Mapper;

@Mapper(uses = {WarehouseEntityMapper.class})
public interface WarehouseOperationMapper extends MappableEntity<WarehouseOperation, WarehouseOperationDto> {

}
