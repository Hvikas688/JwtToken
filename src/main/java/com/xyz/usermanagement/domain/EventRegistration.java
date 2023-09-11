package com.xyz.usermanagement.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity(name = "event_registration")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EventRegistration {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @ManyToOne
    @JoinColumn(name="eventRegistration", nullable=false)
    RegisteredUser registeredUser;
    Date createdDate;
}
