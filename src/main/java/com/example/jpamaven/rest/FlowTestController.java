package com.example.jpamaven.rest;

import com.example.jpamaven.dto.OrderDto;
import com.example.jpamaven.service.FlowTestService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/flow-test")
public class FlowTestController {
    private final FlowTestService flowTestService;

    public FlowTestController(FlowTestService flowTestService) {
        this.flowTestService = flowTestService;
    }

    @GetMapping("/simple")
    public List<OrderDto> checkSimple() {
        return flowTestService.checkOrders();
    }
    @PostMapping("/simple")
    public ResponseEntity<Void> testSimple() {
        flowTestService.fetchAndUpdate();
        return ResponseEntity.ok().build();
    }

}
