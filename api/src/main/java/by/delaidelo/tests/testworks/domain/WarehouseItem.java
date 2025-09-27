package by.delaidelo.tests.testworks.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
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
    private BigDecimal sum;

    @ManyToMany(mappedBy = "warehouseItems")
    Set<IncomingDocument> incomingDocuments;
}
