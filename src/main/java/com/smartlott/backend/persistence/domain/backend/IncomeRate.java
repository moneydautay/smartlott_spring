package com.smartlott.backend.persistence.domain.backend;

import org.springframework.beans.factory.annotation.Value;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Created by Mrs Hoang on 17/12/2016.
 */
@Entity
@Table(name = "income_rate")
public class IncomeRate implements Serializable{

    /** The Serial Version UID for Serializable classes */
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private boolean _default;

    private LocalDateTime fromDate;

    private LocalDateTime toDate;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "create_by")
    private User createBy;


    public IncomeRate() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public boolean isDefault() {
        return _default;
    }

    public void setDefault(boolean _default) {
        this._default = _default;
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

    public User getCreateBy() {
        return createBy;
    }

    public void setCreateBy(User createBy) {
        this.createBy = createBy;
    }

    @Override
    public String toString() {
        return "IncomeRate{" +
                "id=" + id +
                ", _default=" + _default +
                ", fromDate=" + fromDate +
                ", toDate=" + toDate +
                ", createBy=" + createBy +
                '}';
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        IncomeRate that = (IncomeRate) o;

        return id == that.id;
    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }
}
