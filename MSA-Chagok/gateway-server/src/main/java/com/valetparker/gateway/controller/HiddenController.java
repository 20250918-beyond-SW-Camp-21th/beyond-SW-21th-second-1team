package com.valetparker.gateway.controller;

import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Hidden
public class HiddenController {
    @GetMapping("/hiddenEndpoint")
    public String hiddenEndpoint() {
        return "This endpoint will be hidden.";
    }
}