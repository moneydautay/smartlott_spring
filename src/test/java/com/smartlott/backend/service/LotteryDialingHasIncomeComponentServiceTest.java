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
    private UserService userService;

    @Rule
    public TestName testName = new TestName();

    @Before
    public void before() throws Exception{
        Assert.assertNotNull(dialingInCompService);
        Assert.assertNotNull(testName);

        User user = userService.findOne(1);

        Assert.assertNotNull(user);

        LotteryDialing lotteryDialing = lotteryDialingService.getOpenedLotteryDialing(true);

        Assert.assertNotNull(lotteryDialing);

        IncomeComponent incomeComponent = createIncomeComponent(user, testName.getMethodName(), testName.getMethodName(), 47.7 ,true);

        //insert new dialing
        LotteryDialingHasIncomeComponent lotDialInComp1 = new LotteryDialingHasIncomeComponent();
        lotDialInComp1.setIncomeComponent(incomeComponent);
        lotDialInComp1.setLotteryDialing(lotteryDialing);
        lotDialInComp1.setValue(0.12);
        dialingInCompService.create(lotDialInComp1);

        //insert new dialing
        LotteryDialingHasIncomeComponent lotDialInComp2 = new LotteryDialingHasIncomeComponent();
        lotDialInComp2.setIncomeComponent(incomeComponent);
        lotDialInComp2.setLotteryDialing(lotteryDialing);
        lotDialInComp2.setValue(0.2);
        dialingInCompService.create(lotDialInComp2);
    }

    @Test
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
    }

    @Test
    public void saveIncomeForLotteryDialing() throws Exception{
        dialingInCompService.saveIncomeForLotteryDialing(1 ,1.0);
    }
}