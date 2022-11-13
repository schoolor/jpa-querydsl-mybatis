package com.example.jpamaven.dto;

import com.example.jpamaven.entity.Customer;
import com.example.jpamaven.entity.Order;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Set;

@Data
@EqualsAndHashCode
public class OrderCreateDto {
    private int customerId;
    private Set<OrderItemDto> items;
    public Order toOrder() {
        Order order = new Order();
        for (OrderItemDto itemDto : this.items) {
            order.addItem(itemDto.toOrderItem());
        }
        order.setCustomer(new Customer(this.customerId));
        return order;
    }
}
