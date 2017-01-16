package com.smartlott.backend.persistence.repositories;

import com.smartlott.backend.persistence.domain.backend.Network;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Mrs Hoang on 04/01/2017.
 */
@Repository
public interface NetworkRepository extends PagingAndSortingRepository<Network, Long> {

    /**
     * Find all network
     * @return A list of network or null if not exist
     */
    public Page<Network> findAll(Pageable pageable);

    /**
     * Find all network of user
     * @param userId
     * @return A list ancestor of user or null if not exist
     */
    public List<Network> findByOfUserId(long userId);

    /**
     * Find all ancestor of user
     * @param userId
     * @return A list ancestor of user or null if not exist
     */
    public Page<Network> findByOfUserId(Long userId, Pageable pageable);


    /**
     * Find all Ancestor of user or null if not eixst
     * @param ancestorId
     * @return A list descendant of ancestor or null if not eixst
     */
    public List<Network> findByAncestorId(Long ancestorId);

    /**
     * Find all descendant network of user
     * @param ancestorId
     * @return A list of descendant network or null if not exist
     */
    public Page<Network> findByAncestorId(Long ancestorId, Pageable pageable);

}
