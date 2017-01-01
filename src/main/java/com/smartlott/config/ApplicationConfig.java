package com.smartlott.config;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3Client;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
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
@PropertySource("file:///${user.home}/.gluck/smartlott/application-common.properties")
public class ApplicationConfig {

    @Value("${aws.s3.profile}")
    private String awsProfileName;

    @Bean
    public AmazonS3Client s3Client(){
        //Creates credential
        AWSCredentials credentials = new ProfileCredentialsProvider(awsProfileName).getCredentials();
        AmazonS3Client s3Client = new AmazonS3Client(credentials);

        //get region of amazon
        Region region = Region.getRegion(Regions.AP_SOUTHEAST_1);
        s3Client.setRegion(region);

        return  s3Client;
    }
}
