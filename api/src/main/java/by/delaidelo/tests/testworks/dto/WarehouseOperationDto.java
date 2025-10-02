package by.delaidelo.tests.testworks.dto;

import by.delaidelo.tests.testworks.domain.WarehouseOperation;
import by.delaidelo.tests.testworks.enums.OperationManagementDocumentType;
import by.delaidelo.tests.testworks.enums.WarehouseOperationState;
import by.delaidelo.tests.testworks.enums.WarehouseOperationType;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class WarehouseOperationDto {
    private Long id;

    /**
     * Склад, по которому совершается операция
     */
    @NotNull
    @Valid
    private SelectListItemDto warehouse;

    /**
     * Вид ТМЦ с которым совершается операция
     */
    @NotNull
    @Valid
    private SelectListItemDto itemType;

    /**
     * Вид операции
     */
    @Enumerated(EnumType.STRING)
    private WarehouseOperationType type;

    /**
     * Статус операции, при создании всегда должно быть PREPARED
     */
    @Enumerated(EnumType.STRING)
    private WarehouseOperationState state = WarehouseOperationState.PREPARED;

    /**
     * Тип докумета создавшего операцию
     */
    @Enumerated(EnumType.STRING)
    private OperationManagementDocumentType ownerDocType;

    /**
     * ИД документа создавшего операцию
     */
    private Long ownerDocId;

    private String ownerDocInfo;
    private LocalDate operationDate;

    /**
     * Кол-во едениц указанного ТМЦ
     */
    private BigDecimal quantity = BigDecimal.ZERO;

    /**
     * Стоимость единицы указанного ТМЦ по документу (FIXME: возможно тут лучше указывать с/с)
     * Для распределения затрат, списания стоимости - сумма изменения единицы с/с товара
     */
    private BigDecimal price = BigDecimal.ZERO;

    /**
     * Общая стомость по операции
     */
    private BigDecimal totalSum = BigDecimal.ZERO;

    /**
     * Для исходящих операций в этом поле указывается источник
     */
    private WarehouseOperation source;

    /**
     * Для операций распределения и списания стоимости в этом поле указывается над какой операцией производится действие
     */
    private WarehouseOperation reference;

    private BigDecimal remainingQuantity;

    /**
     * Себестоимость единицы ТМЦ, при создании входящей операции по умолчанию должно ровняться цене
     */
    private BigDecimal calculatedCosts;
}
