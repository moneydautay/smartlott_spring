package com.smartlott.backend.persistence.domain.backend;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by Mrs Hoang on 17/12/2016.
 */
@Entity
@Table(name = "income_component")
public class IncomeComponent implements Serializable{

    /** The Serial Version UID for Serializable classes */
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String name;

    private String description;

    @ManyToOne(fetch = FetchType.EAGER)
    private User createBy;

    @ManyToOne(fetch = FetchType.EAGER)
    private User modifyBy;

    private boolean jeckpots = false;

    private double value = 0.0;

    public IncomeComponent() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
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

    public User getCreateBy() {
        return createBy;
    }

    public void setCreateBy(User createBy) {
        this.createBy = createBy;
    }

    public User getModifyBy() {
        return modifyBy;
    }

    public void setModifyBy(User modifyBy) {
        this.modifyBy = modifyBy;
    }

    public boolean isJeckpots() {
        return jeckpots;
    }

    public void setJeckpots(boolean jeckpots) {
        this.jeckpots = jeckpots;
    }


    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "IncomeComponent{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", createBy=" + createBy +
                ", modifyBy=" + modifyBy +
                ", jeckpots=" + jeckpots +
                ", value=" + value +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        IncomeComponent that = (IncomeComponent) o;

        return id == that.id;
    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }


}
