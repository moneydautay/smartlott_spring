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
    private double value;

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

    @Override
    public String toString() {
        return "IncomeDetailRate{" +
                "id=" + id +
                ", incomeRate=" + incomeRate +
                ", incomeComponent=" + incomeComponent +
                ", value=" + value +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        IncomeDetailRate that = (IncomeDetailRate) o;

        if (id != that.id) return false;
        if (Double.compare(that.value, value) != 0) return false;
        if (incomeRate != null ? !incomeRate.equals(that.incomeRate) : that.incomeRate != null) return false;
        return incomeComponent != null ? incomeComponent.equals(that.incomeComponent) : that.incomeComponent == null;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = (int) (id ^ (id >>> 32));
        result = 31 * result + (incomeRate != null ? incomeRate.hashCode() : 0);
        result = 31 * result + (incomeComponent != null ? incomeComponent.hashCode() : 0);
        temp = Double.doubleToLongBits(value);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }
}
