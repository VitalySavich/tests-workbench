package by.delaidelo.tests.testworks.mappers;

import by.delaidelo.tests.testworks.domain.Result;
import by.delaidelo.tests.testworks.dto.ResultDTO;
import org.mapstruct.Mapper;

@Mapper
public interface ResultMapper extends MappableEntity<Result, ResultDTO> {
}
