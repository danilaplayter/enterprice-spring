/* @MENTEE_POWER (C)2025 */
package ru.mentee.power.test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import jakarta.validation.ValidationException;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import ru.mentee.library.api.dto.BookDto;
import ru.mentee.library.api.dto.CreateBookRequest;
import ru.mentee.library.domain.repository.BookRepository;
import ru.mentee.library.service.BookService;

@SpringBootTest
@Testcontainers
class BookServiceIT {

    @Container static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15");

    @Autowired private BookService bookService;

    @Autowired private BookRepository bookRepository;

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

    @Test
    void shouldCreateAndGetBook() {
        CreateBookRequest request =
                new CreateBookRequest(
                        "Effective Java",
                        "Joshua Bloch",
                        "978-0-13-468599-1",
                        LocalDate.of(2018, 1, 1));

        BookDto created = bookService.create(request);
        BookDto found = bookService.getById(created.getId());

        assertThat(found.getTitle()).isEqualTo("Effective Java");
        assertThat(found.getAuthor()).isEqualTo("Joshua Bloch");
    }

    @Test
    void shouldFailOnInvalidIsbn() {
        CreateBookRequest request =
                new CreateBookRequest("Bad Book", "Bad Author", "invalid-isbn", LocalDate.now());

        assertThatThrownBy(() -> bookService.create(request))
                .isInstanceOf(ValidationException.class)
                .hasMessageContaining("ISBN");
    }
}
