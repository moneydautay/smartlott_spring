package com.smartlott.utils;

import java.text.NumberFormat;
import java.util.Locale;

/**
 * Created by Mrs Hoang on 14/02/2017.
 */
public class FormatNumberUtils {

    private FormatNumberUtils(){
        throw new AssertionError("Not instantiable");
    }

    public static double formatNumber(double value){
        NumberFormat formatter = NumberFormat.getInstance(Locale.US);
        String s = formatter.format(value);
        return Double.parseDouble(s);
    }
}
