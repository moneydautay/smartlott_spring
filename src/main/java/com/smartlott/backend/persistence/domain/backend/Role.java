package com.smartlott.backend.persistence.domain.backend;

import com.smartlott.enums.RolesEnum;

import javax.persistence.*;
import javax.validation.constraints.Max;
import java.io.Serializable;

/**
 * Created by Mrs Hoang on 12/14/2016.
 */
@Entity
@Table(name = "role")
public class Role implements Serializable{

    /** The Serial Version UID for Serializable classes */
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "role_id")
    private int id;

    private String name;

    @Max(value = 10)
    private int priority;

    public Role() {
    }

    public Role(RolesEnum rolesEnum) {
        this.id = rolesEnum.getId();
        this.name = rolesEnum.getName();
        this.priority = rolesEnum.getPriority();
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

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    @Override
    public String toString() {
        return "Role{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", priority=" + priority +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Role role = (Role) o;

        if (id != role.id) return false;
        if (priority != role.priority) return false;
        return name != null ? name.equals(role.name) : role.name == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + priority;
        return result;
    }
}
