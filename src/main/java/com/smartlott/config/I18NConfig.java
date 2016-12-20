package com.smartlott.config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.security.crypto.codec.Utf8;

import java.util.Properties;

/**
 * Created by Mrs Hoang on 20/12/2016.
 */
@Configuration
public class I18NConfig {

    @Bean
    public ReloadableResourceBundleMessageSource messageSource(){
        ReloadableResourceBundleMessageSource reloadableResourceBundleMessageSource = new ReloadableResourceBundleMessageSource();
        reloadableResourceBundleMessageSource.setBasename("classpath:i18n/messages");
        reloadableResourceBundleMessageSource.setDefaultEncoding("UTF-8");


        //Check for new every 30 minutes
        reloadableResourceBundleMessageSource.setCacheMillis(1800);
        return reloadableResourceBundleMessageSource;
    }
}
