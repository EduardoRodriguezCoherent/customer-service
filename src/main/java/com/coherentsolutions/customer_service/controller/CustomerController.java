package com.coherentsolutions.customer_service.controller;

import com.coherentsolutions.customer_service.dto.CustomerDto;
import com.coherentsolutions.customer_service.dto.RegisterCustomerDto;
import com.coherentsolutions.customer_service.service.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    public ResponseEntity<List<CustomerDto>> getAllCustomers() {
        return ResponseEntity.ok(customerService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerDto> getCustomerById(@PathVariable Long id) {
        return ResponseEntity.ok(customerService.getById(id));
    }

    @PostMapping("/register")
    public ResponseEntity<CustomerDto> registerCustomer(@RequestBody RegisterCustomerDto registerCustomerDto) {
        CustomerDto savedCustomer = customerService.save(registerCustomerDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedCustomer);
    }

    @PostMapping("/assign-club")
    public ResponseEntity<CustomerDto> assignClubToCustomer(@RequestParam Long userId, @RequestParam String clubName) {
        CustomerDto customerDtoWithClub = customerService.assignClub(userId, clubName);
        return ResponseEntity.ok(customerDtoWithClub);
    }
}
