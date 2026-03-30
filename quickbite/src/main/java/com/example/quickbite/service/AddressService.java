package com.example.quickbite.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.quickbite.model.Address;
import com.example.quickbite.model.AppUser;
import com.example.quickbite.repository.AddressRepository;
import com.example.quickbite.repository.AppUserRepository;

@Service
public class AddressService {

    private final AddressRepository addressRepository;
    private final AppUserRepository userRepository;

    public AddressService(AddressRepository addressRepository, AppUserRepository userRepository) {
        this.addressRepository = addressRepository;
        this.userRepository = userRepository;
    }

    public void saveAddress(String userName, Address address) {
        AppUser user = userRepository.findByUserName(userName);
        List<Address> userAddresses = addressRepository.findByUser(user);
        if (!userAddresses.contains(address) && userAddresses.isEmpty()) {

            Address newAddress = new Address();
            newAddress.setFullAddress(address.getFullAddress());
            newAddress.setFullName(address.getFullName());
            newAddress.setPhoneNo(address.getPhoneNo());
            newAddress.setUser(user);
            addressRepository.save(newAddress);
        }
    }

    public List<Address> getAddress(String userName) {
        AppUser user = userRepository.findByUserName(userName);
        return addressRepository.findByUser(user);
    }

}
