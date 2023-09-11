package com.xyz.usermanagement.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v2/home")
public class HomeController {
    @GetMapping
//    @PreAuthorize("hasRole('ADMIN')")
    public String get(){
        return "Welcome in secure mode";
    }
}
