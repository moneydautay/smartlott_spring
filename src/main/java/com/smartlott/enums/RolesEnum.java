package com.smartlott.enums;

/**
 * Created by Mr Lam on 12/15/2016.
 */
public enum RolesEnum {
    CUSTOMER(1, "Customer",1),
    SEO(2, "Seo",1),
    STAFF(3, "Staff",2),
    EDITOR(4, "Editor",1),
    MANAGER(5, "Manager",4),
    ADMIN(6, "Admin",10);

    private int id;

    private String name;

    private int priority;

    RolesEnum(int id, String name, int priority) {
        this.id = id;
        this.name = name;
        this.priority = priority;
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
}
