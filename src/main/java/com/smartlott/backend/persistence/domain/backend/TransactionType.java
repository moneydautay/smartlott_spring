package com.smartlott.backend.persistence.domain.backend;

import com.smartlott.enums.TransactionTypeEnum;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by Mrs Hoang on 17/12/2016.
 */
@Entity
@Table(name = "transaction_type")
public class TransactionType implements Serializable{

    /** The Serial Version UID for Serializable classes */
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Length(max = 120)
    private String name;

    @Length(max = 255)
    private String description;

    public TransactionType() {
    }

    public TransactionType(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public TransactionType(TransactionTypeEnum typeEnum){
        this.id = typeEnum.getId();
        this.name = typeEnum.getName();
        this.description = typeEnum.getDescription();
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
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
    public String toString() {
        return "TransactionType{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TransactionType that = (TransactionType) o;

        if (id != that.id) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        return description != null ? description.equals(that.description) : that.description == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        return result;
    }
}
