package com.smartlott;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Created by greenlucky on 1/2/17.
 */
public class main {
    public static void main(String[] args){
        String fromDate = "01-01-1970 00:00:00";
        String toDate = "02-01-2017 00:00:00";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy kk:mm:ss");
        LocalDateTime from = LocalDateTime.parse(fromDate, formatter);
        LocalDateTime to = LocalDateTime.parse(toDate, formatter);
        System.out.println(from);
        System.out.println(to);
    }
}
