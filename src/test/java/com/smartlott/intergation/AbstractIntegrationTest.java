package com.smartlott.intergation;

import com.smartlott.SmartlottApplication;
import com.smartlott.backend.persistence.domain.backend.Password;
import com.smartlott.backend.persistence.domain.backend.Role;
import com.smartlott.backend.persistence.domain.backend.User;
import com.smartlott.backend.persistence.domain.backend.UserRole;
import com.smartlott.backend.persistence.repositories.PasswordRepository;
import com.smartlott.backend.persistence.repositories.RoleRepository;
import com.smartlott.backend.persistence.repositories.UserRepository;
import com.smartlott.enums.RolesEnum;
import com.smartlott.utils.UserUtils;
import org.junit.rules.TestName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Mrs Hoang on 12/15/2016.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(SmartlottApplication.class)
public abstract class AbstractIntegrationTest {

    @Autowired
    protected RoleRepository roleRepository;

    @Autowired
    protected UserRepository userRepository;


    @Autowired
    protected PasswordRepository passwordRepository;

    protected Role createCusRole(RolesEnum rolesEnum) {
        return new Role(rolesEnum);
    }

    protected User createCustUser(String username, String email){
        Role role = createCusRole(RolesEnum.CUSTOMER);
        roleRepository.save(role);

        User user = UserUtils.createCustUser(username, email);

        Set<UserRole> userRoles = new HashSet<>();
        UserRole userRole = new UserRole(role, user);

        Set<Password> userPasswords = new HashSet<>();
        userPasswords.add(new Password("0123456789", LocalDateTime.now(Clock.systemUTC()), user));

        user.getUserRoles().add(userRole);
        user.setPasswords(userPasswords);

        user = userRepository.save(user);
        return user;
    }

    public User createCustUser(TestName testName){
        return createCustUser(testName.getMethodName(), testName.getMethodName()+"@gmail.com");
    }
}
