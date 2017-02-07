package com.smartlott.backend.persistence.domain.backend;

import com.smartlott.backend.persistence.converters.LocalDateTimeAttributeConverter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Created by Mrs Hoang on 07/02/2017.
 */
@Entity
@Table(name = "user_investment")
public class UserInvestment implements Serializable{

    /** The Serial Version UID for Serializable classes */
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Convert(converter = LocalDateTimeAttributeConverter.class)
    private LocalDateTime from;

    @Convert(converter = LocalDateTimeAttributeConverter.class)
    private LocalDateTime to;

    private boolean enabled = true;

    @ManyToOne
    private User user;

    @ManyToOne
    private InvestmentPackage investmentPackage;

    public UserInvestment() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public LocalDateTime getFrom() {
        return from;
    }

    public void setFrom(LocalDateTime from) {
        this.from = from;
    }

    public LocalDateTime getTo() {
        return to;
    }

    public void setTo(LocalDateTime to) {
        this.to = to;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
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

    @Override
    public String toString() {
        return "UserInvestment{"
                + "id=" + id
                + ", from=" + from
                + ", to=" + to
                + ", enabled=" + enabled
                + ", user=" + user
                + ", investmentPackage=" + investmentPackage
                + '}';
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
}
