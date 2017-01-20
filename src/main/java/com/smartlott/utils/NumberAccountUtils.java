package com.smartlott.utils;

import com.smartlott.backend.persistence.domain.backend.NumberAccount;
import com.smartlott.backend.persistence.domain.backend.NumberAccountType;
import com.smartlott.backend.persistence.domain.backend.User;

/**
 * Created by greenlucky on 12/24/16.
 */
public class NumberAccountUtils {


    /**
     * Non instantiable
     */
    private NumberAccountUtils() {
        throw new AssertionError("Non instantiable");
    }

    /**
     * Create a new entity number account type
     * @param name
     * @param description
     * @return A entity number account
     */
    public static NumberAccountType createNewNumberAccountType(String name, String description, double feesWithDraw, boolean rate){
        NumberAccountType numberAccountType = new NumberAccountType(name, description, feesWithDraw, rate);
        return numberAccountType;
    }

    /**
     * Create a new entity number account
     * @param numberAccount
     * @param user
     * @param numberAccountType
     * @return A entity number account
     */
    public static NumberAccount createNewNumberAccount(String numberAccount, User user, NumberAccountType numberAccountType){
        return new NumberAccount(numberAccount, user, numberAccountType);
    }
}
