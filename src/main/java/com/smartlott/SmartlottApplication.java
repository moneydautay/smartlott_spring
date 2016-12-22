package com.smartlott;

import com.smartlott.backend.persistence.domain.backend.*;
import com.smartlott.backend.service.*;
import com.smartlott.enums.RolesEnum;
import com.smartlott.utils.CountryUtils;
import com.smartlott.utils.ProvinceUtils;
import com.smartlott.utils.SliderUtils;
import com.smartlott.utils.UserUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.xml.stream.events.ProcessingInstruction;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@SpringBootApplication
public class SmartlottApplication implements CommandLineRunner{
	
	/** The application logger */
	private static final Logger LOGGER = LoggerFactory.getLogger(SmartlottApplication.class);


	@Autowired
	private RoleService roleService;

	@Autowired
	private UserService userService;

	@Autowired
	private FeaturedSliderService featuredSliderService;

	@Autowired
	private FeaturedSliderImageService sliderImageService;


	@Autowired
	private CountryService countryService;

	@Autowired
	private ProvinceService provinceService;

	public static void main(String[] args) {
		SpringApplication.run(SmartlottApplication.class, args);
	}

	@Override
	public void run(String...args) throws Exception{
		LOGGER.info("Creating user with role admin into database...");


		List<Role> roles = new ArrayList<>();
		//create role
		Role custRole = new Role(RolesEnum.CUSTOMER);
		Role seoRole = new Role(RolesEnum.SEO);
		Role staffRole = new Role(RolesEnum.STAFF);
		Role editRole = new Role(RolesEnum.EDITOR);
		Role mantRole = new Role(RolesEnum.MANAGER);
		Role addRole = new Role(RolesEnum.ADMIN);

		roles.add(custRole);
		roles.add(seoRole);
		roles.add(staffRole);
		roles.add(editRole);
		roles.add(mantRole);
		roles.add(addRole);
		LOGGER.info("Create roles into database..");
		roleService.createRoles(roles);


		User user = UserUtils.createCustUser("admin","admin@gmail.com");
		user.setPassword("123456");

		Set<UserRole> userRoles = new HashSet<>();
		userRoles.add(new UserRole(addRole, user));
		userRoles.add(new UserRole(custRole, user));

		user.setUserRoles(userRoles);

		LOGGER.debug("Creating user with username {} and email {}", user.getUsername(), user.getEmail());
		user = userService.createUser(user);
		LOGGER.info("User {} has been created", user);

		//create slider
		createFeaturedSlider();

		//Create country and province
		createCountry();
	}

	public void createFeaturedSlider(){

		FeaturedSlider featuredSlider = SliderUtils.createFeaturedSlider("Homepage");
		LOGGER.debug("Creating Featured Slider {} ...", featuredSlider);
		featuredSliderService.save(featuredSlider);
		LOGGER.info("Created Featured Slider {} ", featuredSlider);

		FeaturedSliderImage featuredSliderImage1 = SliderUtils.createFeaturedSliderImage("Another example headline.","Cras justo odio, dapibus ac facilisis in, egestas eget quam. Donec id elit non mi porta gravida at eget metus. Nullam id dolor id nibh ultricies vehicula ut id elit.");
		featuredSliderImage1.setFeaturedSlider(featuredSlider);
		FeaturedSliderImage featuredSliderImage2 = SliderUtils.createFeaturedSliderImage("One more for good measure.","Cras justo odio, dapibus ac facilisis in, egestas eget quam. Donec id elit non mi porta gravida at eget metus. Nullam id dolor id nibh ultricies vehicula ut id elit.");
		featuredSliderImage2.setFeaturedSlider(featuredSlider);
		FeaturedSliderImage featuredSliderImage3 = SliderUtils.createFeaturedSliderImage("Example headline.","Cras justo odio, dapibus ac facilisis in, egestas eget quam. Donec id elit non mi porta gravida at eget metus. Nullam id dolor id nibh ultricies vehicula ut id elit.");
		featuredSliderImage3.setFeaturedSlider(featuredSlider);

		LOGGER.debug("Creating Featured Slider Images..");
		sliderImageService.save(featuredSliderImage1);
		sliderImageService.save(featuredSliderImage2);
		sliderImageService.save(featuredSliderImage3);
		LOGGER.info("Created Featured Slider Images");



	}


	public void createCountry(){

		//Create country
		Country country1 = CountryUtils.createCountry("United State","");
		countryService.createCountry(country1);
		LOGGER.info("Create a new country {}", country1);

		Country country2 = CountryUtils.createCountry("Vietnam","84");
		countryService.createCountry(country2);
		LOGGER.info("Create a new country {}", country2);

		//Create province
		Province province1 = ProvinceUtils.createProvice("Hồ Chí Minh", country2);
		provinceService.createProvince(province1);
		LOGGER.info("Create a new province {}", province1);

		Province province2 = ProvinceUtils.createProvice("Hà Nội", country2);
		provinceService.createProvince(province2);
		LOGGER.info("Create a new province {}", province2);
	}
}
