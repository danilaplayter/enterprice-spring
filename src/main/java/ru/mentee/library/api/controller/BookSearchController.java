/* @MENTEE_POWER (C)2025 */
package ru.mentee.library.api.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.mentee.library.api.dto.BookDto;
import ru.mentee.library.service.BookService;

@RestController
@RequestMapping("/api/books/search")
@RequiredArgsConstructor
public class BookSearchController {

    private final BookService bookService;

    @GetMapping("/by-author")
    public List<BookDto> searchByAuthor(@RequestParam String author) {
        return bookService.findByAuthor(author);
    }
}
