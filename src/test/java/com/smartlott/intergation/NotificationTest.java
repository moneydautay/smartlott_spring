package com.smartlott.intergation;

import com.smartlott.backend.api.UserHandler;
import com.smartlott.backend.persistence.domain.backend.Notification;
import com.smartlott.backend.persistence.domain.backend.NotificationType;
import com.smartlott.backend.persistence.domain.backend.User;
import com.smartlott.backend.persistence.repositories.NotificationRepository;
import com.smartlott.backend.persistence.repositories.NotificationTypeRepository;
import com.smartlott.enums.NotificationTypeEnum;
import com.smartlott.web.controllers.DashboardController;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mrs Hoang on 29/12/2016.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class NotificationTest extends AbstractIntegrationTest{

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private NotificationTypeRepository notificationTypeRepository;

    @Rule
    public TestName testName = new TestName();

    @Before
    public void before() throws Exception{
        Assert.assertNotNull(notificationRepository);
        Assert.assertNotNull(testName);
    }

    @Test
    public void createNotification() throws Exception{

        User user = createCustUser(testName);

        //create notification type;

        NotificationType type1 = new NotificationType(NotificationTypeEnum.General);
        NotificationType type2 = new NotificationType(NotificationTypeEnum.Pssword);
        NotificationType type3 = new NotificationType(NotificationTypeEnum.AddressValidate);
        NotificationType type4 = new NotificationType(NotificationTypeEnum.NumberAccount);
        NotificationType type5 = new NotificationType(NotificationTypeEnum.Orther);

        List<NotificationType> notificationTypes = new ArrayList<>();
        notificationTypes.add(type1);
        notificationTypes.add(type2);
        notificationTypes.add(type3);
        notificationTypes.add(type4);
        notificationTypes.add(type5);

        //adds notificationTypes
        notificationTypeRepository.save(notificationTypes);

        //add Notification
        Notification noto1 = new Notification();
        noto1.setContent("Update general information");
        noto1.setUser(user);
        noto1.setNotificationType(type1);

        Notification notification = notificationRepository.save(noto1);
        Assert.assertNotNull(notification);
        System.out.println(notification.toString());
        //find by url
        Notification notifUrl = notificationRepository.findByUserIdAndNotificationTypeUrlAndProcessed(user.getId(), DashboardController.PROFILE_GENERAL_URL,false);
        Assert.assertNotNull(notifUrl);
        //find by api url
        Notification notifApiUrl = notificationRepository.findByUserIdAndNotificationTypeApiUrlAndProcessed(user.getId(), UserHandler.API_USER_REST_URL,false);
        Assert.assertNotNull(notifApiUrl);

        noto1.setProcessed(true);
        notificationRepository.save(noto1);

        //find by api url
        Notification notifApiUrlAspect = notificationRepository.findByUserIdAndNotificationTypeApiUrlAndProcessed(user.getId(), UserHandler.API_USER_REST_URL,true);
        Assert.assertNotNull(notifApiUrlAspect);

        //delete notification
        notificationRepository.delete(noto1.getId());

        //find by api url
        Notification notifApiUrlDelete = notificationRepository.findByUserIdAndNotificationTypeApiUrlAndProcessed(user.getId(), UserHandler.API_USER_REST_URL,true);
        Assert.assertNull(notifApiUrlDelete);


    }
}
