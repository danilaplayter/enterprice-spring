/* @MENTEE_POWER (C)2025 */
package ru.mentee.commerce.domain.repository;

import java.awt.print.Pageable;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.mentee.commerce.domain.model.Review;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    /**Проблемный - N+1 при доступе к product и customer**/
    List<Review> findTop20ByOrderByIdDesc();

    /**Оптимизированный с JOIN FETCH**/
    @Query(
            "SELECT r FROM Review r "
                    + "JOIN FETCH r.product p "
                    + "JOIN FETCH r.customer c "
                    + "ORDER BY r.id DESC")
    List<Review> findRecentReviewsOptimized(Pageable pageable);

    /**Оптимизированный с JOIN FETCH**/
    @Query("SELECT r FROM Review r " + "JOIN FETCH r.customer " + "WHERE r.product.id = :productId")
    List<Review> findByProductIdWithCustomer(@Param("productId") Long productId);
}
