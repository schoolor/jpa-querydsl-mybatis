package com.example.jpamaven.dto;

import com.example.jpamaven.entity.Customer;
import com.example.jpamaven.entity.Order;
import com.example.jpamaven.entity.Product;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
public class OrderSearchDto {
    private Integer customerId;
    private String customerName;

    public Order toOrder() {
        Order order = new Order();
        Customer customer = new Customer();
        if (null != customerId) customer.setId(customerId);
        if (null != customerName) customer.setName(customerName);
        order.setCustomer(customer);
        return order;
    }
}
