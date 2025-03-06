package com.coherentsolutions.customer_service.service.impl;

import com.coherentsolutions.customer_service.service.MembershipService;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.UUID;

@FeignClient(name = "membership-service", path = "/api/memberships")
@Service
public interface MembershipClientImpl extends MembershipService {

    @Override
    @PostMapping
    UUID createMembership(@RequestBody String membershipType);
}
