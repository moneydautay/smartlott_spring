package com.smartlott.backend.persistence.domain.backend.trans;

import com.smartlott.backend.persistence.domain.base.BaseModel;
import org.springframework.beans.factory.annotation.Value;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

/**
 * Created by lamdevops on 7/23/17.
 */
@Entity
public class InvoiceAction extends BaseModel{

    @ManyToOne
    private CodingAccount debitAccount;

    @ManyToOne
    private CodingAccount creditAccount;

    @ManyToOne
    private InvoiceType invoiceType;

    @Value(value = "0.0")
    private double rate;

    public CodingAccount getDebitAccount() {
        return debitAccount;
    }

    public void setDebitAccount(CodingAccount debitAccount) {
        this.debitAccount = debitAccount;
    }

    public CodingAccount getCreditAccount() {
        return creditAccount;
    }

    public void setCreditAccount(CodingAccount creditAccount) {
        this.creditAccount = creditAccount;
    }

    public InvoiceType getInvoiceType() {
        return invoiceType;
    }

    public void setInvoiceType(InvoiceType invoiceType) {
        this.invoiceType = invoiceType;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    @Override
    public String toString() {
        return "InvoiceAction{" +
                "debitAccount=" + debitAccount +
                ", creditAccount=" + creditAccount +
                ", invoiceType=" + invoiceType +
                ", rate=" + rate +
                '}';
    }
}
