package com.smartlott.backend.api;

import com.smartlott.backend.persistence.domain.backend.*;
import com.smartlott.backend.service.I18NService;
import com.smartlott.backend.service.InvestmentPackageService;
import com.smartlott.backend.service.TransactionService;
import com.smartlott.backend.service.UserService;
import com.smartlott.enums.MessageType;
import com.smartlott.enums.TransactionStatusEnum;
import com.smartlott.enums.TransactionTypeEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

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
    private UserService userService;

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private I18NService i18NService;

    private List<MessageDTO> messageDTOS = new ArrayList<>();

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public ResponseEntity<Object> getAll(Pageable pageable, Locale locale){
        PageRequest pageRequest = new PageRequest(
                pageable.getPageNumber(),
                pageable.getPageSize(),
                new Sort(Sort.Direction.DESC, "price"));

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
        InvestmentPackage investmentPackage = packageService.getOne(investmentPackageId);

        if (packageService == null){
            messageDTOS.add(new MessageDTO(MessageType.ERROR,
                    i18NService.getMessage("investment.package.id.error.not.found.text",
                            String.valueOf(investmentPackageId), locale)));
            return new ResponseEntity<Object>(messageDTOS, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<Object>(investmentPackage, HttpStatus.OK);
    }

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

}
