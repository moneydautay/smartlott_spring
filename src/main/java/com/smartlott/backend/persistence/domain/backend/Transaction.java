package com.smartlott.backend.persistence.domain.backend;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.smartlott.backend.persistence.converters.LocalDateTimeAttributeConverter;
import com.smartlott.enums.TransactionStatusEnum;
import org.springframework.beans.factory.annotation.Value;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Clock;
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
public class Transaction implements Serializable {

    /**
     * The Serial Version UID for Serializable classes
     */
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

    @ManyToMany(cascade = CascadeType.ALL, targetEntity = User.class)
    @JoinTable(name = "transfer_cash",
            joinColumns = @JoinColumn(name = "transaction_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "to_user", referencedColumnName = "id")
    )
    private Set<User> toUsers = new HashSet<>();

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

    public Set<User> getToUsers() {
        return toUsers;
    }

    public void setToUsers(Set<User> toUsers) {
        this.toUsers = toUsers;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + id +
                ", createdDate=" + createdDate +
                ", amount=" + amount +
                ", ofUser=" + ofUser.getEmail() +
                ", handleBy=" + ((handleBy != null) ? handleBy.getEmail() : null) +
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

    public static class Builder {
        private LocalDateTime createdDate;
        private double amount;
        private User ofUser;
        private User handleBy;
        private LocalDateTime handleDate;
        private TransactionType transactionType;
        private Set<LotteryDetail> lotteryDetails;
        private TransactionStatus transactionStatus;
        private List<InvestmentPackage> investmentPackages;
        private Set<User> toUsers;

        public Builder setCreatedDate(LocalDateTime createdDate) {
            this.createdDate = createdDate;
            return this;
        }

        public Builder setAmount(double amount) {
            this.amount = amount;
            return this;
        }

        public Builder setOfUser(User ofUser) {
            this.ofUser = ofUser;
            return this;
        }

        public Builder setHandleBy(User handleBy) {
            this.handleBy = handleBy;
            return this;
        }

        public Builder setHandleDate(LocalDateTime handleDate) {
            this.handleDate = handleDate;
            return this;
        }

        public Builder setTransactionType(TransactionType transactionType) {
            this.transactionType = transactionType;
            return this;
        }

        public Builder setLotteryDetails(Set<LotteryDetail> lotteryDetails) {
            this.lotteryDetails = lotteryDetails;
            return this;
        }

        public Builder setTransactionStatus(TransactionStatus transactionStatus) {
            this.transactionStatus = transactionStatus;
            return this;
        }

        public Builder setInvestmentPackages(List<InvestmentPackage> investmentPackages) {
            this.investmentPackages = investmentPackages;
            return this;
        }

        public Builder setToUsers(Set<User> toUsers) {
            this.toUsers = toUsers;
            return this;
        }

        public Transaction create() {
            Transaction transaction = new Transaction();
            transaction.setCreatedDate(this.createdDate);
            transaction.setAmount(this.amount);
            transaction.setOfUser(this.ofUser);
            transaction.setTransactionType(this.transactionType);
            transaction.setTransactionStatus(this.transactionStatus);
            if (this.handleBy != null) {
                transaction.setHandleBy(handleBy);
                transaction.setHandleDate(handleDate);
            }
            if (this.lotteryDetails != null)
                transaction.setLotteryDetails(this.lotteryDetails);
            if (this.investmentPackages != null)
                transaction.setInvestmentPackages(investmentPackages);
            if (this.toUsers != null)
                transaction.setToUsers(toUsers);

            return transaction;
        }
    }
}
