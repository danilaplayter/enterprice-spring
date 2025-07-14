/* @MENTEE_POWER (C)2025 */
package ru.mentee.library.service.impl;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import jakarta.transaction.Transactional;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import ru.mentee.library.api.dto.BookDto;
import ru.mentee.library.api.dto.CreateBookRequest;
import ru.mentee.library.api.dto.UpdateBookRequest;
import ru.mentee.library.domain.exception.BookNotFoundException;
import ru.mentee.library.domain.exception.InvalidBookDataException;
import ru.mentee.library.domain.model.Book;
import ru.mentee.library.domain.repository.BookRepository;
import ru.mentee.library.service.BookService;
import ru.mentee.library.service.audit.OperationAuditLogger;
import ru.mentee.library.service.validation.IsbnValidator;

@Service
@RequiredArgsConstructor
@Slf4j
@Scope("prototype")
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final IsbnValidator isbnValidator;
    private final OperationAuditLogger auditLogger;

    @Override
    @Transactional
    public BookDto create(CreateBookRequest request) {
        if (request == null) {
            log.info("Request can't be null.");
        }
        isbnValidator.validate(request.getIsbn());

        Book book =
                Book.builder()
                        .title(request.getTitle())
                        .author(request.getAuthor())
                        .isbn(request.getIsbn())
                        .publishedDate(request.getPublishedDate())
                        .available(true)
                        .build();

        Book savedBook = bookRepository.save(book);
        auditLogger.log("User '{}' performed operation: created Book", "danila");

        log.info("Created book with ID: {}", savedBook.getId());
        return BookDto.from(savedBook);
    }

    @Override
    @Transactional
    public BookDto getById(long id) {
        return bookRepository
                .findById(id)
                .map(BookDto::from)
                .orElseThrow(() -> new BookNotFoundException(id));
    }

    @Override
    @Transactional
    public List<BookDto> getAll() {
        List<Book> books = bookRepository.findAll();
        log.debug("Retrieved {} books from database", books.size());
        return books.stream().map(BookDto::from).toList();
    }

    @Override
    @Transactional
    public BookDto update(long id, UpdateBookRequest request) {
        Book book = bookRepository.findById(id).orElseThrow(() -> new BookNotFoundException(id));
        if (request.getTitle() != null) {
            book.setTitle(request.getTitle());
        }
        if (request.getAuthor() != null) {
            book.setAuthor(request.getAuthor());
        }
        if (request.getAvailable() != null) {
            book.setAvailable(request.getAvailable());
        }
        if (request.getIsbn() != null) {
            book.setIsbn(request.getIsbn());
        }
        if (request.getPublishedDate() != null) {
            book.setPublishedDate(request.getPublishedDate());
        }
        Book updatedBook = bookRepository.save(book);
        log.info("Updated book with ID: {}", id);
        return BookDto.from(updatedBook);
    }

    @Override
    public void delete(long id) {
        if (!bookRepository.existsById(id)) {
            throw new BookNotFoundException(id);
        }
        bookRepository.deleteById(id);
        log.info("Book with ID {} deleted", id);
    }

    @Override
    public List<BookDto> findByAuthor(String author) {
        if (!StringUtils.hasText(author)) {
            throw new InvalidBookDataException("Author name cannot be empty");
        }
        return bookRepository.findByAuthor(author).stream().map(BookDto::from).toList();
    }

    @PostConstruct
    public void init() {
        log.info("BookService initialized");
    }

    @PreDestroy
    public void cleanup() {
        log.info("BookService destroyed");
    }
}
