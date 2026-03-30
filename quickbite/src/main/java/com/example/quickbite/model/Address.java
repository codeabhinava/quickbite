package com.example.quickbite.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "app_user", nullable = false)
    private AppUser user;

    private String fullName;
    private String phoneNo;
    private String fullAddress;

    public Address(AppUser user, String fullName, String phoneNo, String fullAddress) {
        this.user = user;
        this.fullName = fullName;
        this.phoneNo = phoneNo;
        this.fullAddress = fullAddress;
    }
}
