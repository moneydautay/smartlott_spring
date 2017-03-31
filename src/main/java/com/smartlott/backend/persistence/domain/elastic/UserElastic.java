package com.smartlott.backend.persistence.domain.elastic;

import com.smartlott.backend.persistence.domain.backend.User;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.Id;
import java.time.format.DateTimeFormatter;

/**
 * Created by greenlucky on 3/29/17.
 */
@Document(indexName = "smartlott", type = "user",shards = 1, replicas = 0)
public class UserElastic {

    @Id
    private long id;

    private String email;

    private String username;

    private String firstName;

    private String lastName;

    private String birthday;

    private String phoneNumber;

    private boolean enabled = true;

    private String createDate;

    private boolean actived = false;

    private String activeBy;

    private String introducedBy;

    private String userInvestment;

    public UserElastic() {
    }

    public UserElastic(User user) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        this.id = user.getId();
        this.email = user.getEmail();
        this.username = user.getUsername();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.birthday = (user.getBirthday() != null) ? user.getBirthday().format(formatter) : null;
        this.phoneNumber = user.getPhoneNumber();
        this.enabled = user.isEnabled();
        this.createDate = user.getCreateDate().toString();
        this.actived = user.isActived();
        this.activeBy = (user.getActiveBy() != null) ? user.getActiveBy().getUsername() : null;
        this.introducedBy = (user.getIntroducedBy() != null) ? user.getIntroducedBy().getUsername() : null;
        this.userInvestment = user.getUserInvestment().getInvestmentPackage().getName();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public boolean isActived() {
        return actived;
    }

    public void setActived(boolean actived) {
        this.actived = actived;
    }

    public String getActiveBy() {
        return activeBy;
    }

    public void setActiveBy(String activeBy) {
        this.activeBy = activeBy;
    }

    public String getIntroducedBy() {
        return introducedBy;
    }

    public void setIntroducedBy(String introducedBy) {
        this.introducedBy = introducedBy;
    }

    public String getUserInvestment() {
        return userInvestment;
    }

    public void setUserInvestment(String userInvestment) {
        this.userInvestment = userInvestment;
    }

    @Override
    public String toString() {
        return "UserElastic{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", username='" + username + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", birthday='" + birthday + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", enabled=" + enabled +
                ", createDate='" + createDate + '\'' +
                ", actived=" + actived +
                ", activeBy='" + activeBy + '\'' +
                ", introducedBy='" + introducedBy + '\'' +
                ", userInvestment='" + userInvestment + '\'' +
                '}';
    }
}
