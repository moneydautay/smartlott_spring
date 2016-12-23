package com.smartlott.backend.service;

import com.smartlott.backend.persistence.domain.backend.Country;
import com.smartlott.backend.persistence.domain.backend.Province;
import com.smartlott.backend.persistence.repositories.ProvinceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by greenlucky on 12/22/16.
 */
@Service
public class ProvinceService {

    @Autowired
    private ProvinceRepository provinceRepository;

    /**
     * Retrieve A list of province by Country
     * @param country
     * @return A list of ProvinceController
     */
    public List<Province> findByCountry(Country country){
        return provinceRepository.findByCountry(country);
    }


    /**
     * Create new province given by province
     * @param province
     * @return A province after created
     */
    public Province createProvince(Province province){
        return provinceRepository.save(province);
    }

    /**
     * Find a province by id
     * @param id
     * @return A province or null if not found
     */
    public Province findOne(int id) {
       return provinceRepository.findOne(id);
    }
}
