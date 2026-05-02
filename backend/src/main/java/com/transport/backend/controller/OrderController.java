package com.transport.backend.controller;

import com.transport.backend.dto.order.OrderRequest;
import com.transport.backend.dto.order.OrderResponse;
import com.transport.backend.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
@CrossOrigin("*")
public class OrderController {

    private final OrderService orderService;

    @GetMapping
    public List<OrderResponse> getAllOrders() {
        return orderService.getAllOrders();
    }

    @GetMapping("/{id}")
    public OrderResponse getOrderById(@PathVariable Integer id) {
        return orderService.getOrderById(id);
    }

    @PostMapping
    public OrderResponse createOrder(@RequestBody OrderRequest request) {
        return orderService.createOrder(request);
    }

    @PutMapping("/{id}")
    public OrderResponse updateOrder(
            @PathVariable Integer id,
            @RequestBody OrderRequest request
    ) {
        return orderService.updateOrder(id, request);
    }

    @DeleteMapping("/{id}")
    public String deleteOrder(@PathVariable Integer id) {
        orderService.deleteOrder(id);
        return "Xóa đơn thành công";
    }
}