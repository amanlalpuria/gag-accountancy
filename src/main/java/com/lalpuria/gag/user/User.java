package com.lalpuria.gag.user;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.NaturalId;

import java.time.LocalDateTime;
@Entity
@Table(name = "users", uniqueConstraints = @UniqueConstraint(columnNames = "email"))
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NaturalId(mutable = true)
    private String email;
    private String password;
    private String fullName;
    private String role;
    private LocalDateTime updatedAt;
    private LocalDateTime createdAt;
    private boolean isEnabled =false;
}
