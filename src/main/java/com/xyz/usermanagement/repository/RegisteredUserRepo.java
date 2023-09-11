package com.xyz.usermanagement.repository;

import com.xyz.usermanagement.domain.RegisteredUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RegisteredUserRepo extends JpaRepository<RegisteredUser,Long> {

    /**
     * Find user by email
     * @param email
     * @return
     */
    Optional<RegisteredUser> findByEmail(String email);
}
