/* @MENTEE_POWER (C)2025 */
package ru.mentee.library.domain.repository;

import ru.mentee.library.domain.model.Author;

public interface AuthorWithBookCount {
    Author getAuthor();

    Long getBookCount();
}
