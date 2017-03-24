package com.smartlott.utils;

import org.junit.Test;

/**
 * Created by greenlucky on 3/23/17.
 */
public class FormatNumberUtilsTest {

    @Test
    public void formatNumber() throws Exception {
        double price = 8000.12323900923230;
        double result = FormatNumberUtils.formatNumber(price);
        System.out.println(result);
    }

}