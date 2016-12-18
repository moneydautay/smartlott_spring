package com.smartlott.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.security.SecureRandom;
import java.util.Arrays;
import java.util.List;

/**
 * Created by greenlucky on 12/13/16.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{

    /**
     * The key enscryption password
     */
    private static final String SALT = "wjfaj9*($*#_$)#($)#$#$";

    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder(12, new SecureRandom(SALT.getBytes()));
    }

    @Autowired
    private Environment env;

    /** PUBLIC URLS */
    private static final String[] PUBLIC_MATCHES={
            "/webjars/**",
            "/css/**",
            "/js/**",
            "/images/**",
            "/sources/**",
            "/",
            "/console/**",
            "/error/**",
            "/stl-admin/**"
    };

    @Override
    protected void configure(HttpSecurity http) throws Exception{

        List<String> activeProflies = Arrays.asList(env.getActiveProfiles());
        if(activeProflies.contains("dev")){
            http.csrf().disable();
            http.headers().frameOptions().disable();
        }

        http
                .authorizeRequests()
                .antMatchers(PUBLIC_MATCHES).permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin().loginPage("/login").defaultSuccessUrl("/")
                .failureUrl("/login?error=true").permitAll()
                .and()
                .logout().permitAll();
    }



}
