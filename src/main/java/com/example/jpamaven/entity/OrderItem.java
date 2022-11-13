package com.example.jpamaven.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString
public class OrderItem extends Auditable {
    @Id
    @GeneratedValue
    private Integer id;

    @ManyToOne
    @JoinColumn(name="order_id")
    private Order order;

    @ManyToOne
    @JoinColumn(name="product_id")
    private Product product;

    private int amount;

    public OrderItem() {}

    public OrderItem(Product product, int amount) {
        this.product = product;
        this.amount = amount;
    }
}
