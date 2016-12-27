package com.smartlott.backend.persistence.domain.backend;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;

/**
 * Created by Mrs Hoang on 17/12/2016.
 */
@Entity
@Table(name = "number_account")
public class NumberAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotEmpty(message = "NotEmpty.numberAccount.number")
    @Column(unique = true)
    private String number;

    @ManyToOne(fetch = FetchType.EAGER)
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    private NumberAccountType numberAccountType;

    @Transient
    private String securityToken;

    public NumberAccount() {
    }

    public NumberAccount(String number, User user, NumberAccountType numberAccountType) {
        this.number = number;
        this.user = user;
        this.numberAccountType = numberAccountType;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public NumberAccountType getNumberAccountType() {
        return numberAccountType;
    }

    public void setNumberAccountType(NumberAccountType numberAccountType) {
        this.numberAccountType = numberAccountType;
    }

    public String getSecurityToken() {
        return securityToken;
    }

    public void setSecurityToken(String securityToken) {
        this.securityToken = securityToken;
    }

    @Override
    public String toString() {
        return "NumberAccount{" +
                "id=" + id +
                ", number='" + number + '\'' +
                ", user=" + user +
                ", numberAccountType=" + numberAccountType +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        NumberAccount that = (NumberAccount) o;

        if (id != that.id) return false;
        if (user != null ? !user.equals(that.user) : that.user != null) return false;
        return numberAccountType != null ? numberAccountType.equals(that.numberAccountType) : that.numberAccountType == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (user != null ? user.hashCode() : 0);
        result = 31 * result + (numberAccountType != null ? numberAccountType.hashCode() : 0);
        return result;
    }
}
