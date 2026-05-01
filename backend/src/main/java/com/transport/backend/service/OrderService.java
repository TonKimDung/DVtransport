package com.transport.backend.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.transport.backend.dto.order.OrderRequest;
import com.transport.backend.entity.Contract;
import com.transport.backend.entity.Customer;
import com.transport.backend.entity.Order;
import com.transport.backend.entity.Route;
import com.transport.backend.entity.Vehicle;
import com.transport.backend.repository.OrderRepository;
import com.transport.backend.repository.RouteRepository;
import com.transport.backend.repository.VehicleRepository;
import com.transport.backend.repository.ContractRepository;
import com.transport.backend.repository.CustomerRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final VehicleRepository vehicleRepository;
    private final CustomerRepository customerRepository;
    private final ContractRepository contractRepository;
    private final RouteRepository routeRepository;

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Order getOrderById(Integer id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy đơn vận chuyển"));
    }

    public Order createOrder(OrderRequest request) {
        if (orderRepository.existsByOrderCode(request.getOrderCode())) {
            throw new RuntimeException("Mã đơn vận chuyển đã tồn tại");
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
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy tuyến đường"));
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
                .status(request.getStatus() != null ? request.getStatus() : "Chờ nhận")
                .build();

        return orderRepository.save(order);
    }

    public Order updateOrder(Integer id, OrderRequest request) {
        Order order = getOrderById(id);

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
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy tuyến đường"));
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

        return orderRepository.save(order);
    }

    public Order updateOrderStatus(Integer id, String status) {
        Order order = getOrderById(id);
        order.setStatus(status);
        return orderRepository.save(order);
    }

    public List<Order> getOrdersByStatus(String status) {
        return orderRepository.findByStatus(status);
    }

    public List<Vehicle> suggestVehicles(Integer orderId) {
        Order order = getOrderById(orderId);

        return vehicleRepository.findByCapacityGreaterThanEqualAndStatus(
                order.getWeight(),
                "Đang hoạt động");
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
        Order order = getOrderById(id);
        orderRepository.delete(order);
    }
}