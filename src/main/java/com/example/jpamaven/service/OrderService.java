package com.example.jpamaven.service;

import com.example.jpamaven.dto.OrderCreateDto;
import com.example.jpamaven.dto.OrderDto;
import com.example.jpamaven.dto.OrderSearchDto;
import com.example.jpamaven.entity.Order;
import com.example.jpamaven.repository.OrderRepository;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
public class OrderService {
    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public Page<OrderDto> findByCustomerId(int customerId, Pageable pageable) {
        return orderRepository
                .findByCustomer_Id(customerId, pageable)
                .map(OrderDto::new)
                ;
    }
    public Page<OrderDto> findAll(OrderSearchDto orderSearchDto, Pageable pageable) {
        Example<Order> example = Example.of(orderSearchDto.toOrder());
        return orderRepository.findAll(example, pageable)
                .map(OrderDto::new)
                ;
    }
    public Optional<OrderDto> findById(int orderId) {
        return orderRepository.findById(orderId).map(OrderDto::new);
    }

    public OrderDto createOrder(OrderCreateDto orderCreateDto) {
        Order order = orderCreateDto.toOrder();
        return new OrderDto(orderRepository.save(order));
    }
}
