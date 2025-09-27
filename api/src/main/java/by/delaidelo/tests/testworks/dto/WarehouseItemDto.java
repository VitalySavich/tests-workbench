package by.delaidelo.tests.testworks.dto;

import by.delaidelo.tests.testworks.enums.WarehouseItemGroup;
import by.delaidelo.tests.testworks.enums.WarehouseItemSubgroup;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class WarehouseItemDto {
    private Long id;
    @NotNull
    @Valid
    private SelectListItemDto warehouseItemType;
    private BigDecimal quantity;
    private BigDecimal price;
    private BigDecimal sum;
}
