package com.smartlott.backend.persistence.domain.backend;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.smartlott.backend.persistence.converters.LocalDateTimeAttributeConverter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by Mr Lam on 12/14/2016.
 */
@Entity
@Table(
        name = "user",
        uniqueConstraints = @UniqueConstraint(columnNames = {"email", "phone_number", "username"})
)
@DynamicInsert(value = true)
@DynamicUpdate(value = true)
@EntityListeners(AuditingEntityListener.class)
public class User implements Serializable, UserDetails {

    /**
     * The Serial Version UID for Serializable classes
     */
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Email(message = "Email.user.email")
    @Size(min = 5, max = 100, message = "Size.user.email")
    private String email;


    @NotEmpty(message = "NotEmpty.user.username")
    @Size(min = 4, max = 50, message = "Size.user.username")
    private String username;

    @Size(min = 6, max = 100, message = "Size.user.password")
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

    @Column(name = "document_1", updatable = false)
    private String documentOne;

    @Column(name = "document_2", updatable = false)
    private String documentTwo;

    private int sex;

    private boolean enabled = true;

    @LastModifiedBy
    private String modifiedBy;

    @JsonFormat(pattern = "kk:mm:ss dd/MM/yyyy")
    @Convert(converter = LocalDateTimeAttributeConverter.class)
    @LastModifiedDate
    private LocalDateTime modifiedDate;

    @Value(value = "0")
    @Column(updatable = false)
    private double cash;

    @JsonFormat(pattern = "kk:mm:ss dd/MM/yyyy")
    @Convert(converter = LocalDateTimeAttributeConverter.class)
    @Column(name = "create_date", updatable = false)
    @CreatedDate
    private LocalDateTime createDate;

    @Column(updatable = false)
    private boolean actived = false;

    @JoinColumn(name = "active_by", updatable = false)
    private String activeBy;

    @CreatedBy
    private String createdBy;

    @JsonFormat(pattern = "kk:mm:ss dd/MM/yyyy")
    @Convert(converter = LocalDateTimeAttributeConverter.class)
    @Column(updatable = false)
    private LocalDateTime activeDate;

    @JsonIgnore
    @ManyToMany(cascade = CascadeType.DETACH, fetch = FetchType.EAGER)
    @JoinTable(name = "user_role",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id")
    )
    private Set<Role> roles = new HashSet<>();

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

    @JoinColumn(name = "introduced_by", updatable = false)
    private String introducedBy;

    @Column(name = "introduced_key", updatable = false)
    private String introducedKey;

    @JoinColumn(updatable = false, name = "user_id", referencedColumnName = "id")
    @OneToMany(fetch = FetchType.EAGER)
    private Set<UserCash> userCashes = new HashSet<>();

    @JsonIgnore
    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    private Set<UserInvestment> userInvestments = new HashSet<>();

    @Transient
    private UserInvestment userInvestment;

    @Transient
    private String roleNames = "";

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

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public LocalDateTime getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(LocalDateTime modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
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

    public String getActiveBy() {
        return activeBy;
    }

    public void setActiveBy(String activeBy) {
        this.activeBy = activeBy;
    }

    public LocalDateTime getActiveDate() {
        return activeDate;
    }

    public void setActiveDate(LocalDateTime activeDate) {
        this.activeDate = activeDate;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public Set<Password> getPasswords() {
        return passwords;
    }

    public void setPasswords(Set<Password> passwords) {
        this.passwords = passwords;
    }

    public String getIntroducedBy() {
        return introducedBy;
    }

    public void setIntroducedBy(String introducedBy) {
        this.introducedBy = introducedBy;
    }

    public String getIntroducedKey() {
        return introducedKey;
    }

    public void setIntroducedKey(String introducedKey) {
        this.introducedKey = introducedKey;
    }

    public Set<UserCash> getUserCashes() {
        return userCashes;
    }

    public void setUserCashes(Set<UserCash> userCashes) {
        this.userCashes = userCashes;
    }

    public Set<UserInvestment> getUserInvestments() {
        return userInvestments;
    }

    public void setUserInvestments(Set<UserInvestment> userInvestments) {
        this.userInvestments = userInvestments;
    }

    public void addUserInvestment(UserInvestment userInvestment) {
        this.userInvestments.add(userInvestment);
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getRoleNames() {
        roleNames = "";
        roles.forEach(role -> {
            if(!roleNames.equals(""))
                roleNames += ", ";
            roleNames += role.getDescription();
        });
        return roleNames;
    }

    public void setRoleNames(String roleNames) {
        this.roleNames = roleNames;
    }

    public UserInvestment getUserInvestment() {
        UserInvestment investmentPackage = null;
        LocalDateTime now = LocalDateTime.now(Clock.systemDefaultZone());
        Iterator<UserInvestment> userInvestmentIterator = userInvestments.iterator();
        while (userInvestmentIterator.hasNext()) {
            UserInvestment next = userInvestmentIterator.next();
            if ((next.getFromDate().isBefore(now) && (next.getToDate() == null || next.getToDate().isAfter(now)))
                    && next.isEnabled()) {
                if (investmentPackage == null)
                    investmentPackage = next;
                else if (investmentPackage.getInvestmentPackage().getLevelNetwork() < next.getInvestmentPackage().getLevelNetwork())
                    investmentPackage = next;
            }
        }
        return investmentPackage;
    }

    public InvestmentPackage getRequiredInvestmentPackage(int packageId) {
        Iterator<UserInvestment> userInvestmentIterator = userInvestments.iterator();
        LocalDateTime now = LocalDateTime.now(Clock.systemDefaultZone());
        while (userInvestmentIterator.hasNext()) {
            UserInvestment next = userInvestmentIterator.next();
            if ((next.getFromDate().isBefore(now) && (next.getToDate() == null || next.getToDate().isAfter(now)))
                    && next.isEnabled() && next.getInvestmentPackage().getId() == packageId)
                return next.getInvestmentPackage();
        }
        return null;
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

        roles.forEach(ur -> authorities.add(new Authority(ur.getName())));
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
                ", cash=" + cash +
                ", createDate=" + createDate +
                ", actived=" + actived +
                ", activeBy=" + activeBy +
                ", userRoles=" + roles +
                ", roleNames=" + roleNames +
                '}';
    }
}
