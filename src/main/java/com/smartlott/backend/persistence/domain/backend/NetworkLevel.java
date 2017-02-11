package com.smartlott.backend.persistence.domain.backend;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by Mrs Hoang on 16/01/2017.
 */
@Entity
@Table(name = "network_level")
public class NetworkLevel implements Serializable{

    /** The Serial Version UID for Serializable classes */
    private static final long serialVersionUID = 1L;

    @Id
    private int id;

    private String name;

    private String description;

    @ManyToOne(fetch = FetchType.EAGER)
    private IncomeComponent incomeComponent;

    public NetworkLevel() {
    }

    public NetworkLevel(int id) {
        this.id = id;
    }

    public NetworkLevel(int id, String name, String description, IncomeComponent incomeComponent) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.incomeComponent = incomeComponent;
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

    public IncomeComponent getIncomeComponent() {
        return incomeComponent;
    }

    public void setIncomeComponent(IncomeComponent incomeComponent) {
        this.incomeComponent = incomeComponent;
    }

    @Override
    public String toString() {
        return "NetworkLevel{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", incomeComponent=" + incomeComponent +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        NetworkLevel that = (NetworkLevel) o;

        return id == that.id;
    }

    @Override
    public int hashCode() {
        return id;
    }
}
