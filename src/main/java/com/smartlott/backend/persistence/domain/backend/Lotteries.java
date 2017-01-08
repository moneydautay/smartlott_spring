package com.smartlott.backend.persistence.domain.backend;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.util.List;

/**
 * Created by greenlucky on 1/8/17.
 */
public class Lotteries {

    @JsonSerialize
    private List<Lottery> lotteries;

    private long userId;

    private int lotteryTypeId;

    public Lotteries() {
    }

    public Lotteries(List<Lottery> lotteries) {
        this.lotteries = lotteries;
    }

    public List<Lottery> getLotteries() {
        return lotteries;
    }

    public void setLotteries(List<Lottery> lotteries) {
        this.lotteries = lotteries;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public int getLotteryTypeId() {
        return lotteryTypeId;
    }

    public void setLotteryTypeId(int lotteryTypeId) {
        this.lotteryTypeId = lotteryTypeId;
    }
}
