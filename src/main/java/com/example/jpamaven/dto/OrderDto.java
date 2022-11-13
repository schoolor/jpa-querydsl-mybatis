package com.example.jpamaven.dto;

import com.example.jpamaven.entity.Order;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.ZonedDateTime;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@EqualsAndHashCode
public class OrderDto {
    int customerId;
    String customerName;
    int orderId;
    Set<OrderItemDto> items;
    int cost;

    ZonedDateTime createdAt;
    ZonedDateTime updatedAt;
    public OrderDto(Order order) {
        this.customerId = order.getCustomer().getId();
        this.customerName = order.getCustomer().getName();
        this.orderId = order.getId();
        this.items = order.getOrderItems().stream()
                .map(OrderItemDto::new)
                .collect(Collectors.toSet());
        this.cost = order.getCost();
    }
}
