package com.smartlott.backend.service;

import com.smartlott.backend.persistence.domain.backend.Country;
import com.smartlott.backend.persistence.repositories.CountryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * Created by greenlucky on 12/22/16.
 */
@Service
public class CountryService {

    @Autowired
    private CountryRepository countryRepository;

    /**
     * Retrieve all country in database
     * @return A list of country
     */
    public List<Country> findAll(){
        return countryRepository.findAll();
    }

    /**
     * Create new country given by country
     * @param country
     * @return A country
     */
    public Country createCountry(Country country){
        return countryRepository.save(country);
    }

    /**
     * Find country by id
     * @param countryId
     * @return A country
     */
    public Country findOne(int countryId) {
        return countryRepository.findOne(countryId);
    }
}
