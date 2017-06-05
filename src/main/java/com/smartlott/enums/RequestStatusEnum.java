package com.smartlott.enums;

/**
 * Created by greenlucky on 5/17/17.
 */
public enum  RequestStatusEnum {

    PENDING(1, "Pending", ""),
    PROCESSING(2, "Processing", ""),
    HANDLE(3, "Deny", ""),
    DENY(4, "Deny", ""),
    CANCEL(5, "Cancel", "");

    private int id;

    private String name;

    private String description;

    RequestStatusEnum(int id, String name, String description) {
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
