package com.smartlott.backend.api;

import com.smartlott.backend.persistence.domain.backend.FeaturedSlider;
import com.smartlott.backend.service.FeaturedSliderService;
import com.smartlott.exceptions.FeaturedSliderNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Mrs Hoang on 18/12/2016.
 */
@RestController
@RequestMapping("/api/featured-slider")
public class FeaturedSliderController {

    /** The application logger */
    private static final Logger LOGGER = LoggerFactory.getLogger(FeaturedSliderController.class);

    @Autowired
    private FeaturedSliderService sliderService;

    @RequestMapping("")
    public Set<FeaturedSlider> getAll(){
        Set<FeaturedSlider> featuredSliders = new HashSet<>();
        featuredSliders = sliderService.findAll();
        return featuredSliders;
    }

    @RequestMapping(value = "/{sliderId}", method = RequestMethod.GET)
    public FeaturedSlider get(@PathVariable int sliderId)
    {
        FeaturedSlider featuredSlider = sliderService.findOne(sliderId);
        if(featuredSlider == null)
            throw new FeaturedSliderNotFoundException(sliderId);
        return featuredSlider;
    }

    @RequestMapping(value = "",method = RequestMethod.POST)
    private ResponseEntity<FeaturedSlider> save(@RequestBody FeaturedSlider featuredSlider){
        System.out.println(featuredSlider);
        LOGGER.debug("Reparing save Featured Slider {}", featuredSlider);
        sliderService.save(featuredSlider);
        LOGGER.info("Saved Featured Slider {}", featuredSlider);
        return new ResponseEntity<FeaturedSlider>(featuredSlider, HttpStatus.CREATED);
    }

    @RequestMapping(value = "",method = RequestMethod.PUT)
    private ResponseEntity<FeaturedSlider> update(@RequestBody FeaturedSlider featuredSlider){
        System.out.println(featuredSlider);
        LOGGER.debug("Reparing save Featured Slider {}", featuredSlider);
        sliderService.save(featuredSlider);
        LOGGER.info("Saved Featured Slider {}", featuredSlider);
        return new ResponseEntity<FeaturedSlider>(featuredSlider, HttpStatus.OK);
    }
}
