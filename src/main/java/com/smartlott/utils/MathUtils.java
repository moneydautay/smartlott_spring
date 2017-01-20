package com.smartlott.utils;

/**
 * Created by Mrs Hoang on 17/01/2017.
 */
public class MathUtils {

    /**
     * Non instantiable
     */
    private MathUtils() {
        throw new AssertionError("Non instantiable");
    }

    /**
     *
     * @param value
     * @param places
     * @return
     */
    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }
}
