package com.smartlott.backend.service;

import com.smartlott.backend.persistence.domain.backend.Lottery;
import com.smartlott.backend.persistence.domain.backend.LotteryDetail;
import com.smartlott.backend.persistence.domain.backend.Transaction;
import com.smartlott.backend.persistence.repositories.LotteryDetailRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by greenlucky on 1/8/17.
 */
@Service
@Transactional(readOnly = true)
public class LotteryDetailService {

    /** The application logger */
    private static final Logger LOGGER = LoggerFactory.getLogger(LotteryDetailService.class);

    @Autowired
    private LotteryDetailRepository lotteryDetailRepository;


    /**
     * Creates Lottery Details given by list lotteries, transaction and ip address of buyer
     * @param lotteries
     * @param transaction
     * @param ipAddress
     */
    public void createNewLotterDetails(List<Lottery> lotteries, Transaction transaction, String ipAddress){

        for (Lottery lottery: lotteries) {
            LotteryDetail lotteryDetail = new LotteryDetail();
            lotteryDetail.setLottery(lottery);
            lotteryDetail.setTransaction(transaction);
            lotteryDetail.setIpAddress(ipAddress);
            lotteryDetailRepository.save(lotteryDetail);
            LOGGER.info("Created Lottery detail {}", lotteryDetail);
        }
    }
}
