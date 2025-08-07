/* @MENTEE_POWER (C)2025 */
package ru.mentee.library.domain.repository;

import ru.mentee.library.domain.model.Book;

public interface BookWithLoanCount {
    Book getBook();

    Long getLoanCount();
}
