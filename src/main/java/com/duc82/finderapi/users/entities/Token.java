package com.duc82.finderapi.users.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "tokens")
@Data // Lombok's annotation to generate getters, setters, equals, hashcode, and toString
@NoArgsConstructor // Lombok's annotation to generate constructor with no arguments
@AllArgsConstructor // Lombok's annotation to generate constructor with all arguments
public class Token {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String resetPasswordToken;
    @Temporal(TemporalType.TIMESTAMP)
    private OffsetDateTime resetPasswordExpiration;
}
