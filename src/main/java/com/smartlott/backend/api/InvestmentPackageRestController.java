package com.smartlott.backend.api;

import com.smartlott.backend.persistence.domain.backend.*;
import com.smartlott.backend.service.I18NService;
import com.smartlott.backend.service.InvestmentPackageService;
import com.smartlott.backend.service.TransactionService;
import com.smartlott.enums.MessageType;
import com.smartlott.enums.TransactionStatusEnum;
import com.smartlott.enums.TransactionTypeEnum;
import com.smartlott.exceptions.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.ManagedList;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * Created by Mrs Hoang on 16/02/2017.
 */
@RestController
@RequestMapping(InvestmentPackageRestController.API_INVESTMENT_PACKAGE_URL)
public class InvestmentPackageRestController {

    /** The application logger */
    private static final Logger LOGGER = LoggerFactory.getLogger(InvestmentPackageRestController.class);

    public static final String API_INVESTMENT_PACKAGE_URL = "/api/investment-package";

    @Autowired
    private InvestmentPackageService packageService;

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private I18NService i18NService;

    private List<MessageDTO> messageDTOS = new ArrayList<>();

    @Transactional
    @PostMapping("")
    public ResponseEntity<Object> create(@RequestBody InvestmentPackage investmentPackage, Locale locale) {
        messageDTOS = new ManagedList<>();
        investmentPackage = packageService.create(investmentPackage);
        Map<String, Object> respone = new HashMap<>();
        messageDTOS.add(new MessageDTO(MessageType.SUCCESS,
                i18NService.getMessage("admin.investment.package.success.create.text", investmentPackage.getName(), locale)));
        respone.put("data", investmentPackage);
        respone.put("messages", messageDTOS);
        return new ResponseEntity<Object>(respone, HttpStatus.OK);
    }

    @Transactional
    @PutMapping("/{investmentPackageId}")
    public ResponseEntity<Object> update(@PathVariable int investmentPackageId,
                                         @RequestBody InvestmentPackage investmentPackage, Locale locale) {
        messageDTOS = new ManagedList<>();
        Optional<InvestmentPackage> localPackage = packageService.getOneOption(investmentPackageId);

        if(!localPackage.isPresent()) {
            MessageDTO messageDTO = new MessageDTO(MessageType.ERROR,
                    i18NService.getMessage("admin.investment.package.error.not.found.text", investmentPackage.getName(), locale));
            throw new NotFoundException(messageDTO);
        }

        investmentPackage = packageService.update(investmentPackage);
        Map<String, Object> respone = new HashMap<>();

        messageDTOS.add(new MessageDTO(MessageType.SUCCESS,
                i18NService.getMessage("admin.investment.package.success.update.text", investmentPackage.getName(), locale)));
        respone.put("data", investmentPackage);
        respone.put("messages", messageDTOS);

        return new ResponseEntity<Object>(respone, HttpStatus.OK);
    }

    @RequestMapping(value = "/{investmentPackageId}", method = RequestMethod.DELETE)
    public ResponseEntity<Object> delete(@PathVariable int investmentPackageId, Locale locale) {
        messageDTOS = new ManagedList<>();
        if(!packageService.exists(investmentPackageId)) {
            messageDTOS.add(new MessageDTO(MessageType.ERROR,
                    i18NService.getMessage("admin.investment.package.error.not.found.text",
                            String.valueOf(investmentPackageId) ,locale)));
            return new ResponseEntity<Object>(messageDTOS, HttpStatus.NOT_FOUND);
        }

        try {
            packageService.delete(investmentPackageId);
        }  catch (Exception ex) {
            LOGGER.error(ex.getMessage());
            messageDTOS.add(new MessageDTO(MessageType.ERROR, i18NService.getMessage("admin.investment.package.error.constrain.violation.text")));
            return new ResponseEntity<>(messageDTOS, HttpStatus.OK);
        }

        messageDTOS.add(new MessageDTO(MessageType.SUCCESS,
                i18NService.getMessage("admin.investment.package.success.delete.text",
                        String.valueOf(investmentPackageId) ,locale)));

        return new ResponseEntity<>(messageDTOS, HttpStatus.OK);
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public ResponseEntity<Object> getAll(Pageable pageable, Locale locale){
        Sort sort = new Sort(Sort.Direction.DESC, "price");
        if(pageable.getSort() != null)
            sort = pageable.getSort();
        PageRequest pageRequest = new PageRequest(
                pageable.getPageNumber(),
                pageable.getPageSize(),
                sort);

        Page<InvestmentPackage> packages = packageService.getAll(true, pageRequest);

        if (packages.getTotalElements() == 0){
            messageDTOS.add(new MessageDTO(MessageType.WARNING,
                    i18NService.getMessage("investment.package.error.not.found.text", locale)));
            return new ResponseEntity<Object>(messageDTOS, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<Object>(packages, HttpStatus.OK);
    }

    @RequestMapping(value = "/{investmentPackageId}", method = RequestMethod.GET)
    public ResponseEntity<Object> getInvestmentPackage(@PathVariable int investmentPackageId, Locale locale){
        messageDTOS = new ArrayList<>();
        Optional<InvestmentPackage> investmentPackage = packageService.getOneOption(investmentPackageId);

        if (!investmentPackage.isPresent()){
            messageDTOS.add(new MessageDTO(MessageType.ERROR,
                    i18NService.getMessage("investment.package.id.error.not.found.text",
                            String.valueOf(investmentPackageId), locale)));
            return new ResponseEntity<Object>(messageDTOS, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<Object>(investmentPackage.get(), HttpStatus.OK);
    }

    /**
     *
     * @param investmentPackageId
     * @param locale
     * @return
     */
    @RequestMapping(value = "/buy/{investmentPackageId}", method = RequestMethod.GET)
    public ResponseEntity<Object> buyingInvestmentPackage(@PathVariable int investmentPackageId , Locale locale){

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) auth.getPrincipal();

        InvestmentPackage investmentPackage = packageService.getOne(investmentPackageId);

        if(investmentPackage == null){
            messageDTOS.add(new MessageDTO(MessageType.ERROR,
                    i18NService.getMessage("investment.package.id.error.not.found.text",
                            String.valueOf(investmentPackageId), locale)));
            return new ResponseEntity<Object>(messageDTOS, HttpStatus.NOT_FOUND);
        }

        InvestmentPackage requiredPackage = user.getRequriedInvestmentPackage(investmentPackage.getParent());

        //checking user existed required package
        if (investmentPackage.getParent() != 0 && requiredPackage == null){
            requiredPackage = packageService.getOne(investmentPackage.getParent());

            messageDTOS.add(new MessageDTO(MessageType.ERROR,
                    i18NService.getMessage("investment.package.error.required.package",
                            requiredPackage.getName(), locale)));
            return new ResponseEntity<Object>(messageDTOS, HttpStatus.BAD_REQUEST);
        }

        //If current package has network level greater than package level buying
        if (user.getUserInvestment().getInvestmentPackage().getId() >= investmentPackage.getId()){
            messageDTOS.add(new MessageDTO(MessageType.ERROR,
                    i18NService.getMessage("investment.package.error.current.package.is.greater",
                            String.valueOf(user.getUserInvestment().getInvestmentPackage().getLevelNetwork()), locale)));
            return new ResponseEntity<Object>(messageDTOS, HttpStatus.BAD_REQUEST);
        }

        Transaction transaction = new Transaction();
        transaction.setOfUser(user);
        transaction.setAmount(investmentPackage.getPrice());
        transaction.setTransactionType(new TransactionType(TransactionTypeEnum.BuyInvestmentPackage));
        transaction.setTransactionStatus(new TransactionStatus(TransactionStatusEnum.PENDING));
        transaction.setInvestmentPackages(Arrays.asList(investmentPackage));

        transaction = transactionService.createNew(transaction);

        return new ResponseEntity<Object>(transaction, HttpStatus.OK);
    }

    /**
     * Change status given by id and status
     * @param investmentPackageId
     * @param status
     * @param locale
     * @return Message info success or message error if id not found or occurs error
     */
    @GetMapping("/change-status/{investmentPackageId}/{status}")
    public ResponseEntity<Object> activeInvestmentPackage(@PathVariable int investmentPackageId, @PathVariable boolean status, Locale locale) {
        messageDTOS = new ArrayList<>();
        Optional<InvestmentPackage> investmentPackage = packageService.getOneOption(investmentPackageId);

        if(!investmentPackage.isPresent()) {
            MessageDTO messageDTO = new MessageDTO(MessageType.ERROR,
                    i18NService.getMessage("admin.investment.package.error.not.found.text", String.valueOf(investmentPackageId), locale));
            throw new NotFoundException(messageDTO);
        }

        if(packageService.changeStatus(investmentPackageId, status) != 1) {
            messageDTOS.add(new MessageDTO(MessageType.ERROR,
                    i18NService.getMessage("admin.investment.package.error.change.status.text",
                            String.valueOf(investmentPackageId), locale)));
            return new ResponseEntity<Object>(messageDTOS, HttpStatus.OK);
        }

        messageDTOS.add(new MessageDTO(MessageType.SUCCESS,
                i18NService.getMessage("admin.investment.package.success.change.status.text",
                        String.valueOf(status), locale)));

        return new ResponseEntity<Object>(messageDTOS, HttpStatus.OK);
    }

}
