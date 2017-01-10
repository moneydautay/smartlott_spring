package com.smartlott;

import org.joda.time.DateTime;
import sun.text.resources.FormatData;

import java.sql.Timestamp;
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
        Timestamp timestamp = new Timestamp(1484012906);
        System.out.println(timestamp.toLocalDateTime());


    }
}
