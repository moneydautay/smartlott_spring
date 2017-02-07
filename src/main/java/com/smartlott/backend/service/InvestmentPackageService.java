package com.smartlott.backend.service;

import com.smartlott.backend.persistence.domain.backend.InvestmentPackage;
import com.smartlott.backend.persistence.repositories.InvestmentPackageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Mrs Hoang on 07/02/2017.
 */
@Service
@Transactional(readOnly = true)
public class InvestmentPackageService {

    @Autowired
    private InvestmentPackageRepository investmentRepository;


    @Transactional
    public InvestmentPackage create(InvestmentPackage investmentPackage){
        return investmentRepository.save(investmentPackage);
    }

    @Transactional
    public InvestmentPackage update(InvestmentPackage investmentPackage){
        return investmentRepository.save(investmentPackage);
    }

    @Transactional
    public void delete(int id){
        investmentRepository.delete(id);
    }

    public InvestmentPackage getOne(int id){
        return investmentRepository.findOne(id);
    }

    public List<InvestmentPackage> getAll(){
        return investmentRepository.findAll();
    }

    public Page<InvestmentPackage> getAll(Pageable pageable){
        return investmentRepository.findAll(pageable);
    }
}
