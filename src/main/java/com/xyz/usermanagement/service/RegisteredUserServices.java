package com.xyz.usermanagement.service;

import com.xyz.usermanagement.domain.EventRegistration;
import com.xyz.usermanagement.domain.OTP;
import com.xyz.usermanagement.domain.RegisteredUser;
import com.xyz.usermanagement.domain.Temp;
import com.xyz.usermanagement.repository.EventRegistrationRepo;
import com.xyz.usermanagement.repository.OTPRepo;
import com.xyz.usermanagement.repository.RegisteredUserRepo;
import com.xyz.usermanagement.repository.TempRepo;
import com.xyz.usermanagement.sercurity.JwtHelper;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
@AllArgsConstructor
public class RegisteredUserServices {
    private RegisteredUserRepo registeredUserRepo;
    private TempRepo tempRepo;
    private OTPRepo otpRepo;
    private EventRegistrationRepo eventRegistrationRepo;
    private AuthenticationManager manager;
    private JwtHelper jwtHelper;
    private UserDetailsService userDetailsService;
    private PasswordEncoder passwordEncoder;
    public Boolean checkEmailExist(String email){
        return registeredUserRepo.findByEmail(email).isPresent();
    }

    /**
     * submit the registration request
     * @param email
     * @param code
     * @return
     */
    public String submit(String email, String  code) {
        String tempPassword="App-"+ new Random().nextInt((1000 + 1999) + 1000);
        System.out.println("tempPassword: "+tempPassword);
        String password= passwordEncoder.encode(tempPassword);
        RegisteredUser registeredUser;
        if (checkEmailExist(email) && vefifyOtp(email,code)){
            registeredUser=registeredUserRepo.findByEmail(email).get();
            saveEvent(registeredUser);
        }
        else if(vefifyOtp(email,code)) {
            Temp temp=tempRepo.findByEmailAndCode(email,code).orElseThrow(()-> new RuntimeException("Temp recode not found"));
            registeredUser=RegisteredUser.builder()
                    .grade(temp.getGrade())
                    .cityName(temp.getCityName())
                    .email(temp.getEmail())
                    .name(temp.getName())
                    .password(password)
                    .phoneNumber(temp.getPhoneNumber())
                    .schoolName(temp.getSchoolName())
                    .status(temp.getStatus())
                    .build();
           registeredUser= registeredUserRepo.save(registeredUser);
            saveEvent(registeredUser);
        }
        else new RuntimeException("OPT is invalid");
        return getToken(email,password);
    }

    /**
     * get token generate
     * @param email
     * @param tempPassword
     * @return
     */
    private String getToken(String email,String tempPassword){
        UserDetails userDetails=userDetailsService.loadUserByUsername(email);
        String toke=jwtHelper.generateToken(userDetails);
        return toke;
    }

    /**
     * save recode in temp table
     * @param temp
     * @return
     */
    private Temp saveTemp(Temp temp){
        return tempRepo.save(temp);
    }

    /**
     * verify the otp with database
     * @param email
     * @param otp
     * @return
     */
    public Boolean vefifyOtp(String email,String otp){
        return otpRepo.findByEmailAndCode(email,otp).isPresent();
    }

    /**
     * Save the registration event
     * @param registeredUser
     * @return
     */
    public Boolean saveEvent(RegisteredUser registeredUser){
        eventRegistrationRepo.save(
                EventRegistration.builder()
                        .registeredUser(registeredUser)
                        .createdDate(new Date())
                        .build()
        );
        return true;
    }

    /**
     * send otop with and save recode in otp and temp table
     * @param temp
     * @return
     */
    public Boolean sendOTP(Temp temp) {
        String code= String.valueOf(new Random().nextInt((1000+1999)+1000));
        OTP otp=OTP.builder()
                .code(code)
                .email(temp.getEmail())
                .build();
        otpRepo.save(otp);
        temp.setCode(code);
        saveTemp(temp);
        return true;
    }


}
