package com.smartlott.backend.service;

import com.smartlott.backend.persistence.domain.backend.LotteryDialing;
import com.smartlott.backend.persistence.repositories.LotteryDialingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Mrs Hoang on 05/01/2017.
 */
@Service
@Transactional(readOnly = true)
public class LotteryDialingService {

    @Autowired
    private LotteryDialingRepository dialingRepository;

    /**
     * Create new lottery dialing
     * @param lotteryDialing
     * @return A lottery dialing after create
     */
    @Transactional
    public LotteryDialing create(LotteryDialing lotteryDialing){
        return dialingRepository.save(lotteryDialing);
    }

    /**
     * Update lottery dialing
     * @param lotteryDialing
     * @return A lottery dialing after update
     */
    @Transactional
    public LotteryDialing update(LotteryDialing lotteryDialing){
        return dialingRepository.save(lotteryDialing);
    }


    /**
     * Retrive all lottery dialing with pagination
     * @param pageable
     * @return A list of lottery dialing with pageable control page or null if not exist
     */
    public Page<LotteryDialing> getAll(Pageable pageable){
        PageRequest request = new PageRequest(pageable.getPageNumber(), pageable.getPageSize(), new Sort(Sort.Direction.DESC,"id"));
        Page<LotteryDialing> lotteryDialings = dialingRepository.findAll(request);
        return lotteryDialings;
    }

    /**
     * Retrieve a lottery dialing given by id
     * @param id
     * @return A lottery dialing or null if not eixst
     */
    public LotteryDialing getOne(long id){
        return dialingRepository.findOne(id);
    }

    /**
     * Retrieve lottery dialing is opening
     * @param opened
     * @return A lottery dialing is opening or null if not found
     */
    public LotteryDialing getOpenedLotteryDialing(boolean opened) {
        return  dialingRepository.findByOpened(opened);
    }
}
