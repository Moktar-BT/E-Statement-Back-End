package org.estatement.estatementsystemback.jwt;


import lombok.*;

@Data
@RequiredArgsConstructor
public class LoginResponse {

    private String token;

    private long expiresIn;

}
