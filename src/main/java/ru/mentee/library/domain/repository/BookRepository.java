/* @MENTEE_POWER (C)2025 */
package ru.mentee.library.domain.repository;

import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.mentee.library.domain.model.Book;

public interface BookRepository extends JpaRepository<Book, Long> {

    // Стандартные методы (уже есть)

    // Кастомные методы
    List<Book> findByAuthor(String author);

    List<Book> findByAvailableTrue();

    List<Book> findByTitleContainingIgnoreCase(String titlePart);

    // JPQL
    @Query("SELECT b FROM Book b WHERE b.publishedDate >= :date")
    List<Book> findBooksPublishedAfter(@Param("date") LocalDate date);

    // Нативный SQL
    @Query(value = "SELECT * FROM books WHERE LENGTH(title) > :minLength", nativeQuery = true)
    List<Book> findBooksWithLongTitle(@Param("minLength") int minLength);
}
