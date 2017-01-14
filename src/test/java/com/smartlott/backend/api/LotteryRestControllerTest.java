package com.smartlott.backend.api;

import com.smartlott.backend.service.LotterySerivce;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

/**
 * Created by greenlucky on 1/14/17.
 */
@RunWith(SpringRunner.class)
@WebMvcTest(LotteryRestController.class)
public class LotteryRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LotterySerivce lotterySerivce;

    @Test
    public void testGetLotteryByUser() throws Exception{
      /*  PageRequest pageRequest = new PageRequest(0,15,new Sort(Sort.Direction.DESC,"id"));

        Page<Lottery> aspectLottery = lotterySerivce.getLotteriesByUser(1, pageRequest);
        this.mockMvc.perform(get("/api/lottery/ofuser/1").accept(MediaType.TEXT_PLAIN))
                .andExpect(status().isOk()).andExpect(content().contentType(new ResponseEntity<Object>(aspectLottery, HttpStatus.OK).toString()));*/
    }
}