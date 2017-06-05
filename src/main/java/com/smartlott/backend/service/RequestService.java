package com.smartlott.backend.service;

import com.smartlott.backend.persistence.domain.backend.Request;
import com.smartlott.backend.persistence.domain.backend.RequestStatus;
import com.smartlott.backend.persistence.repositories.RequestRepository;
import com.smartlott.enums.RequestStatusEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by greenlucky on 5/17/17.
 */
@Service
@Transactional(readOnly = true)
public class RequestService {

    @Autowired
    private RequestRepository requestRepository;

    @Transactional
    public Request create(Request request) {
        request.setRequestStatus(new RequestStatus(RequestStatusEnum.PENDING));
        return requestRepository.save(request);
    }

    @Transactional
    public Request processing(Request request) {
        request.setRequestStatus(new RequestStatus(RequestStatusEnum.PROCESSING));
        return requestRepository.save(request);
    }

    @Transactional
    public Request handle(Request request) {
        request.setRequestStatus(new RequestStatus(RequestStatusEnum.HANDLE));
        return requestRepository.save(request);
    }

    @Transactional
    public Request deny(Request request) {
        request.setRequestStatus(new RequestStatus(RequestStatusEnum.DENY));
        return requestRepository.save(request);
    }

    @Transactional
    public Request cancel(Request request) {
        request.setRequestStatus(new RequestStatus(RequestStatusEnum.CANCEL));
        return requestRepository.save(request);
    }

    public Page<Request> getAll(Pageable pageable) {
        return requestRepository.findAll(pageable);
    }

    public Page<Request> getRequestOfUser(long userId, Pageable pageable) {
        return requestRepository.findByRequestOfUserId(userId, pageable);
    }

    public long getNumberRequestByStatus(int statusId) {
        return requestRepository.countByStatus(statusId);
    }

    public Page<Request> getByType(int typeId, Pageable pageable) {
        return requestRepository.findByRequestTypeId(typeId, pageable);
    }

    public Page<Request> getByStatus(int statusId, Pageable pageable) {
        return requestRepository.findByRequestStatusId(statusId, pageable);
    }
}
