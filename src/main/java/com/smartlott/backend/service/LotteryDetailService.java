package com.smartlott.backend.service;

import com.smartlott.backend.persistence.domain.backend.LotteryDetail;
import com.smartlott.backend.persistence.domain.backend.LotteryDialing;
import com.smartlott.backend.persistence.domain.backend.Transaction;
import com.smartlott.backend.persistence.repositories.LotteryDetailRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
     * @param transaction
     * @param ipAddress
     */
    public LotteryDetail createNewLotteryDetails(Transaction transaction,
                                        String ipAddress, LotteryDialing lotteryDialing) {

        LotteryDetail lotteryDetail = new LotteryDetail();
        lotteryDetail.setTransaction(transaction);
        lotteryDetail.setIpAddress(ipAddress);
        lotteryDetail.setLotteryDialing(lotteryDialing);
        lotteryDetailRepository.save(lotteryDetail);

        return lotteryDetail;
    }

    public Page<LotteryDetail> getLotteryDetailByUserId(long userId, Pageable pageable){
        return lotteryDetailRepository.findByTransactionOfUserId(userId, pageable);
    }
}
