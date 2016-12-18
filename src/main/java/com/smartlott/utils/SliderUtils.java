package com.smartlott.utils;

import com.smartlott.backend.persistence.domain.backend.FeaturedSlider;
import com.smartlott.backend.persistence.domain.backend.FeaturedSliderImage;
import javafx.scene.control.Slider;

import java.time.Clock;
import java.time.LocalDateTime;

/**
 * Created by Mrs Hoang on 18/12/2016.
 */
public class SliderUtils {

    private SliderUtils() {
        throw  new AssertionError("Non instantiable");
    }

    public static FeaturedSlider createFeaturedSlider(String name){
        return new FeaturedSlider(name);
    }

    public static FeaturedSliderImage createFeaturedSliderImage(String title, String description){

        FeaturedSliderImage featuredSliderImage = new FeaturedSliderImage();
        featuredSliderImage.setTitle(title);
        featuredSliderImage.setUrl("#");
        featuredSliderImage.setImage("R0lGODlhAQABAIAAAHd3dwAAACH5BAAAAAAALAAAAAABAAEAAAICRAEAOw==");
        featuredSliderImage.setDesciption(description);
        featuredSliderImage.setCreatedDate(LocalDateTime.now(Clock.systemUTC()));

        return featuredSliderImage;
    }
}
