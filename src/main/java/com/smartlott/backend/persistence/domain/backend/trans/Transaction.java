package com.smartlott.backend.persistence.domain.backend.trans;

import com.smartlott.backend.persistence.domain.base.BaseModel;
import org.springframework.beans.factory.annotation.Value;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

/**
 * Created by lamdevops on 7/23/17.
 */
@Entity
public class Transaction extends BaseModel{


    @Value(value = "0.0")
    private double amount;

    @ManyToOne
    private InvoiceType invoiceType;

    @ManyToOne
    private TransStatus status;

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public InvoiceType getInvoiceType() {
        return invoiceType;
    }

    public void setInvoiceType(InvoiceType invoiceType) {
        this.invoiceType = invoiceType;
    }

    public TransStatus getStatus() {
        return status;
    }

    public void setStatus(TransStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "amount=" + amount +
                ", invoiceType=" + invoiceType +
                ", status=" + status +
                '}';
    }
}
