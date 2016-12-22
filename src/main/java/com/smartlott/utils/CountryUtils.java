package com.smartlott.utils;

import com.smartlott.backend.persistence.domain.backend.Country;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by greenlucky on 12/22/16.
 */
public class CountryUtils {

    /**
     * Non instantiable
     */
    private CountryUtils() {
        throw new AssertionError("Non instantiable");
    }

    /**
     * Create a country given by name and code
     * @param name
     * @param code
     * @return A country
     */
    public static Country createCountry(String name, String code){
        Country country = new Country(name, code);
        return country;
    }

    /**
     * Creata a list of country
     * @return A list of country
     */
    public static List<Country> createCountries(){
        List<Country> countries = new ArrayList<>();
        countries.add(createCountry("United States","100"));
        countries.add(createCountry("Vietname","70000"));
        return countries;
    }
}
