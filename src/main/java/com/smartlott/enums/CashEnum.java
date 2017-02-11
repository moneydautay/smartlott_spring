package com.smartlott.enums;

/**
 * Created by Mrs Hoang on 10/02/2017.
 */
public enum CashEnum {

    CASH(1, "Cash", true),
    INVESTMENT(2, "Cash investment", false),
    TRADING(3, "Cash trading", false);

    private int id;

    private String name;

    private boolean received;

    CashEnum(int id, String name, boolean received) {
        this.id = id;
        this.name = name;
        this.received = received;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public boolean isReceived() {
        return received;
    }
}
