package com.smartlott.backend.persistence.domain.backend;

import org.springframework.beans.factory.annotation.Value;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by Mrs Hoang on 17/12/2016.
 */
@Entity
@Table(name = "income_detail_rate")
public class IncomeDetailRate implements Serializable{

    /** The Serial Version UID for Serializable classes */
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne(fetch = FetchType.EAGER)
    private IncomeRate incomeRate;

    @ManyToOne(fetch = FetchType.EAGER)
    private IncomeComponent incomeComponent;

    @Value("0.0")
    private double value=0.0;

    private double static_value_reward=0.0;

    private boolean atLeastOne = false;

    public IncomeDetailRate() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public IncomeRate getIncomeRate() {
        return incomeRate;
    }

    public void setIncomeRate(IncomeRate incomeRate) {
        this.incomeRate = incomeRate;
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

    public double getStatic_value_reward() {
        return static_value_reward;
    }

    public void setStatic_value_reward(double static_value_reward) {
        this.static_value_reward = static_value_reward;
    }

    public boolean isAtLeastOne() {
        return atLeastOne;
    }

    public void setAtLeastOne(boolean atLeastOne) {
        this.atLeastOne = atLeastOne;
    }

    @Override
    public String toString() {
        return "IncomeDetailRate{" +
                "id=" + id +
                ", incomeRate=" + incomeRate +
                ", incomeComponent=" + incomeComponent +
                ", value=" + value +
                ", static_value_reward=" + static_value_reward +
                ", atLeastOne=" + atLeastOne +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        IncomeDetailRate that = (IncomeDetailRate) o;

        return id == that.id;
    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }
}
