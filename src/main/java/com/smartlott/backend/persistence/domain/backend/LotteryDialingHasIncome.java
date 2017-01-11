package com.smartlott.backend.persistence.domain.backend;

import javax.persistence.*;

/**
 * Created by Mrs Hoang on 11/01/2017.
 */
@Entity
@Table(name = "lottery_dialing_has_income")
public class LotteryDialingHasIncome {

    @Id
    private long id;

    @ManyToOne(fetch = FetchType.EAGER)
    private LotteryDialing lotteryDialing;

    @ManyToOne(fetch = FetchType.EAGER)
    private IncomeComponent incomeComponent;

    private double value;

    public LotteryDialingHasIncome() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public LotteryDialing getLotteryDialing() {
        return lotteryDialing;
    }

    public void setLotteryDialing(LotteryDialing lotteryDialing) {
        this.lotteryDialing = lotteryDialing;
    }

    public IncomeComponent getIncomeComponent() {
        return incomeComponent;
    }

    public void setIncomeComponent(IncomeComponent incomeComponent) {
        this.incomeComponent = incomeComponent;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "LotteryDialingHasIncome{" +
                "id=" + id +
                ", lotteryDialing=" + lotteryDialing +
                ", incomeComponent=" + incomeComponent +
                ", value=" + value +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LotteryDialingHasIncome that = (LotteryDialingHasIncome) o;

        return id == that.id;
    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }
}
