package com.smartlott.backend.persistence.repositories;

import com.smartlott.backend.persistence.domain.backend.IncomeComponent;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Mrs Hoang on 17/12/2016.
 */
@Repository
public interface IncomeComponentRepository extends CrudRepository<IncomeComponent, Long>{

    public List<IncomeComponent> findAll();

    public Page<IncomeComponent> findAll(Pageable pageable);

    List<IncomeComponent> findByEnabled(boolean b);

    Page<IncomeComponent> findByEnabled(boolean b, Pageable pageable);
}
