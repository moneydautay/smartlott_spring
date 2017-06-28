package com.smartlott.backend.persistence.domain.backend;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;

/**
 * Created by greenlucky on 1/8/17.
 */
@Entity
@Table(name = "lottery_detail")
public class LotteryDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @OneToMany(mappedBy = "lotteryDetail")
    private List<Lottery> lotteries;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "transaction_id")
    private Transaction transaction;

    @Column(name = "ip_address")
    private String ipAddress;


    public LotteryDetail() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<Lottery> getLotteries() {
        return lotteries;
    }

    public void setLotteries(List<Lottery> lotteries) {
        this.lotteries = lotteries;
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
                ", lotteries=" + lotteries +
                ", ipAddress='" + ipAddress + '\'' +
                '}';
    }
}
