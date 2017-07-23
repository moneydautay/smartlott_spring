package com.smartlott.backend.persistence.domain.backend.trans;

import com.smartlott.backend.persistence.domain.base.BaseModel;
import org.springframework.beans.factory.annotation.Value;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

/**
 * Created by lamdevops on 7/23/17.
 */
@Entity
public class Transaction extends BaseModel{


    @Value(value = "0.0")
    private double amount;

    @ManyToOne
    private InvoiceType invoiceType;

    @ManyToOne
    private TransStatus status;



}
