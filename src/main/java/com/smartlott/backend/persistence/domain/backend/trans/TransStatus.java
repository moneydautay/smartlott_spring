package com.smartlott.backend.persistence.domain.backend.trans;

import com.smartlott.backend.persistence.domain.base.BaseModel;

import javax.persistence.Entity;

/**
 * Created by lamdevops on 7/23/17.
 */
@Entity
public class TransStatus extends BaseModel{

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "TransStatus{" +
                "name='" + name + '\'' +
                '}';
    }
}
