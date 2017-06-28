package com.smartlott.backend.persistence.domain.backend;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.smartlott.backend.persistence.converters.LocalDateTimeAttributeConverter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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

    @Transient
    private String tempToDate;

    private boolean opened = true;

    public LotteryDialing() {
    }

    public LotteryDialing(LocalDateTime fromDate, LocalDateTime toDate) {
        this.fromDate = fromDate;
        this.toDate = toDate;
    }

    public LotteryDialing(long termId) {
        this.id = termId;
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

    public String getTempToDate() {
        DateTimeFormatter ft = DateTimeFormatter.ofPattern("yyyy/MM/dd kk:mm:ss");
        if(toDate != null)
            tempToDate = toDate.format(ft);
        return tempToDate;
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
