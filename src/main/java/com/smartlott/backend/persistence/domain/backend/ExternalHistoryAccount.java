package com.smartlott.backend.persistence.domain.backend;

import com.smartlott.backend.persistence.converters.LocalDateTimeAttributeConverter;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Created by greenlucky on 1/26/17.
 */
@Entity
@Table(name = "external_history_account")
public class ExternalHistoryAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "external_transaction_id")
    private String externalTransId;

    @Column(name = "create_date")
    @Convert(converter = LocalDateTimeAttributeConverter.class)
    private LocalDateTime createDate;

    @ManyToOne(fetch = FetchType.EAGER)
    private Transaction transaction;

    @ManyToOne(fetch = FetchType.EAGER)
    private NumberAccountType numberAccountType;

    public ExternalHistoryAccount() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getExternalTransId() {
        return externalTransId;
    }

    public void setExternalTransId(String externalTransId) {
        this.externalTransId = externalTransId;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    public Transaction getTransaction() {
        return transaction;
    }

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }

    public NumberAccountType getNumberAccountType() {
        return numberAccountType;
    }

    public void setNumberAccountType(NumberAccountType numberAccountType) {
        this.numberAccountType = numberAccountType;
    }

    @Override
    public String toString() {
        return "ExternalHistoryAccount{" +
                "id=" + id +
                ", externalTransId=" + externalTransId +
                ", createDate=" + createDate +
                ", transaction=" + transaction.getId() +
                ", numberAccountType=" + numberAccountType +
                '}';
    }
}
