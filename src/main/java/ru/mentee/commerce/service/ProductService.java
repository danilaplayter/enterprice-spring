/* @MENTEE_POWER (C)2025 */
package ru.mentee.commerce.service;

import java.awt.print.Pageable;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.mentee.commerce.api.dto.ProductWithReviewsDto;
import ru.mentee.commerce.api.dto.ReviewDto;
import ru.mentee.commerce.domain.model.Product;
import ru.mentee.commerce.domain.model.Review;
import ru.mentee.commerce.domain.repository.ProductRepository;
import ru.mentee.commerce.domain.repository.ProductSummaryProjection;
import ru.mentee.commerce.domain.repository.ReviewRepository;

@Service
@Transactional(readOnly = true)
public class ProductService {

    private final ProductRepository productRepository;
    private final ReviewRepository reviewRepository;

    public ProductService(ProductRepository productRepository, ReviewRepository reviewRepository) {
        this.productRepository = productRepository;
        this.reviewRepository = reviewRepository;
    }

    /**1 + N при доступе к reviews**/
    public List<ProductWithReviewsDto> getAllProductsWithReviewsProblematic() {
        List<Product> products = productRepository.findAll();

        return products.stream()
                .map(this::convertToProductWithReviewsDto)
                .collect(Collectors.toList());
    }

    /**оптимизированный метод через EntityGraph**/
    public List<ProductWithReviewsDto> getAllProductsWithReviews() {
        List<Product> products = productRepository.findAllWithReviews();

        return products.stream()
                .map(this::convertToProductWithReviewsDto)
                .collect(Collectors.toList());
    }

    /**проекции для сводной информации**/
    public List<ProductSummaryProjection> getProductSummaries() {
        return productRepository.findProductSummaries();
    }

    /**Популярные продукты с batch loading эффектом**/
    public List<Product> getPopularProducts() {
        return productRepository.findPopularProducts((Pageable) PageRequest.of(0, 10));
    }

    private ProductWithReviewsDto convertToProductWithReviewsDto(Product product) {
        List<ReviewDto> reviewDtos =
                product.getReviews().stream()
                        .map(
                                review ->
                                        new ReviewDto(
                                                review.getId(),
                                                review.getCustomer().getName(),
                                                review.getRating(),
                                                review.getComment()))
                        .collect(Collectors.toList());

        Double averageRating =
                product.getReviews().stream().mapToInt(Review::getRating).average().orElse(0.0);

        return new ProductWithReviewsDto(
                product.getId(), product.getName(), product.getPrice(), reviewDtos, averageRating);
    }
}
