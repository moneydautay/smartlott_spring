package com.smartlott.backend.persistence.repositories;

import com.smartlott.backend.persistence.domain.backend.LottDialingIncProcess;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by lamdevops on 6/26/17.
 */
@Repository
public interface LottDialingIncProcessRepository extends JpaRepository<LottDialingIncProcess, Long> {


    LottDialingIncProcess findByLotteryDialingIdAndIncomeProcessId(long dialingId, int incomeProcessId);
}
