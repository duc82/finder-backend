package com.duc82.finderapi.properties.entities;


import com.duc82.finderapi.properties.enums.PropertyStatus;
import com.duc82.finderapi.properties.enums.PropertyTransactionType;
import com.duc82.finderapi.properties.enums.PropertyType;
import com.duc82.finderapi.users.entities.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.Digits;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

@Entity
@Table(name = "properties")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Property {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(nullable = false)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false)
    private String address;

    @Digits(integer = 10, fraction = 8)
    @Column(nullable = false)
    private BigDecimal latitude;

    @Digits(integer = 11, fraction = 8)
    @Column(nullable = false)
    private BigDecimal longitude;

    @Enumerated(EnumType.STRING)
    @Column()
    private PropertyType type;

    @Digits(integer = 15, fraction = 2)
    @Column(nullable = false)
    private BigDecimal price;

    @Digits(integer = 10, fraction = 2)
    @Column(nullable = false)
    private BigDecimal area;

    @Column(nullable = false)
    private int bedrooms;

    @Column(nullable = false)
    private int bathrooms;

    @Column(nullable = false)
    private int garage;

    @Column(nullable = false)
    private int floor;

    @Column(nullable = false)
    private int yearBuilt;

    @Column(columnDefinition = "json")
    private String amenities;

    @Column()
    private int views;

    @Column(nullable = false)
    private boolean verified;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "varchar(4) default 'SALE'")
    private PropertyTransactionType transactionType;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "varchar(255) default 'AVAILABLE'")
    private PropertyStatus status = PropertyStatus.AVAILABLE;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "property", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PropertyImage> images;

    @Temporal(TemporalType.TIMESTAMP)
    private OffsetDateTime deletedAt;

    @CreatedDate
    private OffsetDateTime createdAt;

    @LastModifiedDate
    private OffsetDateTime updatedAt;
}
