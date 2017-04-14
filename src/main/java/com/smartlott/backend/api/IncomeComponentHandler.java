package com.smartlott.backend.api;

import com.smartlott.backend.persistence.domain.backend.IncomeComponent;
import com.smartlott.backend.persistence.domain.backend.MessageDTO;
import com.smartlott.backend.persistence.domain.backend.Reward;
import com.smartlott.backend.service.I18NService;
import com.smartlott.backend.service.IncomeComponentService;
import com.smartlott.backend.service.RewardService;
import com.smartlott.enums.MessageType;
import com.smartlott.exceptions.NotFoundException;
import com.smartlott.exceptions.OccurException;
import com.smartlott.utils.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    /** The application logger */
    private static final Logger LOGGER = LoggerFactory.getLogger(IncomeComponentHandler.class);

    public static final String API_INCOME_COMPONENT = "/api/income-component";

    @Autowired
    private IncomeComponentService incomeComponentService;

    @Autowired
    private RewardService rewardService;

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

        Reward reward = null;
        if(incomeComponent.getReward() != null && incomeComponent.getReward().getId() > 0) {
            reward = rewardService.getOne(incomeComponent.getReward().getId());
        }
        try {
            incomeComponent = incomeComponentService.create(incomeComponent);
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage());
            throw  new OccurException(new MessageDTO(MessageType.ERROR,
                    i18NService.getMessage("admin.income.component.error.update.unique.reward.text",
                            new Object[]{reward.getName(), incomeComponent.getName()}, locale)));
        }

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
        Reward reward = null;
        if(incomeComponent.getReward() != null && incomeComponent.getReward().getId() > 0) {
            reward = rewardService.getOne(incomeComponent.getReward().getId());
        }
        try {
            incomeComponent = incomeComponentService.update(incomeComponent);
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage());
            throw  new OccurException(new MessageDTO(MessageType.ERROR,
                    i18NService.getMessage("admin.income.component.error.update.unique.reward.text",
                            new Object[]{reward.getName(), localIncomeComponent.getName()}, locale)));
        }

        MessageDTO messageDTO = new MessageDTO(MessageType.SUCCESS,
                i18NService.getMessage("admin.income.component.success.updated.text",
                        incomeComponent.getName(), locale));

        Map<String, Object> response = ResponseUtil.Response(messageDTO, incomeComponent);

        return new ResponseEntity<Object>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{incomeComponentId}")
    public ResponseEntity<Object> deleteIncomeComponent(@PathVariable int incomeComponentId, Locale locale) {
        IncomeComponent localIncomeComponent = getLocalIncomeComponent(incomeComponentId, locale);

        try {
            incomeComponentService.delete(incomeComponentId);
        } catch (Exception ex) {
            messageDTOS = Arrays.asList(new MessageDTO(MessageType.ERROR,
                        i18NService.getMessage("admin.income.component.error.delete.text",
                                localIncomeComponent.getName(), locale)));
            return new ResponseEntity<Object>(messageDTOS, HttpStatus.BAD_REQUEST);
        }

        messageDTOS = new ArrayList<>();
        MessageDTO messageDTO = new MessageDTO(MessageType.SUCCESS,
                i18NService.getMessage("admin.income.component.success.deleted.text",
                        localIncomeComponent.getName(), locale));

        messageDTOS.add(messageDTO);

        return new ResponseEntity<Object>(messageDTOS, HttpStatus.OK);
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
