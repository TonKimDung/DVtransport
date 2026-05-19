package com.transport.backend.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.transport.backend.dto.order.OrderRequest;
import com.transport.backend.dto.order.OrderResponse;
import com.transport.backend.entity.Order;
import com.transport.backend.entity.Vehicle;
import com.transport.backend.service.OrderService;

import lombok.RequiredArgsConstructor;

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

    @PatchMapping("/{id}/status")
    public OrderResponse updateOrderStatus(
            @PathVariable Integer id,
            @RequestParam String status
    ) {
        return orderService.updateOrderStatus(id, status);
    }


    @GetMapping("/{id}/suggest-vehicles")
    public List<Vehicle> suggestVehicles(@PathVariable Integer id) {
        return orderService.suggestVehicles(id);
    }

    @GetMapping("/plan/day")
    public List<Order> getDailyPlan(@RequestParam String date) {
        return orderService.getDailyPlan(LocalDate.parse(date));
    }

    @GetMapping("/plan/week")
    public List<Order> getWeeklyPlan(@RequestParam String startDate) {
        return orderService.getWeeklyPlan(LocalDate.parse(startDate));
    }

    @DeleteMapping("/{id}")
    public String deleteOrder(@PathVariable Integer id) {
        orderService.deleteOrder(id);
        return "Xóa đơn thành công";
    }
}