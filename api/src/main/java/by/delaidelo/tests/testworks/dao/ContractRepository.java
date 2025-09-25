package by.delaidelo.tests.testworks.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import by.delaidelo.tests.testworks.domain.Contract;
//import by.delaidelo.tests.testworks.domain.Contract_;
//import by.delaidelo.tests.testworks.domain.Contractor_;
import jakarta.persistence.criteria.Predicate;

public interface ContractRepository extends JpaRepository<Contract, Long>, JpaSpecificationExecutor<Contract> {
    public static Specification<Contract> buildSpecification(Long contractorId, String query) {
        return (root, cq, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (Objects.nonNull(cq)) {
                if (Objects.nonNull(cq.getRestriction())) {
                    predicates.add(cq.getRestriction());
                }

                if (Objects.nonNull(contractorId)) {
                    predicates.add(cb.equal(root.get("contractor").get("id"), contractorId));
                }

                /*if (StringUtils.isNotBlank(query)) {
                    final var q = '%' + query + '%';
                    predicates.add(cb.or(
                            cb.like(cb.upper(root.get(Contract_.contractNumber)), q),
                            cb.like(cb.upper(root.get(Contract_.description)), q)));
                }*/
            }

            return cb.and(predicates.toArray(new Predicate[] {}));
        };
    }
}
