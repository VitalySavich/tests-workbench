package by.delaidelo.tests.testworks.dao;

import by.delaidelo.tests.testworks.domain.Result;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public interface ResultRepositry extends JpaRepository<Result, Long>, JpaSpecificationExecutor<Result> {

    static Specification<Result> buildSpecification(String query) {
        return (root, cq, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (Objects.nonNull(cq)) {
                if (Objects.nonNull(cq.getRestriction())) {
                    predicates.add(cq.getRestriction());
                }
            }

            return cb.and(predicates.toArray(new Predicate[] {}));
        };
    }
}
