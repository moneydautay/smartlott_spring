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
import java.util.*;


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

	@Autowired
	private InvestmentPackageService packageService;

	@Autowired
	private CashService cashService;


	public static void main(String[] args) {
		SpringApplication.run(SmartlottApplication.class, args);
	}

	@Override
	public void run(String...args) throws Exception{
		LOGGER.info("Creating user with role admin into database...");

		//Create number account type
		CreateNumberAccountType();

		//add default package
		addInvestmentPackage();

		//add cash
		createCash();

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

		/*InvestmentPackage agentPackage = new InvestmentPackage(InvestmentPackageEnum.AGENT);
		userService.addInvestmentPackage(user.getId(), agentPackage, LocalDateTime.now(Clock.systemDefaultZone()));*/

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

		createIncomeComponentAndRewardUtils(user);

		//create new Lottery dialing has income component
		createLotDialHasIncome();

		createCusts(user);

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
		TransactionType type3= new TransactionType(TransactionTypeEnum.BuyInvestmentPackage);

		transactionTypeService.createNew(type1);
		transactionTypeService.createNew(type2);
		transactionTypeService.createNew(type3);
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

		Set<UserRole> userRoles = new HashSet<>();
		for(int i = 1; i <= 8; i++) {

			User accestor = null;

			NetworkLevel level = networkLevelService.getOne(1);
			String name = "customer" + i;
			User localUser = UserUtils.createCustUser(name, name + "@smartlott.com");
			localUser.setPassword("123456");
			userRoles = new HashSet<>();
			userRoles.add(new UserRole(custRole, localUser));
			localUser.setUserRoles(userRoles);
			localUser.setActived(true);
			localUser.setCash(100);

			if(i == 1) {
				accestor = user;
				localUser.setIntroducedBy(user);
			}
			else{
				accestor = userService.findOne((i));
				localUser.setIntroducedBy(accestor);
			}

			LOGGER.info("Creating user with username {} and email {}", localUser.getUsername(), localUser.getEmail());
			localUser = userService.createUser(localUser);
			LOGGER.info("User {} has been created", localUser);

			networks.addAll(networkService.findAncestor(localUser ,7, 1));
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
		incomeComponent2.setValue(2);
		incomeComponent2.setCreateBy(user);
		incomeComponent2.setEnabled(true);
		incomeComponent2.setDescription("Hoa hồng bán hàng F2");
		incomeComponent2 = componentService.create(incomeComponent2);

		IncomeComponent incomeComponent3 = new IncomeComponent();
		incomeComponent3.setJackpots(true);
		incomeComponent3.setName("Hoa hồng bán hàng F3");
		incomeComponent3.setValue(2);
		incomeComponent3.setCreateBy(user);
		incomeComponent3.setEnabled(true);
		incomeComponent3.setDescription("Hoa hồng bán hàng F2");
		incomeComponent3 = componentService.create(incomeComponent3);

		IncomeComponent incomeComponent4 = new IncomeComponent();
		incomeComponent4.setJackpots(true);
		incomeComponent4.setName("Hoa hồng bán hàng F4");
		incomeComponent4.setValue(2);
		incomeComponent4.setCreateBy(user);
		incomeComponent4.setEnabled(true);
		incomeComponent4.setDescription("Hoa hồng bán hàng F4");
		incomeComponent4 = componentService.create(incomeComponent4);

		IncomeComponent incomeComponent5 = new IncomeComponent();
		incomeComponent5.setJackpots(true);
		incomeComponent5.setName("Hoa hồng bán hàng F5");
		incomeComponent5.setValue(2);
		incomeComponent5.setCreateBy(user);
		incomeComponent5.setEnabled(true);
		incomeComponent5.setDescription("Hoa hồng bán hàng F5");
		incomeComponent5 = componentService.create(incomeComponent5);

		IncomeComponent incomeComponent6 = new IncomeComponent();
		incomeComponent6.setJackpots(true);
		incomeComponent6.setName("Hoa hồng bán hàng F6");
		incomeComponent6.setValue(2);
		incomeComponent6.setCreateBy(user);
		incomeComponent6.setEnabled(true);
		incomeComponent6.setDescription("Hoa hồng bán hàng F6");
		incomeComponent6 = componentService.create(incomeComponent6);

		IncomeComponent incomeComponent7 = new IncomeComponent();
		incomeComponent7.setJackpots(true);
		incomeComponent7.setName("Hoa hồng bán hàng F7");
		incomeComponent7.setValue(2);
		incomeComponent7.setCreateBy(user);
		incomeComponent7.setEnabled(true);
		incomeComponent7.setDescription("Hoa hồng bán hàng F7");
		incomeComponent7 = componentService.create(incomeComponent7);


		IncomeComponent ja = new IncomeComponent();
		ja.setJackpots(true);
		ja.setName("Giải đặc biệt");
		ja.setValue(46);
		ja.setCreateBy(user);
		ja.setEnabled(true);
		ja.setDescription("D");
		ja = componentService.create(ja);

		IncomeComponent oneth = new IncomeComponent();
		oneth.setName("Giải nhất");
		oneth.setValue(3);
		oneth.setCreateBy(user);
		oneth.setEnabled(true);
		oneth.setDescription("Giải nhất");
		oneth = componentService.create(oneth);

		IncomeComponent second = new IncomeComponent();
		second.setName("Giải nhì");
		second.setValue(3);
		second.setCreateBy(user);
		second.setEnabled(true);
		second.setDescription("Giải nhì");
		second = componentService.create(second);

		IncomeComponent third = new IncomeComponent();
		third.setName("Giải ba");
		third.setValue(4);
		third.setCreateBy(user);
		third.setEnabled(true);
		third.setDescription("Giải ba");
		third = componentService.create(third);

		IncomeComponent four = new IncomeComponent();
		four.setName("Giải tư");
		four.setValue(4);
		four.setCreateBy(user);
		four.setEnabled(true);
		four.setDescription("Giải tư");
		four = componentService.create(four);

		IncomeComponent five = new IncomeComponent();
		five.setName("Quản lý web, server");
		five.setValue(5);
		five.setCreateBy(user);
		five.setEnabled(true);
		five.setDescription("Quản lý web, server");
		five = componentService.create(five);


		//create reward
		Reward jeckpots = new Reward();
		jeckpots.setName("Jeckpots");
		jeckpots.setValue(1000000);
		jeckpots.setIncomeComponent(ja);
		jeckpots.setDefaultNumericReward(0);
		jeckpots.setCoupleNumber(6);
		rewardService.create(jeckpots);

		Reward firstReward = new Reward();
		firstReward.setName("firstReward");
		firstReward.setValue(1500);
		firstReward.setIncomeComponent(oneth);
		firstReward.setDefaultNumericReward(0);
		firstReward.setCoupleNumber(5);
		rewardService.create(firstReward);

		Reward secondReward = new Reward();
		secondReward.setName("secondReward");
		secondReward.setValue(120);
		secondReward.setIncomeComponent(second);
		secondReward.setDefaultNumericReward(0);
		secondReward.setCoupleNumber(4);
		rewardService.create(secondReward);

		Reward thirdReward = new Reward();
		thirdReward.setName("thirdReward");
		thirdReward.setValue(20);
		thirdReward.setIncomeComponent(third);
		thirdReward.setDefaultNumericReward(10);
		thirdReward.setCoupleNumber(3);
		rewardService.create(thirdReward);
		Reward fourReward = new Reward();
		fourReward.setName("fourReward");
		fourReward.setValue(5);
		firstReward.setDefaultNumericReward(10);
		fourReward.setIncomeComponent(five);
		fourReward.setCoupleNumber(2);
		rewardService.create(fourReward);

		//create level of network
		NetworkLevel level1 = new NetworkLevel(1, "Level 1", "", incomeComponent1);
		NetworkLevel level2 = new NetworkLevel(2, "Level 2", "", incomeComponent2);
		NetworkLevel level3 = new NetworkLevel(3, "Level 3", "", incomeComponent3);
		NetworkLevel level4 = new NetworkLevel(4, "Level 4", "", incomeComponent4);
		NetworkLevel level5 = new NetworkLevel(5, "Level 5", "", incomeComponent5);
		NetworkLevel level6 = new NetworkLevel(6, "Level 6", "", incomeComponent6);
		NetworkLevel level7 = new NetworkLevel(7, "Level 7", "", incomeComponent7);

		level1 = networkLevelService.create(level1);
		LOGGER.info("Created network level: {}", level1);

		level2 = networkLevelService.create(level2);
		LOGGER.info("Created network level: {}", level2);

		level3 = networkLevelService.create(level3);
		LOGGER.info("Created network level: {}", level3);

		level4 = networkLevelService.create(level4);
		LOGGER.info("Created network level: {}", level4);

		level5 = networkLevelService.create(level5);
		LOGGER.info("Created network level: {}", level5);

		level6 = networkLevelService.create(level6);
		LOGGER.info("Created network level: {}", level6);

		level7 = networkLevelService.create(level7);
		LOGGER.info("Created network level: {}", level7);

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


	public void addInvestmentPackage() {

		InvestmentPackage investmentPackage1 = new InvestmentPackage(InvestmentPackageEnum.CUSTOMER);
		InvestmentPackage investmentPackage2 = new InvestmentPackage(InvestmentPackageEnum.AGENT);
		InvestmentPackage investmentPackage3 = new InvestmentPackage(InvestmentPackageEnum.INVEST);
		InvestmentPackage investmentPackage4 = new InvestmentPackage(InvestmentPackageEnum.PROINVEST);
		InvestmentPackage investmentPackage5 = new InvestmentPackage(InvestmentPackageEnum.SLIVERINVEST);
		InvestmentPackage investmentPackage6 = new InvestmentPackage(InvestmentPackageEnum.GOLDINVEST);
		InvestmentPackage investmentPackage7 = new InvestmentPackage(InvestmentPackageEnum.PLATIUMINVEST);
		InvestmentPackage investmentPackage8 = new InvestmentPackage(InvestmentPackageEnum.DIAMONDINVEST);

		packageService.create(investmentPackage1);
		packageService.create(investmentPackage2);
		packageService.create(investmentPackage3);
		packageService.create(investmentPackage4);
		packageService.create(investmentPackage5);
		packageService.create(investmentPackage6);
		packageService.create(investmentPackage7);
		packageService.create(investmentPackage8);
	}

	public void createCash() {
		Cash cash1 = new Cash(CashEnum.CASH);
		Cash cash2 = new Cash(CashEnum.INVESTMENT);
		Cash cash3 = new Cash(CashEnum.TRADING);

		cashService.creates(Arrays.asList(cash1, cash2, cash3));
	}

}
