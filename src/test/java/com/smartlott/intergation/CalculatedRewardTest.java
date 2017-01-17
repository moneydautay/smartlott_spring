package com.smartlott.intergation;

import com.smartlott.SmartlottApplication;
import com.smartlott.backend.persistence.domain.backend.Lottery;
import com.smartlott.backend.persistence.domain.backend.LotteryDialing;
import com.smartlott.backend.persistence.domain.backend.LotteryDialingResult;
import com.smartlott.backend.persistence.domain.backend.LotteryType;
import com.smartlott.backend.service.LotteryDialingHasIncomeComponentService;
import com.smartlott.backend.service.LotteryDialingService;
import com.smartlott.backend.service.LotterySerivce;
import com.smartlott.enums.LotteryTypeEnum;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by Mrs Hoang on 17/01/2017.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(SmartlottApplication.class)
public class CalculatedRewardTest {

    @Autowired
    private LotteryDialingService dialingService;

    @Autowired
    private LotterySerivce lotterySerivce;

    @Autowired
    private LotteryDialingHasIncomeComponentService componentService;

    @Before
    public void before() throws Exception{
        Assert.assertNotNull(dialingService);
        Assert.assertNotNull(lotterySerivce);
        Assert.assertNotNull(componentService);
    }

    @Test
    public void test40Lottery() throws Exception{


        LotteryType lotteryType = new LotteryType(LotteryTypeEnum.TYPE1);



    }

}
