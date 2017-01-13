package com.smartlott.backend.service;

import com.smartlott.SmartlottApplication;
import com.smartlott.backend.persistence.domain.backend.*;
import com.smartlott.intergation.AbstractIntegrationTest;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by greenlucky on 1/12/17.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(SmartlottApplication.class)
public class LotteryDialingHasIncomeComponentServiceTest extends AbstractIntegrationTest{

    @Autowired
    private LotteryDialingHasIncomeComponentService dialingInCompService;

    @Autowired
    private LotteryDialingService lotteryDialingService;


    @Autowired
    private IncomeComponentService incCompService;

    @Autowired
    private UserService userService;

    @Rule
    public TestName testName = new TestName();

    private IncomeComponent incomeComponent1;
    private IncomeComponent incomeComponent2;
    private IncomeComponent incomeComponent3;
    private IncomeComponent incomeComponent4;
    private IncomeComponent incomeComponent5;

    @Before
    public void before() throws Exception{
        Assert.assertNotNull(dialingInCompService);
        Assert.assertNotNull(testName);

        User user = userService.findOne(1);

        Assert.assertNotNull(user);

        LotteryDialing lotteryDialing = lotteryDialingService.getOpenedLotteryDialing(true);

        Assert.assertNotNull(lotteryDialing);

        incomeComponent1 = createIncomeComponent(user, testName.getMethodName()+"A", testName.getMethodName(), 47.7 ,true);
        incomeComponent2 = createIncomeComponent(user, testName.getMethodName()+"B", testName.getMethodName(), 20 ,true);
        incomeComponent3 = createIncomeComponent(user, testName.getMethodName()+"C", testName.getMethodName(), 10 ,true);
        incomeComponent4 = createIncomeComponent(user, testName.getMethodName()+"D", testName.getMethodName(), 10 ,true);
        incomeComponent5 = createIncomeComponent(user, testName.getMethodName()+"E", testName.getMethodName(), 12.3 ,true);


        //insert new dialing
        LotteryDialingHasIncomeComponent lotDialInComp1 = new LotteryDialingHasIncomeComponent();
        lotDialInComp1.setIncomeComponent(incomeComponent1);
        lotDialInComp1.setLotteryDialing(lotteryDialing);
        lotDialInComp1.setValue(0);
        dialingInCompService.create(lotDialInComp1);

        //insert new dialing
        LotteryDialingHasIncomeComponent lotDialInComp2 = new LotteryDialingHasIncomeComponent();
        lotDialInComp2.setIncomeComponent(incomeComponent2);
        lotDialInComp2.setLotteryDialing(lotteryDialing);
        lotDialInComp2.setValue(0);
        dialingInCompService.create(lotDialInComp2);

        //insert new dialing
        LotteryDialingHasIncomeComponent lotDialInComp3 = new LotteryDialingHasIncomeComponent();
        lotDialInComp3.setIncomeComponent(incomeComponent3);
        lotDialInComp3.setLotteryDialing(lotteryDialing);
        lotDialInComp3.setValue(0);
        dialingInCompService.create(lotDialInComp3);

        //insert new dialing
        LotteryDialingHasIncomeComponent lotDialInComp4 = new LotteryDialingHasIncomeComponent();
        lotDialInComp4.setIncomeComponent(incomeComponent4);
        lotDialInComp4.setLotteryDialing(lotteryDialing);
        lotDialInComp4.setValue(0);
        dialingInCompService.create(lotDialInComp4);
    }

   /* @Test
    public void getAll() throws Exception {
        PageRequest pageRequest = new PageRequest(0,15);
        Page<LotteryDialingHasIncomeComponent> lstResults = dialingInCompService.getAll(pageRequest);

        Assert.assertEquals("Size of results must be equals 2",2,lstResults.getContent().size());
    }

    @Test
    public void getByLotteryDialingId() throws Exception {
        PageRequest pageRequest = new PageRequest(0,15);
        Page<LotteryDialingHasIncomeComponent> lsResults = dialingInCompService.getByLotteryDialingId(1,pageRequest);
        System.out.println(lsResults);
        Assert.assertEquals("Size of results must be equals 6",2,lsResults.getContent().size());
    }

    @Test
    public void getByIncomeComponentId() throws Exception {
        PageRequest pageRequest = new PageRequest(0,15);
        Page<LotteryDialingHasIncomeComponent> lsResults = dialingInCompService.getByIncomeComponentId(1,pageRequest);
        Assert.assertEquals("Size of results must be equals 8",8,lsResults.getContent().size());
    }*/

    @Test
    public void saveIncomeForLotteryDialing() throws Exception{

        PageRequest pageRequest = new PageRequest(0,15);
        Page<LotteryDialingHasIncomeComponent> lstResults = dialingInCompService.getAll(pageRequest);

        Assert.assertEquals("Size of results must be equals 2",4,lstResults.getContent().size());

        dialingInCompService.saveIncomeForLotteryDialing(1 ,1.0);


        LotteryDialingHasIncomeComponent item1 = dialingInCompService.getOne(1);
        Assert.assertNotNull(item1);
        double aspectValue = 1*incomeComponent1.getValue()/100;
        System.out.println(aspectValue);
        System.out.println(item1.getValue());
        Assert.assertEquals(aspectValue, item1.getValue(),1);

        LotteryDialingHasIncomeComponent item2 = dialingInCompService.getOne(2);
        Assert.assertNotNull(item2);
        aspectValue = 1*incomeComponent2.getValue()/100;
        Assert.assertEquals(aspectValue, item2.getValue(),1);

        LotteryDialingHasIncomeComponent item3 = dialingInCompService.getOne(3);
        aspectValue = 1*incomeComponent3.getValue()/100;
        Assert.assertEquals(aspectValue, item3.getValue(),1);

        LotteryDialingHasIncomeComponent item4 = dialingInCompService.getOne(4);
        aspectValue = 1*incomeComponent4.getValue()/100;
        Assert.assertEquals(aspectValue, item4.getValue(),1);
    }

    @Test
    public void saveIncomeForLotteryDialingWithIncomeDetail() throws Exception{
        User user = userService.findOne(1);


        PageRequest pageRequest = new PageRequest(0,15);
        Page<LotteryDialingHasIncomeComponent> lstResults = dialingInCompService.getAll(pageRequest);

        Assert.assertEquals("Size of results must be equals 2",4,lstResults.getContent().size());
        double price = 2;
        dialingInCompService.saveIncomeForLotteryDialing(1 ,price);


        LotteryDialingHasIncomeComponent item1 = dialingInCompService.getOne(1);
        Assert.assertNotNull(item1);
        double aspectValue = price*incomeComponent1.getValue()/100;
        System.out.println(aspectValue);
        System.out.println(item1.getValue());
        Assert.assertEquals(aspectValue, item1.getValue(),1);

        LotteryDialingHasIncomeComponent item2 = dialingInCompService.getOne(2);
        Assert.assertNotNull(item2);
        aspectValue = price*incomeComponent2.getValue()/100;
        Assert.assertEquals(aspectValue, item2.getValue(),1);

        LotteryDialingHasIncomeComponent item3 = dialingInCompService.getOne(3);
        aspectValue = price*incomeComponent3.getValue()/100;
        Assert.assertEquals(aspectValue, item3.getValue(),1);

        LotteryDialingHasIncomeComponent item4 = dialingInCompService.getOne(4);
        aspectValue = price*incomeComponent4.getValue()/100;
        Assert.assertEquals(aspectValue, item4.getValue(),1);
    }
}