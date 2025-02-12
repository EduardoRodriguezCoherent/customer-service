package com.coherentsolutions.customer_service.dto;

import java.time.LocalDate;
import java.util.UUID;

public record CustomerDto(
        Long id,
        String name,
        String lastName,
        String email,
        String phone,
        String location,
        LocalDate birthDate,
        boolean isActive,
        UUID membershipId,
        String clubName
) {
}
