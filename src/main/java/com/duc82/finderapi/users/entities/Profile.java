package com.duc82.finderapi.users.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "profiles")
@Data // Lombok's annotation to generate getters, setters, equals, hashcode, and toString
@NoArgsConstructor // Lombok's annotation to generate constructor with no arguments
@AllArgsConstructor // Lombok's annotation to generate constructor with all arguments
public class Profile {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String bio;
    private String avatar;
    private String address;
    private String facebook;
    private String twitter;
    private String linkedin;
    private String instagram;
    private String pinterest;
}
