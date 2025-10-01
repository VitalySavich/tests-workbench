package by.delaidelo.tests.testworks.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class WarehouseItemDto {
    private Long id;
    @NotNull
    @Valid
    private SelectListItemDto warehouseItemType;
    private BigDecimal quantity;
    private BigDecimal price;
    private BigDecimal amount;
    private List<Long> incomingDocumentIds;
}
