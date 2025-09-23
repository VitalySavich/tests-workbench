package by.delaidelo.tests.testworks.services;

import by.delaidelo.tests.testworks.dao.IncomingDocumentRepository;
import by.delaidelo.tests.testworks.dto.IncomingDocumentDto;
import by.delaidelo.tests.testworks.dto.SelectListItemDto;
import by.delaidelo.tests.testworks.mappers.IncomingDocumentEntityMapper;
import by.delaidelo.tests.testworks.mappers.IncomingDocumentMapper;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class IncomingDocumentService {
    private final IncomingDocumentRepository incomingDocumentRepository;
    private final IncomingDocumentMapper incomingDocumentMapper;
    private final IncomingDocumentEntityMapper entityMapper;

    public IncomingDocumentService(IncomingDocumentRepository incomingDocumentRepository, IncomingDocumentMapper incomingDocumentMapper,
                                   IncomingDocumentEntityMapper entityMapper) {
        this.incomingDocumentRepository = incomingDocumentRepository;
        this.incomingDocumentMapper = incomingDocumentMapper;
        this.entityMapper = entityMapper;
    }

    @Transactional(readOnly = true)
    public Page<IncomingDocumentDto> find(Long contractorId, String query, Pageable pageable) {
        return incomingDocumentRepository.findAll(IncomingDocumentRepository.buildSpecification(contractorId, query), pageable)
                .map(incomingDocumentMapper::toDto);
    }

    @Transactional(readOnly = true)
    public List<SelectListItemDto> findSimple(Long contractorId, String query) {
        return incomingDocumentRepository.findAll(IncomingDocumentRepository.buildSpecification(contractorId, query))
                .stream()
                .map(entityMapper::fromIncomingDocument)
                .toList();
    }

    @Transactional
    public Long create(@NotNull IncomingDocumentDto dto) {
        final var incomingDocument = incomingDocumentMapper.fromDto(dto);
        incomingDocumentRepository.save(incomingDocument);
        return incomingDocument.getId();
    }

    @Transactional
    public void update(@NotNull Long incomingDocumentId, @NotNull IncomingDocumentDto dto) {
        final var incomingDocument = incomingDocumentRepository.findById(incomingDocumentId).orElseThrow();
        incomingDocumentMapper.update(incomingDocument, dto);
        incomingDocumentRepository.save(incomingDocument);
    }

    @Transactional
    public void delete(@NotNull Long incomingDocumentId) {
        incomingDocumentRepository.deleteById(incomingDocumentId);
    }

    @Transactional(readOnly = true)
    public IncomingDocumentDto findById(Long id) {
        final var incomingDocument = incomingDocumentRepository.findById(id).orElseThrow();
        return incomingDocumentMapper.toDto(incomingDocument);
    }
}
