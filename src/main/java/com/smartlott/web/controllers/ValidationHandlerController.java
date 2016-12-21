package com.smartlott.web.controllers;

import com.smartlott.backend.persistence.domain.backend.MessageDTO;
import com.smartlott.enums.MessageType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.*;

/**
 * Created by greenlucky on 12/21/16.
 */
@ControllerAdvice
public class ValidationHandlerController {

    @Autowired
    private MessageSource messageSource;

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public List<MessageDTO> processValidationError(MethodArgumentNotValidException ex){
        BindingResult result = ex.getBindingResult();
        List<FieldError> errors = result.getFieldErrors();

        return processFieldErrors(errors);
    }

    private List<MessageDTO> processFieldErrors(List<FieldError> errors) {
        List<MessageDTO> messages = new ArrayList<>();
        Locale currentLocale = LocaleContextHolder.getLocale();
        for (FieldError error : errors) {
            messages.add(getMessage(currentLocale, error));
        }
        return messages;
    }

   private MessageDTO getMessage(Locale locale,FieldError error){
        MessageDTO message = null;
        if(error != null) {
            String msg = messageSource.getMessage(error.getDefaultMessage(), null, locale);
            message = new MessageDTO(MessageType.ERROR, msg);
        }
        return message;
   }
}
