/* @MENTEE_POWER (C)2025 */
package ru.mentee.library.domain.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class CustomLoanRepositoryImpl implements CustomLoanRepository {
    private final EntityManager entityManager;

    @Override
    public Map<String, Long> getLoanStatistics(LocalDate startDate, LocalDate endDate) {
        String sql =
                "SELECT to_char(loan_date, 'YYYY-MM') AS period, COUNT(*) "
                        + "FROM loans "
                        + "WHERE loan_date BETWEEN :startDate AND :endDate "
                        + "GROUP BY period "
                        + "ORDER BY period";

        Query query =
                entityManager
                        .createNativeQuery(sql)
                        .setParameter("startDate", startDate)
                        .setParameter("endDate", endDate);

        List<Object[]> results = query.getResultList();

        return results.stream()
                .collect(
                        Collectors.toMap(
                                arr -> (String) arr[0], arr -> ((Number) arr[1]).longValue()));
    }
}
