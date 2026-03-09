package com.example.quickbite.model;

import lombok.Data;
import lombok.ToString;

@ToString
@Data
public class UserRegistration {

    private String name;
    private String emailId;
    private String password;
    private String phoneNumber;
}
