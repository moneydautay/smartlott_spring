package com.smartlott.backend.persistence.domain.backend;


import com.smartlott.backend.persistence.converters.LocalDateTimeAttributeConverter;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Created by Mrs Hoang on 17/12/2016.
 */
@Entity
@Table(name = "bonous")
public class Bonous {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne(fetch = FetchType.EAGER)
    private User ofUser;

    @ManyToOne(fetch = FetchType.EAGER)
    private User fromUser;

    @Convert(converter = LocalDateTimeAttributeConverter.class)
    private LocalDateTime receivedDate;

    public Bonous() {
    }

    public Bonous(User ofUser, User fromUser, LocalDateTime receivedDate) {
        this.ofUser = ofUser;
        this.fromUser = fromUser;
        this.receivedDate = receivedDate;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Bonous bonous = (Bonous) o;

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

    @Override
    public String toString() {
        return "Bonous{" +
                "id=" + id +
                ", ofUser=" + ofUser +
                ", fromUser=" + fromUser +
                ", receivedDate=" + receivedDate +
                '}';
    }
}
