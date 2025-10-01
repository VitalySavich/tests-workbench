package by.delaidelo.tests.testworks.dao;

import by.delaidelo.tests.testworks.domain.WarehouseItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface WarehouseItemRepository extends JpaRepository<WarehouseItem, Long> {
    //@Query("SELECT i FROM WarehouseItem WHERE i.sum > 0" )
    //Page<WarehouseItem> findByQuery(@Param("query") String query, Pageable pageable);

    List<WarehouseItem> findWarehouseItemsByIncomingDocumentsId(Long incomingDocumentId);
}
