package com.smartlott;

import org.joda.time.DateTime;
import sun.text.resources.FormatData;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Clock;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.UUID;

/**
 * Created by greenlucky on 1/2/17.
 */
public class main {
    public static void main(String[] args){


        String time = "12:02:23 05/01/2017";
        DateTimeFormatter ft = DateTimeFormatter.ofPattern("kk:mm:ss dd/MM/yyyy");

        try {
            LocalDateTime now = LocalDateTime.now(Clock.systemDefaultZone());
            System.out.println(now);
            LocalDateTime dateTime = LocalDateTime.parse(time, ft);
            System.out.println(dateTime);
            System.out.println(dateTime.isBefore(now));
        }catch (Exception we){
            System.out.println(we.getMessage());
        }


    }
}
