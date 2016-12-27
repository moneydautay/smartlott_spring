package com.smartlott.config;

/**
 * Created by Mrs Hoang on 12/14/2016.
 */

import com.smartlott.backend.persistence.repositories.EmailService;
import com.smartlott.backend.service.MockEmailService;
import org.h2.server.web.WebServlet;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;

@Configuration
@Profile("dev")
@PropertySource("file:///${user.home}/.gluck/smartlott/application-dev.properties")
public class DevelopmentConfig {

    @Bean
    public ServletRegistrationBean h2ConsoleServletRegistration(){
        ServletRegistrationBean bean = new ServletRegistrationBean(new WebServlet());
        bean.addUrlMappings("/console/*");
        return bean;
    }

    @Bean
    public EmailService emailService(){
        return new MockEmailService();
    }
}
