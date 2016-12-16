package com.smartlott.backend.persistence.domain.backend;

import org.springframework.beans.factory.annotation.Value;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Created by Mrs Hoang on 12/15/2016.
 */
@Entity
@Table(
        name = "password",
        uniqueConstraints =
        @UniqueConstraint( columnNames = {"password", "enabled", "user_id"})
)
public class Password {

    @Id
    @Column(name = "password_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String password;

    @Value("true")
    private boolean enabled = true;

    @Column(name = "create_date")
    private LocalDateTime createDate;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    public Password() {
    }



    public Password(String password, LocalDateTime createDate, User user) {
        this.password = password;
        this.createDate = createDate;
        this.user = user;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Password password1 = (Password) o;

        if (id != password1.id) return false;
        if (password != null ? !password.equals(password1.password) : password1.password != null) return false;
        return user != null ? user.equals(password1.user) : password1.user == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (user != null ? user.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Password{" +
                "id=" + id +
                ", password='" + password + '\'' +
                ", enabled=" + enabled +
                ", createDate=" + createDate +
                ", user=" + user +
                '}';
    }
}
