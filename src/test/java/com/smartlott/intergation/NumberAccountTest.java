package com.smartlott.intergation;

import com.smartlott.SmartlottApplication;
import com.smartlott.backend.persistence.domain.backend.NumberAccount;
import com.smartlott.backend.persistence.domain.backend.NumberAccountType;
import com.smartlott.backend.persistence.domain.backend.User;
import com.smartlott.backend.persistence.repositories.NumberAccountRepository;
import com.smartlott.backend.persistence.repositories.NumberAccountTypeRepository;
import com.smartlott.backend.persistence.repositories.UserRepository;
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
 * Created by Mrs Hoang on 17/12/2016.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(SmartlottApplication.class)
public class NumberAccountTest extends AbstractIntegrationTest{


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private NumberAccountTypeRepository numberAccountType;

    @Autowired
    private NumberAccountRepository numberAccount;


    @Rule
    public TestName testName = new TestName();

    @Before
    public void beforeTest() throws Exception{
        Assert.assertNotNull(numberAccountType);

        Assert.assertNotNull(numberAccount);
    }


    @Test
    public void createAccountTest() throws Exception{


        User user = createCustUser(testName);


        //user not null
        Assert.assertNotNull(user);

        //add account type
        NumberAccountType numberAccountType1 = new NumberAccountType("Perfect Money","");
        NumberAccountType numberAccountType2 = new NumberAccountType("BitCoin","");

        numberAccountType.save(numberAccountType1);
        numberAccountType.save(numberAccountType2);

        //add Account
        NumberAccount numberAccount1 = new NumberAccount("U9843934", user, numberAccountType1);
        numberAccount.save(numberAccount1);

        Assert.assertNotNull(numberAccount1);

        //add Account
        NumberAccount numberAccount2 = new NumberAccount("dundleboy", user, numberAccountType2);
        numberAccount.save(numberAccount2);

        Assert.assertNotNull(numberAccount2);

        //Find account number by User
        List<NumberAccount> numberAccounts = numberAccount.findByUser(user);

        Assert.assertNotNull(numberAccounts);

    }

}
