package com.smartlott.backend.service;

import com.smartlott.backend.persistence.domain.backend.FeaturedSliderImage;
import com.smartlott.backend.persistence.repositories.FeaturedSliderImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Mrs Hoang on 18/12/2016.
 */
@Service
@Transactional(readOnly = true)
public class FeaturedSliderImageService {

    @Autowired
    private FeaturedSliderImageRepository sliderImageRepository;

    public void save(FeaturedSliderImage featuredSliderImage) {
        sliderImageRepository.save(featuredSliderImage);
    }
}
