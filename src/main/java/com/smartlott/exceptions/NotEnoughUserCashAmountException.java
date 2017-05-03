package com.smartlott.exceptions;

import com.smartlott.backend.persistence.domain.backend.MessageDTO;

/**
 * Created by greenlucky on 5/3/17.
 */
public class NotEnoughUserCashAmountException extends RuntimeException {

    private MessageDTO messageDTO;

    public NotEnoughUserCashAmountException(MessageDTO messageDTO) {
        super(messageDTO.getMessage());
        this.messageDTO = messageDTO;
    }

    public MessageDTO getMessageDTO() {
        return messageDTO;
    }
}
