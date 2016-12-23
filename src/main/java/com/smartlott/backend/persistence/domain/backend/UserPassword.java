package com.smartlott.backend.persistence.domain.backend;

import javax.persistence.Embeddable;

/**
 * Created by greenlucky on 12/23/16.
 */
@Embeddable
public class UserPassword {

    private String username;

    private String currentPassword;

    private String newPassword;

    public UserPassword() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCurrentPassword() {
        return currentPassword;
    }

    public void setCurrentPassword(String currentPassword) {
        this.currentPassword = currentPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    @Override
    public String toString() {
        return "UserPassword{" +
                "username='" + username + '\'' +
                ", currentPassword='" + currentPassword + '\'' +
                ", newPassword='" + newPassword + '\'' +
                '}';
    }
}
