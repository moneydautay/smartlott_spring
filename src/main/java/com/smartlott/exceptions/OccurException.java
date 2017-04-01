package com.smartlott.exceptions;

import com.smartlott.backend.persistence.domain.backend.MessageDTO;

/**
 * Created by greenlucky on 4/1/17.
 */
public class OccurException extends RuntimeException {

    private MessageDTO messageDTO;

    public OccurException(MessageDTO messageDTO) {
        super(messageDTO.getMessage());
        this.messageDTO = messageDTO;
    }

    public MessageDTO getMessageDTO() {
        return messageDTO;
    }
}
