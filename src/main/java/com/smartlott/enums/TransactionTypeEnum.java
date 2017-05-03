package com.smartlott.enums;

/**
 * Created by greenlucky on 1/1/17.
 */
public enum TransactionTypeEnum {
    Withdraw(1,"Withdraw","This is withdraw transaction", false),
    BuyingLottery(2,"Buying lottery", "This is buying lottery", true),
    BuyInvestmentPackage(3,"Investment package","This is buying investment package", true),
    TransferCash(4, "Transfer cash", "This is transfer cash", true);

    private  int id;
    private String name;
    private String description;
    private boolean autoHandle = false;

    TransactionTypeEnum(){

    }

    TransactionTypeEnum(int id, String name, String description, boolean autoHandle) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.autoHandle = autoHandle;
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
}
