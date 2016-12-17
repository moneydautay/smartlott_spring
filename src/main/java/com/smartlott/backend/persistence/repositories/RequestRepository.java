package com.smartlott.backend.persistence.repositories;

import com.smartlott.backend.persistence.domain.backend.Request;
import com.smartlott.backend.persistence.domain.backend.RequestType;
import com.smartlott.backend.persistence.domain.backend.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
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

    /**
     * Find all request given by user
     * @param requestOfUser
     * @return A list of request of user
     */
    public List<Request> findByRequestOfUser(User requestOfUser);

    public List<Request> findByHandleByUser(User handleByUser);

    public List<Request> findByRequestDate(LocalDateTime requestDate);

    public List<Request> findByRequestType(RequestType requestType);
}
