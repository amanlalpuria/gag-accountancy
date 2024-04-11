package com.lalpuria.gag.controller.resource;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.Date;

/**
 * Login response object containing the JWT
 *
 * @author amanlalpuria
 */
@Data
@RequiredArgsConstructor
public class LoginResult {

    @NonNull
    private String jwt;
    @NonNull
    private Date expTime;
}
