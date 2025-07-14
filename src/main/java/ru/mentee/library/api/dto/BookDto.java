/* @MENTEE_POWER (C)2025 */
package ru.mentee.library.api.dto;

import java.time.LocalDate;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.mentee.library.domain.model.Book;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookDto {

    private Long id;
    private String title;
    private String author;
    private String isbn;
    private LocalDate publishedDate;
    private boolean available;

    public static BookDto from(Book book) {
        return new BookDto(
                book.getId(),
                Optional.ofNullable(book.getTitle()).orElse(""),
                Optional.ofNullable(book.getAuthor()).orElse(""),
                Optional.ofNullable(book.getIsbn()).orElse(""),
                book.getPublishedDate(),
                book.isAvailable());
    }
}
