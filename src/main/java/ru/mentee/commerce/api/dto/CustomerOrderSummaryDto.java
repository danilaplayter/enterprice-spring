/* @MENTEE_POWER (C)2025 */
package ru.mentee.commerce.api.dto;

import java.math.BigDecimal;

public record CustomerOrderSummaryDto(
        Long customerId, String customerName, Integer orderCount, BigDecimal totalSpent) {}
