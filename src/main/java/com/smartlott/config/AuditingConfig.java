package com.smartlott.config;

import com.smartlott.backend.persistence.domain.backend.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * Created by greenlucky on 4/26/17.
 */
@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
public class AuditingConfig {

    @Bean
    public AuditorAware<String> auditorAware() {
        return new AuditingAwareImpl();
    }

    public class AuditingAwareImpl implements AuditorAware<String> {

        @Override
        public String getCurrentAuditor() {
            if(SecurityContextHolder.getContext().getAuthentication() == null)
                return "";

            User logonUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            return logonUser.getUsername();
        }
    }
}


