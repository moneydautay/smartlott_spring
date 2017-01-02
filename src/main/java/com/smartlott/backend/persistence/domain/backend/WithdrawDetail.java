package com.smartlott.backend.persistence.domain.backend;

import javax.persistence.*;

/**
 * Created by greenlucky on 1/1/17.
 */
@Entity
@Table(name = "withdraw_detail")
public class WithdrawDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private double fees;

    @ManyToOne(fetch = FetchType.EAGER)
    private Transaction transaction;

    @ManyToOne(fetch = FetchType.EAGER)
    private NumberAccount numberAccount;


    public WithdrawDetail() {
    }

    public WithdrawDetail(Transaction transaction, NumberAccount numberAccount, double fees) {
        this.transaction = transaction;
        this.numberAccount = numberAccount;
        this.fees = fees;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getFees() {
        return fees;
    }

    public void setFees(double fees) {
        this.fees = fees;
    }

    public Transaction getTransaction() {
        return transaction;
    }

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }

    public NumberAccount getNumberAccount() {
        return numberAccount;
    }

    public void setNumberAccount(NumberAccount numberAccount) {
        this.numberAccount = numberAccount;
    }

    @Override
    public String toString() {
        return "WithdrawDetail{" +
                "id=" + id +
                ", fees=" + fees +
                ", transaction=" + transaction +
                ", numberAccount=" + numberAccount +
                '}';
    }
}
