package com.smartlott.enums;

/**
 * Created by greenlucky on 1/1/17.
 */
public enum NumberAccountTypeEnum {
    BitCoin(1,"BitCoin","This is BitCoin number account",0.1,true,""),
    PerfectMoney(2,"Perfect Money","This is Perfect Money number account",0.2,true,"U9498703"),
    Cash(3,"Cash","This is Cash number account",0.0,true,"");

    private int id;
    private String name;
    private String description;
    private double feesWithdraw;
    private boolean rate;
    private String payeeAccount;

    NumberAccountTypeEnum() {
    }

    NumberAccountTypeEnum(int id, String name, String description, double fees_withdraw, boolean rate, String payeeAccount) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.feesWithdraw = fees_withdraw;
        this.rate = rate;
        this.payeeAccount = payeeAccount;
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

    public double getFeesWithdraw() {
        return feesWithdraw;
    }

    public boolean isRate() {
        return rate;
    }

    public String getPayeeAccount() {
        return payeeAccount;
    }
}
