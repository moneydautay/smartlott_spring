package com.smartlott.backend.persistence.repositories;

import com.smartlott.backend.persistence.domain.backend.Bonous;
import com.smartlott.backend.persistence.domain.backend.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Mrs Hoang on 17/12/2016.
 */
@Repository
public interface BonousRepository extends CrudRepository<Bonous, Long>{

    /**
     * Find a list bonous of user given by user or null if not found
     * @param ofUser
     * @return A lis bonous of user
     */
    public List<Bonous> findByOfUser(User ofUser);

}
