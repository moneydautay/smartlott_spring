package com.smartlott.backend.persistence.domain.backend;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.smartlott.backend.persistence.converters.LocalDateTimeAttributeConverter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Created by Mrs Hoang on 17/12/2016.
 */
@Entity
@Table(name = "request")
@EntityListeners(AuditingEntityListener.class)
public class Request implements Serializable {

    /**
     * The Serial Version UID for Serializable classes
     */
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @JsonFormat(pattern = "kk:mm:ss dd/MM/yyyy")
    @Convert(converter = LocalDateTimeAttributeConverter.class)
    @CreatedDate
    private LocalDateTime requestDate;

    private String content;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "request_of_user")
    private User requestOfUser;

    @LastModifiedBy
    private String handleBy;

    @Convert(converter = LocalDateTimeAttributeConverter.class)
    @LastModifiedDate
    private LocalDateTime handleDate;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "request_type_id")
    private RequestType requestType;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "request_status_id")
    private RequestStatus requestStatus;

    public Request() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public LocalDateTime getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(LocalDateTime requestDate) {
        this.requestDate = requestDate;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public User getRequestOfUser() {
        return requestOfUser;
    }

    public void setRequestOfUser(User requestOfUser) {
        this.requestOfUser = requestOfUser;
    }

    public String getHandleBy() {
        return handleBy;
    }

    public void setHandleBy(String handleBy) {
        this.handleBy = handleBy;
    }

    public LocalDateTime getHandleDate() {
        return handleDate;
    }

    public void setHandleDate(LocalDateTime handleDate) {
        this.handleDate = handleDate;
    }

    public RequestType getRequestType() {
        return requestType;
    }

    public void setRequestType(RequestType requestType) {
        this.requestType = requestType;
    }

    public RequestStatus getRequestStatus() {
        return requestStatus;
    }

    public void setRequestStatus(RequestStatus requestStatus) {
        this.requestStatus = requestStatus;
    }

    @Override
    public String toString() {
        return "Request{" +
                "id=" + id +
                ", requestDate=" + requestDate +
                ", content='" + content + '\'' +
                ", requestOfUser=" + requestOfUser +
                ", handleBy=" + handleBy +
                ", handleDate=" + handleDate +
                ", requestType=" + requestType +
                ", requestStatus=" + requestStatus +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Request request = (Request) o;

        return id == request.id;
    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }
}
