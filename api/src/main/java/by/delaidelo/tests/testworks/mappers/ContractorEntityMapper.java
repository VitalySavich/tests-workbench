package by.delaidelo.tests.testworks.mappers;

import java.util.Objects;
import java.util.Optional;

import org.springframework.stereotype.Component;

import by.delaidelo.tests.testworks.dao.ContractorRepository;
import by.delaidelo.tests.testworks.domain.Contractor;
import by.delaidelo.tests.testworks.dto.SelectListItemDto;

@Component
public class ContractorEntityMapper {

    private final ContractorRepository contractorRepository;

    public ContractorEntityMapper(ContractorRepository contractorRepository) {
        this.contractorRepository = contractorRepository;
    }

    public SelectListItemDto fromContractor(Contractor contractor) {
        if (Objects.isNull(contractor))
            return null;

        SelectListItemDto contractorDto = new SelectListItemDto(contractor.getId(), contractor.getTitle());
        return new SelectListItemDto(contractor.getId(), contractor.getTitle());
    }

    public Contractor toContractor(SelectListItemDto dto) {
        return Optional.ofNullable(dto)
                .map(SelectListItemDto::id)
                .flatMap(contractorRepository::findById)
                .orElse(null);
    }

}
