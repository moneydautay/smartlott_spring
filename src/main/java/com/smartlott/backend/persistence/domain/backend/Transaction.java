package com.smartlott.backend.persistence.domain.backend;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.smartlott.backend.persistence.converters.LocalDateTimeAttributeConverter;
import org.springframework.beans.factory.annotation.Value;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Mrs Hoang on 17/12/2016.
 */
@Entity
@Table(name = "transaction")
public class Transaction implements Serializable{

    /** The Serial Version UID for Serializable classes */
    private static final long serialVersionUID = 1L;


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @JsonFormat(pattern = "kk:mm:ss dd/MM/yyyy")
    @Column(updatable = false)
    @Convert(converter = LocalDateTimeAttributeConverter.class)
    private LocalDateTime createdDate;

    @Value("0.0")
    private double amount;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "of_user")
    private User ofUser;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "handle_by")
    private User handleBy;

    @JsonFormat(pattern = "kk:mm:ss dd/MM/yyyy")
    @Convert(converter = LocalDateTimeAttributeConverter.class)
    private LocalDateTime handleDate;

    @ManyToOne(fetch = FetchType.EAGER)
    private TransactionType transactionType;

    @OneToMany(mappedBy = "transaction", fetch = FetchType.LAZY)
    private Set<LotteryDetail> lotteryDetails = new HashSet<>();

    @Transient
    private String securityToken;

    public Transaction() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public User getOfUser() {
        return ofUser;
    }

    public void setOfUser(User ofUser) {
        this.ofUser = ofUser;
    }

    public User getHandleBy() {
        return handleBy;
    }

    public void setHandleBy(User handleBy) {
        this.handleBy = handleBy;
    }

    public LocalDateTime getHandleDate() {
        return handleDate;
    }

    public void setHandleDate(LocalDateTime handleDate) {
        this.handleDate = handleDate;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }

    public String getSecurityToken() {
        return securityToken;
    }

    public void setSecurityToken(String securityToken) {
        this.securityToken = securityToken;
    }

    public Set<LotteryDetail> getLotteryDetails() {
        return lotteryDetails;
    }

    public void setLotteryDetails(Set<LotteryDetail> lotteryDetails) {
        this.lotteryDetails = lotteryDetails;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + id +
                ", createdDate=" + createdDate +
                ", amount=" + amount +
                ", ofUser=" + ofUser +
                ", handleBy=" + handleBy +
                ", handleDate=" + handleDate +
                ", transactionType=" + transactionType +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Transaction that = (Transaction) o;

        return id == that.id;
    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }
}
