package com.smartlott.backend.persistence.domain.backend;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

/**
 * Created by greenlucky on 1/8/17.
 */
@Entity
@Table(name = "lottery_detail")
public class LotteryDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "lottery_id")
    private Lottery lottery;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "transaction_id")
    private Transaction transaction;

    @Column(name = "ip_address")
    private String ipAddress;

    public LotteryDetail() {
    }

    public LotteryDetail(Lottery lottery, Transaction transaction, String ipAddress) {
        this.lottery = lottery;
        this.transaction = transaction;
        this.ipAddress = ipAddress;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Lottery getLottery() {
        return lottery;
    }

    public void setLottery(Lottery lottery) {
        this.lottery = lottery;
    }

    public Transaction getTransaction() {
        return transaction;
    }

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    @Override
    public String toString() {
        return "LotteryDetail{" +
                "id=" + id +
                ", lottery=" + lottery +
                ", transaction=" + transaction +
                ", ipAddress='" + ipAddress + '\'' +
                '}';
    }
}
