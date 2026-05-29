package com.transport.backend.service;

import com.transport.backend.dto.driverbasesalary.DriverBaseSalaryRequest;
import com.transport.backend.dto.driverbasesalary.DriverBaseSalaryResponse;
import com.transport.backend.entity.Driver;
import com.transport.backend.entity.DriverBaseSalary;
import com.transport.backend.entity.DriverLicense;
import com.transport.backend.repository.DriverBaseSalaryRepository;
import com.transport.backend.repository.DriverLicenseRepository;
import com.transport.backend.repository.DriverRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DriverBaseSalaryService {

        private final DriverBaseSalaryRepository driverBaseSalaryRepository;
        private final DriverRepository driverRepository;
        private final DriverLicenseRepository driverLicenseRepository;

        private DriverBaseSalaryResponse toResponse(DriverBaseSalary salary) {
                return DriverBaseSalaryResponse.builder()
                                .id(salary.getId())

                                .driverId(salary.getDriver() != null ? salary.getDriver().getId() : null)
                                .driverName(salary.getDriver() != null ? salary.getDriver().getFullName() : null)

                                .driverLicenseId(salary.getDriverLicense() != null ? salary.getDriverLicense().getId()
                                                : null)
                                .licenseNumber(salary.getDriverLicense() != null
                                                ? salary.getDriverLicense().getLicenseNumber()
                                                : null)
                                .licenseClass(salary.getDriverLicense() != null
                                                ? salary.getDriverLicense().getLicenseType().getLicenseClass()
                                                : null)

                                .baseSalary(salary.getBaseSalary())
                                .status(salary.getStatus())
                                .createdAt(salary.getCreatedAt())
                                .build();
        }

        public List<DriverBaseSalaryResponse> getAllBaseSalaries() {
                return driverBaseSalaryRepository.findAll()
                                .stream()
                                .map(this::toResponse)
                                .toList();
        }

        public DriverBaseSalaryResponse getBaseSalaryById(Integer id) {
                DriverBaseSalary salary = driverBaseSalaryRepository.findById(id)
                                .orElseThrow(() -> new RuntimeException("Không tìm thấy lương cứng"));
                return toResponse(salary);
        }

        public List<DriverBaseSalaryResponse> getBaseSalariesByDriver(Integer driverId) {
                return driverBaseSalaryRepository.findByDriverId(driverId)
                                .stream()
                                .map(this::toResponse)
                                .toList();
        }

        public DriverBaseSalaryResponse createBaseSalary(DriverBaseSalaryRequest request) {
                Driver driver = driverRepository.findById(request.getDriverId())
                                .orElseThrow(() -> new RuntimeException("Không tìm thấy tài xế"));

                DriverLicense license = null;
                if (request.getDriverLicenseId() != null) {
                        license = driverLicenseRepository.findById(request.getDriverLicenseId())
                                        .orElseThrow(() -> new RuntimeException("Không tìm thấy bằng lái"));
                }

                DriverBaseSalary salary = DriverBaseSalary.builder()
                                .driver(driver)
                                .driverLicense(license)
                                .baseSalary(request.getBaseSalary())
                                .status(request.getStatus() != null ? request.getStatus() : "Đang áp dụng")
                                .build();

                return toResponse(driverBaseSalaryRepository.save(salary));
        }

        public DriverBaseSalaryResponse updateBaseSalary(Integer id,
                        DriverBaseSalaryRequest request) {
                DriverBaseSalary salary = driverBaseSalaryRepository.findById(id)
                                .orElseThrow(() -> new RuntimeException("Không tìm thấy lương cứng"));

                Driver driver = driverRepository.findById(request.getDriverId())
                                .orElseThrow(() -> new RuntimeException("Không tìm thấy tài xế"));

                DriverLicense license = null;
                if (request.getDriverLicenseId() != null) {
                        license = driverLicenseRepository.findById(request.getDriverLicenseId())
                                        .orElseThrow(() -> new RuntimeException("Không tìm thấy bằng lái"));
                }

                salary.setDriver(driver);
                salary.setDriverLicense(license);
                salary.setBaseSalary(request.getBaseSalary());
                salary.setStatus(request.getStatus());

                return toResponse(driverBaseSalaryRepository.save(salary));
        }

        public void deleteBaseSalary(Integer id) {
                DriverBaseSalary salary = driverBaseSalaryRepository.findById(id)
                                .orElseThrow(() -> new RuntimeException("Không tìm thấy lương cứng"));
                driverBaseSalaryRepository.delete(salary);
        }
}