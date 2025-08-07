/* @MENTEE_POWER (C)2025 */
package ru.mentee.library.domain.repository;

import java.time.LocalDate;
import java.util.Map;

/**
 * Кастомный репозиторий для работы со статистикой по займам.
 */
public interface CustomLoanRepository {

    /**
     * Получает статистику по займам за указанный период.
     *
     * @param start начальная дата периода (включительно)
     * @param end конечная дата периода (включительно)
     * @return Map, где ключ - строка в формате "ГГГГ-ММ",
     *         а значение - количество займов за этот месяц
     */
    Map<String, Long> getLoanStatistics(LocalDate start, LocalDate end);
}
