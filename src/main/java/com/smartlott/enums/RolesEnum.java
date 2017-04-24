package com.smartlott.enums;

/**
 * Created by Mr Lam on 12/15/2016.
 */
public enum RolesEnum {
    CUSTOMER(1, "ROLE_CUSTOMER", 1, "Customer"),
    SEO(2, "ROLE_SEO", 1, "Seo"),
    STAFF(3, "ROLE_STAFF", 2, "Staff"),
    EDITOR(4, "ROLE_EDITOR", 1, "Editor"),
    MANAGER(5, "ROLE_MANAGER", 4, "Manager"),
    ADMIN(6, "ROLE_ADMIN", 10, "Admin");

    private int id;

    private String name;

    private String description;

    private int priority;

    RolesEnum(int id, String name, int priority, String description) {
        this.id = id;
        this.name = name;
        this.priority = priority;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getPriority() {
        return priority;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
