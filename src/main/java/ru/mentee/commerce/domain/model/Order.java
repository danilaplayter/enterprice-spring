/* @MENTEE_POWER (C)2025 */
package ru.mentee.commerce.domain.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedAttributeNode;
import jakarta.persistence.NamedEntityGraph;
import jakarta.persistence.NamedEntityGraphs;
import jakarta.persistence.NamedSubgraph;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.BatchSize;

@Entity
@Table(name = "orders")
@NamedEntityGraphs({
    @NamedEntityGraph(
            name = "Order.withCustomer",
            attributeNodes = @NamedAttributeNode("customer")),
    @NamedEntityGraph(
            name = "Order.withItems",
            attributeNodes = @NamedAttributeNode(value = "orderItems", subgraph = "orderItems")),
    @NamedEntityGraph(
            name = "Order.full",
            attributeNodes = {
                @NamedAttributeNode("customer"),
                @NamedAttributeNode(value = "orderItems", subgraph = "orderItems")
            },
            subgraphs =
                    @NamedSubgraph(
                            name = "orderItems",
                            attributeNodes = @NamedAttributeNode("product")))
})
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @Column(name = "order_date")
    private LocalDate orderDate;

    @OneToMany(mappedBy = "order", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @BatchSize(size = 10)
    private List<OrderItem> orderItems = new ArrayList<>();
}
