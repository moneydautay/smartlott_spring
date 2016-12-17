package com.smartlott.backend.persistence.domain.backend;

import javax.persistence.*;
import java.util.Set;

/**
 * Created by Mrs Hoang on 17/12/2016.
 */
@Entity
@Table(name = "category_post")
public class CategoryPost {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String name;

    private String description;


    public CategoryPost() {
    }

    public CategoryPost(String name, String description) {
        this.name = name;
        this.description = description;
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
    public String toString() {
        return "CategoryPost{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CategoryPost that = (CategoryPost) o;

        return id == that.id;
    }

    @Override
    public int hashCode() {
        return id;
    }
}
