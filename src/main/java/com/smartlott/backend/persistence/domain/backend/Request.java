package com.smartlott.backend.persistence.domain.backend;

import com.smartlott.backend.persistence.converters.LocalDateTimeAttributeConverter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Created by Mrs Hoang on 17/12/2016.
 */
@Entity
@Table(name = "request")
public class Request implements Serializable{

    /** The Serial Version UID for Serializable classes */
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private LocalDateTime requestDate;

    private String content;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "request_of_user")
    private User requestOfUser;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "handle_by_user")
    private User handleByUser;

    @Convert(converter = LocalDateTimeAttributeConverter.class)
    private LocalDateTime handleDate;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "request_type_id")
    private RequestType requestType;

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

    public User getHandleByUser() {
        return handleByUser;
    }

    public void setHandleByUser(User handleByUser) {
        this.handleByUser = handleByUser;
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

    @Override
    public String toString() {
        return "Request{" +
                "id=" + id +
                ", requestDate=" + requestDate +
                ", content='" + content + '\'' +
                ", requestOfUser=" + requestOfUser +
                ", handleByUser=" + handleByUser +
                ", handleDate=" + handleDate +
                ", requestType=" + requestType +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Request request = (Request) o;

        if (id != request.id) return false;
        if (requestDate != null ? !requestDate.equals(request.requestDate) : request.requestDate != null) return false;
        if (content != null ? !content.equals(request.content) : request.content != null) return false;
        if (requestOfUser != null ? !requestOfUser.equals(request.requestOfUser) : request.requestOfUser != null)
            return false;
        if (handleByUser != null ? !handleByUser.equals(request.handleByUser) : request.handleByUser != null)
            return false;
        if (handleDate != null ? !handleDate.equals(request.handleDate) : request.handleDate != null) return false;
        return requestType != null ? requestType.equals(request.requestType) : request.requestType == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (requestDate != null ? requestDate.hashCode() : 0);
        result = 31 * result + (content != null ? content.hashCode() : 0);
        result = 31 * result + (requestOfUser != null ? requestOfUser.hashCode() : 0);
        result = 31 * result + (handleByUser != null ? handleByUser.hashCode() : 0);
        result = 31 * result + (handleDate != null ? handleDate.hashCode() : 0);
        result = 31 * result + (requestType != null ? requestType.hashCode() : 0);
        return result;
    }
}
