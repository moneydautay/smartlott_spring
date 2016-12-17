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

    public boolean isJeckpots() {
        return jeckpots;
    }

    public void setJeckpots(boolean jeckpots) {
        this.jeckpots = jeckpots;
    }

    @Override
    public String toString() {
        return "Reward{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", value=" + value +
                ", jeckpots=" + jeckpots +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Reward reward = (Reward) o;

        if (id != reward.id) return false;
        if (Double.compare(reward.value, value) != 0) return false;
        if (jeckpots != reward.jeckpots) return false;
        return name != null ? name.equals(reward.name) : reward.name == null;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        temp = Double.doubleToLongBits(value);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (jeckpots ? 1 : 0);
        return result;
    }
}
