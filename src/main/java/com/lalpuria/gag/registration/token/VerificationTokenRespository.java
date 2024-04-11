package com.lalpuria.gag.registration.token;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface VerificationTokenRespository extends JpaRepository<VerificationToken, Long> {

    VerificationToken findByToken(String token);
}
