package com.smartlott.backend.api;

import com.smartlott.backend.persistence.domain.backend.InvestmentPackage;
import com.smartlott.backend.persistence.domain.backend.MessageDTO;
import com.smartlott.backend.service.I18NService;
import com.smartlott.backend.service.InvestmentPackageService;
import com.smartlott.enums.MessageType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
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
        InvestmentPackage investmentPackage = packageService.getOne(investmentPackageId);

        if (packageService == null){
            messageDTOS.add(new MessageDTO(MessageType.ERROR,
                    i18NService.getMessage("investment.package.id.error.not.found.text",
                            String.valueOf(investmentPackageId), locale)));
            return new ResponseEntity<Object>(messageDTOS, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<Object>(investmentPackage, HttpStatus.OK);

    }

}
