package com.smartlott.backend.persistence.repositories;

import com.smartlott.backend.persistence.domain.backend.Request;
import com.smartlott.backend.persistence.domain.backend.RequestType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Mrs Hoang on 17/12/2016.
 */
@Repository
public interface RequestRepository extends CrudRepository<Request, Long>{

    /**
     * Find all request
     * @return List of Request
     */
    public List<Request> findAll();

    public Page<Request> findByRequestOfUserId(long userId, Pageable pageable);

    public Page<Request> findByHandleBy(String handleBy, Pageable pageable);

    public Page<Request> findByRequestTypeId(int typeId, Pageable pageable);

    public Page<Request> findByRequestStatusId(int statusId, Pageable pageable);

    public Page<Request> findAll(Pageable pageable);

    @Query("select count(*) from Request r where r.requestStatus.id = :statusId")
    public long countByStatus(@Param("statusId") int statusId);


}
