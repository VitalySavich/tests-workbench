package by.delaidelo.tests.testworks.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "incoming_documents")
@Getter
@Setter
public class IncomingDocument extends AbstractEntity {
    @ManyToOne
    @JoinColumn
    private Contractor contractor;
    @Column
    private LocalDate incomingDocumentDate;
    @Column
    private String incomingDocumentNumber;
    @ManyToOne
    @JoinColumn
    private Contract contract;
    @ManyToOne
    @JoinColumn
    private Warehouse warehouse;
    @Column
    private String description;

    @ManyToMany(cascade=CascadeType.ALL)
    @JoinTable(
            name = "incoming_document_warehouse_item",
            joinColumns = @JoinColumn(name = "incoming_document_id"),
            inverseJoinColumns = @JoinColumn(name = "warehouse_item_id"))
    @JsonManagedReference
    Set<WarehouseItem> warehouseItems = new HashSet<WarehouseItem>();;
}
