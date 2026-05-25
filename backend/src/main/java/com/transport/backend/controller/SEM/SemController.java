package com.transport.backend.controller.SEM;

import com.transport.backend.service.SEM.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/sem")
public class SEMController {

    @Autowired
    private SEMClientService semClientService;

    @GetMapping("/analyze")
    public String analyze() {

        return semClientService.analyzeSEM();
    }
}