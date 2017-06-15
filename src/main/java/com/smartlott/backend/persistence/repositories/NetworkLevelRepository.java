package com.smartlott.backend.persistence.repositories;

import com.smartlott.backend.persistence.domain.backend.NetworkLevel;
import com.smartlott.enums.BonusType;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Mrs Hoang on 16/01/2017.
 */
@Repository
public interface NetworkLevelRepository extends CrudRepository<NetworkLevel, Integer>{

    List<NetworkLevel> findAll();

    List<NetworkLevel> findByEnabledAndBonusType(boolean b, BonusType bonusType);
}
