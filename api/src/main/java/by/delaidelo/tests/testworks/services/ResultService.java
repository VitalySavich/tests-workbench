package by.delaidelo.tests.testworks.services;

import by.delaidelo.tests.testworks.dao.ContractRepository;
import by.delaidelo.tests.testworks.dao.ResultRepositry;
import by.delaidelo.tests.testworks.dto.ContractDto;
import by.delaidelo.tests.testworks.dto.ResultDTO;
import by.delaidelo.tests.testworks.mappers.ResultMapper;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ResultService {
    private final ResultRepositry resultRepositry;
    private final ResultMapper mapper;

    public ResultService(ResultRepositry resultRepositry, ResultMapper mapper) {
        this.resultRepositry = resultRepositry;
        this.mapper = mapper;
    }

    public List<ResultDTO> findAllResults() {
        return resultRepositry.findAll().stream().map(mapper::toDto).toList();
    }

    @Transactional(readOnly = true)
    public Page<ResultDTO> find(String query, Pageable pageable) {
        Page<ResultDTO> results = resultRepositry.findAll(ResultRepositry.buildSpecification(query), pageable)
                .map(mapper::toDto);
        return resultRepositry.findAll(ResultRepositry.buildSpecification(query), pageable)
                .map(mapper::toDto);
    }

    @Transactional
    public Long create(ResultDTO dto) {
        final var r = mapper.fromDto(dto);
        resultRepositry.save(r);
        return r.getId();
    }

    @Transactional
    public void delete(@NotNull Long id) {
        final var result = resultRepositry.findById(id).orElseThrow();
        resultRepositry.delete(result);
    }

    public ResultDTO findById(Long id) {
        return resultRepositry.findById(id)
            .map(mapper::toDto)
            .orElseThrow();
    }
}
