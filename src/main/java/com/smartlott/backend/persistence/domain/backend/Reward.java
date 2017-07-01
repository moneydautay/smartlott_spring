package com.smartlott.backend.persistence.domain.backend;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    private boolean jackpots;

    private int defaultNumericReward = 0;

    private int coupleNumber = 1;

    private boolean accumulation = false;

    @JsonIgnore
    @OneToOne(mappedBy = "reward")
    private IncomeComponent incomeComponent;

    private boolean enabled = true;

    public Reward() {
    }

    public Reward(String name) {
        this.name = name;
    }

    public Reward(Integer k) {
        this.id = id;
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

    public boolean isJackpots() {
        return jackpots;
    }

    public void setJackpots(boolean jackpots) {
        this.jackpots = jackpots;
    }


    public int getCoupleNumber() {
        return coupleNumber;
    }

    public void setCoupleNumber(int coupleNumber) {
        this.coupleNumber = coupleNumber;
    }

    public IncomeComponent getIncomeComponent() {
        return incomeComponent;
    }

    public void setIncomeComponent(IncomeComponent incomeComponent) {
        this.incomeComponent = incomeComponent;
    }

    public boolean isAccumulation() {
        return accumulation;
    }

    public void setAccumulation(boolean accumulation) {
        this.accumulation = accumulation;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    public String toString() {
        return "Reward{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", value=" + value +
                ", jackpots=" + jackpots +
                ", defaultNumericReward=" + defaultNumericReward +
                ", coupleNumber=" + coupleNumber +
                ", accumulation=" + accumulation +
                ", enabled=" + enabled +
                ", incomeComponent=" + ((incomeComponent != null ) ? incomeComponent.getName() : null) +
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
