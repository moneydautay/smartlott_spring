package com.smartlott.intergation;

import com.smartlott.SmartlottApplication;
import com.smartlott.backend.persistence.domain.backend.Network;
import com.smartlott.backend.persistence.domain.backend.User;
import com.smartlott.backend.persistence.repositories.UserRepository;
import com.smartlott.backend.service.NetworkService;
import com.smartlott.backend.service.UserService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * Created by Mrs Hoang on 04/01/2017.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(SmartlottApplication.class)
public class NetworkTest {

    @Autowired
    private NetworkService networkService;

    @Autowired
    private UserRepository userRepository;

    @Before
    public void before() throws Exception{
        Assert.assertNotNull(networkService);
    }

    @Test
    public void getAccestor() throws Exception{

        List<Network> networks = networkService.getByOfUserId(4);
        for(Network network : networks)
            System.out.println(network.getNetworkLevel().getIncomeComponent().getValue());

    }
}
