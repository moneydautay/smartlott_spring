package com.smartlott.backend.persistence.domain.backend;

import com.smartlott.enums.InvestmentPackageEnum;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * Created by Mrs Hoang on 09/02/2017.
 */
@Entity
@Table(name = "investment_package")
public class InvestmentPackage implements Serializable {

    /** The Serial Version UID for Serializable classes */
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @NotNull
    private String name;

    private String description;

    private boolean enabled = true;

    private double price = 0;

    private int levelNetwork = 0;

    private boolean limitTime = true;

    int durationTime = 0;

    private int parent = 0;

    @OneToMany(mappedBy = "investmentPackage", fetch = FetchType.EAGER)
    private List<InvestmentPackageCash> investmentPackageCashes;


    public InvestmentPackage() {
    }

    public InvestmentPackage(InvestmentPackageEnum packageEnum) {
        this.id = packageEnum.getId();
        this.name = packageEnum.getName();
        this.description = packageEnum.getDescription();
       this.price = packageEnum.getPrice();
        this.levelNetwork = packageEnum.getLevelNetwork();
        this.limitTime = packageEnum.isLimit();
        this.durationTime = packageEnum.getDurationTime();
        this.parent = packageEnum.getParent();
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

   public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getLevelNetwork() {
        return levelNetwork;
    }

    public void setLevelNetwork(int levelNetwork) {
        this.levelNetwork = levelNetwork;
    }

    public boolean isLimitTime() {
        return limitTime;
    }

    public void setLimitTime(boolean limitTime) {
        this.limitTime = limitTime;
    }

    public int getDurationTime() {
        return durationTime;
    }

    public void setDurationTime(int durationTime) {
        this.durationTime = durationTime;
    }

    public List<InvestmentPackageCash> getInvestmentPackageCashes() {
        return investmentPackageCashes;
    }

    public void setInvestmentPackageCashes(List<InvestmentPackageCash> investmentPackageCashes) {
        this.investmentPackageCashes = investmentPackageCashes;
    }

    public int getParent() {
        return parent;
    }

    public void setParent(int parent) {
        this.parent = parent;
    }

    @Override
    public String toString() {
        return "InvestmentPackage{"
                + "id=" + id
                + ", name='" + name + '\''
                + ", description='" + description + '\''
                + ", enabled=" + enabled
                + ", price=" + price
                + ", levelNetwork=" + levelNetwork
                + ", limitTime=" + limitTime
                + ", durationTime=" + durationTime
                + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        InvestmentPackage that = (InvestmentPackage) o;

        return id == that.id;
    }

    @Override
    public int hashCode() {
        return id;
    }
}
