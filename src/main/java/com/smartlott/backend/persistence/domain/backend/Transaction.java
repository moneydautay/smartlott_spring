package com.smartlott.backend.persistence.domain.backend;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.smartlott.backend.persistence.converters.LocalDateTimeAttributeConverter;
import org.springframework.beans.factory.annotation.Value;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "transaction_status_id")
    private TransactionStatus transactionStatus;

    @ManyToMany(cascade =
            CascadeType.DETACH,
            targetEntity = InvestmentPackage.class)
    @JoinTable(name = "transaction_investment_detail",
            joinColumns = @JoinColumn(name = "transaction_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "investment_package_id", referencedColumnName = "id")
    )
    private List<InvestmentPackage> investmentPackages = new ArrayList<>();

    @Transient
    private String securityToken;

    @Transient
    private long userCashId;

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


    public TransactionStatus getTransactionStatus() {
        return transactionStatus;
    }

    public void setTransactionStatus(TransactionStatus transactionStatus) {
        this.transactionStatus = transactionStatus;
    }

    public List<InvestmentPackage> getInvestmentPackages() {
        return investmentPackages;
    }

    public void setInvestmentPackages(List<InvestmentPackage> investmentPackages) {
        this.investmentPackages = investmentPackages;
    }

    public long getUserCashId() {
        return userCashId;
    }

    public void setUserCashId(long userCashId) {
        this.userCashId = userCashId;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + id +
                ", createdDate=" + createdDate +
                ", amount=" + amount +
                ", ofUser=" + ofUser.getEmail() +
                ", handleBy=" + ((handleBy != null)? handleBy.getEmail() : null) +
                ", handleDate=" + handleDate +
                ", transactionType=" + transactionType.getName() +
                ", lotteryDetails=" + lotteryDetails +
                ", transactionStatus=" + transactionStatus.getName() +
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
