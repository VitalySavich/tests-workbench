package by.delaidelo.tests.testworks.mvc.controllers;

import by.delaidelo.tests.testworks.dao.WarehouseItemTypeRepository;
import by.delaidelo.tests.testworks.dao.WarehouseRepositry;
import by.delaidelo.tests.testworks.domain.Warehouse;
import by.delaidelo.tests.testworks.domain.WarehouseItemType;
import by.delaidelo.tests.testworks.domain.WarehouseOperation;
import by.delaidelo.tests.testworks.dto.WarehouseItemDto;
import by.delaidelo.tests.testworks.dto.WarehouseOperationDto;
import by.delaidelo.tests.testworks.enums.OperationManagementDocumentType;
import by.delaidelo.tests.testworks.enums.WarehouseOperationType;
import by.delaidelo.tests.testworks.services.warehouse.WarehouseOperationService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/warehouse-operations")
@CrossOrigin("*")
public class WarehouseOperationController {
    private final WarehouseOperationService service;
    private final WarehouseRepositry warehouseRepositry;
    private final WarehouseItemTypeRepository warehouseItemTypeRepository;

    public WarehouseOperationController(WarehouseOperationService service, WarehouseRepositry warehouseRepositry, WarehouseItemTypeRepository warehouseItemTypeRepository) {
        this.service = service;
        this.warehouseRepositry = warehouseRepositry;
        this.warehouseItemTypeRepository = warehouseItemTypeRepository;
    }

    @GetMapping
    public List<WarehouseOperationDto> findAll() {
        return service.findAll();
    }

    @PostMapping("/create-operation")
    public ResponseEntity<Long> createOperation(@RequestBody @Valid WarehouseItemDto dto, @RequestParam Long incomingDocumentId) {

        WarehouseOperation operation = new WarehouseOperation();
        Optional<Warehouse> warehouse = warehouseRepositry.findById(1L);
        warehouse.ifPresent(operation::setWarehouse);

        Optional<WarehouseItemType> warehouseItemType = warehouseItemTypeRepository.findById(dto.getWarehouseItemType().id());
        warehouseItemType.ifPresent(operation::setItemType);

        operation.setOperationDate(LocalDate.now());

        operation.setType(WarehouseOperationType.INCOMING);
        operation.setOwnerDocType(OperationManagementDocumentType.WAREHOUSE_INCOMING_DOCUMENT);
        operation.setOwnerDocId(incomingDocumentId);
        operation.setQuantity(dto.getQuantity());
        operation.setPrice(dto.getPrice());

        final var id = service.create(operation);

        return ResponseEntity.ok(id);
    }
}
