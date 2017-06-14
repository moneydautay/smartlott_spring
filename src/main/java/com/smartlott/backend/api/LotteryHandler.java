package com.smartlott.backend.api;

import com.smartlott.backend.persistence.domain.backend.*;
import com.smartlott.backend.persistence.domain.elastic.LotteryElastic;
import com.smartlott.backend.service.*;
import com.smartlott.backend.service.elasticsearch.LotteryElasticService;
import com.smartlott.enums.MessageType;
import com.smartlott.enums.TransactionStatusEnum;
import com.smartlott.utils.PageRequestUtils;
import com.smartlott.utils.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletRequest;
import java.time.Clock;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Created by greenlucky on 1/8/17.
 */
@RestController
@RequestMapping(LotteryHandler.API_LOTTERY_URL)
public class LotteryHandler {

    /**
     * The application logger
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(LotteryHandler.class);

    public static final String API_LOTTERY_URL = "/api/lottery";

    private List<MessageDTO> messageDTOS;

    @Autowired
    private LotteryDialingService dialingService;

    @Autowired
    private LotteryService lotteryService;

    @Autowired
    private UserService userService;

    @Autowired
    private TransactionTypeService transactionTypeService;

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private LotteryTypeService lotteryTypeService;

    @Autowired
    private LotteryDetailService lotteryDetailService;

    @Autowired
    private I18NService i18NService;

    @Autowired
    private LotteryElasticService lotteryElasticService;

    /**
     * Adds lotteries given by lotteries..
     *
     * @param lotteries
     * @param request
     * @param locale
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<Object> addLotteries(@RequestBody Lotteries lotteries, ServletRequest request, Locale locale) throws Exception {
        messageDTOS = new ArrayList<>();

        //checking lottery dialing is opening
        LotteryDialing lotteryDialing = dialingService.getOpenedLotteryDialing(true);
        if (lotteryDialing == null) {
            LOGGER.error("Lottery Dialing was closed");
            messageDTOS.add(new MessageDTO(MessageType.ERROR, i18NService.getMessage("lottery.dialing.error.closed.text", locale)));
            return new ResponseEntity<Object>(messageDTOS, HttpStatus.EXPECTATION_FAILED);
        }

        long userId = lotteries.getUserId();
        List<Lottery> localLottery = new ArrayList<>();
        Transaction transaction = null;
        if (userId != 0) {
            double totalPrice = 0;
            for (Lottery lottery : lotteries.getLotteries()) {
                LotteryType lotteryType = lotteryTypeService.getOne(lottery.getLotteryType().getId());
                totalPrice += lotteryType.getPrice();
                lottery.setLotteryType(lotteryType);
                localLottery.add(lottery);
            }


            User user = userService.findOne(lotteries.getUserId());

            TransactionType transactionType = transactionTypeService.getOne(2);
            TransactionStatus status = new TransactionStatus(TransactionStatusEnum.PENDING);


            transaction = new Transaction();
            transaction.setAmount(totalPrice);
            transaction.setCreatedDate(LocalDateTime.now(Clock.systemDefaultZone()));
            transaction.setTransactionType(transactionType);
            transaction.setOfUser(user);
            transaction.setTransactionStatus(status);

            transactionService.createNew(transaction);
            LOGGER.info("Created new transaction {} for user {}", transaction, user);

            String ipAddress = request.getRemoteAddr();

            //creates Lottery Details
            LotteryDetail lotteryDetail = lotteryDetailService.createNewLotteryDetails(transaction, ipAddress, lotteryDialing);
            LOGGER.info("Created Lottery detail {}", lotteryDetail);


            for (Lottery lottery : localLottery) {
                lottery.setLotteryDetail(lotteryDetail);
                lottery = lotteryService.createNewLottery(lottery);
                LOGGER.info("Created new lotteries {}", lottery);
            }
        }

        return new ResponseEntity<Object>(transaction, HttpStatus.OK);
    }


    @RequestMapping(value = "/ofuser/{userId}", method = RequestMethod.GET)
    public ResponseEntity<Object> getLotteriesByUser(@PathVariable("userId") long userId, Pageable pageable, Locale locale) {

        //checking user id exist
        User user = userService.findOne(userId);
        if (user == null) {
            LOGGER.error("User id {} was not found", userId);
            messageDTOS.add(new MessageDTO(MessageType.ERROR, i18NService.getMessage("Id.user.not.found", String.valueOf(userId), locale)));
            return new ResponseEntity<Object>(messageDTOS, HttpStatus.EXPECTATION_FAILED);
        }

        Page<LotteryDetail> lotteryDetails = lotteryDetailService.getLotteryDetailByUserId(userId, PageRequestUtils.createPageRequest(pageable));

        return new ResponseEntity<Object>(lotteryDetails, HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<Object> getAll(Pageable pageable) {
        Page<LotteryElastic> lotteries = lotteryElasticService.getAll(pageable);
        return new ResponseEntity<Object>(lotteries, HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<Object> search(@RequestParam("q") String query, Pageable pageable, Locale locale) {
        Page<LotteryElastic> lotteryElastics = lotteryElasticService.search(query, pageable);
        MessageDTO messageDTO = new MessageDTO(MessageType.SUCCESS,
                i18NService.getMessage("admin.lottery.search.result.found.text",
                        new Object[] {String.valueOf(lotteryElastics.getTotalElements()), query}, locale));
        Map<String, Object> response = ResponseUtil.Response(messageDTO, lotteryElastics);
        return new ResponseEntity<Object>(response, HttpStatus.OK);
    }


    @GetMapping("/current-dialing/ofuser/{userId}")
    public ResponseEntity<Object> getLotteriesCurrentDialing(@PathVariable long userId, Pageable pageable) {

        LotteryDialing currentDialing = dialingService.getOpenedLotteryDialing(true);

        Page<Lottery> lotteries = lotteryService.getByUserIdAndLotteryDialingId(userId, currentDialing.getId(), pageable);


        return new ResponseEntity<Object>(lotteries, HttpStatus.OK);
    }

    @GetMapping("/total")
    public ResponseEntity<Object> getTotalLottery() {
        long totalLottery = lotteryService.getTotalLottery();
        return new ResponseEntity<Object>(totalLottery, HttpStatus.OK);
    }

    @GetMapping("total-in-term")
    public ResponseEntity<Object> getTotalLotteryInTerm() {
        long termId = dialingService.getOpenedLotteryDialing(true).getId();
        long totalLottery = lotteryDetailService.getTotalLotteryInTerm(termId);
        return new ResponseEntity<Object>(totalLottery, HttpStatus.OK);
    }
}
