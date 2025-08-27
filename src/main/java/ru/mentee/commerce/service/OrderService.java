/* @MENTEE_POWER (C)2025 */
package ru.mentee.commerce.service;

import java.awt.print.Pageable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.mentee.commerce.api.dto.OrderDto;
import ru.mentee.commerce.api.dto.OrderItemDto;
import ru.mentee.commerce.domain.model.Order;
import ru.mentee.commerce.domain.repository.CustomerRepository;
import ru.mentee.commerce.domain.repository.OrderRepository;
import ru.mentee.commerce.domain.repository.OrderSummaryProjection;

@Service
@Transactional(readOnly = true)
@AllArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;

    /**Проблемный метод, вызывает 1 + N**/
    public List<OrderDto> getRecentOrdersProblematic() {
        List<Order> orders = orderRepository.findTop10ByOrderByOrderDateDesc();
        return orders.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    /**Данный метод оптимизирован, так как используется EntityGraph**/
    public List<OrderDto> getRecentOrders() {
        List<Order> orders =
                orderRepository.findRecentOrdersOptimized((Pageable) PageRequest.of(0, 10));

        return orders.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    /** ОПТИМИЗИРОВАННЫЙ метод из-за использует проекцию **/
    public List<OrderSummaryProjection> getOrderSummaries() {
        return orderRepository.findOrderSummaries((Pageable) PageRequest.of(0, 20));
    }

    /** Метод с JOIN FETCH**/
    public List<OrderDto> getOrdersAfterDate(LocalDate date) {
        List<Order> orders = orderRepository.findOrdersAfterDateWithCustomer(date);

        return orders.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    /** Получение заказов клиента оптимизировано с помощью @BatchSize**/
    public List<OrderDto> getCustomerOrders(Long customerId) {
        List<Order> orders = orderRepository.findByCustomerIdOrderByOrderDateDesc(customerId);

        return orders.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    private OrderDto convertToDto(Order order) {
        List<OrderItemDto> items =
                order.getOrderItems().stream()
                        .map(
                                item ->
                                        new OrderItemDto(
                                                item.getId(),
                                                item.getProduct().getName(),
                                                item.getProduct().getPrice(),
                                                item.getQuantity(),
                                                item.getProduct()
                                                        .getPrice()
                                                        .multiply(
                                                                new BigDecimal(
                                                                        item.getQuantity()))))
                        .collect(Collectors.toList());

        BigDecimal total =
                items.stream().map(OrderItemDto::subtotal).reduce(BigDecimal.ZERO, BigDecimal::add);

        return new OrderDto(
                order.getId(), order.getCustomer().getName(), order.getOrderDate(), items, total);
    }
}
