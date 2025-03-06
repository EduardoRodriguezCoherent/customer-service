package com.coherentsolutions.customer_service.service;

import java.util.UUID;

public interface MembershipService {
    UUID createMembership(String membershipType);
}
