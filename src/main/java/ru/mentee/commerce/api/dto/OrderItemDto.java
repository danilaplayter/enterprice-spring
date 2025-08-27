/* @MENTEE_POWER (C)2025 */
package ru.mentee.commerce.api.dto;

import java.math.BigDecimal;

public record OrderItemDto(
        Long id, String productName, BigDecimal price, Integer quantity, BigDecimal subtotal) {}
