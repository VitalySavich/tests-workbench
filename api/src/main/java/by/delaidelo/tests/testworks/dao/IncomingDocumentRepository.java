package by.delaidelo.tests.testworks.dao;

//import by.delaidelo.tests.testworks.domain.IncomingDocument_;
//import by.delaidelo.tests.testworks.domain.Contractor_;
import by.delaidelo.tests.testworks.domain.IncomingDocument;
import jakarta.persistence.criteria.Predicate;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public interface IncomingDocumentRepository extends JpaRepository<IncomingDocument, Long>, JpaSpecificationExecutor<IncomingDocument> {
    public static Specification<IncomingDocument> buildSpecification(Long incomingDocumentId, String query) {
        return (root, cq, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (Objects.nonNull(cq)) {
                if (Objects.nonNull(cq.getRestriction())) {
                    predicates.add(cq.getRestriction());
                }

                /*if (Objects.nonNull(incomingDocumentId)) {
                    predicates.add(cb.equal(root.get(IncomingDocument_.contractor).get(Contractor_.id), incomingDocumentId));
                }

                if (StringUtils.isNotBlank(query)) {
                    final var q = '%' + query + '%';
                    predicates.add(cb.or(
                            cb.like(cb.upper(root.get(IncomingDocument_.contractNumber)), q),
                            cb.like(cb.upper(root.get(IncomingDocument_.description)), q)));
                }*/
            }

            return cb.and(predicates.toArray(new Predicate[] {}));
        };
    }
}
