/* @MENTEE_POWER (C)2025 */
package ru.mentee.library.api.dto;

import java.time.LocalDate;
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
                book.getTitle(),
                book.getAuthor(),
                book.getIsbn(),
                book.getPublishedDate(),
                book.isAvailable());
    }
}
