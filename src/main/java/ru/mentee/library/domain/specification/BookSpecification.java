/* @MENTEE_POWER (C)2025 */
package ru.mentee.library.domain.specification;

import jakarta.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;
import org.springframework.data.jpa.domain.Specification;
import ru.mentee.library.domain.model.*;
import ru.mentee.library.domain.model.BookSearchCriteria;

public class BookSpecification {

    public static Specification<Book> buildSpecification(BookSearchCriteria criteria) {
        return (root, query, cb) -> {
            query.distinct(true);
            List<Predicate> predicates = new ArrayList<>();

            // Фильтр по названию
            if (criteria.getTitle() != null && !criteria.getTitle().isBlank()) {
                predicates.add(
                        cb.like(
                                cb.lower(root.get(Book_.title)),
                                "%" + criteria.getTitle().toLowerCase() + "%"));
            }

            // Фильтр по автору (имя и/или фамилия)
            if (criteria.getAuthorFirstName() != null || criteria.getAuthorLastName() != null) {
                Join<Book, Author> authorsJoin = root.join(Book_.AUTHORS, JoinType.INNER);
                if (criteria.getAuthorFirstName() != null
                        && !criteria.getAuthorFirstName().isBlank()) {
                    predicates.add(
                            cb.like(
                                    cb.lower(authorsJoin.get(Author_.firstName)),
                                    "%" + criteria.getAuthorFirstName().toLowerCase() + "%"));
                }
                if (criteria.getAuthorLastName() != null
                        && !criteria.getAuthorLastName().isBlank()) {
                    predicates.add(
                            cb.like(
                                    cb.lower(authorsJoin.get(Author_.lastName)),
                                    "%" + criteria.getAuthorLastName().toLowerCase() + "%"));
                }
            }

            // Фильтр по категории
            if (criteria.getCategoryName() != null && !criteria.getCategoryName().isBlank()) {
                predicates.add(
                        cb.equal(
                                root.get(Book_.category).get(Category_.name),
                                criteria.getCategoryName()));
            }

            // Фильтр по году публикации (диапазон)
            if (criteria.getPublicationYearFrom() != null
                    || criteria.getPublicationYearTo() != null) {
                Path<Integer> yearPath = root.get(Book_.publicationYear);
                if (criteria.getPublicationYearFrom() != null) {
                    predicates.add(cb.ge(yearPath, criteria.getPublicationYearFrom()));
                }
                if (criteria.getPublicationYearTo() != null) {
                    predicates.add(cb.le(yearPath, criteria.getPublicationYearTo()));
                }
            }

            // Фильтр по количеству страниц (диапазон)
            if (criteria.getPagesFrom() != null || criteria.getPagesTo() != null) {
                Path<Integer> pagesPath = root.get(Book_.PAGES);
                if (criteria.getPagesFrom() != null) {
                    predicates.add(cb.ge(pagesPath, criteria.getPagesFrom()));
                }
                if (criteria.getPagesTo() != null) {
                    predicates.add(cb.le(pagesPath, criteria.getPagesTo()));
                }
            }

            // Фильтр по доступности
            if (Boolean.TRUE.equals(criteria.getAvailableOnly())) {
                Subquery<Long> subquery = query.subquery(Long.class);
                Root<Loan> loanRoot = subquery.from(Loan.class);
                subquery.select(loanRoot.get("book").get("id"));
                subquery.where(
                        cb.equal(loanRoot.get("book"), root),
                        cb.isNull(loanRoot.get("returnDate")));
                predicates.add(cb.not(cb.exists(subquery)));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
