package com.smartlott.enums;

/**
 * Created by greenlucky on 1/1/17.
 */
public enum TransactionTypeEnum {
    Withdraw(1,"Withdraw","This is withdraw transaction",0.1),
    BuyingLottery(2,"Buying lottery", "This is buying lottery",.1);

    private  int id;
    private String name;
    private String description;
    private  double feesWithdraw =0;

    TransactionTypeEnum(){

    }

    TransactionTypeEnum(int id, String name, String description, double fees) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.feesWithdraw = fees;
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

    public void setFeesWithdraw(double feesWithdraw) {
        this.feesWithdraw = feesWithdraw;
    }
}
