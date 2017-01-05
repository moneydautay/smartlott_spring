package com.smartlott.backend.persistence.domain.backend;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.smartlott.backend.persistence.converters.LocalDateTimeAttributeConverter;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Created by Mrs Hoang on 17/12/2016.
 */
@Entity
@Table(name = "lottery_dialing")
public class LotteryDialing implements Serializable{

    /** The Serial Version UID for Serializable classes */
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @JsonFormat(pattern = "kk:mm:ss dd/MM/yyyy")
    @Column(name = "from_date", updatable = false)
    //@JsonSerialize(using = LocalDateTimeSerializer.class)
    @Convert(converter =LocalDateTimeAttributeConverter.class)
    private LocalDateTime fromDate;

    @JsonFormat(pattern = "kk:mm:ss dd/MM/yyyy")
    @Column(name = "to_date", updatable = false)
    //@JsonSerialize(using = LocalDateTimeSerializer.class)
    @Convert(converter = LocalDateTimeAttributeConverter.class)
    private LocalDateTime toDate;

    private boolean opened = true;

    public LotteryDialing() {
    }

    public LotteryDialing(LocalDateTime fromDate, LocalDateTime toDate) {
        this.fromDate = fromDate;
        this.toDate = toDate;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public boolean isOpened() {
        return opened;
    }

    public void setOpened(boolean opened) {
        this.opened = opened;
    }

    @Override
    public String toString() {
        return "LotteryDialing{" +
                "id=" + id +
                ", fromDate=" + fromDate +
                ", toDate=" + toDate +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LotteryDialing that = (LotteryDialing) o;

        return id == that.id;
    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }
}
