package com.smartlott.backend.persistence.repositories;

import com.smartlott.backend.persistence.domain.backend.IncomeProcess;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by lamdevops on 6/26/17.
 */
@Repository
public interface IncomeProcessRepository extends JpaRepository<IncomeProcess, Integer>{
}
