package org.estatement.estatementsystemback.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import lombok.NoArgsConstructor;
import org.estatement.estatementsystemback.enums.Role;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User implements UserDetails {
    @Id
    @GeneratedValue
    private Long id_user;
    private String firstName ;
    private String lastName ;
    private String email ;
    private String password ;
    private Date userCreationDate ;
    private Date userExpirationDate;
    private double interestRateTotal;
    private boolean status ;
    private boolean receiveEmailNotifications;
    private boolean receiveSmsNotifications;
    private boolean receivePushNotifications;
    @OneToMany
    private List<Account> accounts;
    @OneToMany
    private List<Connection> connectionHistory;
    @OneToMany
    private List<Notification> Notifications ;
    @Enumerated(EnumType.STRING)
    private Role role = Role.USER ;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
