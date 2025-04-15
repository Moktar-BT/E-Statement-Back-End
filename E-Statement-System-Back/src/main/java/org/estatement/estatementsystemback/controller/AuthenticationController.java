package org.estatement.estatementsystemback.controller;



import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.estatement.estatementsystemback.dto.RegisterDTO;
import org.estatement.estatementsystemback.entity.User;
import org.estatement.estatementsystemback.jwt.AuthenticationService;
import org.estatement.estatementsystemback.jwt.JwtService;
import org.estatement.estatementsystemback.dto.LoginDTO;
import org.estatement.estatementsystemback.jwt.LoginResponse;
import org.estatement.estatementsystemback.service.GeolocationService.GeolocationService;
import org.estatement.estatementsystemback.service.connection.ConnectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua_parser.Client;
import ua_parser.Parser;


import java.io.IOException;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor

public class AuthenticationController {

    @Autowired
    private JwtService jwtService;
    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private GeolocationService geolocationService;
    @Autowired
    private ConnectionService connectionService;
    @PostMapping("/signin")
    public ResponseEntity<LoginResponse> signIn(@RequestBody LoginDTO loginDTO,
                                                HttpServletRequest request) throws IOException {
        try {
            // 1. Authenticate user
            User authenticatedUser = authenticationService.singIn(loginDTO);

            // 2. Generate JWT
            String jwt = jwtService.generateToken(authenticatedUser);

            // 3. Detect OS from User-Agent
            String userAgent = request.getHeader("User-Agent");
            Parser parser = new Parser();
            Client client = parser.parse(userAgent);
            String os = client.os.family;

            // 4. Get location (based on public IP)
            String location = geolocationService.getCurrentLocation();

            // 5. Save connection log
            connectionService.saveConnectionByEmail(loginDTO.getEmail(),location, os, client.userAgent.family, true);

            // 6. Create response
            LoginResponse loginResponse = new LoginResponse();
            loginResponse.setToken(jwt);
            loginResponse.setExpiresIn(jwtService.getJwtExpiration());
            loginResponse.setOs(os);
            loginResponse.setLocation(location);
            loginResponse.setStatus(true);

            return ResponseEntity.ok(loginResponse);

        } catch (Exception e) {
            // Handle failed login (e.g., wrong password/email)
            LoginResponse loginResponse = new LoginResponse();
            loginResponse.setStatus(false);

            // Optional: add error message/logging if needed
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(loginResponse);
        }
    }

    @PostMapping("/signup")
    public ResponseEntity<User> register(@RequestBody RegisterDTO registerDTO){

        User registedUser = this.authenticationService.singup(registerDTO);

        return ResponseEntity.ok(registedUser);
    }
}
