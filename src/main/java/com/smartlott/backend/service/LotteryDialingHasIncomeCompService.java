package com.smartlott.backend.service;

import com.smartlott.backend.persistence.domain.backend.IncomeComponent;
import com.smartlott.backend.persistence.domain.backend.LotteryDialingHasIncomeComp;
import com.smartlott.backend.persistence.repositories.LotteryDialingHasIncomeComponentRepository;
import com.smartlott.utils.MathUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by greenlucky on 1/12/17.
 */
@Service
@Transactional
public class LotteryDialingHasIncomeCompService {
    
    /** The application logger */
    private static final Logger LOGGER = LoggerFactory.getLogger(LotteryDialingHasIncomeCompService.class);

    @Autowired
    private LotteryDialingHasIncomeComponentRepository dialingIncomCompRepository;


    @Transactional
    public LotteryDialingHasIncomeComp create(LotteryDialingHasIncomeComp lotteryDialingHasIncomeComponent){
        return dialingIncomCompRepository.save(lotteryDialingHasIncomeComponent);
    }

    @Transactional
    public LotteryDialingHasIncomeComp update(LotteryDialingHasIncomeComp lotteryDialingHasIncomeComponent){
        return dialingIncomCompRepository.save(lotteryDialingHasIncomeComponent);
    }

    @Transactional
    public void delete(long id){
        dialingIncomCompRepository.delete(id);
    }

    public LotteryDialingHasIncomeComp getOne(long id){
        return dialingIncomCompRepository.findOne(id);
    }

    public Page<LotteryDialingHasIncomeComp> getAll(Pageable pageable){
        return dialingIncomCompRepository.findAll(pageRequest(pageable));
    }

    public Page<LotteryDialingHasIncomeComp> getByLotteryDialingId(long lotteryDialingId, Pageable pageable){
        return dialingIncomCompRepository.findByLotteryDialingId(lotteryDialingId, pageRequest(pageable));
    }

    public List<LotteryDialingHasIncomeComp> getByLotteryDialingId(long lotteryDialingId){
        return dialingIncomCompRepository.findByLotteryDialingId(lotteryDialingId);
    }

    public Page<LotteryDialingHasIncomeComp> getByIncomeComponentId(long incomeComponentId, Pageable pageable){
        return dialingIncomCompRepository.findByIncomeComponentId(incomeComponentId, pageable);
    }

    public List<LotteryDialingHasIncomeComp> getByIncomeComponentId(List<IncomeComponent> incomeComponents, long dialingId){
        return dialingIncomCompRepository.findByLotteryDialingIdAndIncomeComponentIn(dialingId, incomeComponents);
    }

    private PageRequest pageRequest(Pageable pageable){
        return new PageRequest(pageable.getPageNumber(), pageable.getPageSize(), new Sort(Sort.Direction.DESC,"Id"));
    }

    @Transactional
    public void saveIncomeForLotteryDialing(long lotteryDialingId, double value) {


        List<LotteryDialingHasIncomeComp> results = dialingIncomCompRepository.findByLotteryDialingId(lotteryDialingId);


        for (LotteryDialingHasIncomeComp item : results){
            LOGGER.info("Update value of income in Lottery dialing has income component entity {}", item);
            double defautlRate = item.getIncomeComponent().getValue();
            double currentValue = item.getValue();
            //calculate income for each income component
            double income = (value*defautlRate)/100;
            income = MathUtils.round(income,4);
            currentValue = MathUtils.round(currentValue+income,4);
            item.setValue(currentValue);
            item = dialingIncomCompRepository.save(item);
            LOGGER.info("Updated value of income in Lottery dialing has income component entity {}", item);
        }

    }

    public List<LotteryDialingHasIncomeComp> getAll() {
        return dialingIncomCompRepository.findAll();
    }
}
