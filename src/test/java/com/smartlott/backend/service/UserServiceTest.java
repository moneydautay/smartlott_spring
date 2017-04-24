package com.smartlott.backend.service;

import com.smartlott.backend.persistence.domain.backend.User;
import com.smartlott.enums.RolesEnum;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Arrays;
import java.util.List;

/**
 * Created by greenlucky on 3/31/17.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserServiceTest {

    private static  final List<String> USER_ROLES = Arrays.asList(
            RolesEnum.SEO.getName(),
            RolesEnum.MANAGER.getName(),
            RolesEnum.ADMIN.getName(),
            RolesEnum.STAFF.getName());

    @Autowired
    private UserService userService;

    @Test
    public void getUserByIds() throws Exception {

        List<Long> ids = Arrays.asList(Long.valueOf(1));

        List<User> userList = userService.getUserByIds(ids);

        Assert.assertEquals(1, userList.size());

    }

    @Test
    public void activeUser() throws Exception {
        User byUser = userService.findOne(1);
        int userNum = userService.active(1, byUser);
        Assert.assertEquals(1, userNum);
    }

    @Test
    public void changeStatus() throws Exception {
        User byUser = userService.findOne(1);
        int userNum = userService.changeStatus(false,1, byUser);
        Assert.assertEquals(1, userNum);
    }

    @Test
    public void getByRoles() throws Exception {

        PageRequest pageRequest = new PageRequest(0, 10);
        Page<User> users = userService.getByRoles(USER_ROLES, pageRequest);

        System.out.println(users.getContent());
    }

    @Test
    public void findOneByIdAndRoleNamesIn() throws Exception {

        User user = userService.findOneByIdAndRoleNamesIn(1, USER_ROLES);
        System.out.println(user.toString());
    }

}