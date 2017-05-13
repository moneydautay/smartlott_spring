package com.smartlott.intergation;

import com.smartlott.backend.persistence.domain.backend.*;
import com.smartlott.backend.persistence.repositories.*;
import com.smartlott.enums.RolesEnum;
import com.smartlott.utils.UserUtils;
import org.junit.rules.TestName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

;

/**
 * Created by Mrs Hoang on 12/15/2016.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public abstract class ITAbstractIntegrationTest {

    @Autowired
    protected RoleRepository roleRepository;

    @Autowired
    protected UserRepository userRepository;

    @Autowired
    protected PasswordRepository passwordRepository;

    @Autowired
    protected CountryRepository countryRepository;

    @Autowired
    protected ProvinceRepository provinceRepository;

    @Autowired
    protected AddressRepository addressRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private IncomeComponentRepository incomeComponentRepository;



    protected Role createCusRole(RolesEnum rolesEnum) {
        return new Role(rolesEnum);
    }

    protected User createCustUser(String username, String email){
        Role role = createCusRole(RolesEnum.CUSTOMER);
        roleRepository.save(role);

        User user = UserUtils.createCustUser(username, email);

        Set<Role> userRoles = new HashSet<>();
        userRoles.add(new Role(RolesEnum.CUSTOMER));

        String encodedPassword = passwordEncoder.encode("0123456789");
        Password password = new Password(encodedPassword, LocalDateTime.now(Clock.systemUTC()), user);
        Set<Password> passwords = new HashSet<>();
        passwords.add(password);

        user.setRoles(userRoles);
        user.setPassword(encodedPassword);
        user.setPasswords(passwords);
        user = userRepository.save(user);


        return user;
    }

    public User createCustUser(TestName testName){
        return createCustUser(testName.getMethodName(), testName.getMethodName()+"@gmail.com");
    }



    protected Address createAddress(String address, String city, String state){

        Country country1 = new Country("United State","100");
        Country country2 = new Country("United Kingdom","101");
        Country country3 = new Country("Viet Name","102");

        countryRepository.save(country1);
        countryRepository.save(country2);
        countryRepository.save(country3);

        Province province1 = new Province("New Youk", country1);
        Province province2 = new Province("Texas", country1);

        Province province3 = new Province("London", country2);
        Province province4 = new Province("Manchester", country2);

        Province province5 = new Province("Ha Noi", country3);
        Province province6 = new Province("Ho Chi Minh", country3);

        provinceRepository.save(province1);
        provinceRepository.save(province2);
        provinceRepository.save(province3);
        provinceRepository.save(province4);
        provinceRepository.save(province5);
        provinceRepository.save(province6);

        Address address1 = new Address();
        address1.setAddress(address);
        address1.setCity(city);
        address1.setState(state);
        address1.setProvince(province6);

        addressRepository.save(address1);
        return  address1;
    }

    public Address createAddress(TestName testName){
        return createAddress(testName.getMethodName(), testName.getMethodName(), testName.getMethodName());
    }

    public IncomeComponent createIncomeComponent(User user, String name, String description, double value){
        IncomeComponent incomeComponent = new IncomeComponent();
        incomeComponent.setName(name);
        incomeComponent.setDescription(description);
        incomeComponent.setValue(value);
        return incomeComponentRepository.save(incomeComponent);
    }
}
