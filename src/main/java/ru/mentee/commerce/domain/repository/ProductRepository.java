/* @MENTEE_POWER (C)2025 */
package ru.mentee.commerce.domain.repository;

import java.awt.print.Pageable;
import java.util.List;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.mentee.commerce.domain.model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    /**1 + N при доступе к reviews**/
    List<Product> findAll();

    /**Оптимизированный через EntityGraph**/
    @EntityGraph("Product.withReviews")
    @Query("SELECT p FROM Product p")
    List<Product> findAllWithReviews();

    /** Проекция для списка продуктов с рейтингами**/
    @Query(
            "SELECT p.id as id, p.name as name, p.price as price, "
                    + "COUNT(r.id) as reviewCount, COALESCE(AVG(r.rating), 0) as averageRating "
                    + "FROM Product p "
                    + "LEFT JOIN p.reviews r "
                    + "GROUP BY p.id, p.name, p.price")
    List<ProductSummaryProjection> findProductSummaries();

    /**Batch fetch для популярных продуктов**/
    @Query(
            "SELECT p FROM Product p "
                    + "JOIN p.orderItems oi "
                    + "GROUP BY p "
                    + "ORDER BY SUM(oi.quantity) DESC")
    List<Product> findPopularProducts(Pageable pageable);
}
