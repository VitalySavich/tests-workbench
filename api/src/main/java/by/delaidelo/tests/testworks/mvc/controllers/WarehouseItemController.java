package by.delaidelo.tests.testworks.mvc.controllers;

import by.delaidelo.tests.testworks.dto.WarehouseItemDto;
import by.delaidelo.tests.testworks.services.warehouse.WarehouseItemService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/warehouse-items")
@CrossOrigin("*")
public class WarehouseItemController {
    private final WarehouseItemService service;

    public WarehouseItemController(WarehouseItemService service) {
        this.service = service;
    }

    @GetMapping
    public Page<WarehouseItemDto> find(@RequestParam(defaultValue = "", value = "query") String query, Pageable pageable) {
        return service.find(query, pageable);
    }

    @GetMapping("/{id:\\d+}")
    public WarehouseItemDto findById(@PathVariable("id") Long id) {
        return service.findById(id);
    }

    @PostMapping
    public ResponseEntity<Long> create(@RequestBody @Valid WarehouseItemDto dto) {
        final var id = service.create(dto);
        return ResponseEntity.ok(id);
    }

    @PutMapping("/{id:\\d+}")
    public ResponseEntity<Void> update(@PathVariable("id") Long id, @RequestBody @Valid WarehouseItemDto dto) {
        service.update(id, dto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        service.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
