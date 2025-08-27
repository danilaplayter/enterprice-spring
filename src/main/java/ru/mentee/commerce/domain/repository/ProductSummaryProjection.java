/* @MENTEE_POWER (C)2025 */
package ru.mentee.commerce.domain.repository;

import java.math.BigDecimal;

public interface ProductSummaryProjection {
    Long getId();

    String getName();

    BigDecimal getPrice();

    Long getReviewCount();

    Double getAverageRating();
}
