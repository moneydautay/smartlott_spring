package com.smartlott.backend.persistence.domain.backend;

import org.springframework.beans.factory.annotation.Value;

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

    @Value("0.0")
    @Column(name = "default_rate_value")
    private double defaultRateValue;

    @ManyToOne(fetch = FetchType.EAGER)
    private User createBy;

    @ManyToOne(fetch = FetchType.EAGER)
    private User modifyBy;

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

    public double getDefaultRateValue() {
        return defaultRateValue;
    }

    public void setDefaultRateValue(double defaultRateValue) {
        this.defaultRateValue = defaultRateValue;
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

    @Override
    public String toString() {
        return "IncomeComponent{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", defaultRateValue=" + defaultRateValue +
                ", createBy=" + createBy +
                ", modifyBy=" + modifyBy +
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
