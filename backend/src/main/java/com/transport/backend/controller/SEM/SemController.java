package com.transport.backend.controller.SEM;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.transport.backend.service.SEM.SEMClientService;

@RestController
@RequestMapping("/api/sem")
public class SemController {

    @Autowired
    private SEMClientService semClientService;

    @GetMapping("/analyze")
    public String analyze() {

        return semClientService.analyzeSEM();
    }
}