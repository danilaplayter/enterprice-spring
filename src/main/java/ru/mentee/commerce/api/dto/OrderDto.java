/* @MENTEE_POWER (C)2025 */
package ru.mentee.commerce.api.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public record OrderDto(
        Long id,
        String customerName,
        LocalDate orderDate,
        List<OrderItemDto> items,
        BigDecimal totalAmount) {}
