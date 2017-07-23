package com.smartlott.backend.persistence.domain.backend.trans;

import com.smartlott.backend.persistence.domain.base.BaseModel;

import javax.persistence.Entity;

/**
 * Created by lamdevops on 7/23/17.
 */
@Entity
public class SysAccNum extends BaseModel{

    private String name;

    private String acc_num;

    private double amount;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAcc_num() {
        return acc_num;
    }

    public void setAcc_num(String acc_num) {
        this.acc_num = acc_num;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "SysAccNum{" +
                "name='" + name + '\'' +
                ", acc_num='" + acc_num + '\'' +
                ", amount=" + amount +
                '}';
    }
}
