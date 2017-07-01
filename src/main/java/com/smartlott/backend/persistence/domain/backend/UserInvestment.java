package com.smartlott.backend.persistence.domain.backend;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.smartlott.backend.persistence.converters.LocalDateTimeAttributeConverter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Created by Mrs Hoang on 09/02/2017.
 */
@Entity
@Table(name = "user_investment")
public class UserInvestment implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @JsonIgnore
    @ManyToOne
    private User user;

    @ManyToOne
    private InvestmentPackage investmentPackage;

    @Convert(converter = LocalDateTimeAttributeConverter.class)
    private LocalDateTime fromDate;

    @Convert(converter = LocalDateTimeAttributeConverter.class)
    private LocalDateTime toDate;

    private boolean enabled = true;

    public UserInvestment() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public InvestmentPackage getInvestmentPackage() {
        return investmentPackage;
    }

    public void setInvestmentPackage(InvestmentPackage investmentPackage) {
        this.investmentPackage = investmentPackage;
    }


    public LocalDateTime getFromDate() {
        return fromDate;
    }

    public void setFromDate(LocalDateTime fromDate) {
        this.fromDate = fromDate;
    }

    public LocalDateTime getToDate() {
        return toDate;
    }

    public void setToDate(LocalDateTime toDate) {
        this.toDate = toDate;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserInvestment that = (UserInvestment) o;

        return id == that.id;
    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }

    @Override
    public String toString() {
        return "UserInvestment{"
                + "id=" + id
                + ", user=" + user.getId()
                + ", investmentPackage=" + investmentPackage.getName()
                + ", from=" + fromDate
                + ", to=" + toDate
                + ", enabled=" + enabled
                + '}';
    }
}
