package by.delaidelo.tests.testworks.mvc.controllers;

import by.delaidelo.tests.testworks.domain.WarehouseItem;
import by.delaidelo.tests.testworks.dto.IncomingDocumentDto;
import by.delaidelo.tests.testworks.dto.SelectListItemDto;
import by.delaidelo.tests.testworks.dto.WarehouseItemDto;
import by.delaidelo.tests.testworks.services.IncomingDocumentService;
import by.delaidelo.tests.testworks.services.warehouse.WarehouseItemService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/incoming-documents")
@CrossOrigin("*")
public class IncomingDocumentController {
    private final IncomingDocumentService service;
    private final WarehouseItemService warehouseItemService;

    public IncomingDocumentController(IncomingDocumentService service, WarehouseItemService warehouseItemService) {
        this.service = service;
        this.warehouseItemService = warehouseItemService;
    }

    @GetMapping
    public Page<IncomingDocumentDto> find(
            @RequestParam(required = false) Long contractorId, @RequestParam(defaultValue = "") String query,
            Pageable pageable) {
        return service.find(contractorId, query, pageable);
    }

    /**
     * Return no more than 20 records
     * 
     * @param query
     * @return
     */
    @GetMapping("/simple")
    public List<SelectListItemDto> findSimple(@RequestParam Long contractorId,
            @RequestParam(defaultValue = "") String query) {
        return service.findSimple(contractorId, query);
    }

    @PostMapping
    public ResponseEntity<Long> create(@RequestBody @Valid IncomingDocumentDto dto) {
        final var id = service.create(dto);
        return ResponseEntity.ok(id);
    }

    @PutMapping("/{id:\\d+}")
    public void update(@PathVariable Long id, @RequestBody IncomingDocumentDto dto) {
        service.update(id, dto);
    }

    @DeleteMapping("/{id:\\d+}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }

    @GetMapping("/{id:\\d+}")
    public IncomingDocumentDto findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @GetMapping("/{id}/warehouse-items")
    public ResponseEntity<List<WarehouseItem>> getAllWarehouseItemsByIncomingDocumentId(@PathVariable(value = "id") Long id) {
        List<WarehouseItem> warehouseItems = warehouseItemService.findAllWarehouseItemsByIncomingDocumentId(id);
        return new ResponseEntity<>(warehouseItems, HttpStatus.OK);
    }


}
