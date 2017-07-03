package com.smartlott.backend.persistence.domain.backend;

import org.springframework.beans.factory.annotation.Value;

import javax.persistence.*;

/**
 * Created by lamdevops on 7/3/17.
 */
@Entity
@Table(name = "divide_award")
public class DivideAward {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String name;

    private String description;

    @Value(value = "0.0")
    private double value;

    @ManyToOne
    private Cash cash;

    public DivideAward() {
    }

    public DivideAward(String name, double value, Cash cash) {
        this.name = name;
        this.value = value;
        this.cash = cash;
    }

    public DivideAward(String name, String description, Cash cash) {
        this.name = name;
        this.description = description;
        this.cash = cash;
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

    public Cash getCash() {
        return cash;
    }

    public void setCash(Cash cash) {
        this.cash = cash;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DivideAward that = (DivideAward) o;

        return id == that.id;
    }

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public String toString() {
        return "DivideAward{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", cash=" + cash +
                ", value=" + value +
                '}';
    }
}
