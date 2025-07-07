/* @MENTEE_POWER (C)2025 */
package ru.mentee.library.api.dto;

import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.ISBN;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateBookRequest {
    @Size(max = 255, message = "Title must be less than 255 characters")
    private String title;

    @Size(max = 255, message = "Author must be less than 255 characters")
    private String author;

    @ISBN(message = "Invalid ISBN format")
    private String isbn;

    @PastOrPresent(message = "Publication date cannot be in the future")
    private LocalDate publishedDate;

    private Boolean available;
}
