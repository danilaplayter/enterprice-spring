/* @MENTEE_POWER (C)2025 */
package ru.mentee.commerce.domain.repository;

import java.awt.print.Pageable;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.mentee.commerce.domain.model.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    /**Проблемный запрос 1 + N при доступе к customer && orderItems**/
    List<Order> findTop10ByOrderByOrderDateDesc();

    /**Оптимизировано через @EntityGraph**/
    @EntityGraph("Order.full")
    @Query("SELECT o FROM Order o ORDER BY o.orderDate DESC")
    List<Order> findRecentOrdersOptimized(Pageable pageable);

    /**Используем JOIN FETCH для простых запросов**/
    @Query("SELECT o FROM Order o" + "JOIN FETCH o.customer" + "WHERE o.orderDate => :date")
    List<Order> findOrdersAfterDateWithCustomer(@Param("date") LocalDate date);

    /**Оптимизированный запрос с проекцией**/
    @Query(
            "SELECT o.id as id, o.orderDate as orderDate, c.name as customerName, "
                    + "SUM(oi.quantity * p.price) as totalAmount "
                    + "FROM Order o "
                    + "JOIN o.customer c "
                    + "JOIN o.orderItems oi "
                    + "JOIN oi.product p "
                    + "GROUP BY o.id, o.orderDate, c.name "
                    + "ORDER BY o.orderDate DESC")
    List<OrderSummaryProjection> findOrderSummaries(Pageable pageable);

    /**Оптимизированный для одного клиннта через @EntityGraph**/
    @EntityGraph("Order.withItems")
    List<Order> findByCustomerIdOrderByOrderDateDesc(Long customerId);
}
