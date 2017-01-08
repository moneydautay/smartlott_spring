package com.smartlott.backend.service;

import com.smartlott.SmartlottApplication;
import com.smartlott.backend.persistence.domain.backend.LotteryType;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * Created by greenlucky on 1/8/17.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(SmartlottApplication.class)
public class LotteryTypeServiceTest {

    @Autowired
    private LotteryTypeService lotteryTypeService;

    @Rule
    public TestName testName = new TestName();

    @Before
    public void before() throws Exception{
        Assert.assertNotNull(lotteryTypeService);
        Assert.assertNotNull(testName);
    }

    @Test
    public void test() throws Exception{

        //create lottery type
        LotteryType lotteryType1 = new LotteryType();
        lotteryType1.setName(testName.getMethodName());
        lotteryType1.setDescription(testName.getMethodName());

        LotteryType lotteryType2 = new LotteryType();
        lotteryType2.setName(testName.getMethodName());
        lotteryType2.setDescription(testName.getMethodName());

        lotteryTypeService.create(lotteryType1);
        lotteryTypeService.create(lotteryType2);


        //find all lottery type
        List<LotteryType> lotteryTypeList = lotteryTypeService.getAll();

        Assert.assertEquals("Lottery type must be equals 2", 2, lotteryTypeList.size());

        //find lottery service
        LotteryType result = lotteryTypeService.getOne(lotteryType1.getId());

        Assert.assertEquals("Lottery type must be true", lotteryType1, result);

        //delete lottery type
        lotteryTypeService.delete(lotteryType1.getId());

        LotteryType result1 = lotteryTypeService.getOne(lotteryType1.getId());

        Assert.assertNull(result1);
    }
}
