package com.smartlott;

import com.smartlott.utils.MathUtils;
import org.joda.time.DateTime;
import sun.text.resources.FormatData;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.time.Clock;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;
import java.util.Random;
import java.util.UUID;

/**
 * Created by greenlucky on 1/2/17.
 */
public class main {
    public static void main(String[] args){

        double price = 0;
        int i = 0;
        while (i <= 10){
            price += 0.02;
            NumberFormat formatter = NumberFormat.getInstance(Locale.US);
            String s = formatter.format(price);
            price = Double.parseDouble(s);
            System.out.println(price);
            i++;
        }

    }



}
