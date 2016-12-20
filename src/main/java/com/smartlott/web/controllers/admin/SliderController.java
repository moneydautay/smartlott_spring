package com.smartlott.web.controllers.admin;

import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by Mrs Hoang on 18/12/2016.
 */
@Controller
@RequestMapping("/stl-admin/theme-management/slider")
public class SliderController {

    private static final String SLIDER_URL="admin/theme/slider";

    @RequestMapping("")
    public String sliderManagement(){
        return SLIDER_URL+"/index";
    }

    @RequestMapping("/add")
    public String addSlider(){
        return SLIDER_URL+"/addSlider";
    }

    @RequestMapping(value = "/edit/{sliderId}", method = RequestMethod.GET)
    public String editSlider(@PathVariable int sliderId){
        return SLIDER_URL+"/addSlider";
    }


    @RequestMapping(value = "/slider-images/{sliderId}",method = RequestMethod.GET)
    public String sliderImages(@PathVariable long sliderId, Model model){
        model.addAttribute("sliderId", sliderId);
        return SLIDER_URL +"/sliderItems";
    }
}
