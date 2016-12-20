package com.smartlott.exceptions;

/**
 * Created by Mrs Hoang on 20/12/2016.
 */
public class RoleNotFoundException extends RuntimeException{

    private int id;


    public RoleNotFoundException(int role){
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
