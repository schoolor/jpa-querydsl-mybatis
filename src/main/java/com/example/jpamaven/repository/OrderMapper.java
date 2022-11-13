package com.example.jpamaven.repository;

import com.example.jpamaven.entity.Order;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;

@Mapper
public interface OrderMapper {
    @Insert("INSERT INTO orders (customer_id) VALUES (${customer.id})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insert(Order order);
}
