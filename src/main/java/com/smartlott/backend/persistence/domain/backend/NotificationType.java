package com.smartlott.backend.persistence.domain.backend;

import com.smartlott.enums.NotificationTypeEnum;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by Mrs Hoang on 29/12/2016.
 */
@Entity
@Table(name = "notification_type")
public class NotificationType implements Serializable{

    /** The Serial Version UID for Serializable classes */
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Length(max = 45)
    private String name;

    private String description;

    private boolean required = false;

    private String url;

    private String apiUrl;

    public NotificationType() {
    }

    public NotificationType(NotificationTypeEnum typeEnum) {
        this.name = typeEnum.getName();
        this.description = typeEnum.getDescription();
        this.required = typeEnum.isRequired();
        this.url = typeEnum.getUrl();
        this.apiUrl = typeEnum.getApiUrl();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isRequired() {
        return required;
    }

    public void setRequired(boolean required) {
        this.required = required;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getApiUrl() {
        return apiUrl;
    }

    public void setApiUrl(String apiUrl) {
        this.apiUrl = apiUrl;
    }

    @Override
    public String toString() {
        return "NotificationType{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", required=" + required +
                ", url='" + url + '\'' +
                ", apiUrl='" + apiUrl + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        NotificationType that = (NotificationType) o;

        return id == that.id;
    }

    @Override
    public int hashCode() {
        return id;
    }
}
