package com.smartlott.enums;

/**
 * Created by Mrs Hoang on 10/02/2017.
 */
public enum CashEnum {

    CASH(1, "Cash", true, true, true),
    INVESTMENT(2, "Cash investment", false, false, true),
    TRADING(3, "Cash trading", false, false ,false);

    private int id;

    private String name;

    private boolean received;

    private boolean withdraw = false;

    private boolean transfer = true;

    CashEnum(int id, String name, boolean received, boolean withdraw, boolean transfer) {
        this.id = id;
        this.name = name;
        this.received = received;
        this.withdraw = withdraw;
        this.transfer = transfer;
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

    public boolean isWithdraw() {
        return withdraw;
    }

    public boolean isTransfer() {
        return transfer;
    }
}
