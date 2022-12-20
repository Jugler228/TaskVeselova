package com.example.demo.controller;

import com.example.demo.domain.Customer;
import com.example.demo.exception.NotFoundException;
import com.example.demo.service.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/home")
public class MainController {
    private final CustomerService customerService;

    public MainController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("{id}")
    public Customer getOne(@PathVariable("id") Long id) {
        return customerService.getById(id);
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    Map<String, String> errorHandler(Exception e) {
        return Map.of("error", e.getMessage());
    }
}
