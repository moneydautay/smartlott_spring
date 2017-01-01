package com.smartlott.backend.persistence.domain.backend;

import com.smartlott.enums.NumberAccountTypeEnum;

import javax.persistence.*;

/**
 * Created by Mrs Hoang on 17/12/2016.
 */
@Entity
@Table(name = "number_account_type")
public class NumberAccountType {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String name;

    private String description;

    private double feesWithdraw = 0.0;

    private boolean rate = true;

    public NumberAccountType() {
    }

    public NumberAccountType(NumberAccountTypeEnum accountTypeEnum){
        this.id = accountTypeEnum.getId();
        this.name = accountTypeEnum.getName();
        this.description = accountTypeEnum.getDescription();
        this.feesWithdraw = accountTypeEnum.getFeesWithdraw();
        this.rate = accountTypeEnum.isRate();
    }

    public NumberAccountType(String name, String description, double feesWithdraw, boolean rate) {
        this.name = name;
        this.description = description;
        this.feesWithdraw = feesWithdraw;
        this.rate = rate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getfeesWithdraw() {
        return feesWithdraw;
    }

    public void setfeesWithdraw(double feesWithdraw) {
        this.feesWithdraw = feesWithdraw;
    }

    public boolean isRate() {
        return rate;
    }

    public void setRate(boolean rate) {
        this.rate = rate;
    }

    @Override
    public String toString() {
        return "NumberAccountTypeEnum{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        NumberAccountType that = (NumberAccountType) o;

        if (id != that.id) return false;
        return name != null ? name.equals(that.name) : that.name == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }
}
