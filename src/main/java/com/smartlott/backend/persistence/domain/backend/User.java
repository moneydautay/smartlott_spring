package com.smartlott.backend.persistence.domain.backend;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.smartlott.backend.persistence.converters.LocalDateTimeAttributeConverter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SelectBeforeUpdate;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Mr Lam on 12/14/2016.
 */
@Entity
@Table(
        name = "user",
        uniqueConstraints = @UniqueConstraint(columnNames = {"email","phone_number","username"})
)
@DynamicInsert(value = true)
@DynamicUpdate(value = true)
@SelectBeforeUpdate
public class User implements Serializable, UserDetails{

    /** The Serial Version UID for Serializable classes */
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id")
    private long id;

    @Email(message = "Email.user.email")
    @Size(min = 5, max = 100, message = "Size.user.email")
    private String email;


    @NotEmpty(message = "NotEmpty.user.username")
    @Size(min=4, max=50, message = "Size.user.username")
    private String username;

    @JsonIgnore
    @Size(min=6, max = 100, message = "Size.user.password")
    @Column(updatable = false)
    private String password;

    private String firstName;

    private String lastName;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate birthday;

    @NotEmpty(message = "NotEmpty.user.phone")
    @Size(max = 50, message = "Size.user.phone")
    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "profile_image")
    private String profileImage;

    @Column(name="document_1")
    private String documentOne;

    @Column(name="document_2")
    private String documentTwo;

    private int sex;

    private boolean enabled = true;

    private int status;

    @Value(value = "0")
    @Column(updatable = false)
    private double cash;

    @JsonFormat(pattern = "kk:mm:ss dd/MM/yyyy")
    @Convert(converter = LocalDateTimeAttributeConverter.class)
    @Column(updatable = false)
    private LocalDateTime createDate;

    private boolean actived = false;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "active_by", updatable = false)
    private User activeBy;

    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<UserRole> userRoles = new HashSet<>();

    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Password> passwords = new HashSet<>();

    @JsonIgnore
    @OneToMany(
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            mappedBy = "user"
    )
    private Set<SecurityToken> securityTokens = new HashSet<>();

    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade=CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Address> addresses = new HashSet<>();

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "introducted_by")
    private User introductedBy;

    public User() {
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phone) {
        this.phoneNumber = phone;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public String getDocumentOne() {
        return documentOne;
    }

    public void setDocumentOne(String documentOne) {
        this.documentOne = documentOne;
    }

    public String getDocumentTwo() {
        return documentTwo;
    }

    public void setDocumentTwo(String documentTwo) {
        this.documentTwo = documentTwo;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public double getCash() {
        return cash;
    }

    public void setCash(double cash) {
        this.cash = cash;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }


    public boolean isActived() {
        return actived;
    }

    public void setActived(boolean actived) {
        this.actived = actived;
    }

    public User getActiveBy() {
        return activeBy;
    }

    public void setActiveBy(User activeBy) {
        this.activeBy = activeBy;
    }

    public Set<UserRole> getUserRoles() {
        return userRoles;
    }

    public void setUserRoles(Set<UserRole> userRoles) {
        this.userRoles = userRoles;
    }

    public Set<Password> getPasswords() {
        return passwords;
    }

    public void setPasswords(Set<Password> passwords) {
        this.passwords = passwords;
    }

    public Set<Address> getAddresses() {
        return addresses;
    }

    public void setAddresses(Set<Address> addresses) {
        this.addresses = addresses;
    }

    public User getIntroductedBy() {
        return introductedBy;
    }

    public void setIntroductedBy(User introductedBy) {
        this.introductedBy = introductedBy;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority> authorities = new HashSet<>();
        userRoles.forEach(ur->authorities.add(new Authority(ur.getRole().getName())));
        return authorities;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (id != user.id) return false;
        return email.equals(user.email);
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + email.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", birthday=" + birthday +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", profileImage='" + profileImage + '\'' +
                ", documentOne='" + documentOne + '\'' +
                ", documentTwo='" + documentTwo + '\'' +
                ", sex=" + sex +
                ", enabled=" + enabled +
                ", status=" + status +
                ", cash=" + cash +
                ", createDate=" + createDate +
                ", actived=" + actived +
                ", activeBy=" + activeBy +
                ", userRoles=" + userRoles +
                '}';
    }
}
