package com.xyz.usermanagement.repository;

import com.xyz.usermanagement.domain.OTP;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OTPRepo extends JpaRepository<OTP,Long> {
    /**
     * find the OTP recode by email and otp code
     * @param email
     * @param otp
     * @return
     */
    Optional<OTP>  findByEmailAndCode(String email, String otp);
}
