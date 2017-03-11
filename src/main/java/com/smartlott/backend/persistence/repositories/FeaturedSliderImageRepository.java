package com.smartlott.backend.persistence.repositories;

import com.smartlott.backend.persistence.domain.backend.FeaturedSlider;
import com.smartlott.backend.persistence.domain.backend.FeaturedSliderImage;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

/**
 * Created by Mrs Hoang on 18/12/2016.
 */
@Repository
public interface FeaturedSliderImageRepository extends CrudRepository<FeaturedSliderImage, Integer>{

    /**
     * Find a list Featured Slider Image by Featured Image given by user
     * @param featuredSlider
     * @return A list of featured slider images
     */
    List<FeaturedSliderImage> findByFeaturedSlider(FeaturedSlider featuredSlider);

    Set<FeaturedSliderImage> findAll();

}
