package com.smartlott.intergation;

import com.smartlott.backend.service.PerfectMoneyService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

/**
 * Created by greenlucky on 12/25/16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PerfectMoneyTest {


    @Autowired
    private PerfectMoneyService perfectMoneyService;

    @Value("${perfectmoney.id}")
    private String id;

    @Value("${perfectmoney.passPhrase}")
    private String passPhrase;

    @Before
    public void before() throws Exception{
        Assert.notNull(id);
        Assert.notNull(passPhrase);
    }

    @Test
    public void verifyAccountTest() throws Exception{

       /* String account = perfectMoneyService.verifyAccount(id, passPhrase,"U13344806");
        //Assert.notNull(account);*/
    }

    @Test
    public void getHistoryTransactionTest() throws Exception{
       /* LocalDate starDate = LocalDate.of(2017,1,1);
        boolean result = perfectMoneyService.checkExistBatch(starDate, "161648305");

        System.out.println("Found batch [160099402] is: "+ result);*/
    }
}
