package com.smartlott.backend.persistence.repositories;

import com.smartlott.backend.persistence.domain.backend.Country;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Mrs Hoang on 16/12/2016.
 */

@Repository
public interface CountryRepository extends CrudRepository<Country, Integer>{

    /**
     * Retrieve all country in database
     * @return
     */
    public List<Country> findAll();
}
