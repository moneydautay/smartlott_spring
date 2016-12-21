package com.smartlott.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;

/**
 * Created by Mrs Hoang on 18/12/2016.
 */
@Configuration
@Profile("prod")
@PropertySource("file:///${user.home}/.gluck/application-prod.properties")
public class ProductionConfig {

}
