package by.delaidelo.tests.testworks.mvc.controllers;

import by.delaidelo.tests.testworks.dto.SelectListItemDto;
import by.delaidelo.tests.testworks.dto.WarehouseItemTypeDto;
import by.delaidelo.tests.testworks.services.warehouse.WarehouseItemTypeService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/item-types")
@CrossOrigin("*")
public class WarehouseItemTypeController {
    private final WarehouseItemTypeService service;

    public WarehouseItemTypeController(WarehouseItemTypeService service) {
        this.service = service;
    }

    @GetMapping
    public Page<WarehouseItemTypeDto> find(@RequestParam(defaultValue = "", value = "query") String query, Pageable pageable) {
        return service.find(query, pageable);
    }

    /**
     * Return no more than 20 records
     *
     * @param query
     * @return
     */
    @GetMapping("/simple")
    public List<WarehouseItemTypeDto> findSimple(@RequestParam(defaultValue = "") String query) {
        return service.findSimple(query);
    }

    @GetMapping("/{id:\\d+}")
    public WarehouseItemTypeDto findById(@PathVariable("id") Long id) {
        return service.findById(id);
    }

    @PostMapping
    public ResponseEntity<Long> create(@RequestBody @Valid WarehouseItemTypeDto dto) {
        final var id = service.create(dto);
        return ResponseEntity.ok(id);
    }

    @PutMapping("/{id:\\d+}")
    public ResponseEntity<Void> update(@PathVariable("id") Long id, @RequestBody @Valid WarehouseItemTypeDto dto) {
        service.update(id, dto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        service.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
