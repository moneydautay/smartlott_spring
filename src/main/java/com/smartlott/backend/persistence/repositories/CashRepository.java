package com.smartlott.backend.persistence.repositories;

import com.smartlott.backend.persistence.domain.backend.Cash;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by greenlucky on 2/3/17.
 */
@Repository
public interface CashRepository extends CrudRepository<Cash,Integer>{

    List<Cash> findAll();

    List<Cash> findByEnabled(boolean e);
}
