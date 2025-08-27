/* @MENTEE_POWER (C)2025 */
package ru.mentee.commerce.domain.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.mentee.commerce.api.dto.CustomerOrderSummaryDto;
import ru.mentee.commerce.domain.model.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    /**Проблемный запрос - вызывает N+1**/
    @Query("SELECT c FROM Customer c")
    List<Customer> findAllBasic();

    /**Оптимизированный с помощью JOIN FETCH**/
    @Query(
            "SELECT DISTINCT c FROM Customer c"
                    + "LEFT JOIN FETCH c.orders o"
                    + "LEFT JOIN FETCH o.orderItems")
    List<Customer> findAllWithOrdersOptimized();

    /**Проекция для сводной информации**/
    @Query(
            "SELECT c.id as customerId, c.name as customerName, COUNT(o.id) as orderCount,"
                + " COALESCE(SUM(oi.quantity * p.price), 0) as totalSpent FROM Customer c LEFT JOIN"
                + " c.orders o LEFT JOIN o.orderItems oi LEFT JOIN oi.product p GROUP BY c.id,"
                + " c.name")
    List<CustomerOrderSummaryDto> findCustomerOrderSummary();
}
