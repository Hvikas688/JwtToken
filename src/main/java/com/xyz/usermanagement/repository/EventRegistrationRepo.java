package com.xyz.usermanagement.repository;

import com.xyz.usermanagement.domain.EventRegistration;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRegistrationRepo extends JpaRepository<EventRegistration,Long> {
}
