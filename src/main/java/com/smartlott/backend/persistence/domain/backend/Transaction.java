package com.smartlott.backend.persistence.domain.backend;

import com.smartlott.backend.persistence.converters.LocalDateTimeAttributeConverter;
import org.springframework.beans.factory.annotation.Value;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

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

    private LocalDateTime createdDate;

    @Value("0.0")
    private double amount;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "of_user")
    private User ofUser;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "handle_by")
    private User handleBy;

    @Convert(converter = LocalDateTimeAttributeConverter.class)
    private LocalDateTime handleDate;

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

    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + id +
                ", createdDate=" + createdDate +
                ", amount=" + amount +
                ", ofUser=" + ofUser +
                ", handleBy=" + handleBy +
                ", handleDate=" + handleDate +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Transaction that = (Transaction) o;

        if (id != that.id) return false;
        if (Double.compare(that.amount, amount) != 0) return false;
        if (createdDate != null ? !createdDate.equals(that.createdDate) : that.createdDate != null) return false;
        if (ofUser != null ? !ofUser.equals(that.ofUser) : that.ofUser != null) return false;
        if (handleBy != null ? !handleBy.equals(that.handleBy) : that.handleBy != null) return false;
        return handleDate != null ? handleDate.equals(that.handleDate) : that.handleDate == null;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = (int) (id ^ (id >>> 32));
        result = 31 * result + (createdDate != null ? createdDate.hashCode() : 0);
        temp = Double.doubleToLongBits(amount);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (ofUser != null ? ofUser.hashCode() : 0);
        result = 31 * result + (handleBy != null ? handleBy.hashCode() : 0);
        result = 31 * result + (handleDate != null ? handleDate.hashCode() : 0);
        return result;
    }
}
