package com.smartlott.backend.persistence.repositories;

import com.smartlott.backend.persistence.domain.backend.User;
import com.smartlott.backend.persistence.domain.backend.UserLogs;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by Mr Lam on 17/12/2016.
 */
@Repository
public interface UserLogsRepository extends CrudRepository<UserLogs, Long>{

    /**
     * Find A list User logs by user given by user
     * @param user
     * @return A list UserLogs
     */
    public List<UserLogs> findByUser(User user);

    /**
     * Find a list UserLogs by createdDate given by user
     * @param createdDate
     * @return A list UserLogs
     */
    public List<UserLogs> findByCreatedDate(LocalDateTime createdDate);
}
