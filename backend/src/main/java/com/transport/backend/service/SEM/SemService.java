package com.transport.backend.service.SEM;

import com.transport.backend.dto.SEM.ApiResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class SemService {

    private final RestTemplate restTemplate;

    public SemService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public ApiResponse<?> getSemResult() {

        String url = "http://sem_service:8000/sem";

        try {
            Map response = restTemplate.getForObject(url, Map.class);

            if (response == null) {
                return ApiResponse.error("Empty response from SEM service");
            }

            // 👉 Python trả error
            if (response.containsKey("error")) {
                return ApiResponse.warning(
                        response.get("error").toString(),
                        response);
            }

            return ApiResponse.success(response);

        } catch (Exception e) {
            return ApiResponse.error("SEM service failed: " + e.getMessage());
        }
    }
}