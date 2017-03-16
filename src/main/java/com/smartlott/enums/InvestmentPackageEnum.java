package com.smartlott.enums;

/**
 * Created by Mrs Hoang on 08/02/2017.
 */
public enum InvestmentPackageEnum {

    CUSTOMER(1,"CUSTOMER","",0,false,0,0, 0),
    AGENT(2,"AGENT","",1,false,0,50, 0),
    INVEST(3,"INVEST","",2,true,30, 100, 2),
    PROINVEST(4,"PRO INVEST","",3,true,30, 200, 2),
    SLIVERINVEST(5,"SLIVER INVEST","",4,true,30, 400, 2),
    GOLDINVEST(6,"GOLD INVEST","",5,true,30, 600, 2),
    PLATIUMINVEST(7,"PRO INVEST","",6,true,30, 800, 2),
    DIAMONDINVEST(8,"DIAMOND INVEST","",7,true,30, 1000, 2);

    private int id;
    private String name;
    private String description;
    private int levelNetwork = 0;
    private boolean limit = false;
    private int durationTime = 0;
    private double price = 0;
    private int parent = 0;

    InvestmentPackageEnum(int id, String name,
                          String description,
                          int levelNetwork, boolean limit,
                          int durationTime, double price, int parent) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.levelNetwork = levelNetwork;
        this.limit = limit;
        this.durationTime = durationTime;
        this.price = price;
        this.parent = parent;
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

    public int getLevelNetwork() {
        return levelNetwork;
    }

    public boolean isLimit() {
        return limit;
    }

    public int getDurationTime() {
        return durationTime;
    }

    public double getPrice() {
        return price;
    }

    public int getParent() {
        return parent;
    }
}
