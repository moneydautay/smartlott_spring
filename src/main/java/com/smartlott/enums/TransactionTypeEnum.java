package com.smartlott.enums;

/**
 * Created by greenlucky on 1/1/17.
 */
public enum TransactionTypeEnum {
    Withdraw(1,"Withdraw","This is withdraw transaction"),
    BuyingLottery(2,"Buying lottery", "This is buying lottery");

    private  int id;
    private String name;
    private String description;

    TransactionTypeEnum(){

    }

    TransactionTypeEnum(int id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
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
}
