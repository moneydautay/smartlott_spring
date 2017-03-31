package com.smartlott.exceptions;

import com.smartlott.backend.persistence.domain.backend.MessageDTO;
import com.smartlott.backend.service.I18NService;
import com.smartlott.enums.MessageType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Locale;

/**
 * Created by greenlucky on 3/31/17.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class GlobalExceptionHandlerTest {

    @Autowired
    private I18NService i18NService;

    @Test
    public void notFoundExceptionHandler() throws Exception {

        Locale locale = new Locale("");

        MessageDTO messageDTO = new MessageDTO(MessageType.ERROR,
                i18NService.getMessage("Id.user.not.found", String.valueOf(1), locale));
        throw new NotFoundException(messageDTO);
    }

}