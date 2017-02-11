package com.smartlott.backend.service;

import com.smartlott.backend.persistence.domain.backend.Cash;
import com.smartlott.backend.persistence.domain.backend.InvestmentPackage;
import com.smartlott.backend.persistence.domain.backend.InvestmentPackageCash;
import com.smartlott.backend.persistence.repositories.InvestmentPackageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mrs Hoang on 09/02/2017.
 */
@Service
@Transactional(readOnly = true)
public class InvestmentPackageService {

    @Autowired
    private InvestmentPackageRepository packageRepository;

    public InvestmentPackageService(InvestmentPackageRepository investmentPackageRepository) {
    }

    /**
     * Creates new investment package given by investment package
     *
     * @param investmentPackage
     * @return A investment package after created
     * @see InvestmentPackage
     */
    @Transactional
    public InvestmentPackage create(InvestmentPackage investmentPackage) {
        return packageRepository.save(investmentPackage);
    }

    /**
     * Updates investment package given by investment package
     * @param investmentPackage
     * @return A investment package
     * @see InvestmentPackage
     */
    @Transactional
    public InvestmentPackage update(InvestmentPackage investmentPackage) {
        return packageRepository.save(investmentPackage);
    }

    /**
     * Deletes investment package given by package id or nothing if id not exist
     * @param id of investment package
     */
    @Transactional
    public void delete(int id){
        packageRepository.delete(id);
    }

    /**
     * Gets a Investment Package given by id or null if not exist
     * @param id
     * @return A investment package or null if not exist
     */
    public InvestmentPackage getOne(int id){
        return packageRepository.findOne(id);
    }


    /**
     * Gets all package exist in database
     * @return A list of package or list is empty if investment package is not exist
     */
    public List<InvestmentPackage> getAll(){
        return packageRepository.findAll();
    }

    /**
     * Gets all package have enabled is equals enabled given by enabled or null if not exist.
     *
     * @param enabled true or false
     * @return A list of package or list is empty if investment package is not exist
     */
    public List<InvestmentPackage> getAll(boolean enabled) {
        return packageRepository.findByEnabled(enabled);
    }

    /**
     * Gets all investment package given by pageable or null if not exist
     * @param pageable
     * @return A page of investment package or page is empty
     * if package is not exist
     * @see Pageable
     */
    public Page<InvestmentPackage> getAll(Pageable pageable) {
        return packageRepository.findAll(pageable);
    }

    /**
     * Gets all investment package give by enabled and pageable or null if not exist
     * @param enabled
     * @param pageable
     * @return A page of investment package or page is empty
     * if package is not exist
     * @see Pageable
     */
    public Page<InvestmentPackage> getAll(boolean enabled,Pageable pageable) {
        return packageRepository.findByEnabled(enabled, pageable);
    }

    /**
     * Adds list investment package cashes to investment package given by invpId (investmentPackageId) and list of investmentPackageCashes
     *
     * @param invpId
     * @param investmentPackageCashes
     * @return InvestmentPackage after added
     */
    @Transactional
    public InvestmentPackage addInvestmentPackageCash(int invpId, List<InvestmentPackageCash> investmentPackageCashes) {

        InvestmentPackage investmentPackage = packageRepository.findOne(invpId);
        investmentPackage.setInvestmentPackageCashes(investmentPackageCashes);

        return packageRepository.save(investmentPackage);
    }


}
