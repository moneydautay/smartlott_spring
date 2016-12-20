package com.smartlott.exceptions;

/**
 * Created by Mrs Hoang on 19/12/2016.
 */
public class FeaturedSliderNotFoundException extends RuntimeException{

    private int id;
    /** The Serial Version UID for Serializable classes */
    private static final long serialVersionUID = 1L;

    public FeaturedSliderNotFoundException(int id){
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
