package com.smartlott.config;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3Client;
import org.elasticsearch.node.NodeBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;


/**
 * Created by Mrs Hoang on 12/14/2016.
 */
@Configuration
@EnableJpaRepositories(basePackages =  "com.smartlott.backend.persistence.repositories")
@EnableElasticsearchRepositories(basePackages =  "com.smartlott.backend.persistence.repositories.elasticsearch")
@EnableTransactionManagement
@PropertySource("/config/application-common.properties")
public class ApplicationConfig {
    
    /** The application logger */
    private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationConfig.class);
    
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


    @Bean
    public NodeBuilder nodeBuilder() {
        return new NodeBuilder();
    }

    @Bean
    public ElasticsearchOperations elasticsearchTemplate() {
        return new ElasticsearchTemplate(nodeBuilder().local(true).node().client());
    }
}
