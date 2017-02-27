package com.smartlott.backend.persistence.repositories;

import com.smartlott.backend.persistence.domain.backend.FeaturedSlider;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

/**
 * Created by Mrs Hoang on 18/12/2016.
 */
@Repository
public interface FeaturedSliderRepository extends CrudRepository<FeaturedSlider, Integer>{

    Set<FeaturedSlider> findAll();

}
