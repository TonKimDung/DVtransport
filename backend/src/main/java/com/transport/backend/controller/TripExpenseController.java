package com.transport.backend.controller;

import com.transport.backend.dto.trip_expense.TripExpenseRequest;
import com.transport.backend.dto.trip_expense.TripExpenseResponse;
import com.transport.backend.service.TripExpenseService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/trip-expenses")
@RequiredArgsConstructor
@CrossOrigin("*")
public class TripExpenseController {

    private final TripExpenseService tripExpenseService;

    @GetMapping
    public List<TripExpenseResponse> getAllExpenses() {
        return tripExpenseService.getAllExpenses();
    }

    @GetMapping("/{id}")
    public TripExpenseResponse getExpenseById(@PathVariable Integer id) {
        return tripExpenseService.getExpenseById(id);
    }

    @PostMapping
    public TripExpenseResponse createExpense(@RequestBody TripExpenseRequest request) {
        return tripExpenseService.createExpense(request);
    }

    @PutMapping("/{id}")
    public TripExpenseResponse updateExpense(@PathVariable Integer id, @RequestBody TripExpenseRequest request) {
        return tripExpenseService.updateExpense(id, request);
    }

    @DeleteMapping("/{id}")
    public String deleteExpense(@PathVariable Integer id) {
        tripExpenseService.deleteExpense(id);
        return "Xóa chi phí thành công";
    }
}