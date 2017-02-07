package com.smartlott.backend.persistence.domain.backend;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * Created by Mrs Hoang on 07/02/2017.
 */
@Entity
@Table(name = "investment_package")
public class InvestmentPackage implements Serializable{

    /** The Serial Version UID for Serializable classes */
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String name;

    private String description;

    private double value = 0;

    /**
     * The level of network this account of member will be receive bonus.
     */
    private int levelNetwork = 0;

    /**
     * Type of Investment Package has limit time or not.
     * If limit = false that mean duration time is 0 anyway
     * Else The duration time will have affection
     */
    private boolean limit = false;

    private int durationTime = 0;

    /**
     * The default price this package
     */
    private double price = 0;

    /**
     * If true package has affection
     * Otherwise not affection
     */
    private boolean enabled = true;

    @JsonIgnore
    @ManyToMany(mappedBy = "investmentPackages",cascade = CascadeType.ALL)
    private List<SaleOff> saleOffs;

    public InvestmentPackage() {
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

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public int getLevelNetwork() {
        return levelNetwork;
    }

    public void setLevelNetwork(int levelNetwork) {
        this.levelNetwork = levelNetwork;
    }

    public boolean isLimit() {
        return limit;
    }

    public void setLimit(boolean limit) {
        this.limit = limit;
    }

    public int getDurationTime() {
        return durationTime;
    }

    public void setDurationTime(int durationTime) {
        this.durationTime = durationTime;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public List<SaleOff> getSaleOffs() {
        return saleOffs;
    }

    public void setSaleOffs(List<SaleOff> saleOffs) {
        this.saleOffs = saleOffs;
    }

    @Override
    public String toString() {
        return "InvestmentPackage{"
                + "id=" + id
                + ", name='" + name + '\''
                + ", description='" + description + '\''
                + ", value=" + value
                + ", levelNetwork=" + levelNetwork
                + ", limit=" + limit
                + ", durationTime=" + durationTime
                + ", price=" + price
                + ", enabled=" + enabled
                + '}';
    }
}
