package com.smartlott.backend.persistence.domain.backend;

import com.smartlott.enums.IncomeProcessEnum;

import javax.persistence.*;

/**
 * Created by lamdevops on 6/26/17.
 */
@Entity
@Table(name = "income_process")
public class IncomeProcess {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String name;

    private String desscription;

    public IncomeProcess() {
    }

    public IncomeProcess(String name, String desscription) {
        this.name = name;
        this.desscription = desscription;
    }

    public IncomeProcess(IncomeProcessEnum processEnum) {
        this.id = processEnum.getId();
        this.name = processEnum.getName();
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

    public String getDesscription() {
        return desscription;
    }

    public void setDesscription(String desscription) {
        this.desscription = desscription;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        IncomeProcess that = (IncomeProcess) o;

        return id == that.id;
    }

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public String toString() {
        return "IncomeProcess{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", desscription='" + desscription + '\'' +
                '}';
    }
}
