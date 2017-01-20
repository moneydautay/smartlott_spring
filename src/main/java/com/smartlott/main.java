package com.smartlott;

import com.smartlott.utils.MathUtils;
import org.joda.time.DateTime;
import sun.text.resources.FormatData;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.Clock;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

/**
 * Created by greenlucky on 1/2/17.
 */
public class main {
    public static void main(String[] args){
        double income = 0.99939434934;
        income = MathUtils.round(income,4);

        System.out.println(income);
    }



}
