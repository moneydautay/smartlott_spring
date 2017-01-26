package com.smartlott.enums;

/**
 * Created by Mrs Hoang on 17/01/2017.
 */
public enum  LotteryTypeEnum {
    TYPE1(1, "Type One","This is type one lottery type",100.0),
    TYPE2(3, "Type Tow","This is type two lottery type",200.0);

    private int id;
    private String name;
    private String description;
    private double price;

    LotteryTypeEnum(int id, String name, String description, double price) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public double getPrice() {
        return price;
    }
}
