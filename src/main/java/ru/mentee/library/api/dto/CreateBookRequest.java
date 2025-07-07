/* @MENTEE_POWER (C)2025 */
package ru.mentee.library.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.ISBN;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateBookRequest {
    @NotBlank private String title;

    @NotBlank private String author;

    @ISBN private String isbn;

    @PastOrPresent private LocalDate publishedDate;
}
