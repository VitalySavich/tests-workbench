package by.delaidelo.tests.testworks.dao;

import by.delaidelo.tests.testworks.domain.Result;
import by.delaidelo.tests.testworks.domain.Warehouse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ResultRepositry extends JpaRepository<Result, Long> {
}
