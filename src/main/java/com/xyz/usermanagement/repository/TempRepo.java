package com.xyz.usermanagement.repository;

import com.xyz.usermanagement.domain.Temp;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TempRepo extends JpaRepository<Temp,Long> {
    /**
     * find the temp user recode by email and otp code
     * @param email
     * @param code
     * @return
     */
    Optional<Temp> findByEmailAndCode(String email, String  code);
}
