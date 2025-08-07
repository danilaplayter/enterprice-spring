/* @MENTEE_POWER (C)2025 */
package ru.mentee.library.domain.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.mentee.library.domain.model.Book;
import ru.mentee.library.domain.model.BookSearchCriteria;
import ru.mentee.library.domain.repository.BookRepository;
import ru.mentee.library.domain.specification.BookSpecification;

@Service
@RequiredArgsConstructor
public class BookSearchService {

    private final BookRepository bookRepository;

    public Page<Book> searchBooks(BookSearchCriteria criteria, Pageable pageable) {
        return bookRepository.findAll(BookSpecification.buildSpecification(criteria), pageable);
    }
}
