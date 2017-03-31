package com.smartlott.exceptions;

import com.smartlott.backend.persistence.domain.backend.MessageDTO;

/**
 * Created by greenlucky on 3/31/17.
 */
public class NotFoundException extends RuntimeException{

    private long id;

    private MessageDTO messageDTO;

    public NotFoundException(long id) {
        super(String.valueOf(id));
        this.id = id;
    }

    public NotFoundException(MessageDTO messageDTO) {
        super(messageDTO.getMessage());
        this.messageDTO = messageDTO;
    }

    public long getId() {
        return id;
    }

    public MessageDTO getMessageDTO() {
        return messageDTO;
    }
}
