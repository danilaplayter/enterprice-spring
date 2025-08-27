/* @MENTEE_POWER (C)2025 */
package ru.mentee.commerce.domain.repository;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface OrderSummaryProjection {
    Long getId();

    LocalDate getOrderDate();

    String getCustomerName();

    BigDecimal getTotalAmount();
}
