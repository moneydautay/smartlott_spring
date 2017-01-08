package com.smartlott.backend.api;

import com.smartlott.backend.persistence.domain.backend.LotteryType;
import com.smartlott.backend.persistence.domain.backend.MessageDTO;
import com.smartlott.backend.service.I18NService;
import com.smartlott.backend.service.LotteryTypeService;
import com.smartlott.enums.MessageType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by greenlucky on 1/8/17.
 */
@RestController
@RequestMapping(LotteryTypeRestController.API_LOTTERY_TYPE_URL)
public class LotteryTypeRestController {

    /** The application logger */
    private static final Logger LOGGER = LoggerFactory.getLogger(LotteryTypeRestController.class);
    
    public static final String API_LOTTERY_TYPE_URL = "/api/lottery-type";

    @Autowired
    private LotteryTypeService lotteryTypeService;

    @Autowired
    private I18NService i18NService;

    /**
     * Retrieves all Lottery Type from database
     * @param locale
     * @return A list of Lottery Type or null if not exist
     * @exception Exception will be return if occurs errors
     * @see com.smartlott.backend.persistence.domain.backend.LotteryType
     */
    @RequestMapping(value = "" , method = RequestMethod.GET)
    public ResponseEntity<Object> getAllLotteryTypes(Locale locale) throws Exception{

        List<LotteryType> lotteryTypes = lotteryTypeService.getAll();

        return new ResponseEntity<Object>(lotteryTypes,HttpStatus.OK);
    }

    /**
     * Retrieves a LotteryType given by lotteryTypeId or null if not exist
     * @param lotteryTypeId
     * @param locale
     * @return A lotteryType or null if not exist
     */
    @RequestMapping(value = "/{lotteryTypeId}", method = RequestMethod.GET)
    public ResponseEntity<Object> getLotteryType(@PathVariable int lotteryTypeId, Locale locale){

        List<MessageDTO> messageDTOS = new ArrayList<>();

        LotteryType lotteryType = lotteryTypeService.getOne(lotteryTypeId);

        if(lotteryType == null){
            LOGGER.error("Lottery Type with id {} not exsit", lotteryTypeId);
            messageDTOS.add(new MessageDTO(MessageType.ERROR,i18NService.getMessage("lottery.type.error.id.not.found", String.valueOf(lotteryTypeId), locale)));
            return new ResponseEntity<Object>(messageDTOS, HttpStatus.OK);
        }

        return new ResponseEntity<Object>(lotteryType, HttpStatus.OK);
    }

    /**
     * Creates new Lottery Type given by LotteryType
     * This function createNewLotteryType will be call
     * @param lotteryType
     * @param locale
     * @exception Exception if occurs errors
     * @return A LotteryType or exception if occurs errors
     */
    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<Object> createNewLotteryType(@Valid @RequestBody LotteryType lotteryType, Locale locale) throws Exception{

        lotteryType = lotteryTypeService.create(lotteryType);

        return new ResponseEntity<Object>(lotteryType, HttpStatus.OK);
    }
}
