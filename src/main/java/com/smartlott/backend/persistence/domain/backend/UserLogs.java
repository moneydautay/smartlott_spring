package com.smartlott.backend.persistence.domain.backend;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Created by Mr Lam on 17/12/2016.
 */
@Entity
@Table(name = "user_logs")
public class UserLogs {

    @Id
    @GeneratedValue(strategy =  GenerationType.AUTO)
    private long id;

    private String content;

    @Column(name = "created_date")
    private LocalDateTime createdDate;

    @ManyToOne(fetch =  FetchType.EAGER)
    private User user;

    public UserLogs() {
    }

    public UserLogs(String content, LocalDateTime createdDate, User user) {
        this.content = content;
        this.createdDate = createdDate;
        this.user = user;
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

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "UserLogs{" +
                "id=" + id +
                ", content='" + content + '\'' +
                ", createdDate=" + createdDate +
                ", user=" + user +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserLogs userLogs = (UserLogs) o;

        if (id != userLogs.id) return false;
        if (content != null ? !content.equals(userLogs.content) : userLogs.content != null) return false;
        if (createdDate != null ? !createdDate.equals(userLogs.createdDate) : userLogs.createdDate != null)
            return false;
        return user != null ? user.equals(userLogs.user) : userLogs.user == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (content != null ? content.hashCode() : 0);
        result = 31 * result + (createdDate != null ? createdDate.hashCode() : 0);
        result = 31 * result + (user != null ? user.hashCode() : 0);
        return result;
    }
}
