package com.clinic.authservice.services.impl;

import com.clinic.authservice.api.models.requests.LoginRequest;
import com.clinic.authservice.api.models.responses.LoginResponse;
import com.clinic.authservice.api.models.responses.UserResponse;
import com.clinic.authservice.configs.security.JwtTokenProvider;
import com.clinic.authservice.services.contracts.LoginService;
import com.clinic.commonservice.exceptions.NotFoundException;
import com.clinic.commonservice.exceptions.UnauthorizedException;
import com.clinic.commonservice.logs.WriteLog;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

/**
 * LoginServiceImpl class implements the LoginService interface.
 * It provides the functionality to authenticate users and generate JWT tokens upon successful login.
 *
 * @author caito
 *
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class LoginServiceImpl implements LoginService {
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;
    HttpClient client = HttpClient.newHttpClient();
    ObjectMapper mapper = new ObjectMapper();

    /**
     * Authenticate the user based on the provided login request and generate a JWT token.
     *
     * @param request The LoginRequest object containing login credentials.
     * @return A LoginResponse object containing the generated JWT token.
     * @throws NotFoundException      if the user is not found.
     * @throws UnauthorizedException  if authentication fails.
     */
    @Override
    public LoginResponse login(LoginRequest request) {
        log.info(WriteLog.logInfo("--> Login Service started..."));
        try {
            HttpRequest req = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:40000/api/v1/clinical/users/full-data/" + request.getEmail()))
                    .header("content-type", "application/json")
                    .GET()
                    .build();
            HttpResponse<String> response = client.send(req, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() != 200) {
                log.error(WriteLog.logInfo("--> User not found with email: " + request.getEmail()));
                throw  new NotFoundException("User not found");
            }
            UserResponse user = mapper.readValue(response.body(), UserResponse.class);
            this.authenticate(user, request);
            return LoginResponse.builder()
                    .access_token(jwtTokenProvider.generateToken(user))
                    .build();
        }catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Authenticate the user based on the provided login request.
     *
     * @param user    The UserResponse object containing user details.
     * @param request The LoginRequest object containing login credentials.
     * @throws UnauthorizedException if authentication fails due to incorrect password or account issues.
     */
    private void authenticate(UserResponse user, LoginRequest request){
        log.info(WriteLog.logInfo("--> authService - authenticate - start"));


        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            log.error(WriteLog.logError("--> Password not match"));
            throw new UnauthorizedException("Incorrect Password");
        }

        if (!user.isEnabled()) {
            log.error(WriteLog.logError("--> User not enabled"));
            throw new UnauthorizedException("User not enabled");
        }
        if (!user.isAccountNonExpired()) {
            log.error(WriteLog.logError("--> User account expired"));
            throw new UnauthorizedException("User account expired");
        }
        if (!user.isAccountNonLocked()) {
            log.error(WriteLog.logError("--> User account locked"));
            throw new UnauthorizedException("User account locked");
        }

        if (!user.isCredentialsNonExpired()){
            log.error(WriteLog.logError("--> User credentials expired"));
            throw new UnauthorizedException("User credentials expired");
        }
    }

}
