package by.delaidelo.tests.testworks.mappers;

import by.delaidelo.tests.testworks.domain.IncomingDocument;
import by.delaidelo.tests.testworks.dto.IncomingDocumentDto;
import org.mapstruct.Mapper;

@Mapper(uses = {ContractorEntityMapper.class})
public interface IncomingDocumentMapper extends MappableEntity<IncomingDocument, IncomingDocumentDto> {

}
