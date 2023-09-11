package com.xyz.usermanagement.controller;

import com.xyz.usermanagement.domain.Temp;
import com.xyz.usermanagement.dto.AppResponse;
import com.xyz.usermanagement.service.RegisteredUserServices;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/reUser")
@AllArgsConstructor
public class RegisteredUserController {
    private RegisteredUserServices registeredUserServices;

    @GetMapping("")
    public ResponseEntity<AppResponse> checkEmailExist(@RequestParam String email){
        return new ResponseEntity<>(AppResponse.builder()
                .data(registeredUserServices.checkEmailExist(email))
                .code(HttpStatus.OK.value())
                .message("Success")
                .build(), HttpStatus.OK);
    }
    @PostMapping("/sendOPT")
    public ResponseEntity<AppResponse> saveOTP(@RequestBody Temp temp){
        return new ResponseEntity<>(AppResponse.builder()
                .message("Save")
                .data(registeredUserServices.sendOTP(temp))
                .code(HttpStatus.CREATED.value())
                .build(),HttpStatus.CREATED);
    }
    @PostMapping("/submit")
    public ResponseEntity<AppResponse> save(@RequestParam String email, String code){
        return new ResponseEntity<>(AppResponse.builder()
                .message("Save")
                .data(registeredUserServices.submit(email,code))
                .code(HttpStatus.CREATED.value())
                .build(),HttpStatus.CREATED);
    }
}
