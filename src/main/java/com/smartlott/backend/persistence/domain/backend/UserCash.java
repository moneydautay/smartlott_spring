package com.smartlott.backend.persistence.domain.backend;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

/**
 * Created by greenlucky on 2/3/17.
 */
@Entity
@Table(name = "user_cash")
public class UserCash {

    /** The Serial Version UID for Serializable classes */
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    private User user;

    @ManyToOne
    private Cash cash;

    double value=0.0;

    private boolean enabled = true;

    public UserCash() {
    }

    public UserCash(User user, Cash cash) {
        this.user = user;
        this.cash = cash;
    }

    public UserCash(User user, Cash cash, double value) {
        this.user = user;
        this.cash = cash;
        this.value = value;
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

    public Cash getCash() {
        return cash;
    }

    public void setCash(Cash cash) {
        this.cash = cash;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public void plusValue(double value) {
        this.value += value;
    }

    public void minusValue(double value) {
        this.value -= value;
    }

    @Override
    public String toString() {
        return "UserCash{"
                + "id=" + id
                + ", user=" + user
                + ", cash=" + cash
                + ", value=" + value
                + ", enabled=" + enabled
                + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserCash userCash = (UserCash) o;

        return id == userCash.id;
    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }
}
