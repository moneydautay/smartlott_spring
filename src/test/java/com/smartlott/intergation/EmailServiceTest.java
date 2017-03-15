package com.smartlott.intergation;

import com.smartlott.backend.persistence.repositories.EmailService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

/**
 * Created by Mrs Hoang on 27/12/2016.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EmailServiceTest {

    @Autowired
    private EmailService emailService;

    @Value("${webmaster.email}")
    private String webmasterEmail;

    @Before
    public void before() throws Exception{
        Assert.notNull(emailService);
        Assert.notNull(webmasterEmail);
    }

    @Test
    public void sendEmail() throws Exception{
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom(webmasterEmail);
        simpleMailMessage.setTo("nguyenlamit86@gmail.com");
        simpleMailMessage.setText("Test email service");
        emailService.sendGenericEmailMessage(simpleMailMessage);
    }
}
