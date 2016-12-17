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

    @Value("true")
    private boolean enabled;

    private LocalDateTime fromDate;

    private LocalDateTime toDate;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "create_by")
    private User createBy;

    @Value("false")
    private boolean jeckpots;

    public IncomeRate() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
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

    public boolean isJeckpots() {
        return jeckpots;
    }

    public void setJeckpots(boolean jeckpots) {
        this.jeckpots = jeckpots;
    }

    @Override
    public String toString() {
        return "IncomeRate{" +
                "id=" + id +
                ", enabled=" + enabled +
                ", fromDate=" + fromDate +
                ", toDate=" + toDate +
                ", createBy=" + createBy +
                ", jeckpots=" + jeckpots +
                '}';
    }
}
