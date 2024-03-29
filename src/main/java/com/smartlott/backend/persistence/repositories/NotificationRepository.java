package com.smartlott.backend.persistence.repositories;

import com.smartlott.backend.persistence.domain.backend.Notification;
import com.smartlott.backend.persistence.domain.backend.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by Mrs Hoang on 29/12/2016.
 */
@Repository
public interface NotificationRepository extends CrudRepository<Notification, Long> {

    /**
     * Find all notifications
     *
     * @return A list of notification or null if not exists
     */
    public List<Notification> findAll();

    /**
     * Find notifications by user
     *
     * @param user
     * @return A list of notification or null if not exists
     */
    public List<Notification> findByUser(User user);


    /**
     * Find notification by userId, user url and proccess
     * @param userId
     * @param notificationTypeUrl
     * @param processed
     * @return A Notification or null if not found
     */
    public Notification findByUserIdAndNotificationTypeUrlAndProcessed(Long userId,String notificationTypeUrl, boolean processed);

    /**
     * Find notification by userId, user api url and processed
     * @param userId
     * @param notificationTypeApiUrl
     * @param processed
     * @return A notification or null if not found
     */
    public Notification findByUserIdAndNotificationTypeApiUrlAndProcessed(long userId, String notificationTypeApiUrl, boolean processed);

    /**
     * Find notifications by user id
     * @param userId
     * @return A list of notification or null if not exists
     */
    public List<Notification> findByUserId(long userId);

    List<Notification> findByUserIdAndProcessed(long userId, boolean processed);

    @Modifying
    @Query("SELECT n FROM Notification n where n.user.id =:userId AND n.processed =:processed AND (n.timeShow = null OR n.timeShow <=:localDateTime)")
    List<Notification> findByUserIdAndProcessedAndShowTime(@Param("userId") long userId,@Param("processed") boolean processed,@Param("localDateTime") LocalDateTime localDateTime);
}
