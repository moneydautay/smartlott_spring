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
    @JoinColumn(name = "transaction_id")
    private Transaction transaction;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "lottery_dialing_id")
    private LotteryDialing lotteryDialing;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "lottery_type_id")
    private LotteryType lotteryType;

    public Lottery() {
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

    public Transaction getTransaction() {
        return transaction;
    }

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }

    public LotteryDialing getLotteryDialing() {
        return lotteryDialing;
    }

    public void setLotteryDialing(LotteryDialing lotteryDialing) {
        this.lotteryDialing = lotteryDialing;
    }

    public LotteryType getLotteryType() {
        return lotteryType;
    }

    public void setLotteryType(LotteryType lotteryType) {
        this.lotteryType = lotteryType;
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
                ", transaction=" + transaction +
                ", lotteryDialing=" + lotteryDialing +
                ", lotteryType=" + lotteryType +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Lottery lottery = (Lottery) o;

        if (id != lottery.id) return false;
        if (coupleOne != null ? !coupleOne.equals(lottery.coupleOne) : lottery.coupleOne != null) return false;
        if (coupleTwo != null ? !coupleTwo.equals(lottery.coupleTwo) : lottery.coupleTwo != null) return false;
        if (coupleThree != null ? !coupleThree.equals(lottery.coupleThree) : lottery.coupleThree != null) return false;
        if (coupleFour != null ? !coupleFour.equals(lottery.coupleFour) : lottery.coupleFour != null) return false;
        if (coupleFive != null ? !coupleFive.equals(lottery.coupleFive) : lottery.coupleFive != null) return false;
        if (coupleSix != null ? !coupleSix.equals(lottery.coupleSix) : lottery.coupleSix != null) return false;
        if (transaction != null ? !transaction.equals(lottery.transaction) : lottery.transaction != null) return false;
        if (lotteryDialing != null ? !lotteryDialing.equals(lottery.lotteryDialing) : lottery.lotteryDialing != null)
            return false;
        return lotteryType != null ? lotteryType.equals(lottery.lotteryType) : lottery.lotteryType == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (coupleOne != null ? coupleOne.hashCode() : 0);
        result = 31 * result + (coupleTwo != null ? coupleTwo.hashCode() : 0);
        result = 31 * result + (coupleThree != null ? coupleThree.hashCode() : 0);
        result = 31 * result + (coupleFour != null ? coupleFour.hashCode() : 0);
        result = 31 * result + (coupleFive != null ? coupleFive.hashCode() : 0);
        result = 31 * result + (coupleSix != null ? coupleSix.hashCode() : 0);
        result = 31 * result + (transaction != null ? transaction.hashCode() : 0);
        result = 31 * result + (lotteryDialing != null ? lotteryDialing.hashCode() : 0);
        result = 31 * result + (lotteryType != null ? lotteryType.hashCode() : 0);
        return result;
    }
}
