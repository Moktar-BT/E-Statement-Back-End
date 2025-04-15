package org.estatement.estatementsystemback.jwt;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class LoginResponse {

    private String token;
    private long expiresIn;
    private String os;
    private String location;
    private boolean status;

}
