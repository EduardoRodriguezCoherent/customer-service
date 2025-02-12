package com.coherentsolutions.customer_service.service.impl;

import com.coherentsolutions.customer_service.dto.CustomerDto;
import com.coherentsolutions.customer_service.dto.RegisterCustomerDto;
import com.coherentsolutions.customer_service.mapper.CustomerMapper;
import com.coherentsolutions.customer_service.model.Customer;
import com.coherentsolutions.customer_service.repository.CustomerRepository;
import com.coherentsolutions.customer_service.service.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    public CustomerServiceImpl(CustomerRepository customerRepository, CustomerMapper customerMapper) {
        this.customerRepository = customerRepository;
        this.customerMapper = customerMapper;
    }

    @Override
    public List<CustomerDto> getAll() {
        return customerRepository.findAll().stream()
                .map(customerMapper::customerToCustomerDto)
                .toList();
    }

    @Override
    public CustomerDto getById(Long id) {
        return customerRepository.findById(id)
                .map(customerMapper::customerToCustomerDto)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found with id: " + id));
    }

    @Override
    public CustomerDto save(RegisterCustomerDto registerCustomerDto) {
        Customer customer = customerMapper.dtoToCustomer(registerCustomerDto);

        customer.setActive(true); // Set the customer as active by default
        customer.setMembershipId(UUID.randomUUID()); // Generate a new membership ID
        customer.setClubName("Default Club"); // Assign a default club name

        return customerMapper.customerToCustomerDto(customerRepository.save(customer));
    }
}
