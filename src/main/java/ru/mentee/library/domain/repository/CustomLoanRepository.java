/* @MENTEE_POWER (C)2025 */
package ru.mentee.library.domain.repository;

import java.time.LocalDate;
import java.util.Map;

public interface CustomLoanRepository {
    Map<String, Long> getLoanStatistics(LocalDate start, LocalDate end);
}
