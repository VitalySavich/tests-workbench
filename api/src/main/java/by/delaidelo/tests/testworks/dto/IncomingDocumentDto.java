package by.delaidelo.tests.testworks.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class IncomingDocumentDto {
    private Long id;
    @NotNull
    @Valid
    private SelectListItemDto contractor;
    private LocalDate incomingDocumentDate;
    private String incomingDocumentNumber;
    @NotNull
    @Valid
    private SelectListItemDto contract;
    @NotNull
    @Valid
    private SelectListItemDto warehouse;
    private String description;
}
