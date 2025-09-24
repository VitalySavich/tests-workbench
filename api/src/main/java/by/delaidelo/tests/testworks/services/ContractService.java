package by.delaidelo.tests.testworks.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import by.delaidelo.tests.testworks.dao.ContractRepository;
import by.delaidelo.tests.testworks.dto.ContractDto;
import by.delaidelo.tests.testworks.dto.SelectListItemDto;
import by.delaidelo.tests.testworks.mappers.ContractEntityMapper;
import by.delaidelo.tests.testworks.mappers.ContractMapper;
import jakarta.validation.constraints.NotNull;

@Service
public class ContractService {
    private final ContractRepository contractRepository;
    private final ContractMapper contractMapper;
    private final ContractEntityMapper entityMapper;

    public ContractService(ContractRepository contractRepository, ContractMapper contractMapper,
            ContractEntityMapper entityMapper) {
        this.contractRepository = contractRepository;
        this.contractMapper = contractMapper;
        this.entityMapper = entityMapper;
    }

    @Transactional(readOnly = true)
    public Page<ContractDto> find(Long contractorId, String query, Pageable pageable) {
        Page<ContractDto> contracts = contractRepository.findAll(ContractRepository.buildSpecification(contractorId, query), pageable)
                .map(contractMapper::toDto);
        return contractRepository.findAll(ContractRepository.buildSpecification(contractorId, query), pageable)
                .map(contractMapper::toDto);
    }

    @Transactional(readOnly = true)
    public List<SelectListItemDto> findSimple(Long contractorId, String query) {
        return contractRepository.findAll(ContractRepository.buildSpecification(contractorId, query))
                .stream()
                .map(entityMapper::fromContract)
                .toList();
    }

    @Transactional
    public Long create(@NotNull ContractDto dto) {
        final var contract = contractMapper.fromDto(dto);
        contractRepository.save(contract);
        return contract.getId();
    }

    @Transactional
    public void update(@NotNull Long contractId, @NotNull ContractDto dto) {
        final var contract = contractRepository.findById(contractId).orElseThrow();
        contractMapper.update(contract, dto);
        contractRepository.save(contract);
    }

    @Transactional
    public void delete(@NotNull Long contractId) {
        contractRepository.deleteById(contractId);
    }

    @Transactional(readOnly = true)
    public ContractDto findById(Long id) {
        final var contract = contractRepository.findById(id).orElseThrow();
        return contractMapper.toDto(contract);
    }
}
