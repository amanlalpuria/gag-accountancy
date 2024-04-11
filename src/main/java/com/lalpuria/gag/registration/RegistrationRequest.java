package com.lalpuria.gag.registration;

public record RegistrationRequest( String email,
         String password,
         String fullName,
         String role) {
}
