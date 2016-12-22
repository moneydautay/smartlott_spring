package com.smartlott.backend.api;

import com.smartlott.backend.persistence.domain.backend.Country;
import com.smartlott.backend.persistence.domain.backend.Province;
import com.smartlott.backend.service.CountryService;
import com.smartlott.backend.service.I18NService;
import com.smartlott.backend.service.ProvinceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by greenlucky on 12/22/16.
 */
@RestController
@RequestMapping(ProvinceController.API_PROVINCE_URL)
public class ProvinceController {

    /** The application logger */
    private static final Logger LOGGER = LoggerFactory.getLogger(ProvinceController.class);


    public static final String API_PROVINCE_URL = "/api/province";

    @Autowired
    private I18NService i18NService;

    @Autowired
    private ProvinceService provinceService;

    @Autowired
    private CountryService countryService;

    @RequestMapping(value = "/{countryId}", method = RequestMethod.GET)
    public ResponseEntity<Object> getProvinces(@PathVariable int countryId){
        Country country = countryService.findOne(countryId);
        if(country == null){
            LOGGER.error("Country with id {} was not existed", countryId);
            return new ResponseEntity<Object>(new Object[]{i18NService.getMessage("Country.not.found", String.valueOf(countryId) ,LocaleContextHolder.getLocale())}, HttpStatus.BAD_REQUEST);
        }
        List<Province> provinces = provinceService.findByCountry(country);
        return new ResponseEntity<Object>(provinces, HttpStatus.OK);
    }
}
