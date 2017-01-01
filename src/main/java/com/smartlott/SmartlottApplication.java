package com.smartlott;

import com.smartlott.backend.persistence.domain.backend.*;
import com.smartlott.backend.service.*;
import com.smartlott.enums.NotificationTypeEnum;
import com.smartlott.enums.RolesEnum;
import com.smartlott.utils.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

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

	@Autowired
	private AddressService addressService;

	@Autowired
	private NumberAccountTypeService numberAccountTypeService;

	@Autowired
	private NumberAccountService numberAccountService;

	@Autowired
	private NotificationTypeService notificationTypeService;

	@Autowired
	private NotificationService notificationService;

	public static void main(String[] args) {
		SpringApplication.run(SmartlottApplication.class, args);
	}

	@Override
	public void run(String...args) throws Exception{
		LOGGER.info("Creating user with role admin into database...");


		//Create country and province
		createCountry();

		Country country = countryService.findOne(2);
		Province province = provinceService.findOne(1);

		Set<Address> addresses = new HashSet<>();
		Address address = new Address();
		address.setAddress("521/64 Binh Duong 1");
		address.setCity("Di An");
		address.setState("");
		address.setProvince(province);

		//Create number account type
		CreateNumberAccountType();

		NumberAccountType bigCoin = numberAccountTypeService.getOne(1);
		NumberAccountType pm = numberAccountTypeService.getOne(1);

		List<Role> roles = new ArrayList<>();
		//createSecurityTokenForUsername role
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

		address.setUser(user);

		//Add address to user
		addressService.createAddress(address);

		createNotificationForNewUser(user);

		//createSecurityTokenForUsername slider
		createFeaturedSlider();
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

	public void CreateNumberAccountType(){
		//Create new account for BigCoin
		NumberAccountType bigCoin = NumberAccountUtils.createNewNumberAccountType("BigCoin","This is account of BigCoin");
		NumberAccountType pm = NumberAccountUtils.createNewNumberAccountType("Perfect Money","This is account of Perfect Money");

		numberAccountTypeService.create(bigCoin);
		numberAccountTypeService.create(pm);
	}

	public void createNotificationForNewUser(User user){
		//add notification type
		//create notification type;

		NotificationType type1 = new NotificationType(NotificationTypeEnum.General);
		NotificationType type2 = new NotificationType(NotificationTypeEnum.Pssword);
		NotificationType type3 = new NotificationType(NotificationTypeEnum.AddressValidate);
		NotificationType type4 = new NotificationType(NotificationTypeEnum.NumberAccount);
		NotificationType type5 = new NotificationType(NotificationTypeEnum.Orther);

		List<NotificationType> notificationTypes = new ArrayList<>();
		notificationTypes.add(type1);
		notificationTypes.add(type2);
		notificationTypes.add(type3);
		notificationTypes.add(type4);
		notificationTypes.add(type5);

		//adds notificationTypes
		notificationTypeService.creates(notificationTypes);

		//add notification after create new user;
		Notification notif1= new Notification();
		notif1.setContent("Welcome to Smartlott");
		notif1.setUser(user);
		notif1.setNotificationType(type5);

		notificationService.create(notif1);

		Notification notif2= new Notification();
		notif2.setContent("Please update your information before buy lottery");
		notif2.setUser(user);
		notif2.setNotificationType(type1);

		notificationService.create(notif2);

		Notification notif3= new Notification();
		notif3.setContent("Please upload your bill of bank or bill or electricity or water to varify your address");
		notif3.setUser(user);
		notif3.setNotificationType(type3);

		notificationService.create(notif3);

		Notification notif4= new Notification();
		notif4.setContent("Please add your number account of bank to withdraw you reward of lottery");
		notif4.setUser(user);
		notif4.setNotificationType(type4);
		notificationService.create(notif4);
	}

}
