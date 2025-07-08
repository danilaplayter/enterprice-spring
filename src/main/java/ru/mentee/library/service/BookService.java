/* @MENTEE_POWER (C)2025 */
package ru.mentee.library.service;

import java.time.LocalDate;
import java.util.List;
import ru.mentee.library.api.dto.BookDto;
import ru.mentee.library.api.dto.CreateBookRequest;
import ru.mentee.library.api.dto.UpdateBookRequest;

public interface BookService {

    BookDto create(CreateBookRequest request);

    BookDto getById(long id);

    List<BookDto> getAll();

    BookDto update(long id, UpdateBookRequest request);

    void delete(long id);

    List<BookDto> findByAuthor(String author);

}
