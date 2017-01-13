package com.smartlott.backend.service;

import com.smartlott.backend.persistence.domain.backend.LotteryDialingHasIncomeComponent;
import com.smartlott.backend.persistence.repositories.LotteryDialingHasIncomeComponentRepository;
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
public class LotteryDialingHasIncomeComponentService {

    @Autowired
    private LotteryDialingHasIncomeComponentRepository dialingIncomCompRepository;


    @Transactional
    public LotteryDialingHasIncomeComponent create(LotteryDialingHasIncomeComponent lotteryDialingHasIncomeComponent){
        return dialingIncomCompRepository.save(lotteryDialingHasIncomeComponent);
    }

    @Transactional
    public LotteryDialingHasIncomeComponent update(LotteryDialingHasIncomeComponent lotteryDialingHasIncomeComponent){
        return dialingIncomCompRepository.save(lotteryDialingHasIncomeComponent);
    }

    @Transactional
    public void delete(long id){
        dialingIncomCompRepository.delete(id);
    }

    public LotteryDialingHasIncomeComponent getOne(long id){
        return dialingIncomCompRepository.findOne(id);
    }

    public Page<LotteryDialingHasIncomeComponent> getAll(Pageable pageable){
        return dialingIncomCompRepository.findAll(pageRequest(pageable));
    }

    public Page<LotteryDialingHasIncomeComponent> getByLotteryDialingId(long lotteryDialingId, Pageable pageable){
        return dialingIncomCompRepository.findByLotteryDialingId(lotteryDialingId, pageRequest(pageable));
    }

    public Page<LotteryDialingHasIncomeComponent> getByIncomeComponentId(long incomeComponentId, Pageable pageable){
        return dialingIncomCompRepository.findByIncomeComponentId(incomeComponentId, pageable);
    }

    private PageRequest pageRequest(Pageable pageable){
        return new PageRequest(pageable.getPageNumber(), pageable.getPageSize(), new Sort(Sort.Direction.DESC,"Id"));
    }

    @Transactional
    public void saveIncomeForLotteryDialing(long lotteryDialingId, double value) {

        List<LotteryDialingHasIncomeComponent> results = dialingIncomCompRepository.findByLotteryDialingId(lotteryDialingId);


        for (LotteryDialingHasIncomeComponent item : results){
            double defautlRate = item.getIncomeComponent().getValue();
            double currentValue = item.getValue();
            //calculate income for each income component
            double income = value*defautlRate/100;
            currentValue+=income;
            item.setValue(currentValue);
            dialingIncomCompRepository.save(item);
        }
    }
}
