package com.coherentsolutions.customer_service.service;

import com.coherentsolutions.customer_service.dto.CustomerDto;
import com.coherentsolutions.customer_service.dto.RegisterCustomerDto;

import java.util.List;

public interface CustomerService {

    List<CustomerDto> getAll();

    CustomerDto getById(Long id);

    CustomerDto save(RegisterCustomerDto registerCustomerDto);
}
