package com.example.jpamaven.entity;

import com.example.jpamaven.dto.NativeQueryResultDto;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@SqlResultSetMapping(
        name="orderStatisticsByCustomerNameMapping",
        classes = @ConstructorResult(
                targetClass = NativeQueryResultDto.class,
                columns= {
                        @ColumnResult(name="name", type = String.class),
                        @ColumnResult(name="order_count", type = int.class)
                }
        )
)

@Entity
@Table(name = "orders")
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString
public class Order extends Auditable {
    @Id
    @GeneratedValue
    private Integer id;

    @ManyToOne
    @JoinColumn(name="customer_id")
    private Customer customer;


    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "order")
    private Set<OrderItem> orderItems = new HashSet<>();

    public int getCost() {
        int sum = 0;
        for (OrderItem item : orderItems) {
            sum = sum + item.getAmount() * item.getProduct().getListPrice();
        }
        return sum;
    }
    public void addItem(OrderItem orderItem) {
        orderItem.setOrder(this);
        this.orderItems.add(orderItem);
    }
}

