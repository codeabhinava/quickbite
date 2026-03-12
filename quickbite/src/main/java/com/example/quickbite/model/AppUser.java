package com.example.quickbite.model;

import java.util.Collection;
import java.util.Collections;
import java.util.UUID;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Data
public class AppUser implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userName;

    private String emailId;
    private String passWord;
    private String phoneNumber;

    private UUID confirmationToken;

    @Enumerated(EnumType.STRING)
    private UserRole userRole;

    private boolean isenabled;

    public AppUser(String userName, String emailId, String passWord, String phoneNumber, UUID confirmationToken) {
        this.userName = userName;
        this.emailId = emailId;
        this.passWord = passWord;
        this.phoneNumber = phoneNumber;
        this.confirmationToken = confirmationToken;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(userRole.name());
        return Collections.singletonList(authority);
    }

    @Override
    public String getPassword() {
        return passWord;
    }

    @Override
    public String getUsername() {
        return userName;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
