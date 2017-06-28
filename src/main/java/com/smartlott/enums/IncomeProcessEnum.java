package com.smartlott.enums;

/**
 * Created by lamdevops on 6/26/17.
 */
public enum  IncomeProcessEnum {


    LIST_JACKPOT(1, "List jackpot"),
    INCOME_PACKAGE(2, "Income package");


    private int id;
    private String name;

    IncomeProcessEnum(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
