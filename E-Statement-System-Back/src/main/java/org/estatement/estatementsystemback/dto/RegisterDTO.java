package org.estatement.estatementsystemback.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterDTO {


    private String firstName;

    private String lastName;

    private String email;

    private String password;
    private boolean status ;
//    private LocalDate userCreationDate = LocalDate.now();
}