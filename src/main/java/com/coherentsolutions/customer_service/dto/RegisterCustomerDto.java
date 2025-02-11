package com.coherentsolutions.customer_service.dto;

import java.time.LocalDate;

public record RegisterCustomerDto(
        String name,
        String lastName,
        String email,
        String password,
        String phone,
        String location,
        LocalDate birthDate
) {
}
