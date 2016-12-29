package com.smartlott.backend.persistence.domain.backend;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.smartlott.backend.persistence.converters.LocalDateTimeAttributeConverter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Created by Mrs Hoang on 29/12/2016.
 */
@Entity
@Table(name = "notification")
public class Notification implements Serializable{

    /** The Serial Version UID for Serializable classes */
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String content;

    private boolean showLater = false;

    private boolean processed = false;

    @Convert(converter = LocalDateTimeAttributeConverter.class)
    private LocalDateTime timeShow;

    @ManyToOne(fetch = FetchType.EAGER)
    private  NotificationType notificationType;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    private User user;

    public Notification() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isShowLater() {
        return showLater;
    }

    public void setShowLater(boolean showLater) {
        this.showLater = showLater;
    }

    public boolean isProcessed() {
        return processed;
    }

    public void setProcessed(boolean processed) {
        this.processed = processed;
    }

    public LocalDateTime getTimeShow() {
        return timeShow;
    }

    public void setTimeShow(LocalDateTime timeShow) {
        this.timeShow = timeShow;
    }

    public NotificationType getNotificationType() {
        return notificationType;
    }

    public void setNotificationType(NotificationType notificationType) {
        this.notificationType = notificationType;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Notification{" +
                "id=" + id +
                ", content='" + content + '\'' +
                ", showLater=" + showLater +
                ", processed=" + processed +
                ", timeShow=" + timeShow +
                ", notificationType=" + notificationType +
                ", user=" + user +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Notification that = (Notification) o;

        return id == that.id;
    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }
}
