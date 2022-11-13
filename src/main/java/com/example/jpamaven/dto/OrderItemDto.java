package com.example.jpamaven.dto;

import com.example.jpamaven.entity.OrderItem;
import com.example.jpamaven.entity.Product;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
public class OrderItemDto {
    private int productId;
    private String productName;
    private int listPrice;
    private int amount;

    public OrderItemDto(OrderItem orderItem) {
        this.productId = orderItem.getProduct().getId();
        this.productName = orderItem.getProduct().getName();
        this.listPrice = orderItem.getProduct().getListPrice();
        this.amount = orderItem.getAmount();
    }
    public OrderItem toOrderItem() {
        return new OrderItem(
                new Product(this.productId),
                this.amount
        );
    }
}
