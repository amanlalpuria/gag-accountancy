package com.lalpuria.gag.controller.resource;

public class AuthDTO {
    public record LoginRequest(String username, String password) {
    }

    public record LoginResponse(String message, String token, java.time.Instant expireTime) {
    }
}
