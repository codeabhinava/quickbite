package com.example.quickbite.model;

import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Data
public class AppUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userName;

    private String emailId;
    private String passWord;
    private String phoneNumber;

    private UUID confirmationToken;

    public AppUser(String userName, String emailId, String passWord, String phoneNumber, UUID confirmationToken) {
        this.userName = userName;
        this.emailId = emailId;
        this.passWord = passWord;
        this.phoneNumber = phoneNumber;
        this.confirmationToken = confirmationToken;
    }

}
