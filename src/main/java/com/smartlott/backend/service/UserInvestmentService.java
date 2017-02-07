package com.smartlott.backend.service;

import com.smartlott.backend.persistence.domain.backend.InvestmentPackage;
import com.smartlott.backend.persistence.domain.backend.User;
import com.smartlott.backend.persistence.domain.backend.UserInvestment;
import com.smartlott.backend.persistence.repositories.InvestmentPackageRepository;
import com.smartlott.backend.persistence.repositories.UserInvestmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

/**
 * Created by Mrs Hoang on 07/02/2017.
 */
@Service
@Transactional(readOnly = true)
public class UserInvestmentService {

    @Autowired
    private UserInvestmentRepository investmentRepository;

    @Autowired
    private InvestmentPackageRepository investmentPackageRepository;

    /**
     * Creates new User Investment given by User, InvestmentPackage and from date.
     *
     * @param user
     * @param investmentPackage
     * @param from
     * @return A user Investment after created
     * @see User
     * @see InvestmentPackage
     */
    public UserInvestment create(User user, InvestmentPackage investmentPackage, LocalDateTime from){

        UserInvestment userInvestment = new UserInvestment();

        //calculate end of day given by duration time of package and from date
        LocalDateTime to = from.plusDays(investmentPackage.getDurationTime());

        userInvestment.setUser(user);
        userInvestment.setInvestmentPackage(investmentPackage);
        userInvestment.setFrom(from);
        userInvestment.setTo(to);

        return investmentRepository.save(userInvestment);
    }

    /**
     * Changes status of User Investment status given by userInvestmentId and enabled
     *
     * @param userInvestmentId
     * @param enabled has two true is active or false is block
     * @return A UserInvestment after change status
     */
    public UserInvestment changeStatus(long userInvestmentId,boolean enabled){
        UserInvestment userInvestment = investmentRepository.findOne(userInvestmentId);
        if(userInvestment == null)
            return null;
        userInvestment.setEnabled(enabled);

        return investmentRepository.save(userInvestment);
    }

    /**
     * Gets UserInvestment given by userId and pageable
     * @param userId
     * @param pageable
     * @return A page of UserInvestment or null if not exist
     */
    public Page<UserInvestment> getByUserId(long userId, Pageable pageable){
        return investmentRepository.findByUserId(userId, pageable);
    }

    /**
     * Gets UserInvsetments given by userId and investmentPackageId
     * @param userid
     * @param investmentPackageId
     * @param pageable
     * @return A page of UserInvestment or null if not exist
     */
    public Page<UserInvestment> getByUserIdAndInvestmentPackageId(long userid, int investmentPackageId, Pageable pageable){
        return investmentRepository.findByUserIdAndInvestmentPackageId(userid, investmentPackageId, pageable);
    }
}
