package com.smartlott.config;

/**
 * Created by Mrs Hoang on 12/14/2016.
 */

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;

@Configuration
@Profile("dev")
@PropertySource("file:///${user.home}/.glucks/smartlott/application-dev.properties")
public class DevelopmentConfig {


}
