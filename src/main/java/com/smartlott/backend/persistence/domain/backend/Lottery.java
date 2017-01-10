package com.smartlott.backend.persistence.domain.backend;

import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by Mrs Hoang on 17/12/2016.
 */
@Entity
@Table(name = "lottery")
public class Lottery implements Serializable{

    /** The Serial Version UID for Serializable classes */
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Length(max = 2, min = 2)
    private String coupleOne;

    @Length(max = 2, min = 2)
    private String coupleTwo;

    @Length(max = 2, min = 2)
    private String coupleThree;

    @Length(max = 2, min = 2)
    private String coupleFour;

    @Length(max = 2, min = 2)
    private String coupleFive;

    @Length(max = 2, min = 2)
    private String coupleSix;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "lottery_type_id")
    private LotteryType lotteryType;

    private boolean enabled = false;

    public Lottery() {

    }

    public Lottery(String coupleOne, String coupleTwo, String coupleThree,
                   String coupleFour, String coupleFive, String coupleSix,
                   LotteryType lotteryType) {
        this.coupleOne = coupleOne;
        this.coupleTwo = coupleTwo;
        this.coupleThree = coupleThree;
        this.coupleFour = coupleFour;
        this.coupleFive = coupleFive;
        this.coupleSix = coupleSix;
        this.lotteryType = lotteryType;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCoupleOne() {
        return coupleOne;
    }

    public void setCoupleOne(String coupleOne) {
        this.coupleOne = coupleOne;
    }

    public String getCoupleTwo() {
        return coupleTwo;
    }

    public void setCoupleTwo(String coupleTwo) {
        this.coupleTwo = coupleTwo;
    }

    public String getCoupleThree() {
        return coupleThree;
    }

    public void setCoupleThree(String coupleThree) {
        this.coupleThree = coupleThree;
    }

    public String getCoupleFour() {
        return coupleFour;
    }

    public void setCoupleFour(String coupleFour) {
        this.coupleFour = coupleFour;
    }

    public String getCoupleFive() {
        return coupleFive;
    }

    public void setCoupleFive(String coupleFive) {
        this.coupleFive = coupleFive;
    }

    public String getCoupleSix() {
        return coupleSix;
    }

    public void setCoupleSix(String coupleSix) {
        this.coupleSix = coupleSix;
    }

    public LotteryType getLotteryType() {
        return lotteryType;
    }

    public void setLotteryType(LotteryType lotteryType) {
        this.lotteryType = lotteryType;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    public String toString() {
        return "Lottery{" +
                "id=" + id +
                ", coupleOne='" + coupleOne + '\'' +
                ", coupleTwo='" + coupleTwo + '\'' +
                ", coupleThree='" + coupleThree + '\'' +
                ", coupleFour='" + coupleFour + '\'' +
                ", coupleFive='" + coupleFive + '\'' +
                ", coupleSix='" + coupleSix + '\'' +
                ", lotteryType=" + lotteryType +
                ", enabled=" + enabled +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Lottery lottery = (Lottery) o;

        return id == lottery.id;
    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }
}
