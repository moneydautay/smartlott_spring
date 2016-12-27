package com.smartlott.backend.persistence.domain.backend;

import com.smartlott.backend.persistence.converters.LocalDateTimeAttributeConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Created by Mrs Hoang on 12/15/2016.
 */
@Entity
@Table(name = "security_token")
public class SecurityToken implements Serializable{

    /** The Serial Version UID for Serializable classes */
    private static final long serialVersionUID = 1L;
    
    /** The application logger */
    private static final Logger LOGGER = LoggerFactory.getLogger(SecurityToken.class);

    public static final Integer DEFAULT_TOKEN_SECURITY_IN_MINUTES = 120;

    @Id
    @Column(name="security_token_id")
    private long id;

    @Column(unique = true)
    private String token;

    @ManyToOne(fetch = FetchType.EAGER)
    private User user;

    @Column(name="expiry_date")
    @Convert(converter = LocalDateTimeAttributeConverter.class)
    private LocalDateTime expiryDate;


    public SecurityToken() {
    }

    public SecurityToken(String token, User user, LocalDateTime createDateTime ,int expirationInMinutes) {
        if(token == null | user == null | createDateTime == null)
            throw  new IllegalArgumentException("Token, user and createion time can't be not null.");
        if(expirationInMinutes == 0){
            LOGGER.warn("The token expiration in minutes length is zero, Assign the default value {}", DEFAULT_TOKEN_SECURITY_IN_MINUTES);
            expirationInMinutes = DEFAULT_TOKEN_SECURITY_IN_MINUTES;
        }

        this.token = token;
        this.user = user;
        this.expiryDate = createDateTime.plusMinutes(expirationInMinutes);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDateTime getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(LocalDateTime expiryDate) {
        this.expiryDate = expiryDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SecurityToken that = (SecurityToken) o;

        return id == that.id;
    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }

    @Override
    public String toString() {
        return "SecurityToken{" +
                "defaultTokenSecurityInMinutes=" + DEFAULT_TOKEN_SECURITY_IN_MINUTES +
                ", id=" + id +
                ", token='" + token + '\'' +
                ", user=" + user +
                ", expiryDate=" + expiryDate +
                '}';
    }
}
