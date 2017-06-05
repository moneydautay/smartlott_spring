package com.smartlott.backend.persistence.domain.backend;

import com.smartlott.enums.RequestStatusEnum;

import javax.persistence.*;

/**
 * Created by greenlucky on 5/17/17.
 */
@Entity
@Table(name = "request_status")
public class RequestStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String name;

    private String description;

    public RequestStatus(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public RequestStatus(RequestStatusEnum cancel) {
        this.id = cancel.getId();
        this.name = cancel.getName();
        this.description = cancel.getDescription();
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
}
