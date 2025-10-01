package by.delaidelo.tests.testworks.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "warehouse_items")
@Getter
@Setter
public class WarehouseItem extends AbstractEntity {
    @ManyToOne
    @JoinColumn
    private WarehouseItemType warehouseItemType;

    @Column
    private BigDecimal quantity;

    @Column
    private BigDecimal price;

    @Column
    private BigDecimal amount;

    @ManyToMany(mappedBy = "warehouseItems")
    @JsonBackReference
    Set<IncomingDocument> incomingDocuments = new HashSet<IncomingDocument>();;
}
