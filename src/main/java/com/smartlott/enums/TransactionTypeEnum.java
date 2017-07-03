package com.smartlott.enums;

/**
 * Created by greenlucky on 1/1/17.
 */
public enum TransactionTypeEnum {
    Withdraw(1,"Withdraw","This is withdraw transaction", false, "-"),
    BuyingLottery(2,"Buying lottery", "This is buying lottery", true, "-"),
    BuyInvestmentPackage(3,"Investment package","This is buying investment package", true, "-"),
    SendCash(4, "Send cash", "This is transfer cash", true, "-"),
    ReceiveCash(5, "Receive cash", "This is transfer cash", true, "+"),
    IncomeInvestment(6, "Income Process", "This is payment income investment package for members", true, "+"),
    DivideAward(7, "Divide award", "This is divide award for members", true, "+");

    private  int id;
    private String name;
    private String description;
    private boolean autoHandle = false;
    private String symbol;

    TransactionTypeEnum(){

    }

    TransactionTypeEnum(int id, String name, String description, boolean autoHandle, String symbol) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.autoHandle = autoHandle;
        this.symbol = symbol;
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

    public boolean isAutoHandle() {
        return autoHandle;
    }

    public String getSymbol() {
        return symbol;
    }
}
