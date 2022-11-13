package com.example.jpamaven.repository;

import com.example.jpamaven.entity.OrderItem;
import org.apache.ibatis.annotations.*;

@Mapper
public interface OrderItemMapper {
    @Insert("INSERT INTO order_item (order_id, product_id) VALUES (#{order.id}, #{product.id})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insert(OrderItem orderItem);

    @Delete("DELETE order_item WHERE id = #{itemId}")
    void deleteById(@Param("itemId") int itemId);

    @Update("UPDATE order_item SET amount = #{amount} WHERE id= #{itemId}")
    void updateAmount(@Param("itemId") int itemId, @Param("amount") int amount);
}
