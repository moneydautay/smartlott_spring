package com.smartlott.web.controllers;

import com.smartlott.SmartlottApplication;
import com.smartlott.backend.persistence.domain.source.PerfectMoney;
import com.smartlott.backend.service.TransactionService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.*;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.Assert.*;

/**
 * Created by Mrs Hoang on 09/01/2017.
 */
@RunWith(MockitoJUnitRunner.class)
@WebMvcTest(CheckoutController.class)
public class CheckoutControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TransactionService transactionService;

    @Before
    public void before() throws Exception{
        Assert.assertNotNull(mockMvc);
        Assert.assertNotNull(transactionService);
    }

    @Test
    public void testPostCheckoutPerfectMoney() throws Exception{


    }

}