package com.reservia.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
public class VerificationToken {

    @Id
    @GeneratedValue
    private Long id;

    private String token;

    private LocalDateTime expiryDate;

    @OneToOne
    private User user;
}
