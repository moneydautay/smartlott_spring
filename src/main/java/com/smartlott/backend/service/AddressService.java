package com.smartlott.backend.service;

import com.smartlott.backend.persistence.domain.backend.Address;
import com.smartlott.backend.persistence.repositories.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by greenlucky on 12/23/16.
 */
@Service
public class AddressService {

    @Autowired
    private AddressRepository addressRepository;

    /**
     * Create new address
     * @param address
     * @return a address after insert or null if ortherwise
     */
    public Address createAddress(Address address){
        return addressRepository.save(address);
    }


    /**
     * Find addresses by userId
     * @param userId given by user
     * @return a list of address or null if not found
     */
    public List<Address> findByUserId(long userId){
        return addressRepository.findByUserId(userId);
    }
}
