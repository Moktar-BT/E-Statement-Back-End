package org.estatement.estatementsystemback.jwt;


import lombok.RequiredArgsConstructor;
import org.estatement.estatementsystemback.dao.UserDAO;
import org.estatement.estatementsystemback.dto.LoginDTO;
import org.estatement.estatementsystemback.dto.RegisterDTO;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.estatement.estatementsystemback.entity.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;


@Service
@RequiredArgsConstructor
public class AuthenticationService {



        private final UserDAO userDAO;
        private final AuthenticationManager authenticationManager;
        private final PasswordEncoder passwordEncoder;

        public User singIn(LoginDTO loginDTO) {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginDTO.getEmail(), loginDTO.getPassword()
                    )
            );

            return userDAO.findByEmail(loginDTO.getEmail()).orElseThrow(
                    () -> new UsernameNotFoundException("User cannot be found.")
            );
        }

        public User singup(RegisterDTO registerDTO) {
            // Check if the user already exists
            if (userDAO.findByEmail(registerDTO.getEmail()).isPresent()) {
                throw new RuntimeException("User with this email already exists.");
            }

            // Create a new user
            User user = new User();
            user.setFirstName(registerDTO.getFirstName());
            user.setLastName(registerDTO.getLastName());
            user.setEmail(registerDTO.getEmail());
            user.setPassword(passwordEncoder.encode(registerDTO.getPassword()));
            user.setStatus(true);

            user.setUserCreationDate(new Date()); // Set creation date

            // Save the user
            return this.userDAO.save(user);
        }
    }