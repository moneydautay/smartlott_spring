package com.smartlott.backend.persistence.domain.backend;

import javax.persistence.*;

/**
 * Created by greenlucky on 2/3/17.
 */
@Entity
@Table(name = "user_cash")
public class UserCash {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne
    private User user;

    @ManyToOne
    private Cash cash;

    double value=0.0;

    public UserCash() {
    }

    public UserCash(User user, Cash cash) {
        this.user = user;
        this.cash = cash;
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
}
