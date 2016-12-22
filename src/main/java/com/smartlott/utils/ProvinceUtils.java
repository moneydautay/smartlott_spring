package com.smartlott.utils;

import com.smartlott.backend.persistence.domain.backend.Country;
import com.smartlott.backend.persistence.domain.backend.Province;

/**
 * Created by greenlucky on 12/22/16.
 */
public class ProvinceUtils {

    /**
     * Non instantiable
     */
    private ProvinceUtils(){ throw new AssertionError("Non instantiable");}

    public static Province createProvice(String name, Country country){
        return new Province(name,country);
    }

}
