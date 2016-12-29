package com.smartlott.backend.persistence.repositories;

import com.smartlott.backend.persistence.domain.backend.NotificationType;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Mrs Hoang on 29/12/2016.
 */
@Repository
public interface NotificationTypeRepository extends CrudRepository<NotificationType, Integer>{

    /**
     * Find all notification type
     * @return A list of notification type or null
     */
    public List<NotificationType> findAll();

    /**
     * Find notifcation types by requried
     * @param required given by user
     * @return A list of notification or null if not eixst
     */
    public List<NotificationType> findByRequired(boolean required);

    /**
     * Find notification types by required and url
     * @param required
     * @param url
     * @return A list of notification or null if not found
     */
    public List<NotificationType> findByRequiredAndUrl(boolean required, String url);

    /**
     * Find notification types by required and api url
     * @param required
     * @param apiUrl
     * @return A list of notification or null if not found
     */
    public List<NotificationType> findByRequiredAndApiUrl(boolean required, String apiUrl);

}
