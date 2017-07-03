package com.smartlott.backend.persistence.repositories;

import com.smartlott.backend.persistence.domain.backend.DivideAward;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by lamdevops on 7/3/17.
 */
@Repository
public interface DivideAwardRepository extends JpaRepository<DivideAward, Integer> {
}
