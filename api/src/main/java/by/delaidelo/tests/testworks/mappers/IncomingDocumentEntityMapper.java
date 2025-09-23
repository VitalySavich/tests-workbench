package by.delaidelo.tests.testworks.mappers;

import by.delaidelo.tests.testworks.dao.IncomingDocumentRepository;
import by.delaidelo.tests.testworks.domain.IncomingDocument;
import by.delaidelo.tests.testworks.dto.SelectListItemDto;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.Optional;

@Component
public class IncomingDocumentEntityMapper {
    private final IncomingDocumentRepository incomingDocumentRepository;

    public IncomingDocumentEntityMapper(IncomingDocumentRepository incomingDocumentRepository) {
        this.incomingDocumentRepository = incomingDocumentRepository;
    }

    public SelectListItemDto fromIncomingDocument(IncomingDocument incomingDocument) {
        if (Objects.isNull(incomingDocument))
            return null;
        final var dtd = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        return new SelectListItemDto(incomingDocument.getId(), incomingDocument.getIncomingDocumentNumber() + " от " + dtd.format(incomingDocument.getIncomingDocumentDate()));
    }

    public IncomingDocument toIncomingDocument(SelectListItemDto dto) {
        return Optional.ofNullable(dto)
                .map(SelectListItemDto::id)
                .flatMap(incomingDocumentRepository::findById)
                .orElse(null);
    }
}
