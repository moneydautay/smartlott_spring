package com.smartlott.backend.persistence.domain.dto;

import com.smartlott.backend.persistence.domain.backend.NumberAccount;
import com.smartlott.backend.persistence.domain.backend.NumberAccountType;

/**
 * Created by greenlucky on 3/31/17.
 */
public class NumberAccountDTO {

    private long id;

    private String number;

    private NumberAccountType numberAccountType;

    private boolean enabled = true;

    public NumberAccountDTO() {
    }

    public NumberAccountDTO(NumberAccount numberAccount) {
        this.id = numberAccount.getId();
        this.number = numberAccount.getNumber();
        this.numberAccountType = numberAccount.getNumberAccountType();
        this.enabled = numberAccount.isEnabled();
    }

    public long getId() {
        return id;
    }

    public String getNumber() {
        return number;
    }

    public NumberAccountType getNumberAccountType() {
        return numberAccountType;
    }

    public boolean isEnabled() {
        return enabled;
    }

    @Override
    public String toString() {
        return "NumberAccountDTO{" +
                "id=" + id +
                ", number='" + number + '\'' +
                ", numberAccountType=" + numberAccountType +
                ", enabled=" + enabled +
                '}';
    }
}
