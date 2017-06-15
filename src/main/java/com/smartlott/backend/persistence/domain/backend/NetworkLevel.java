package com.smartlott.backend.persistence.domain.backend;

import com.smartlott.enums.BonusType;

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

    private int level;

    private String description;

    private boolean enabled = true;

    @ManyToOne(fetch = FetchType.EAGER)
    private IncomeComponent incomeComponent;

    private BonusType bonusType;

    public NetworkLevel() {
    }

    public NetworkLevel(int id) {
        this.id = id;
    }

    public NetworkLevel(int id, int level, String description, IncomeComponent incomeComponent, BonusType bonusType) {
        this.id = id;
        this.level = level;
        this.description = description;
        this.incomeComponent = incomeComponent;
        this.bonusType = bonusType;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
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

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public BonusType getBonusType() {
        return bonusType;
    }

    public void setBonusType(BonusType bonusType) {
        this.bonusType = bonusType;
    }

    @Override
    public String toString() {
        return "NetworkLevel{"
                + "id=" + id
                + ", level=" + level
                + ", description='" + description + '\''
                + ", enabled=" + enabled
                + ", incomeComponent=" + incomeComponent
                + ", bonusType=" + bonusType
                + '}';
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
