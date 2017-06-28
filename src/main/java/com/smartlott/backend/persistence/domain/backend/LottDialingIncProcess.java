package com.smartlott.backend.persistence.domain.backend;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.smartlott.backend.persistence.converters.LocalDateTimeAttributeConverter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Created by lamdevops on 6/26/17.
 */

@Entity
@Table(name = "lottdiaing_incprocess")
@EntityListeners(AuditingEntityListener.class)
public class LottDialingIncProcess {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne
    private LotteryDialing lotteryDialing;

    @ManyToOne
    private IncomeProcess incomeProcess;

    @JsonFormat(pattern = "dd/MM/yyyy")
    @Convert(converter = LocalDateTimeAttributeConverter.class)
    @CreatedDate
    private LocalDateTime handleDate;

    public LottDialingIncProcess() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public LotteryDialing getLotteryDialing() {
        return lotteryDialing;
    }

    public void setLotteryDialing(LotteryDialing lotteryDialing) {
        this.lotteryDialing = lotteryDialing;
    }

    public IncomeProcess getIncomeProcess() {
        return incomeProcess;
    }

    public void setIncomeProcess(IncomeProcess incomeProcess) {
        this.incomeProcess = incomeProcess;
    }

    public LocalDateTime getHandleDate() {
        return handleDate;
    }

    public void setHandleDate(LocalDateTime handleDate) {
        this.handleDate = handleDate;
    }

    @Override
    public String toString() {
        return "LottDialingIncProcess{" +
                "id=" + id +
                ", lotteryDialing=" + lotteryDialing.getId() +
                ", incomeProcess=" + incomeProcess.getName() +
                ", handleDate=" + handleDate +
                '}';
    }
}
