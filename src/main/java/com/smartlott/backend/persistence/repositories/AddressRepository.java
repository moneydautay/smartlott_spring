package com.smartlott.backend.persistence.repositories;

import com.smartlott.backend.persistence.domain.backend.Address;
import com.smartlott.backend.persistence.domain.backend.Country;
import com.smartlott.backend.persistence.domain.backend.Province;
import com.smartlott.backend.persistence.domain.backend.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Mrs Hoang on 16/12/2016.
 */
@Repository
public interface AddressRepository extends CrudRepository<Address, Long>{

    /**
     * Returns a list address given by User or null if not found
     * @param user
     * @return A list address given by User or null if not found
     */
    public List<Address> findByUser(User user);


    /**
     *  Returns a list address given by Provice or null if not found
     * @param province
     * @return Reuturns a list address or null if not found
     */
    public List<Address> findByProvince(Province province);


    /**
     * Find List addresses by user id
     * @param userId
     * @return A list addresses or null if not found
     */
    public List<Address> findByUserId(long userId);
}
