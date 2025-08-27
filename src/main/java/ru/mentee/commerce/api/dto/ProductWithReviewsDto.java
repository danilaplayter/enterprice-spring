/* @MENTEE_POWER (C)2025 */
package ru.mentee.commerce.api.dto;

import java.math.BigDecimal;
import java.util.List;

public record ProductWithReviewsDto(
        Long id, String name, BigDecimal price, List<ReviewDto> reviews, Double averageRating) {}
