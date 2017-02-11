package com.smartlott.backend.persistence.domain.backend;

import com.smartlott.enums.CashEnum;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by greenlucky on 2/3/17.
 */
@Entity
@Table(name = "cash")
public class Cash implements Serializable{

    /** The Serial Version UID for Serializable classes */
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String name;

    private String description;

    private boolean enabled = true;

    private boolean received = false;

    public Cash() {
    }

    public Cash(CashEnum cashEnum) {
        this.id = cashEnum.getId();
        this.name = cashEnum.getName();
        this.received = cashEnum.isReceived();
    }

    public Cash(String name, String description) {
        this.name = name;
        this.description = description;
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

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isReceived() {
        return received;
    }

    public void setReceived(boolean received) {
        this.received = received;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Cash userCash = (Cash) o;

        return id == userCash.id;
    }

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public String toString() {
        return "Cash{"
                + "id=" + id
                + ", name='" + name + '\''
                + ", description='" + description + '\''
                + ", enabled=" + enabled
                + ", received=" + received
                + '}';
    }
}
