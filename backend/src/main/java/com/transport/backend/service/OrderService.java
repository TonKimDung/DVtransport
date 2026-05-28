package com.transport.backend.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.transport.backend.dto.order.OrderRequest;
import com.transport.backend.dto.order.OrderResponse;
import com.transport.backend.entity.Contract;
import com.transport.backend.entity.Customer;
import com.transport.backend.entity.Order;
import com.transport.backend.entity.Route;
import com.transport.backend.entity.Vehicle;
import com.transport.backend.repository.ContractRepository;
import com.transport.backend.repository.CustomerRepository;
import com.transport.backend.repository.OrderRepository;
import com.transport.backend.repository.RouteRepository;
import com.transport.backend.repository.VehicleRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    private final ContractRepository contractRepository;
    private final RouteRepository routeRepository;
    private final VehicleRepository vehicleRepository;

    private OrderResponse toResponse(Order order) {
        return OrderResponse.builder()
                .id(order.getId())
                .orderCode(order.getOrderCode())
                .customerId(order.getCustomer() != null ? order.getCustomer().getId() : null)
                .customerName(order.getCustomer() != null ? order.getCustomer().getName() : null)
                .contractId(order.getContract() != null ? order.getContract().getId() : null)
                .routeId(order.getRoute() != null ? order.getRoute().getId() : null)
                .routeName(order.getRoute() != null ? order.getRoute().getRouteName() : null)
                .cargoType(order.getCargoType())
                .weight(order.getWeight())
                .quantity(order.getQuantity())
                .pickupAddress(order.getPickupAddress())
                .deliveryAddress(order.getDeliveryAddress())
                .totalAmount(order.getTotalAmount())
                .status(order.getStatus())
                .createdAt(order.getCreatedAt())
                .build();
    }

    private Order getOrderEntityById(Integer id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy đơn"));
    }

    public List<OrderResponse> getAllOrders() {
        return orderRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public OrderResponse getOrderById(Integer id) {
        return toResponse(getOrderEntityById(id));
    }

    public OrderResponse createOrder(OrderRequest request) {
        if (orderRepository.existsByOrderCode(request.getOrderCode())) {
            throw new RuntimeException("Mã đơn đã tồn tại");
        }

        Customer customer = customerRepository.findById(request.getCustomerId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy khách hàng"));

        Contract contract = null;
        if (request.getContractId() != null) {
            contract = contractRepository.findById(request.getContractId())
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy hợp đồng"));
        }

        Route route = null;
        if (request.getRouteId() != null) {
            route = routeRepository.findById(request.getRouteId())
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy tuyến"));
        }

        Order order = Order.builder()
                .orderCode(request.getOrderCode())
                .customer(customer)
                .contract(contract)
                .route(route)
                .cargoType(request.getCargoType())
                .weight(request.getWeight())
                .quantity(request.getQuantity())
                .pickupAddress(request.getPickupAddress())
                .deliveryAddress(request.getDeliveryAddress())
                .totalAmount(request.getTotalAmount())
                .status(request.getStatus() != null ? request.getStatus() : "CREATED")
                .createdAt(LocalDateTime.now())
                .build();

        return toResponse(orderRepository.save(order));
    }

    public OrderResponse updateOrder(Integer id, OrderRequest request) {
        Order order = getOrderEntityById(id);

        Customer customer = customerRepository.findById(request.getCustomerId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy khách hàng"));

        Contract contract = null;
        if (request.getContractId() != null) {
            contract = contractRepository.findById(request.getContractId())
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy hợp đồng"));
        }

        Route route = null;
        if (request.getRouteId() != null) {
            route = routeRepository.findById(request.getRouteId())
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy tuyến"));
        }

        order.setOrderCode(request.getOrderCode());
        order.setCustomer(customer);
        order.setContract(contract);
        order.setRoute(route);
        order.setCargoType(request.getCargoType());
        order.setWeight(request.getWeight());
        order.setQuantity(request.getQuantity());
        order.setPickupAddress(request.getPickupAddress());
        order.setDeliveryAddress(request.getDeliveryAddress());
        order.setTotalAmount(request.getTotalAmount());
        order.setStatus(request.getStatus());

        return toResponse(orderRepository.save(order));
    }

    public OrderResponse updateOrderStatus(Integer id, String status) {
        Order order = getOrderEntityById(id);
        order.setStatus(status);
        return toResponse(orderRepository.save(order));
    }

    public List<OrderResponse> getOrdersByStatus(String status) {
    return orderRepository.findByStatus(status)
            .stream()
            .map(this::toResponse)
            .toList();
}

    public List<Vehicle> suggestVehicles(Integer orderId) {
        Order order = getOrderEntityById(orderId);

        return vehicleRepository.findByCapacityGreaterThanEqualAndStatus(
                order.getWeight(),
                "ACTIVE");
    }

    public List<Order> getDailyPlan(LocalDate date) {
        LocalDateTime start = date.atStartOfDay();
        LocalDateTime end = date.plusDays(1).atStartOfDay();

        return orderRepository.findByCreatedAtBetween(start, end);
    }

    public List<Order> getWeeklyPlan(LocalDate startDate) {
        LocalDateTime start = startDate.atStartOfDay();
        LocalDateTime end = startDate.plusDays(7).atStartOfDay();

        return orderRepository.findByCreatedAtBetween(start, end);
    }

    public void deleteOrder(Integer id) {
        Order order = getOrderEntityById(id);
        orderRepository.delete(order);
    }
}