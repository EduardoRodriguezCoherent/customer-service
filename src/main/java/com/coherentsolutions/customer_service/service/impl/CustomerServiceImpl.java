package com.coherentsolutions.customer_service.service.impl;

import com.coherentsolutions.customer_service.dto.CustomerDto;
import com.coherentsolutions.customer_service.dto.RegisterCustomerDto;
import com.coherentsolutions.customer_service.mapper.CustomerMapper;
import com.coherentsolutions.customer_service.model.Customer;
import com.coherentsolutions.customer_service.repository.CustomerRepository;
import com.coherentsolutions.customer_service.service.CustomerService;
import com.coherentsolutions.customer_service.service.MembershipService;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@Service
public class CustomerServiceImpl implements CustomerService {

    private static final String DEFAULT_MEMBERSHIP_TYPE = "BASIC";
    private static final Logger log = LoggerFactory.getLogger(CustomerServiceImpl.class);

    private final CustomerRepository customerRepository;
    private final MembershipService membershipService;
    private final CustomerMapper customerMapper;

    public CustomerServiceImpl(CustomerRepository customerRepository, MembershipService membershipService, CustomerMapper customerMapper) {
        this.customerRepository = customerRepository;
        this.membershipService = membershipService;
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
    @Transactional
    public CustomerDto save(RegisterCustomerDto registerCustomerDto) {
        Customer customer = customerMapper.dtoToCustomer(registerCustomerDto);
        UUID membershipId = membershipService.createMembership(DEFAULT_MEMBERSHIP_TYPE);

        customer.setMembershipId(membershipId);

        return customerMapper.customerToCustomerDto(customerRepository.save(customer));
    }

    @Override
    public CustomerDto assignClub(Long id, String clubName) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found with id: " + id));
        customer.setClubName(clubName);
        return customerMapper.customerToCustomerDto(customerRepository.save(customer));
    }

    @Override
    public boolean existsByEmail(String email) {
        if (customerRepository.existsByEmail(email)) {
            log.warn("Customer with email {} already exists. Skipping this entry.", email);
            return true;
        }
        return false;
    }
}