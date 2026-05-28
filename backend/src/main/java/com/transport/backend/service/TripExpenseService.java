package com.transport.backend.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.transport.backend.dto.trip_expense.TripExpenseRequest;
import com.transport.backend.dto.trip_expense.TripExpenseResponse;
import com.transport.backend.entity.Trip;
import com.transport.backend.entity.TripExpense;
import com.transport.backend.repository.TripExpenseRepository;
import com.transport.backend.repository.TripRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TripExpenseService {

    private final TripExpenseRepository tripExpenseRepository;
    private final TripRepository tripRepository;

    private TripExpenseResponse toResponse(TripExpense expense) {
        return TripExpenseResponse.builder()
                .id(expense.getId())
                .tripId(expense.getTrip() != null ? expense.getTrip().getId() : null)
                .tripCode(expense.getTrip() != null ? expense.getTrip().getTripCode() : null)
                .expenseType(expense.getExpenseType())
                .amount(expense.getAmount())
                .description(expense.getDescription())
                .createdAt(expense.getCreatedAt())
                .build();
    }

    public List<TripExpenseResponse> getAllExpenses() {
        return tripExpenseRepository.findAll().stream().map(this::toResponse).toList();
    }

    public TripExpenseResponse getExpenseById(Integer id) {
        TripExpense expense = tripExpenseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy chi phí"));
        return toResponse(expense);
    }

    public TripExpenseResponse createExpense(TripExpenseRequest request) {
        Trip trip = tripRepository.findById(request.getTripId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy chuyến đi"));

        TripExpense expense = TripExpense.builder()
                .trip(trip)
                .expenseType(request.getExpenseType())
                .amount(request.getAmount())
                .description(request.getDescription())
                .build();

        return toResponse(tripExpenseRepository.save(expense));
    }

    public TripExpenseResponse updateExpense(Integer id, TripExpenseRequest request) {
        TripExpense expense = tripExpenseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy chi phí"));

        Trip trip = tripRepository.findById(request.getTripId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy chuyến đi"));

        expense.setTrip(trip);
        expense.setExpenseType(request.getExpenseType());
        expense.setAmount(request.getAmount());
        expense.setDescription(request.getDescription());

        return toResponse(tripExpenseRepository.save(expense));
    }

    public void deleteExpense(Integer id) {
        TripExpense expense = tripExpenseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy chi phí"));
        tripExpenseRepository.delete(expense);
    }
}