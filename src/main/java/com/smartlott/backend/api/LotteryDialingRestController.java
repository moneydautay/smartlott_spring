package com.smartlott.backend.api;

import com.smartlott.backend.persistence.domain.backend.LotteryDialing;
import com.smartlott.backend.persistence.domain.backend.MessageDTO;
import com.smartlott.backend.service.I18NService;
import com.smartlott.backend.service.LotteryDialingService;
import com.smartlott.enums.MessageType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.Clock;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by Mrs Hoang on 05/01/2017.
 */
@RestController
@RequestMapping(LotteryDialingRestController.API_LOTTERY_DIALING_URL)
public class LotteryDialingRestController {

    public static final String API_LOTTERY_DIALING_URL = "/api/lottery-dialing";
    
    /** The application logger */
    private static final Logger LOGGER = LoggerFactory.getLogger(LotteryDialingRestController.class);

    @Autowired
    private LotteryDialingService dialingService;

    @Autowired
    private I18NService i18NService;

    /**
     * Retrieve all lottery dialing given by
     * @param pageable
     * @param locale
     * @return A list of lottery dialing and http status of or error if occurs error
     */
    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<Object> getAll(Pageable pageable, Locale locale){
        Page<LotteryDialing> lotteryDialings = dialingService.getAll(pageable);
        return new ResponseEntity<Object>(lotteryDialings, HttpStatus.OK);
    }

    /**
     * Create new lottery dialing
     * @param lotteryDialing
     * @param locale
     * @return A lottery dialing and http status or or Http status error if occurs errors
     */
    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<Object> create(@Valid @RequestBody LotteryDialing lotteryDialing, Locale locale){
        System.out.println("PRINT LOTTERY DIALING: "+lotteryDialing.getFromDate());
        System.out.println("PRINT LOTTERY DIALING: "+lotteryDialing.getToDate());

        List<MessageDTO> messageDTOS = new ArrayList<>();
        boolean error = false;

        //Make sure don't have any lottery dialing is opening
        LotteryDialing localLotteryDialing = dialingService.getOpenedLotteryDialing(true);
        if(localLotteryDialing != null){
            LOGGER.error("Have a lottery dialing {} is opening can't create new lottery dialing", localLotteryDialing);
            messageDTOS.add(new MessageDTO(MessageType.ERROR, i18NService.getMessage("admin.lottery.dialing.error.opening.text", String.valueOf(localLotteryDialing.getId()), locale)));
            return new ResponseEntity<Object>(messageDTOS, HttpStatus.EXPECTATION_FAILED);
        }
        DateTimeFormatter ft = DateTimeFormatter.ofPattern("kk:mm:ss dd/MM/yyyy");
        LocalDateTime now = LocalDateTime.now(Clock.systemDefaultZone());
        if(lotteryDialing.getFromDate().isBefore(now)){
            LOGGER.error("Lottery dialing opened date {} can't greater than now {}", lotteryDialing.getFromDate().format(ft), now.format(ft));
            messageDTOS.add(new MessageDTO(MessageType.ERROR, i18NService.getMessage("admin.lottery.dialing.error.from.date.before.now.text", String.valueOf(now.format(ft)), locale)));
            error = true;
        }

        if(lotteryDialing.getToDate().isBefore(lotteryDialing.getFromDate()) || lotteryDialing.getToDate().isEqual(lotteryDialing.getFromDate())){
            LOGGER.error("Lottery dialing closed date {} can't after from date {}", lotteryDialing.getToDate(), lotteryDialing.getFromDate());
            messageDTOS.add(new MessageDTO(MessageType.ERROR, i18NService.getMessage("admin.lottery.dialing.error.to.date.beforeorequal.from.date.text", String.valueOf(lotteryDialing.getFromDate()), locale)));
            error = true;
        }

        //If occurs error will be return
        if(error)
            return new ResponseEntity<Object>(messageDTOS, HttpStatus.EXPECTATION_FAILED);

        dialingService.create(lotteryDialing);
        messageDTOS.add(new MessageDTO(MessageType.SUCCESS,i18NService.getMessage("admin.lottery.dialing.success.add", String.valueOf(lotteryDialing.getId()), locale)));
        return new ResponseEntity<Object>(messageDTOS, HttpStatus.OK);
    }


    @RequestMapping(value = "/{lotteryDialingId}", method = RequestMethod.PUT)
    public ResponseEntity<Object> update(@PathVariable long lotteryDialingId, @Valid LotteryDialing lotteryDialing, Locale locale){

        List<MessageDTO> messageDTOS = new ArrayList<>();

        LotteryDialing localLotteryDialing = dialingService.getOne(lotteryDialingId);
        if(localLotteryDialing == null){
            LOGGER.error("Lottery dialing id {} was not found", lotteryDialingId);
            messageDTOS.add(new MessageDTO(MessageType.ERROR, i18NService.getMessage("admin.lottery.dialing.id.not.found", String.valueOf(lotteryDialingId), locale)));
            return new ResponseEntity<Object>(messageDTOS, HttpStatus.EXPECTATION_FAILED);
        }

        lotteryDialing = dialingService.update(lotteryDialing);
        messageDTOS.add(new MessageDTO(MessageType.SUCCESS,i18NService.getMessage("admin.lottery.dialing.success.close", String.valueOf(lotteryDialing.getId()), locale)));
        return new ResponseEntity<Object>(messageDTOS, HttpStatus.OK);
    }

    @RequestMapping(value = "/close/{lotteryDialingId}", method = RequestMethod.PUT)
    public ResponseEntity<Object> close(@PathVariable long lotteryDialingId, Locale locale){

        List<MessageDTO> messageDTOS = new ArrayList<>();

        LotteryDialing localLotteryDialing = dialingService.getOne(lotteryDialingId);
        if(localLotteryDialing == null){
            LOGGER.error("Lottery dialing id {} was not found", lotteryDialingId);
            messageDTOS.add(new MessageDTO(MessageType.ERROR, i18NService.getMessage("admin.lottery.dialing.id.not.found", String.valueOf(lotteryDialingId), locale)));
            return new ResponseEntity<Object>(messageDTOS, HttpStatus.EXPECTATION_FAILED);
        }

        if(!localLotteryDialing.isOpened()){
            LOGGER.error("Lottery dialing id {} is not openning", lotteryDialingId);
            messageDTOS.add(new MessageDTO(MessageType.ERROR, i18NService.getMessage("admin.lottery.dialing.not.opening", String.valueOf(lotteryDialingId), locale)));
            return new ResponseEntity<Object>(messageDTOS, HttpStatus.EXPECTATION_FAILED);
        }

        localLotteryDialing.setOpened(false);
        localLotteryDialing = dialingService.update(localLotteryDialing);
        messageDTOS.add(new MessageDTO(MessageType.SUCCESS,i18NService.getMessage("admin.lottery.dialing.success.close", String.valueOf(localLotteryDialing.getId()), locale)));
        return new ResponseEntity<Object>(messageDTOS, HttpStatus.OK);
    }

    @RequestMapping(value = "/{lotteryDialingId}", method = RequestMethod.GET)
    public ResponseEntity<Object> getOne(@PathVariable long lotteryDialingId, Locale locale){
        List<MessageDTO> messageDTOS = new ArrayList<>();
        //find lotteryDialing by id
        LotteryDialing localLotteryDialing = dialingService.getOne(lotteryDialingId);
        if(localLotteryDialing == null){
            LOGGER.error(" Lottery dialing id {} was not found", lotteryDialingId);
            messageDTOS.add(new MessageDTO(MessageType.ERROR, i18NService.getMessage("admin.lottery.dialing.error.id.not.found", String.valueOf(lotteryDialingId), locale)));
            return new ResponseEntity<Object>(messageDTOS, HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<Object>(localLotteryDialing, HttpStatus.OK);
    }

}


