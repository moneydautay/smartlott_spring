package com.smartlott;

import java.util.UUID;

/**
 * Created by greenlucky on 1/2/17.
 */
public class main {
    public static void main(String[] args){
        byte[] chars = ("greenlucky").getBytes();
        String introducedKey = UUID.nameUUIDFromBytes(chars).toString().replace("-","").substring(0,8);
        System.out.printf(introducedKey);
    }
}
