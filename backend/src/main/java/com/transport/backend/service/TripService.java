package com.transport.backend.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.transport.backend.dto.order.OrderSimpleResponse;
import com.transport.backend.dto.trip.CreateTripRequest;
import com.transport.backend.dto.trip.TripResponse;
import com.transport.backend.dto.trip.VehicleSuggestionResponse;
import com.transport.backend.entity.Driver;
import com.transport.backend.entity.Order;
import com.transport.backend.entity.Route;
import com.transport.backend.entity.Trip;
import com.transport.backend.entity.TripOrder;
import com.transport.backend.entity.Vehicle;
import com.transport.backend.entity.VehicleDriverAssignment;
import com.transport.backend.repository.OrderRepository;
import com.transport.backend.repository.RouteRepository;
import com.transport.backend.repository.TripOrderRepository;
import com.transport.backend.repository.TripRepository;
import com.transport.backend.repository.VehicleRepository;
import com.transport.backend.repository.driver_assignment.VehicleDriverAssignmentRepository;

@Service
public class TripService {

        private final TripRepository tripRepo;
        private final RouteRepository routeRepo;
        private final VehicleRepository vehicleRepo;
        private final OrderRepository orderRepo;
        private final TripOrderRepository tripOrderRepo;
        private final VehicleDriverAssignmentRepository assignmentRepo;

        public TripService(
                        TripRepository tripRepo,
                        RouteRepository routeRepo,
                        VehicleRepository vehicleRepo,
                        OrderRepository orderRepo,
                        TripOrderRepository tripOrderRepo,
                        VehicleDriverAssignmentRepository assignmentRepo) {
                this.tripRepo = tripRepo;
                this.routeRepo = routeRepo;
                this.vehicleRepo = vehicleRepo;
                this.orderRepo = orderRepo;
                this.tripOrderRepo = tripOrderRepo;
                this.assignmentRepo = assignmentRepo;
        }

        // =====================================================
        // GET ORDERS BY ROUTE
        // =====================================================

        public List<OrderSimpleResponse> getPendingOrdersByRoute(
                        Integer routeId) {

                List<Order> orders = orderRepo.findByRoute_IdAndStatus(
                                routeId,
                                "CREATED");

                return orders.stream()
                                .map(this::mapOrder)
                                .toList();
        }
        // =====================================================
        // SUGGEST VEHICLES
        // =====================================================

        public List<VehicleSuggestionResponse> suggestVehicles(
                        Integer routeId) {

                List<Order> orders = orderRepo.findByRoute_IdAndStatus(
                                routeId,
                                "CREATED");

                BigDecimal totalWeight = orders.stream()
                                .map(Order::getWeight)
                                .reduce(BigDecimal.ZERO, BigDecimal::add);

                List<Vehicle> vehicles = vehicleRepo.findByStatusAndCapacityGreaterThanEqual(
                                "Hoạt động",
                                totalWeight);

                return vehicles.stream().map(v -> {

                        VehicleSuggestionResponse res = new VehicleSuggestionResponse();

                        res.setVehicleId(v.getId());
                        res.setPlateNumber(v.getPlateNumber());
                        res.setVehicleType(v.getVehicleType());
                        res.setCapacity(v.getCapacity());
                        res.setCurrentLocation(v.getCurrentLocation());

                        assignmentRepo
                                        .findTopByVehicle_IdOrderByAssignedDateDesc(v.getId())
                                        .ifPresent(a -> {
                                                res.setDriverName(
                                                                a.getDriver().getFullName());
                                        });

                        return res;

                }).toList();
        }

        // =====================================================
        // CREATE TRIP
        // =====================================================

        @Transactional
        public TripResponse createTrip(CreateTripRequest req) {

                try {

                        System.out.println("REQ = " + req.getVehicleId());

                        Route route = routeRepo.findById(req.getRouteId())
                                        .orElseThrow(() -> new RuntimeException("Route not found"));

                        Vehicle vehicle = vehicleRepo.findById(req.getVehicleId())
                                        .orElseThrow(() -> new RuntimeException("Vehicle not found"));

                        System.out.println("VEHICLE STATUS = " + vehicle.getStatus());

                        if (!"Hoạt động".equals(vehicle.getStatus())) {
                                throw new RuntimeException(
                                                "Vehicle not available: " + vehicle.getStatus());
                        }

                        VehicleDriverAssignment assignment = assignmentRepo
                                        .findTopByVehicle_IdOrderByAssignedDateDesc(vehicle.getId())
                                        .orElseThrow(() -> new RuntimeException(
                                                        "Vehicle chưa có tài xế"));

                        Driver driver = assignment.getDriver();

                        List<Order> orders = orderRepo.findAllById(req.getOrderIds());

                        System.out.println("ORDERS SIZE = " + orders.size());

                        Trip trip = new Trip();

                        trip.setTripCode("TRIP-" + System.currentTimeMillis());
                        trip.setRoute(route);
                        trip.setVehicle(vehicle);
                        trip.setDriver(driver);
                        trip.setDepartureTime(req.getDepartureTime());
                        trip.setStatus("CREATED");

                        Trip savedTrip = tripRepo.saveAndFlush(trip);

                        System.out.println("TRIP SAVED = " + savedTrip.getId());

                        for (Order order : orders) {

                                TripOrder tripOrder = new TripOrder();

                                tripOrder.setTrip(savedTrip);
                                tripOrder.setOrder(order);
                                tripOrder.setAllocatedWeight(order.getWeight());
                                tripOrder.setAllocatedQuantity(order.getQuantity());
                                tripOrder.setStatus("ASSIGNED");

                                tripOrderRepo.save(tripOrder);

                                order.setStatus("ASSIGNED");

                                orderRepo.save(order);
                        }

                        vehicle.setStatus("IN_TRIP");

                        vehicleRepo.save(vehicle);

                        return map(savedTrip);

                } catch (Exception e) {

                        e.printStackTrace();

                        throw e;
                }
        }
        // =====================================================
        // GET ALL
        // =====================================================

        public List<TripResponse> getAll() {
                return tripRepo.findAll().stream()
                                .map(this::map)
                                .toList();
        }

        // =====================================================
        // GET DETAIL
        // =====================================================

        public TripResponse getById(Integer id) {

                Trip trip = tripRepo.findById(id)
                                .orElseThrow(() -> new RuntimeException("Trip not found"));

                return map(trip);
        }

        // =====================================================
        // MAPPER
        // =====================================================

        private TripResponse map(Trip t) {

                TripResponse res = new TripResponse();

                res.setId(t.getId());
                res.setTripCode(t.getTripCode());

                // ROUTE
                if (t.getRoute() != null) {

                        res.setRouteId(
                                        t.getRoute().getId());

                        res.setRouteName(
                                        t.getRoute()
                                                        .getRouteName());
                }

                // VEHICLE
                if (t.getVehicle() != null) {

                        res.setVehicleId(
                                        t.getVehicle().getId());

                        res.setPlateNumber(
                                        t.getVehicle()
                                                        .getPlateNumber());
                }

                // DRIVER
                if (t.getDriver() != null) {

                        res.setDriverId(
                                        t.getDriver().getId());

                        res.setDriverName(
                                        t.getDriver()
                                                        .getFullName());
                }

                res.setDepartureTime(
                                t.getDepartureTime());

                res.setArrivalTime(
                                t.getArrivalTime());

                res.setStatus(
                                t.getStatus());

                return res;
        }

        private OrderSimpleResponse mapOrder(Order o) {

                OrderSimpleResponse res = new OrderSimpleResponse();

                res.setId(o.getId());

                res.setOrderCode(o.getOrderCode());

                res.setCargoType(o.getCargoType());

                res.setWeight(o.getWeight());

                res.setQuantity(o.getQuantity());

                res.setPickupAddress(
                                o.getPickupAddress());

                res.setDeliveryAddress(
                                o.getDeliveryAddress());

                res.setStatus(o.getStatus());

                res.setCreatedAt(
                                o.getCreatedAt());

                if (o.getRoute() != null) {

                        res.setRouteId(
                                        o.getRoute().getId());

                        res.setRouteName(
                                        o.getRoute().getRouteName());
                }

                return res;
        }
}