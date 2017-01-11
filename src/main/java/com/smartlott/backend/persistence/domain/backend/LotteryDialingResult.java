package com.smartlott.backend.persistence.domain.backend;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.smartlott.backend.persistence.converters.LocalDateTimeAttributeConverter;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Created by Mrs Hoang on 18/12/2016.
 */
@Entity
@Table(name = "lottery_dialing_result")
public class LotteryDialingResult implements Serializable{

    /** The Serial Version UID for Serializable classes */
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Length(min = 2, max = 2)
    private String coupleNumber;

    @Length(max = 1, min = 1)
    private int position;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "lottery_dialing")
    private LotteryDialing lotteryDialing;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "of_user")
    private User ofUser;

    @ManyToOne(fetch = FetchType.EAGER)
    private Reward reward;

    @JsonFormat(pattern = "kk:mm:ss dd/MM/yyyy")
    @Convert(converter = LocalDateTimeAttributeConverter.class)
    private LocalDateTime createDate;

    public LotteryDialingResult() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCoupleNumber() {
        return coupleNumber;
    }

    public void setCoupleNumber(String coupleNumber) {
        this.coupleNumber = coupleNumber;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public LotteryDialing getLotteryDialing() {
        return lotteryDialing;
    }

    public void setLotteryDialing(LotteryDialing lotteryDialing) {
        this.lotteryDialing = lotteryDialing;
    }

    public User getOfUser() {
        return ofUser;
    }

    public void setOfUser(User ofUser) {
        this.ofUser = ofUser;
    }

    public Reward getReward() {
        return reward;
    }

    public void setReward(Reward reward) {
        this.reward = reward;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    @Override
    public String toString() {
        return "LotteryDialingResult{" +
                "id=" + id +
                ", coupleNumber='" + coupleNumber + '\'' +
                ", position=" + position +
                ", lotteryDialing=" + lotteryDialing +
                ", ofUser=" + ofUser +
                ", reward=" + reward +
                ", createDate=" + createDate +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LotteryDialingResult that = (LotteryDialingResult) o;

        return id == that.id;
    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }
}
