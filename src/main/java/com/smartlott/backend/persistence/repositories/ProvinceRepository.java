package com.smartlott.backend.persistence.repositories;

import com.smartlott.backend.persistence.domain.backend.Country;
import com.smartlott.backend.persistence.domain.backend.Province;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Mrs Hoang on 16/12/2016.
 */
@Repository
public interface ProvinceRepository extends CrudRepository<Province, Integer>{

    /**
     * Returns a list of provice of country or null if not found
     * @param country
     * @return Returns a list of provice of country or null if not found
     */
    public List<Province> findByCountry(Country country);
}
