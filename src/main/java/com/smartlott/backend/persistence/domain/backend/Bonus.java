package com.smartlott.backend.persistence.domain.backend;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.smartlott.backend.persistence.converters.LocalDateTimeAttributeConverter;
import com.smartlott.enums.BonusType;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Created by Mr Lam on 17/12/2016.
 */
@Entity
@Table(name = "bonus")
public class Bonus {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne(fetch = FetchType.EAGER)
    private User ofUser;

    @ManyToOne(fetch = FetchType.EAGER)
    private User fromUser;

    @JsonFormat(pattern = "kk:mm:ss dd/MM/yyyy")
    @Convert(converter = LocalDateTimeAttributeConverter.class)
    private LocalDateTime receivedDate;

    private int level;

    private double bonus;

    private BonusType bonusType;

    public Bonus() {
    }

    public Bonus(double bonus, User ofUser, User fromUser, LocalDateTime receivedDate, int level, BonusType bonusType) {
        this.bonus = bonus;
        this.ofUser = ofUser;
        this.fromUser = fromUser;
        this.receivedDate = receivedDate;
        this.level = level;
        this.bonusType = bonusType;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getOfUser() {
        return ofUser;
    }

    public void setOfUser(User ofUser) {
        this.ofUser = ofUser;
    }

    public User getFromUser() {
        return fromUser;
    }

    public void setFromUser(User fromUser) {
        this.fromUser = fromUser;
    }

    public LocalDateTime getReceivedDate() {
        return receivedDate;
    }

    public void setReceivedDate(LocalDateTime receivedDate) {
        this.receivedDate = receivedDate;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public double getBonus() {
        return bonus;
    }

    public void setBonus(double bonus) {
        this.bonus = bonus;
    }

    public BonusType getBonusType() {
        return bonusType;
    }

    public void setBonusType(BonusType bonusType) {
        this.bonusType = bonusType;
    }

    @Override
    public String toString() {
        return "Bonus{" +
                "id=" + id +
                ", ofUser=" + ofUser +
                ", fromUser=" + fromUser +
                ", receivedDate=" + receivedDate +
                ", level=" + level +
                ", bonus=" + bonus +
                ", bonusType=" + bonusType +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Bonus bonous = (Bonus) o;

        if (id != bonous.id) return false;
        if (ofUser != null ? !ofUser.equals(bonous.ofUser) : bonous.ofUser != null) return false;
        if (fromUser != null ? !fromUser.equals(bonous.fromUser) : bonous.fromUser != null) return false;
        return receivedDate != null ? receivedDate.equals(bonous.receivedDate) : bonous.receivedDate == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (ofUser != null ? ofUser.hashCode() : 0);
        result = 31 * result + (fromUser != null ? fromUser.hashCode() : 0);
        result = 31 * result + (receivedDate != null ? receivedDate.hashCode() : 0);
        return result;
    }
}
