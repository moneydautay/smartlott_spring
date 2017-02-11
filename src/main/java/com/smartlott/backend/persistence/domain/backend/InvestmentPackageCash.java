package com.smartlott.backend.persistence.domain.backend;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by Mrs Hoang on 10/02/2017.
 */
@Entity
@Table(name = "investment_package_cash")
public class InvestmentPackageCash implements Serializable{

    /** The Serial Version UID for Serializable classes */
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne(fetch = FetchType.EAGER)
    private InvestmentPackage investmentPackage;

    @ManyToOne(fetch = FetchType.EAGER)
    private Cash cash;

    /**
     * The rate dividing price of investmentPackage to each cash
     */
    private int ratevalue = 0;

    public InvestmentPackageCash() {
    }

    public InvestmentPackage getInvestmentPackage() {
        return investmentPackage;
    }

    public void setInvestmentPackage(InvestmentPackage investmentPackage) {
        this.investmentPackage = investmentPackage;
    }

    public Cash getCash() {
        return cash;
    }

    public void setCash(Cash cash) {
        this.cash = cash;
    }

    public int getRatevalue() {
        return ratevalue;
    }

    public void setRatevalue(int ratevalue) {
        this.ratevalue = ratevalue;
    }

    @Override
    public String toString() {
        return "InvestmentPackageCash{"
                + "id=" + id
                + ", investmentPackage=" + investmentPackage
                + ", cash=" + cash
                + ", ratevalue=" + ratevalue
                + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        InvestmentPackageCash that = (InvestmentPackageCash) o;

        return id == that.id;
    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }
}
