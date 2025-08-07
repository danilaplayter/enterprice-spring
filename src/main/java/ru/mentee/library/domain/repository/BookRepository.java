/* @MENTEE_POWER (C)2025 */
package ru.mentee.library.domain.repository;

import java.awt.print.Pageable;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.mentee.library.domain.model.Book;

public interface BookRepository extends JpaRepository<Book, Long>, JpaSpecificationExecutor<Book> {

    // Простые derived queries
    Optional<Book> findByIsbn(String isbn);

    List<Book> findByTitleContainingIgnoreCase(String title);

    List<Book> findByStatus(String status);

    List<Book> findByPublicationYear(Integer year);

    // Поиск по автору
    @Query("SELECT b FROM Book b JOIN b.authors a WHERE a.lastName = :lastName")
    List<Book> findByAuthorLastName(@Param("lastName") String lastName);

    // Фильтрация по категории
    @Query("SELECT b FROM Book b WHERE b.category.name = :categoryName")
    List<Book> findByCategoryName(@Param("categoryName") String categoryName);

    // Просроченные книги
    @Query(
            "SELECT DISTINCT b FROM Book b JOIN b.loans l "
                    + "WHERE l.returnDate IS NULL AND l.dueDate < CURRENT_TIMESTAMP")
    List<Book> findOverdueBooks();

    // Топ популярных книг
    @Query(
            "SELECT b AS book, COUNT(l) AS loanCount FROM Book b LEFT JOIN b.loans l "
                    + "GROUP BY b ORDER BY loanCount DESC")
    Page<BookWithLoanCount> findPopularBooks(Pageable pageable);

    // Проверка доступности
    @Query(
            "SELECT CASE WHEN COUNT(l) = 0 THEN true ELSE false END "
                    + "FROM Loan l WHERE l.book.id = :bookId AND l.returnDate IS NULL")
    boolean isBookAvailable(@Param("bookId") Long bookId);

    // Комплексный поиск с пагинацией
    @Query(
            "SELECT b FROM Book b WHERE "
                    + "(:title IS NULL OR LOWER(b.title) LIKE LOWER(CONCAT('%', :title, '%'))) AND "
                    + "(:status IS NULL OR b.status = :status) AND "
                    + "(:year IS NULL OR b.publicationYear = :year) AND "
                    + "(:categoryId IS NULL OR b.category.id = :categoryId)")
    Page<Book> searchBooks(
            @Param("title") String title,
            @Param("status") String status,
            @Param("year") Integer year,
            @Param("categoryId") Long categoryId,
            Pageable pageable);
}
