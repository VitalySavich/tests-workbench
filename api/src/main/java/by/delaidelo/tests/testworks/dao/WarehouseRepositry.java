package by.delaidelo.tests.testworks.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import by.delaidelo.tests.testworks.domain.Warehouse;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface WarehouseRepositry extends JpaRepository<Warehouse, Long> {
    Page<Warehouse> findByTitleContainingIgnoreCase(String query, Pageable pageable);
    @Query("SELECT w FROM Warehouse w WHERE w.title ILIKE %:query%")
    Page<Warehouse> findByQuery(@Param("query") String query, Pageable pageable);
} 
