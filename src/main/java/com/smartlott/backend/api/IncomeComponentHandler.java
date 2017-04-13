package com.smartlott.backend.api;

import com.smartlott.backend.persistence.domain.backend.IncomeComponent;
import com.smartlott.backend.persistence.domain.backend.MessageDTO;
import com.smartlott.backend.service.I18NService;
import com.smartlott.backend.service.IncomeComponentService;
import com.smartlott.enums.MessageType;
import com.smartlott.exceptions.NotFoundException;
import com.smartlott.utils.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * Created by greenlucky on 4/13/17.
 */
@RestController
@RequestMapping(IncomeComponentHandler.API_INCOME_COMPONENT)
public class IncomeComponentHandler {

    public static final String API_INCOME_COMPONENT = "/api/income-component";

    @Autowired
    private IncomeComponentService incomeComponentService;

    @Autowired
    private I18NService i18NService;

    private List<MessageDTO> messageDTOS;

    @GetMapping("/all")
    public ResponseEntity<Object> getAll(Pageable pageable) {

        Page<IncomeComponent> incomeComponentPage = incomeComponentService.getAll(pageable);

        return new ResponseEntity<Object>(incomeComponentPage, HttpStatus.OK);
    }

    @GetMapping("/{incomeComponentId}")
    public ResponseEntity<Object> getIncomeComponent(@PathVariable int incomeComponentId, Locale locale) {

        IncomeComponent incomeComponent = getLocalIncomeComponent(incomeComponentId, locale);

        return new ResponseEntity<Object>(incomeComponent, HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<Object> createIncomeComponent(@RequestBody IncomeComponent incomeComponent, Locale locale) {

        messageDTOS = new ArrayList<>();

        incomeComponent = incomeComponentService.create(incomeComponent);

        MessageDTO messageDTO = new MessageDTO(MessageType.SUCCESS,
                i18NService.getMessage("admin.income.component.success.created.text",
                        incomeComponent.getName(), locale));

        Map<String, Object> response = ResponseUtil.Response(messageDTO, incomeComponent);

        return new ResponseEntity<Object>(response, HttpStatus.OK);
    }

    @PutMapping("/{incomeComponentId}")
    public ResponseEntity<Object> updateIncomeComponent(@PathVariable int incomeComponentId,
                                                        @RequestBody IncomeComponent incomeComponent, Locale locale) {
        IncomeComponent localIncomeComponent = getLocalIncomeComponent(incomeComponentId, locale);

        incomeComponent = incomeComponentService.update(incomeComponent);

        MessageDTO messageDTO = new MessageDTO(MessageType.SUCCESS,
                i18NService.getMessage("admin.income.component.success.updated.text",
                        incomeComponent.getName(), locale));

        Map<String, Object> response = ResponseUtil.Response(messageDTO, incomeComponent);

        return new ResponseEntity<Object>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{incomeComponentId}")
    public ResponseEntity<Object> deleteIncomeComponent(@PathVariable int incomeComponentId,
                                                        @RequestBody IncomeComponent incomeComponent, Locale locale) {
        IncomeComponent localIncomeComponent = getLocalIncomeComponent(incomeComponentId, locale);

        try {
            incomeComponentService.delete(incomeComponentId);
        } catch (Exception ex) {
            messageDTOS = Arrays.asList(new MessageDTO(MessageType.ERROR,
                        i18NService.getMessage("admin.income.component.error.delete.text",
                                localIncomeComponent.getName(), locale)));
            return new ResponseEntity<Object>(messageDTOS, HttpStatus.BAD_REQUEST);
        }

        MessageDTO messageDTO = new MessageDTO(MessageType.SUCCESS,
                i18NService.getMessage("admin.income.component.success.deleted.text",
                        incomeComponent.getName(), locale));

        Map<String, Object> response = ResponseUtil.Response(messageDTO, incomeComponent);

        return new ResponseEntity<Object>(response, HttpStatus.OK);
    }


    private IncomeComponent getLocalIncomeComponent(int incomeComponentId, Locale locale) {

        IncomeComponent incomeComponent = incomeComponentService.getOne(incomeComponentId);

        if (incomeComponent == null) {
            MessageDTO messageDTO = new MessageDTO(MessageType.ERROR,
                        i18NService.getMessage("admin.income.component.error.not.found.text",
                                String.valueOf(incomeComponentId), locale));
            throw  new NotFoundException(messageDTO);
        }

        return incomeComponent;
    }


}
