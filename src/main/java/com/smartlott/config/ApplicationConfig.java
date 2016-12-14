package com.smartlott.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Created by Mrs Hoang on 12/14/2016.
 */
@Configuration
@EnableJpaRepositories(basePackages =  "com.smartlott.backend.persistence.repositories")
@EnableTransactionManagement
@PropertySource("file:///${user.home}/.glucks/smartlott/application-common.properties")
public class ApplicationConfig {
}
