package by.delaidelo.tests.testworks.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "incomingDocuments")
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
    @Column
    private String contractNumber;
    @Column
    private String description;
}
