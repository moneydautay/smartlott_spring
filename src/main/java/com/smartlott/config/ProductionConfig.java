package com.smartlott.config;

import com.smartlott.backend.persistence.repositories.EmailService;
import com.smartlott.backend.service.SmtpEmailService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;

/**
 * Created by Mrs Hoang on 18/12/2016.
 */
@Configuration
@Profile("prod")
@PropertySource("file:///${user.home}/.gluck/smartlott/application-prod.properties")
public class ProductionConfig {
    @Bean
    public EmailService emailService(){
        return new SmtpEmailService();
    }
}
