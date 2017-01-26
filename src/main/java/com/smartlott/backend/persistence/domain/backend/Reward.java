package com.smartlott.backend.persistence.domain.backend;

import org.hibernate.validator.constraints.Length;
import org.springframework.beans.factory.annotation.Value;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by Mrs Hoang on 18/12/2016.
 */
@Entity
@Table(name = "reward")
public class Reward implements Serializable{

    /** The Serial Version UID for Serializable classes */
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Length(max = 45)
    private String name;

    @Value("0.0")
    private double value;

    @Value("false")
    private boolean jeckpots;

    private int defaultNumericReward = 0;

    @ManyToOne(fetch = FetchType.EAGER)
    private IncomeComponent incomeComponent;

    private int coupleNumber = 2;

    public Reward() {
    }

    public Reward(String name) {
        this.name = name;
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

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public int getDefaultNumericReward() {
        return defaultNumericReward;
    }

    public void setDefaultNumericReward(int defaultNumericReward) {
        this.defaultNumericReward = defaultNumericReward;
    }

    public boolean isJeckpots() {
        return jeckpots;
    }

    public void setJeckpots(boolean jeckpots) {
        this.jeckpots = jeckpots;
    }


    public IncomeComponent getIncomeComponent() {
        return incomeComponent;
    }

    public void setIncomeComponent(IncomeComponent incomeComponent) {
        this.incomeComponent = incomeComponent;
    }

    public int getCoupleNumber() {
        return coupleNumber;
    }

    public void setCoupleNumber(int coupleNumber) {
        this.coupleNumber = coupleNumber;
    }

    @Override
    public String toString() {
        return "Reward{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", value=" + value +
                ", jeckpots=" + jeckpots +
                ", defaultNumericReward=" + defaultNumericReward +
                ", incomeComponent=" + incomeComponent +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Reward reward = (Reward) o;

        return id == reward.id;
    }

    @Override
    public int hashCode() {
        return id;
    }
}
