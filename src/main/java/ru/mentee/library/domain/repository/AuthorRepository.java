/* @MENTEE_POWER (C)2025 */
package ru.mentee.library.domain.repository;

import java.awt.print.Pageable;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.mentee.library.domain.model.Author;

public interface AuthorRepository extends JpaRepository<Author, Long> {
    @Query(
            "SELECT a FROM Author a WHERE "
                    + "LOWER(a.firstName) LIKE LOWER(CONCAT('%', :name, '%')) OR "
                    + "LOWER(a.lastName) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<Author> findByFullName(@Param("name") String name);

    @Query(
            "SELECT a AS author, COUNT(b) AS bookCount FROM Author a "
                    + "LEFT JOIN a.books b GROUP BY a")
    Page<AuthorWithBookCount> findAllWithBookCount(Pageable pageable);

    @Query(
            "SELECT DISTINCT a FROM Author a JOIN a.books b "
                    + "JOIN b.category c WHERE c.name = :categoryName")
    List<Author> findByBookCategory(@Param("categoryName") String categoryName);
}
