package com.coherentsolutions.customer_service.mapper;

import com.coherentsolutions.customer_service.dto.CustomerDto;
import com.coherentsolutions.customer_service.dto.RegisterCustomerDto;
import com.coherentsolutions.customer_service.model.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CustomerMapper {

    @Mapping(target = "password", ignore = true)
    Customer dtoToCustomer(CustomerDto customerDto);

    @Mapping(target = "active", ignore = true)
    @Mapping(target = "membershipId", ignore = true)
    Customer dtoToCustomer(RegisterCustomerDto registerCustomerDto);

    CustomerDto customerToCustomerDto(Customer customer);
}
