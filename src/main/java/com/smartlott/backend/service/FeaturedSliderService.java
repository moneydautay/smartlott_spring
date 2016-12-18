package com.smartlott.backend.service;

import com.smartlott.backend.persistence.domain.backend.FeaturedSlider;
import com.smartlott.backend.persistence.repositories.FeaturedSliderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

/**
 * Created by Mrs Hoang on 18/12/2016.
 */
@Service
@Transactional(readOnly = true)
public class FeaturedSliderService {

    @Autowired
    private FeaturedSliderRepository featuredSliderRepository;

    /**
     * Find all Featured Slider
     * @return A Set of featured Slider
     */
    public Set<FeaturedSlider> findAll(){
        return featuredSliderRepository.findAll();
    }

    public void save(FeaturedSlider featuredSlider) {
        featuredSliderRepository.save(featuredSlider);
    }
}
