package com.smartlott.backend.persistence.repositories;

import com.smartlott.backend.persistence.domain.backend.ExternalHistoryAccount;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by greenlucky on 1/26/17.
 */
@Repository
public interface ExternalHistoryAccountRepository extends PagingAndSortingRepository<ExternalHistoryAccount, Long>{

    ExternalHistoryAccount findByExternalTransId(String externalTransId);
}
