package com.lalpuria.gag.registration.token;

import com.lalpuria.gag.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class VerificationToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String token;
    private LocalDateTime expirationTime;
    private static final int EXPIRACTION_TIME = 6;
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    public VerificationToken(String token, User user) {
        this.token = token;
        this.user = user;
        this.expirationTime = this.getTokenExpirationTime();
    }


    public VerificationToken(String token) {
        super();
        this.token = token;
        this.expirationTime = this.getTokenExpirationTime();
    }

    private LocalDateTime getTokenExpirationTime() {
        LocalDateTime expDateTime = LocalDateTime.now();
        return expDateTime.plus(EXPIRACTION_TIME, ChronoUnit.MINUTES);
    }


}

