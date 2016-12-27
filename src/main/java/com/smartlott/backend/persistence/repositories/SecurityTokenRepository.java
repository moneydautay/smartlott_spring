package com.smartlott.backend.persistence.repositories;

import com.smartlott.backend.persistence.domain.backend.SecurityToken;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Mrs Hoang on 12/15/2016.
 */
@Repository
public interface SecurityTokenRepository extends CrudRepository<SecurityToken, Long>{

    /**
     * Find token by token
     * @param token
     * @return A security token or null if not founds
     */
    public SecurityToken findByToken(String token);

    /**
     * Find security token by user id
     * @param userId
     * @return Security Token
     */
    List<SecurityToken> findByUserId(long userId);

}
