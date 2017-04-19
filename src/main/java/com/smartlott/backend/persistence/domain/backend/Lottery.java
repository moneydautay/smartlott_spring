package com.smartlott.backend.persistence.domain.backend;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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

    @Transient
    private String sequense;

    @JsonFormat(pattern = "kk:mm:ss dd/MM/yyyy")
    @Transient
    private LocalDateTime buyDate;

    @Transient
    private String buyBy;

    @Transient
    private String lotteryDialing;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "lottery_type_id")
    private LotteryType lotteryType;

    @JsonIgnore
    @ManyToOne
    @Fetch(FetchMode.JOIN)
    private LotteryDetail lotteryDetail;


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

    public LotteryDetail getLotteryDetail() {
        return lotteryDetail;
    }

    public void setLotteryDetail(LotteryDetail lotteryDetail) {
        this.lotteryDetail = lotteryDetail;
    }

    public String getSequense() {
        sequense = coupleOne + "-" + coupleTwo + "-" + coupleThree+ "-" + coupleFour + "-" + coupleFive + "-" + coupleSix;
        return sequense;
    }

    public LocalDateTime getBuyDate() {
        return lotteryDetail.getTransaction().getCreatedDate();
    }

    public String getBuyBy() {
        return lotteryDetail.getTransaction().getOfUser().getUsername();
    }

    public String getLotteryDialing() {

        DateTimeFormatter df = DateTimeFormatter.ofPattern("kk:mm:ss dd/MM/yyyy");

        return lotteryDetail.getLotteryDialing().getFromDate().format(df) + "-" + lotteryDetail.getLotteryDialing().getToDate().format(df);
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

    public String printCoupleLottery(int numberComparedCouple) {
        String strCouple= "[" ;
        if(numberComparedCouple >=6)
            strCouple+= coupleOne + "-";
        if(numberComparedCouple >=5)
            strCouple+=coupleTwo + "-";
        if(numberComparedCouple >=4)
            strCouple+=coupleThree + "-";
        if(numberComparedCouple >=3)
            strCouple+=coupleFour + "-";
        if(numberComparedCouple >=2)
            strCouple+=coupleFive + "-";
        strCouple+=coupleSix;
                        strCouple+="]";
        return strCouple;
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

    /**
     * Compares two lotteries base on numberComparedCouple. The minimize is 2 couple
     *
     * @param o
     * @param numberComparedCouple
     * @return True or false
     */
    public boolean compareTwoLotteries(Object o, int numberComparedCouple) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Lottery lottery = (Lottery) o;
        if(numberComparedCouple>=6)
            if (!coupleOne.equals(lottery.coupleOne)) return false;
        if(numberComparedCouple>=5)
            if (!coupleTwo.equals(lottery.coupleTwo)) return false;
        if(numberComparedCouple>=4)
            if (!coupleThree.equals(lottery.coupleThree)) return false;
        if(numberComparedCouple>=3)
            if (!coupleFour.equals(lottery.coupleFour)) return false;
        if (!coupleFive.equals(lottery.coupleFive)) return false;
        return coupleSix.equals(lottery.coupleSix);
    }
}
