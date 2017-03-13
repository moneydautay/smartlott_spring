package com.smartlott.backend.service;

import com.smartlott.SmartlottApplication;
import com.smartlott.backend.persistence.domain.backend.IncomeComponent;
import com.smartlott.backend.persistence.domain.backend.User;
import com.smartlott.intergation.AbstractIntegrationTest;
import org.junit.*;
import org.junit.rules.TestName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by greenlucky on 1/12/17.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(SmartlottApplication.class)
public class IncomeComponentServiceTest extends AbstractIntegrationTest{


    @Autowired
    private IncomeComponentService incomeComponentService;

    @Autowired
    private UserService userService;

    private User user;

    @Rule
    public TestName testName = new TestName();

    @Before
    public void before() throws Exception{
        Assert.assertNotNull(incomeComponentService);
        Assert.assertNotNull(testName);
        user = userService.findOne(1);
    }

    @Test
    public void testCreateNew() throws  Exception{

        IncomeComponent incomeComponent = new IncomeComponent();
        incomeComponent.setName(testName.getMethodName());
        incomeComponent.setDescription("This is test create a income component");
        incomeComponent.setCreateBy(user);

        incomeComponent = incomeComponentService.create(incomeComponent);

        IncomeComponent actualIncomeComponent  = incomeComponentService.getOne(incomeComponent.getId());

        Assert.assertNotNull(actualIncomeComponent);

    }

    @Test
    public void testDelete() throws  Exception{

        IncomeComponent incomeComponent = new IncomeComponent();
        incomeComponent.setName(testName.getMethodName());
        incomeComponent.setDescription("This is test create a income component");
        incomeComponent.setCreateBy(user);

        incomeComponent = incomeComponentService.create(incomeComponent);

        Assert.assertNotNull(incomeComponent);

        //delete incomeComponent
        incomeComponentService.delete(incomeComponent.getId());

        IncomeComponent actualIncomeComponent = incomeComponentService.getOne(incomeComponent.getId());

        Assert.assertNull(actualIncomeComponent);
    }

    @Test
    public void testUpdate() throws  Exception{

        User modifiler = createCustUser(testName.getMethodName(),testName.getMethodName()+"@gmail.com");

        IncomeComponent incomeComponent = new IncomeComponent();
        incomeComponent.setName(testName.getMethodName());
        incomeComponent.setDescription("This is test create a income component");
        incomeComponent.setCreateBy(user);

        incomeComponent = incomeComponentService.create(incomeComponent);

        Assert.assertNotNull(incomeComponent);


        incomeComponent.setModifyBy(modifiler);

        //delete incomeComponent
        incomeComponent = incomeComponentService.update(incomeComponent);

        Assert.assertEquals("Modifiler must be equals", modifiler, incomeComponent.getModifyBy());
    }
}