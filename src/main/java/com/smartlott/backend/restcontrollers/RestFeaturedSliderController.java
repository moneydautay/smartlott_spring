package com.smartlott.backend.restcontrollers;

import com.smartlott.backend.persistence.domain.backend.FeaturedSlider;
import com.smartlott.backend.service.FeaturedSliderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Mrs Hoang on 18/12/2016.
 */
@RestController
@RequestMapping("/data/featured-slider")
public class RestFeaturedSliderController {

    @Autowired
    private FeaturedSliderService sliderService;

    @RequestMapping("")
    public Set<FeaturedSlider> getAll(){
        Set<FeaturedSlider> featuredSliders = new HashSet<>();
        featuredSliders = sliderService.findAll();
        return featuredSliders;
    }
}
