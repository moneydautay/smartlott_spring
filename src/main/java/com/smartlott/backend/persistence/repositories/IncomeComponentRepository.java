package com.smartlott.backend.persistence.repositories;

import com.smartlott.backend.persistence.domain.backend.IncomeComponent;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Mrs Hoang on 17/12/2016.
 */
@Repository
public interface IncomeComponentRepository extends CrudRepository<IncomeComponent, Long>{

    public List<IncomeComponent> findAll();
}
