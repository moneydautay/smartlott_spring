package com.smartlott.backend.persistence.repositories;

import com.smartlott.backend.persistence.domain.backend.NumberAccount;
import com.smartlott.backend.persistence.domain.backend.NumberAccountType;
import com.smartlott.backend.persistence.domain.backend.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Mrs Hoang on 17/12/2016.
 */
@Repository
public interface NumberAccountRepository extends CrudRepository<NumberAccount, Long>{


    /**
     * FInd A list of number account of User or null if not found
     * @param user
     * @return Returns A list of number account of User or null if not found
     */
    public List<NumberAccount> findByUser(User user);


    /**
     * Find a number account of user or null if not found
     * @param user
     * @param numberAccountType
     * @return Returns a number account of user or null if not found
     */
    public NumberAccount findByUserAndNumberAccountType(User user, NumberAccountType numberAccountType);
}