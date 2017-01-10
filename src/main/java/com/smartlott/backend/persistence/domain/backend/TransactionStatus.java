package com.smartlott.backend.persistence.domain.backend;

import com.smartlott.enums.TransactionStatusEnum;

import javax.persistence.*;
import javax.validation.constraints.Size;

/**
 * Created by Mrs Hoang on 10/01/2017.
 */
@Entity
@Table(name = "transaction_status")
public class TransactionStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String name;

    private String description;


    public TransactionStatus() {
    }

    public TransactionStatus(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public TransactionStatus(TransactionStatusEnum statusEnum) {
        this.id = statusEnum.getId();
        this.name = statusEnum.getName();
        this.description = statusEnum.getDescription();
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

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TransactionStatus that = (TransactionStatus) o;

        return id == that.id;
    }

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public String toString() {
        return "TransactionStatus{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
