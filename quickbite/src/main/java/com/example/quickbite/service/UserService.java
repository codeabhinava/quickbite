package com.example.quickbite.service;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.quickbite.model.AppUser;
import com.example.quickbite.model.ConfirmationToken;
import com.example.quickbite.model.UserRegistration;
import com.example.quickbite.producer.EmailProducer;
import com.example.quickbite.repository.AppUserRepository;
import com.example.quickbite.repository.ConfirmationTokenRepository;
import com.example.quickbite.common.Email;

@Service
public class UserService implements UserDetailsService {

    private final AppUserRepository appUserRepository;
    private final ConfirmationTokenRepository confirmationTokenRepository;
    private final EmailProducer emailProducer;
    private final PasswordEncoder passwordEncoder;

    public UserService(AppUserRepository appUserRepository, ConfirmationTokenRepository confirmationTokenRepository, EmailProducer emailProducer, PasswordEncoder passwordEncoder) {
        this.appUserRepository = appUserRepository;
        this.passwordEncoder = passwordEncoder;
        this.confirmationTokenRepository = confirmationTokenRepository;
        this.emailProducer = emailProducer;
    }

    public AppUser getUserByToken(UUID token) {
        return appUserRepository.findByToken(token);
    }

    public String createUser(UserRegistration userRegistration) {
        if (appUserRepository.findByEmailId(userRegistration.getEmailId()) != null) {
            return "Email already exists";
        }
        if (appUserRepository.findByPhoneNumber(userRegistration.getPhoneNumber()) != null) {
            return "Phone number already exists";
        }
        if (appUserRepository.findByUserName(userRegistration.getName()) != null) {
            return "Username already exists";
        }
        UUID confirmationToken = UUID.randomUUID();
        String encoded = passwordEncoder.encode(userRegistration.getPassword());
        AppUser newUser = new AppUser(userRegistration.getName(), userRegistration.getEmailId(), encoded, userRegistration.getPhoneNumber(), confirmationToken);
        appUserRepository.save(newUser);
        confirmationTokenRepository.save(new ConfirmationToken(confirmationToken, LocalDateTime.now(), LocalDateTime.now().plusMinutes(15), newUser));
        Email email = new Email("User", userRegistration.getEmailId(), confirmationToken);
        emailProducer.sendEmailConfirmation(email);
        return "User created successfully";
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser user = appUserRepository.findByUserName(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
        return User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .roles("USER")
                .build();
    }

}
