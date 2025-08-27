/* @MENTEE_POWER (C)2025 */
package ru.mentee.commerce.api.controller;

import java.time.LocalDate;
import java.util.List;
import org.springframework.web.bind.annotation.*;
import ru.mentee.commerce.api.dto.CustomerOrderSummaryDto;
import ru.mentee.commerce.api.dto.OrderDto;
import ru.mentee.commerce.api.dto.ProductWithReviewsDto;
import ru.mentee.commerce.config.StatisticsHelper;
import ru.mentee.commerce.domain.model.Customer;
import ru.mentee.commerce.domain.model.Product;
import ru.mentee.commerce.domain.repository.OrderSummaryProjection;
import ru.mentee.commerce.domain.repository.ProductSummaryProjection;
import ru.mentee.commerce.service.CustomerService;
import ru.mentee.commerce.service.OrderService;
import ru.mentee.commerce.service.ProductService;

@RestController
@RequestMapping("/api")
public class ECommerceController {

    private final OrderService orderService;
    private final ProductService productService;
    private final CustomerService customerService;
    private final StatisticsHelper statisticsHelper;

    public ECommerceController(
            OrderService orderService,
            ProductService productService,
            CustomerService customerService,
            StatisticsHelper statisticsHelper) {
        this.orderService = orderService;
        this.productService = productService;
        this.customerService = customerService;
        this.statisticsHelper = statisticsHelper;
    }

    /** ПРОБЛЕМНЫЕ ENDPOINTS (для демонстрации N+1)**/
    @GetMapping("/orders/problematic")
    public List<OrderDto> getRecentOrdersProblematic() {
        statisticsHelper.clearStatistics();
        List<OrderDto> result = orderService.getRecentOrdersProblematic();
        statisticsHelper.logStatistics("Recent Orders (Problematic)");
        return result;
    }

    @GetMapping("/products/problematic")
    public List<ProductWithReviewsDto> getProductsWithReviewsProblematic() {
        statisticsHelper.clearStatistics();
        List<ProductWithReviewsDto> result = productService.getAllProductsWithReviewsProblematic();
        statisticsHelper.logStatistics("Products with Reviews (Problematic)");
        return result;
    }

    @GetMapping("/customers/problematic")
    public List<Customer> getCustomersProblematic() {
        statisticsHelper.clearStatistics();
        List<Customer> result = customerService.getAllCustomersProblematic();
        statisticsHelper.logStatistics("All Customers (Problematic)");
        return result;
    }

    /** ОПТИМИЗИРОВАННЫЕ ENDPOINTS**/
    @GetMapping("/orders/optimized")
    public List<OrderDto> getRecentOrdersOptimized() {
        statisticsHelper.clearStatistics();
        List<OrderDto> result = orderService.getRecentOrders();
        statisticsHelper.logStatistics("Recent Orders (Optimized)");
        return result;
    }

    @GetMapping("/orders/summaries")
    public List<OrderSummaryProjection> getOrderSummaries() {
        statisticsHelper.clearStatistics();
        List<OrderSummaryProjection> result = orderService.getOrderSummaries();
        statisticsHelper.logStatistics("Order Summaries (Projection)");
        return result;
    }

    @GetMapping("/orders/after/{date}")
    public List<OrderDto> getOrdersAfterDate(@PathVariable String date) {
        statisticsHelper.clearStatistics();
        List<OrderDto> result = orderService.getOrdersAfterDate(LocalDate.parse(date));
        statisticsHelper.logStatistics("Orders After Date (JOIN FETCH)");
        return result;
    }

    @GetMapping("/products/optimized")
    public List<ProductWithReviewsDto> getProductsWithReviewsOptimized() {
        statisticsHelper.clearStatistics();
        List<ProductWithReviewsDto> result = productService.getAllProductsWithReviews();
        statisticsHelper.logStatistics("Products with Reviews (Optimized)");
        return result;
    }

    @GetMapping("/products/summaries")
    public List<ProductSummaryProjection> getProductSummaries() {
        statisticsHelper.clearStatistics();
        List<ProductSummaryProjection> result = productService.getProductSummaries();
        statisticsHelper.logStatistics("Product Summaries (Projection)");
        return result;
    }

    @GetMapping("/products/popular")
    public List<Product> getPopularProducts() {
        statisticsHelper.clearStatistics();
        List<Product> result = productService.getPopularProducts();
        statisticsHelper.logStatistics("Popular Products (Batch Size)");
        return result;
    }

    @GetMapping("/customers/optimized")
    public List<Customer> getCustomersOptimized() {
        statisticsHelper.clearStatistics();
        List<Customer> result = customerService.getAllCustomersOptimized();
        statisticsHelper.logStatistics("All Customers (Optimized)");
        return result;
    }

    @GetMapping("/customers/summaries")
    public List<CustomerOrderSummaryDto> getCustomerSummaries() {
        statisticsHelper.clearStatistics();
        List<CustomerOrderSummaryDto> result = customerService.getCustomerSummaries();
        statisticsHelper.logStatistics("Customer Summaries (Projection)");
        return result;
    }

    @GetMapping("/customers/{customerId}/orders")
    public List<OrderDto> getCustomerOrders(@PathVariable Long customerId) {
        statisticsHelper.clearStatistics();
        List<OrderDto> result = orderService.getCustomerOrders(customerId);
        statisticsHelper.logStatistics("Customer Orders (Batch Size Effect)");
        return result;
    }
}
