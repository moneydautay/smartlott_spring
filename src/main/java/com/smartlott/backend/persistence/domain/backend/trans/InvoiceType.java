package com.smartlott.backend.persistence.domain.backend.trans;

import com.smartlott.backend.persistence.domain.base.BaseModel;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

/**
 * Created by lamdevops on 7/23/17.
 */
@Entity
public class InvoiceType extends BaseModel{

    private String name;

    @ManyToOne
    private PaymentType paymentType;

    @Override
    public String toString() {
        return "InvoiceType{" +
                "name='" + name + '\'' +
                ", paymentType=" + paymentType +
                '}';
    }
}
