package com.xyz.usermanagement.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity(name = "registered_users")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegisteredUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String name;
    String email;
    String password;
    String phoneNumber;
    String cityName;
    int grade;
    String status;
    String schoolName;
    @OneToMany(mappedBy = "registeredUser")
    Set<EventRegistration> eventRegistration;
}
