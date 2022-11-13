package com.example.jpamaven.rest;

import com.example.jpamaven.dto.OrderCreateDto;
import com.example.jpamaven.dto.OrderDto;
import com.example.jpamaven.dto.OrderSearchDto;
import com.example.jpamaven.service.OrderService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class OrderController {
    private final OrderService orderService;
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    // TODO: authorize. hasRole(admin) or session-id equals customerId
    @GetMapping("customer/{customerId}/order")
    public Page<OrderDto> findOrder(
            @PathVariable int customerId,
            @PageableDefault(size=30) Pageable pageable
    ) {
        return orderService.findByCustomerId(customerId, pageable);
    }

    @GetMapping("/order")
    public Page<OrderDto> searchOrder(
            OrderSearchDto orderSearchDto,
            @PageableDefault(size=30) Pageable pageable
    ) {
        return orderService.findAll(orderSearchDto, pageable);
    }
    @PostMapping("/order")
    public OrderDto createOrder(@RequestBody OrderCreateDto order) {
        // TODO: fill customerId from session
        return orderService.createOrder(order);
    }

    @GetMapping("/order/{orderId}")
    public ResponseEntity<OrderDto> findById(@PathVariable int orderId) {
        return orderService.findById(orderId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.badRequest().build());
    }
}
