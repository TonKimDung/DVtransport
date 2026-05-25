package com.transport.backend.service.SEM;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class SEMClientService {

    private final RestTemplate restTemplate;

    public SEMClientService() {
        this.restTemplate = new RestTemplate();
    }

    public String analyzeSEM() {

        String url = "http://localhost:8000/sem/analyze";

        return restTemplate.getForObject(
                url,
                String.class);
    }
}