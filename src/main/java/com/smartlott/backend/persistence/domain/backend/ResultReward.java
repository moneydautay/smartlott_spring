package com.smartlott.backend.persistence.domain.backend;

import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by Mrs Hoang on 18/12/2016.
 */
@Entity
@Table(name = "result_reward")
public class ResultReward implements Serializable{

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

    public ResultReward() {
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

    @Override
    public String toString() {
        return "ResultReward{" +
                "id=" + id +
                ", coupleNumber='" + coupleNumber + '\'' +
                ", position=" + position +
                ", lotteryDialing=" + lotteryDialing +
                ", ofUser=" + ofUser +
                ", reward=" + reward +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ResultReward that = (ResultReward) o;

        if (id != that.id) return false;
        if (position != that.position) return false;
        if (coupleNumber != null ? !coupleNumber.equals(that.coupleNumber) : that.coupleNumber != null) return false;
        if (lotteryDialing != null ? !lotteryDialing.equals(that.lotteryDialing) : that.lotteryDialing != null)
            return false;
        if (ofUser != null ? !ofUser.equals(that.ofUser) : that.ofUser != null) return false;
        return reward != null ? reward.equals(that.reward) : that.reward == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (coupleNumber != null ? coupleNumber.hashCode() : 0);
        result = 31 * result + position;
        result = 31 * result + (lotteryDialing != null ? lotteryDialing.hashCode() : 0);
        result = 31 * result + (ofUser != null ? ofUser.hashCode() : 0);
        result = 31 * result + (reward != null ? reward.hashCode() : 0);
        return result;
    }
}
