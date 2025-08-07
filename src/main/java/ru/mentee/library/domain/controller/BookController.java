/* @MENTEE_POWER (C)2025 */
package ru.mentee.library.domain.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;
import ru.mentee.library.domain.model.Book;
import ru.mentee.library.domain.model.BookSearchCriteria;
import ru.mentee.library.domain.service.BookSearchService;

@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
public class BookController {

    private final BookSearchService bookSearchService;

    @GetMapping("/search")
    public Page<Book> searchBooks(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String authorFirstName,
            @RequestParam(required = false) String authorLastName,
            @RequestParam(required = false) String categoryName,
            @RequestParam(required = false) Integer publicationYearFrom,
            @RequestParam(required = false) Integer publicationYearTo,
            @RequestParam(required = false) Integer pagesFrom,
            @RequestParam(required = false) Integer pagesTo,
            @RequestParam(required = false) Boolean availableOnly,
            @PageableDefault(size = 20) Pageable pageable) {

        BookSearchCriteria criteria =
                BookSearchCriteria.builder()
                        .title(title)
                        .authorFirstName(authorFirstName)
                        .authorLastName(authorLastName)
                        .categoryName(categoryName)
                        .publicationYearFrom(publicationYearFrom)
                        .publicationYearTo(publicationYearTo)
                        .pagesFrom(pagesFrom)
                        .pagesTo(pagesTo)
                        .availableOnly(availableOnly)
                        .build();

        return bookSearchService.searchBooks(criteria, pageable);
    }
}
