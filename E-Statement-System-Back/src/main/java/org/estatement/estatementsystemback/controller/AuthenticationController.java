package org.estatement.estatementsystemback.controller;



import lombok.RequiredArgsConstructor;
import org.estatement.estatementsystemback.dto.RegisterDTO;
import org.estatement.estatementsystemback.entity.User;
import org.estatement.estatementsystemback.jwt.AuthenticationService;
import org.estatement.estatementsystemback.jwt.JwtService;
import org.estatement.estatementsystemback.dto.LoginDTO;
import org.estatement.estatementsystemback.jwt.LoginResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor

public class AuthenticationController {

    @Autowired
    private JwtService jwtService;
    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/signin")
    public ResponseEntity<LoginResponse> signIn(@RequestBody LoginDTO loginDTO){

        User authenticatedUser = this.authenticationService.singIn(loginDTO);
        String jwt = jwtService.generateToken(authenticatedUser);
        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setToken(jwt);
        loginResponse.setExpiresIn(jwtService.getJwtExpiration());
        return ResponseEntity.ok(loginResponse);
    }
    @PostMapping("/signup")
    public ResponseEntity<User> register(@RequestBody RegisterDTO registerDTO){

        User registedUser = this.authenticationService.singup(registerDTO);

        return ResponseEntity.ok(registedUser);
    }
}
