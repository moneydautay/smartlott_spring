package com.smartlott.enums;

/**
 * Created by Mrs Hoang on 10/01/2017.
 * @author Lam Nguyen
 * @version %I% %G%
 * @since 1.0
 */
public enum TransactionStatusEnum {

    PENDING(1,"PENDING","The status is waiting deposit or handle by manager"),
    SUCCESS(2,"SUCCESS","The status is success"),
    CANCEL(3,"CANCEL","The status was cancel by customer or manager");

    private int id;

    private String name;

    private String description;

    TransactionStatusEnum(int id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}
