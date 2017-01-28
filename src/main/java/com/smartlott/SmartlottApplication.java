package com.smartlott;

import com.smartlott.backend.persistence.domain.backend.*;
import com.smartlott.backend.service.*;
import com.smartlott.enums.*;
import com.smartlott.utils.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.Clock;
import java.time.LocalDateTime;
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
	private NumberAccountTypeService numberAccountTypeService;

	@Autowired
	private NumberAccountService numberAccountService;

	@Autowired
	private NotificationTypeService notificationTypeService;


	@Autowired
	private TransactionTypeService transactionTypeService;

	@Autowired
	private BonusService bonusService;

	@Autowired
	private NetworkService networkService;

	@Autowired
	private LotteryDialingService dialingService;

	@Autowired
	private LotteryTypeService lotteryTypeService;

	@Autowired
	private TransactionStatusService transactionStatusService;

	@Autowired
	private IncomeComponentService componentService;

	@Autowired
	private RewardService rewardService;

	@Autowired
	private NetworkLevelService networkLevelService;

	@Autowired
	private LotteryDialingHasIncomeComponentService incomeComponentService;


	public static void main(String[] args) {
		SpringApplication.run(SmartlottApplication.class, args);
	}

	@Override
	public void run(String...args) throws Exception{
		LOGGER.info("Creating user with role admin into database...");

		//Create number account type
		CreateNumberAccountType();

		NumberAccountType bigCoin = numberAccountTypeService.getOne(1);
		NumberAccountType pm = numberAccountTypeService.getOne(2);

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
		user.setActived(true);
		user.setCash(1200);
		LOGGER.debug("Creating user with username {} and email {}", user.getUsername(), user.getEmail());
		user = userService.createUser(user);
		LOGGER.info("User {} has been created", user);


		//`createNotificationForNewUser(user);

		//createSecurityTokenForUsername slider
		createFeaturedSlider();

		//add number account
		NumberAccount numberAccount1 = new NumberAccount("greenlucky", user, bigCoin);
		NumberAccount numberAccount2 = new NumberAccount("U73273487", user, pm);
		numberAccountService.create(numberAccount1);
		numberAccountService.create(numberAccount2);

		//creat transaction type
		createTransactionType();

		//create transaction status
		createTransactionStatus();

		//open new lottery dialing
		createLotteryDialing();

		//create new lottery type;
		createLotteryType();

		//create new Income component and reward

		//createIncomeComponentAndRewardUtils(user);

		//create new Lottery dialing has income component
		createLotDialHasIncome();

		//createCusts(user);


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
		NumberAccountType bigCoin = new NumberAccountType(NumberAccountTypeEnum.BitCoin);
		NumberAccountType pm = new NumberAccountType(NumberAccountTypeEnum.PerfectMoney);
		NumberAccountType cash = new NumberAccountType(NumberAccountTypeEnum.Cash);

		numberAccountTypeService.create(bigCoin);
		numberAccountTypeService.create(pm);
		numberAccountTypeService.create(cash);
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

		/*//add notification after create new user;
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
		notificationService.create(notif4);*/
	}

	public void createTransactionType(){
		TransactionType type1= new TransactionType(TransactionTypeEnum.Withdraw);
		TransactionType type2= new TransactionType(TransactionTypeEnum.BuyingLottery);

		transactionTypeService.createNew(type1);
		transactionTypeService.createNew(type2);
	}

	public void createBonusForUser(User user){
		for(int i = 0; i < 25 ; i++){
			Bonus bonus = new Bonus(0.1, user, user, LocalDateTime.now(Clock.systemUTC()).plusMinutes(i), 1);
			bonusService.createNew(bonus);
		}
	}

	public void createCusts(User user){

		List<Network> networks = new ArrayList<>();

		Role custRole = new Role(RolesEnum.CUSTOMER);

		String name = "customerA";
		User localUserA = UserUtils.createCustUser(name, name + "@smartlott.com");
		localUserA.setPassword("123456");

		Set<UserRole> userRoles = new HashSet<>();
		userRoles.add(new UserRole(custRole, localUserA));

		localUserA.setUserRoles(userRoles);
		localUserA.setActived(true);
		localUserA.setCash(100);
		localUserA.setIntroducedBy(user);

		LOGGER.debug("Creating user with username {} and email {}", localUserA.getUsername(), localUserA.getEmail());
		localUserA = userService.createUser(localUserA);
		LOGGER.info("User {} has been created", localUserA);

		NetworkLevel level1 = networkLevelService.getOne(1);

		//add network
		networks.add(new Network(localUserA, user,level1));

		name = "customerB";
		User localUserB = UserUtils.createCustUser(name, name + "@smartlott.com");
		localUserB.setPassword("123456");
		userRoles = new HashSet<>();
		userRoles.add(new UserRole(custRole, localUserB));
		localUserB.setUserRoles(userRoles);
		localUserB.setActived(true);
		localUserB.setCash(100);
		localUserB.setIntroducedBy(user);

		LOGGER.debug("Creating user with username {} and email {}", localUserB.getUsername(), localUserB.getEmail());
		localUserB = userService.createUser(localUserB);
		LOGGER.info("User {} has been created", localUserB);

		//add network
		networks.add(new Network(localUserB, user,level1));

		for(int i = 0; i < 5; i++) {
			name = "customer" + i;
			User localUser = UserUtils.createCustUser(name, name + "@smartlott.com");
			localUser.setPassword("123456");
			userRoles = new HashSet<>();
			userRoles.add(new UserRole(custRole, localUser));
			localUser.setUserRoles(userRoles);
			localUser.setActived(true);
			localUser.setCash(100);
			localUser.setIntroducedBy(localUserA);

			LOGGER.debug("Creating user with username {} and email {}", localUser.getUsername(), localUser.getEmail());
			localUser = userService.createUser(localUser);
			LOGGER.info("User {} has been created", localUser);

			//add network
			networks.add(new Network(localUser, localUserA,level1));
			networks.addAll(networkService.findAncestor(localUser, localUserA,1,2));
		}

		for(int i = 6; i < 11; i++) {
			name = "customer" + i;
			User localUser = UserUtils.createCustUser(name, name + "@smartlott.com");
			localUser.setPassword("123456");
			userRoles = new HashSet<>();
			userRoles.add(new UserRole(custRole, localUser));
			localUser.setUserRoles(userRoles);
			localUser.setActived(true);
			localUser.setCash(100);
			localUser.setIntroducedBy(localUserB);

			LOGGER.debug("Creating user with username {} and email {}", localUser.getUsername(), localUser.getEmail());
			localUser = userService.createUser(localUser);
			LOGGER.info("User {} has been created", localUser);

			//add network
			networks.add(new Network(localUser, localUserB,level1));
			networks.addAll(networkService.findAncestor(localUser, localUserB,1,2));
		}
		networkService.createNetworks(networks);
	}

	public void createLotteryDialing() throws Exception{
		LotteryDialing lotteryDialing = new LotteryDialing();
		lotteryDialing.setFromDate(LocalDateTime.now(Clock.systemDefaultZone()).plusMinutes(10));
		lotteryDialing.setToDate(LocalDateTime.now(Clock.systemDefaultZone()).plusDays(2));
		dialingService.create(lotteryDialing);
	}

	public void createLotteryType() throws Exception{
		LotteryType lotteryType1 = new LotteryType(LotteryTypeEnum.TYPE1);
		lotteryTypeService.create(lotteryType1);

		LOGGER.info("Created lottery type {}", lotteryType1);

		LotteryType lotteryType2 = new LotteryType(LotteryTypeEnum.TYPE2);
		lotteryTypeService.create(lotteryType2);

		LOGGER.info("Created lottery type {}", lotteryType2);
	}


	public void createTransactionStatus(){
		TransactionStatus status1 = new TransactionStatus(TransactionStatusEnum.PENDING);
		TransactionStatus status2 = new TransactionStatus(TransactionStatusEnum.SUCCESS);
		TransactionStatus status3 = new TransactionStatus(TransactionStatusEnum.CANCEL);
		transactionStatusService.create(status1);
		transactionStatusService.create(status2);
		transactionStatusService.create(status3);
	}

	public void createIncomeComponentAndRewardUtils(User user){
		IncomeComponent incomeComponent1 = new IncomeComponent();
		incomeComponent1.setName("Hoa hồng bán hàng F1");
		incomeComponent1.setValue(20);
		incomeComponent1.setCreateBy(user);
		incomeComponent1.setEnabled(true);
		incomeComponent1.setDescription("Hoa hồng bán hàng F1");
		incomeComponent1 = componentService.create(incomeComponent1);

		IncomeComponent incomeComponent2 = new IncomeComponent();
		incomeComponent2.setName("Hoa hồng bán hàng F2");
		incomeComponent2.setValue(5);
		incomeComponent2.setCreateBy(user);
		incomeComponent2.setEnabled(true);
		incomeComponent2.setDescription("Hoa hồng bán hàng F2");
		incomeComponent2 = componentService.create(incomeComponent2);

		IncomeComponent incomeComponent3 = new IncomeComponent();
		incomeComponent3.setJackpots(true);
		incomeComponent3.setName("Hoa hồng năng động");
		incomeComponent3.setValue(10);
		incomeComponent3.setCreateBy(user);
		incomeComponent3.setEnabled(true);
		incomeComponent3.setDescription("Hoa hồng năng động");
		incomeComponent3 = componentService.create(incomeComponent3);

		IncomeComponent incomeComponent4 = new IncomeComponent();
		incomeComponent4.setJackpots(true);
		incomeComponent4.setName("Giải đặc biệt");
		incomeComponent4.setValue(48);
		incomeComponent4.setCreateBy(user);
		incomeComponent4.setEnabled(true);
		incomeComponent4.setDescription("D");
		incomeComponent4 = componentService.create(incomeComponent4);

		IncomeComponent incomeComponent5 = new IncomeComponent();
		incomeComponent5.setName("Giải nhất");
		incomeComponent5.setValue(2);
		incomeComponent5.setCreateBy(user);
		incomeComponent5.setEnabled(true);
		incomeComponent5.setDescription("Giải nhất");
		incomeComponent5 = componentService.create(incomeComponent5);

		IncomeComponent incomeComponent6 = new IncomeComponent();
		incomeComponent6.setName("Giải nhì");
		incomeComponent6.setValue(2);
		incomeComponent6.setCreateBy(user);
		incomeComponent6.setEnabled(true);
		incomeComponent6.setDescription("Giải nhì");
		incomeComponent6 = componentService.create(incomeComponent6);

		IncomeComponent incomeComponent7 = new IncomeComponent();
		incomeComponent7.setName("Giải ba");
		incomeComponent7.setValue(3);
		incomeComponent7.setCreateBy(user);
		incomeComponent7.setEnabled(true);
		incomeComponent7.setDescription("Giải ba");
		incomeComponent7 = componentService.create(incomeComponent7);

		IncomeComponent incomeComponent8 = new IncomeComponent();
		incomeComponent8.setName("Giải tư");
		incomeComponent8.setValue(5);
		incomeComponent8.setCreateBy(user);
		incomeComponent8.setEnabled(true);
		incomeComponent8.setDescription("Giải tư");
		incomeComponent8 = componentService.create(incomeComponent8);


		IncomeComponent incomeComponent9 = new IncomeComponent();
		incomeComponent9.setName("Quản lý web, server");
		incomeComponent9.setValue(5);
		incomeComponent9.setCreateBy(user);
		incomeComponent9.setEnabled(true);
		incomeComponent9.setDescription("Quản lý web, server");
		incomeComponent9 = componentService.create(incomeComponent9);


		//create reward
		Reward jeckpots = new Reward();
		jeckpots.setName("Jeckpots");
		jeckpots.setValue(1000000);
		jeckpots.setIncomeComponent(incomeComponent4);
		jeckpots.setDefaultNumericReward(0);
		jeckpots.setCoupleNumber(6);
		rewardService.create(jeckpots);

		Reward firstReward = new Reward();
		firstReward.setName("firstReward");
		firstReward.setValue(1500);
		firstReward.setIncomeComponent(incomeComponent5);
		firstReward.setDefaultNumericReward(0);
		firstReward.setCoupleNumber(5);
		rewardService.create(firstReward);

		Reward secondReward = new Reward();
		secondReward.setName("secondReward");
		secondReward.setValue(120);
		secondReward.setIncomeComponent(incomeComponent6);
		secondReward.setDefaultNumericReward(0);
		secondReward.setCoupleNumber(4);
		rewardService.create(secondReward);

		Reward thirdReward = new Reward();
		thirdReward.setName("thirdReward");
		thirdReward.setValue(20);
		thirdReward.setIncomeComponent(incomeComponent7);
		thirdReward.setDefaultNumericReward(10);
		thirdReward.setCoupleNumber(3);
		rewardService.create(thirdReward);
		Reward fourReward = new Reward();
		fourReward.setName("fourReward");
		fourReward.setValue(5);
		firstReward.setDefaultNumericReward(10);
		fourReward.setIncomeComponent(incomeComponent8);
		fourReward.setCoupleNumber(2);
		rewardService.create(fourReward);

		//create level of network
		NetworkLevel level1 = new NetworkLevel(1, "Level 1", "", incomeComponent1);
		NetworkLevel level2 = new NetworkLevel(2, "Level 2", "", incomeComponent2);

		level1 = networkLevelService.create(level1);
		LOGGER.info("Created network level: {}", level1);

		level2 = networkLevelService.create(level2);
		LOGGER.info("Created network level: {}", level2);

	}

	public void createLotDialHasIncome(){

		//get current lottery dialing is opening
		LotteryDialing lotteryDialing = dialingService.getOpenedLotteryDialing(true);

		List<IncomeComponent> lstInc = componentService.getAll();

		for (IncomeComponent item : lstInc){
			LotteryDialingHasIncomeComponent loC = new LotteryDialingHasIncomeComponent();
			loC.setValue(0);
			loC.setIncomeComponent(item);
			loC.setLotteryDialing(lotteryDialing);
			incomeComponentService.create(loC);
		}
	}


}
