package com.duc82.finderapi.users.entities;

import com.duc82.finderapi.properties.entities.Property;
import com.duc82.finderapi.users.UserRole;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.text.MessageFormat;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;


@Entity
@Table(name = "users", indexes = {
        @Index(name = "idx_email_users", columnList = "email") // Create index for email column
}, uniqueConstraints = @UniqueConstraint(columnNames = "email") // Create unique constraint for email column
)
@Data // Lombok's annotation to generate getters, setters, equals, hashcode, and toString
@NoArgsConstructor // Lombok's annotation to generate constructor with no arguments
@AllArgsConstructor // Lombok's annotation to generate constructor with all arguments
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String fullName;

    @Column(nullable = false)
    private String email;

    @Column()
    private String phone;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "varchar(255) default 'CUSTOMER'")
    private UserRole role = UserRole.CUSTOMER;

    @Column()
    @JsonIgnore
    private String password;

    @Temporal(TemporalType.TIMESTAMP)
    private OffsetDateTime deletedAt;

    @CreatedDate
    private OffsetDateTime createdAt;

    @LastModifiedDate
    private OffsetDateTime updatedAt;

    @Temporal(TemporalType.TIMESTAMP)
    private OffsetDateTime verifiedAt;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "profile_id", referencedColumnName = "id")
    private Profile profile;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "token_id", referencedColumnName = "id")
    private Token token;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Property> properties;

    public void softDelete() {
        this.deletedAt = OffsetDateTime.now();
        this.email = MessageFormat.format("{0}-deleted-{1}", this.email, this.id);
    }

    public void restore() {
        this.deletedAt = null;
        String[] parts = this.email.split("-");
        this.email = parts[0];
    }
}
