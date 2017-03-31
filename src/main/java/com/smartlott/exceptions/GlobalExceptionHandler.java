package com.smartlott.exceptions;

import com.smartlott.backend.persistence.domain.backend.MessageDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by greenlucky on 3/31/17.
 */
@ControllerAdvice
@RestController
public class GlobalExceptionHandler {

    /** The application logger */
    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    private List<MessageDTO> messageDTOS;

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Object> notFoundExceptionHandler(NotFoundException e) {
        LOGGER.error(e.getMessage());
        messageDTOS = new ArrayList<>();
        messageDTOS.add(e.getMessageDTO());
        return new ResponseEntity<Object>(messageDTOS, HttpStatus.NOT_FOUND);
    }
}
