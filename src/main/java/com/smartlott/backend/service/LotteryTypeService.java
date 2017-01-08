package com.smartlott.backend.service;

import com.smartlott.backend.persistence.domain.backend.LotteryType;
import com.smartlott.backend.persistence.repositories.LotteryTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by greenlucky on 1/8/17.
 */
@Service
public class LotteryTypeService {

    @Autowired
    private LotteryTypeRepository typeRepository;

    /**
     * Creates new lottery type given by lottery type
     * This function using method save of LotteryTypeRepository to create
     * new lottery type
     *
     * @param lotteryType given by user
     * @return A lottery after create or exception if occurs error
     * @exception Exception if occurs errors
     * @see LotteryType
     * @see LotteryTypeRepository
     */
    public LotteryType create(LotteryType lotteryType) throws Exception{
        return typeRepository.save(lotteryType);
    }


    /**
     * Retrieves all Lottery Type exist in database
     * This function getAll using method findAll of LotteryTypeRepository to get data
     *
     * @return A list of Lottery Type of null if not found
     * @see LotteryType
     * @see LotteryTypeRepository
     */
    public List<LotteryType> getAll(){
        return typeRepository.findAll();
    }

    /**
     * Retrieves a lottery type or null if not found
     * This function getOne call method findOne of <b>LotteryTypeRepository</b> to get data
     *
     * @param lotteryTypeId
     * @return A Lottery Type or null if not exist
     * @see LotteryType
     */
    public LotteryType getOne(int lotteryTypeId){
        return typeRepository.findOne(lotteryTypeId);
    }

    /**
     * Deletes Lottery Type given by lotteryTypeId
     * This function delete call method delete of <b>LotteryTypeRepository</b>
     * and assign param lotteryTypeId to delete Lottery Type
     *
     * @param lotteryTypeId
     * @see LotteryTypeRepository
     */
    public void delete(int lotteryTypeId){
        typeRepository.delete(lotteryTypeId);
    }
}
