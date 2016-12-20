package com.smartlott.backend.api;

import com.smartlott.backend.persistence.domain.backend.FeaturedSliderImage;
import com.smartlott.backend.service.FeaturedSliderImageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Mrs Hoang on 18/12/2016.
 */
@RestController
@RequestMapping("/api/featured-slider-image")
public class FeaturedSliderImageController {

    /** The application logger */
    private static final Logger LOGGER = LoggerFactory.getLogger(FeaturedSliderImageController.class);

    @Autowired
    private FeaturedSliderImageService sliderService;

    @RequestMapping("")
    public Set<FeaturedSliderImage> getAll(){
        Set<FeaturedSliderImage> sliderImages = new HashSet<>();
        sliderImages = sliderService.findAll();
        return sliderImages;
    }
}
