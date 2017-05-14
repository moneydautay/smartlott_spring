package com.smartlott.backend.persistence.domain.elastic;

import com.smartlott.backend.persistence.domain.backend.Lottery;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.Id;
import java.io.Serializable;
import java.time.format.DateTimeFormatter;

/**
 * Created by Mrs Hoang on 17/12/2016.
 */
@Document(indexName = "smartlott", type = "lottery", shards = 1, replicas = 0)
public class LotteryElastic implements Serializable{

    /** The Serial Version UID for Serializable classes */
    private static final long serialVersionUID = 1L;

    @Id
    private long id;

    private String sequense;

    private String buyDate;

    private String buyBy;

    private String lotteryDialing;

    private boolean enabled;
    public LotteryElastic() {

    }

    public LotteryElastic(Lottery lottery) {
        DateTimeFormatter df = DateTimeFormatter.ofPattern("kk:mm:ss dd/MM/yyyy");
        this.id = lottery.getId();
        this.sequense = lottery.getSequense();
        this.buyDate = (null != lottery.getBuyDate()) ? lottery.getBuyDate().format(df) : null;
        this.buyBy = lottery.getBuyBy();
        this.lotteryDialing = lottery.getLotteryDialing();
        this.enabled = lottery.isEnabled();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getSequense() {
        return sequense;
    }

    public void setSequense(String sequense) {
        this.sequense = sequense;
    }

    public String getBuyDate() {
        return buyDate;
    }

    public void setBuyDate(String buyDate) {
        this.buyDate = buyDate;
    }

    public String getBuyBy() {
        return buyBy;
    }

    public void setBuyBy(String buyBy) {
        this.buyBy = buyBy;
    }

    public String getLotteryDialing() {
        return lotteryDialing;
    }

    public void setLotteryDialing(String lotteryDialing) {
        this.lotteryDialing = lotteryDialing;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LotteryElastic that = (LotteryElastic) o;

        return id == that.id;
    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }
}
