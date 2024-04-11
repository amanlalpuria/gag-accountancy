package com.lalpuria.gag.user;

import com.lalpuria.gag.exception.UserAlreadyExistsException;
import com.lalpuria.gag.registration.RegistrationRequest;
import com.lalpuria.gag.registration.token.VerificationToken;
import com.lalpuria.gag.registration.token.VerificationTokenRespository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private final VerificationTokenRespository verificationTokenRespository;


    @Override
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @Override
    public User registerUser(RegistrationRequest request) {
        Optional<User> user = this.findByEmail(request.email());
        if(user.isPresent()){
            throw new UserAlreadyExistsException("User with the email " + request.email() + " already exists");
        }
        var newUser = new User();
        newUser.setEmail(request.email());
        newUser.setFullName(request.fullName());
        newUser.setPassword(passwordEncoder.encode(request.password()));
        newUser.setRole(request.role());
        newUser.setCreatedAt(LocalDateTime.now());
        return userRepository.save(newUser);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public void saveUserVerificationToken(User theUser, String token) {
        var verificationToken = new VerificationToken(token,theUser);
        verificationTokenRespository.save(verificationToken);
    }

    @Override
    public String validatToken(String token) {
        VerificationToken vtoken = verificationTokenRespository.findByToken(token);
        log.info("validatToken :: {}", vtoken.toString() );
        if(vtoken == null){
            return "Invalid Verification Token";
        }
        User user = vtoken.getUser();
        LocalDateTime currentTime = LocalDateTime.now();
        if(currentTime.isAfter(vtoken.getExpirationTime())){
            verificationTokenRespository.delete(vtoken);
            return "Verification Token already Expired";
        }
        user.setEnabled(true);
        log.info("User verification enabled updated :: {}", user);
        userRepository.save(user);
        return "VALID";
    }
}
