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
	private LotteryDialingHasIncomeCompService incomeComponentService;

	@Autowired
	private InvestmentPackageService packageService;

	@Autowired
	private CashService cashService;

	@Autowired
	private InvestmentPackageCashService investmentPackageCashService;


	public static void main(String[] args) {
		SpringApplication.run(SmartlottApplication.class, args);
	}

	@Override
	public void run(String...args) throws Exception{
		LOGGER.info("Creating user with role admin into database...");

		//Create number account type
		CreateNumberAccountType();

		//creat transaction type
		createTransactionType();

		//create transaction status
		createTransactionStatus();

		//open new lottery dialing
		createLotteryDialing();

		//create new lottery type;
		createLotteryType();

		//create new Income component and reward

		createIncomeComponentAndRewardUtils();

		//create new Lottery dialing has income component
		createLotDialHasIncome();

		NumberAccountType bigCoin = numberAccountTypeService.getOne(1);
		NumberAccountType pm = numberAccountTypeService.getOne(2);

		//createSecurityTokenForUsername role
		Role custRole = new Role(RolesEnum.CUSTOMER);
		Role seoRole = new Role(RolesEnum.SEO);
		Role staffRole = new Role(RolesEnum.STAFF);
		Role editRole = new Role(RolesEnum.EDITOR);
		Role mantRole = new Role(RolesEnum.MANAGER);
		Role addRole = new Role(RolesEnum.ADMIN);

		List<Role> roles = Arrays.asList(custRole, seoRole, staffRole, editRole, mantRole, addRole);

		LOGGER.info("Create roles into database..");
		roleService.createRoles(roles);

		User user = UserUtils.createCustUser("admin","admin@gmail.com");
		user.setPassword("123456");

		Set<Role> userRoles = new HashSet<>();
		userRoles.add(custRole);
		userRoles.add(addRole);

		user.setRoles(userRoles);
		user.setActived(true);
		user.setCash(12000);

		LOGGER.debug("Creating user with username {} and email {}", user.getUsername(), user.getEmail());
		user = userService.createUser(user);
		LOGGER.info("User {} has been created", user);


		//createSecurityTokenForUsername slider
		createFeaturedSlider();

		//add number account
		NumberAccount numberAccount1 = new NumberAccount("greenlucky", user, bigCoin);
		NumberAccount numberAccount2 = new NumberAccount("U9498703", user, pm);
		numberAccountService.create(numberAccount1);
		numberAccountService.create(numberAccount2);



		createCusts(user);

		createCountry();

	}

	public void createFeaturedSlider(){

		FeaturedSlider featuredSlider = SliderUtils.createFeaturedSlider("Homepage");
		featuredSliderService.save(featuredSlider);

		FeaturedSliderImage featuredSliderImage1 = SliderUtils.createFeaturedSliderImage("Another example headline.","Cras justo odio, dapibus ac facilisis in, egestas eget quam. Donec id elit non mi porta gravida at eget metus. Nullam id dolor id nibh ultricies vehicula ut id elit.");
		featuredSliderImage1.setFeaturedSlider(featuredSlider);
		FeaturedSliderImage featuredSliderImage2 = SliderUtils.createFeaturedSliderImage("One more for good measure.","Cras justo odio, dapibus ac facilisis in, egestas eget quam. Donec id elit non mi porta gravida at eget metus. Nullam id dolor id nibh ultricies vehicula ut id elit.");
		featuredSliderImage2.setFeaturedSlider(featuredSlider);
		FeaturedSliderImage featuredSliderImage3 = SliderUtils.createFeaturedSliderImage("Example headline.","Cras justo odio, dapibus ac facilisis in, egestas eget quam. Donec id elit non mi porta gravida at eget metus. Nullam id dolor id nibh ultricies vehicula ut id elit.");
		featuredSliderImage3.setFeaturedSlider(featuredSlider);

		sliderImageService.save(featuredSliderImage1);
		sliderImageService.save(featuredSliderImage2);
		sliderImageService.save(featuredSliderImage3);
	}


	public void createCountry(){

		//Create country
		Country country1 = CountryUtils.createCountry("United State","");
		countryService.createCountry(country1);

		Country country2 = CountryUtils.createCountry("Vietnam","84");
		countryService.createCountry(country2);

		//Create province
		Province province1 = ProvinceUtils.createProvice("Hồ Chí Minh", country2);
		provinceService.createProvince(province1);

		Province province2 = ProvinceUtils.createProvice("Hà Nội", country2);
		provinceService.createProvince(province2);
	}

	public void CreateNumberAccountType(){
		//Create new account for BigCoin
		NumberAccountType pm = new NumberAccountType(NumberAccountTypeEnum.PerfectMoney);
		NumberAccountType cash = new NumberAccountType(NumberAccountTypeEnum.Cash);

		numberAccountTypeService.create(pm);
		numberAccountTypeService.create(cash);
	}


	public void createTransactionType(){
		TransactionType type1= new TransactionType(TransactionTypeEnum.Withdraw);
		TransactionType type2= new TransactionType(TransactionTypeEnum.BuyingLottery);
		TransactionType type3= new TransactionType(TransactionTypeEnum.BuyInvestmentPackage);
		TransactionType type4= new TransactionType(TransactionTypeEnum.TransferCash);

		transactionTypeService.createNew(type1);
		transactionTypeService.createNew(type2);
		transactionTypeService.createNew(type3);
		transactionTypeService.createNew(type4);
	}

	public void createCusts(User user){

		List<Network> networks = new ArrayList<>();

		Role custRole = new Role(RolesEnum.CUSTOMER);

		Set<Role> userRoles = new HashSet<>();
		for(int i = 1; i <= 8; i++) {

			User accestor = null;

			NetworkLevel level = networkLevelService.getOne(1);
			String name = "customer" + i;
			User localUser = UserUtils.createCustUser(name, name + "@smartlott.com");
			localUser.setPassword("123456");
			userRoles = new HashSet<>();
			userRoles.add(custRole);
			localUser.setRoles(userRoles);
			localUser.setActived(false);
			localUser.setCash(10000);

			if(i == 1) {
				accestor = user;
				localUser.setIntroducedBy(user.getUsername());
			}
			else{
				accestor = userService.findOne((i));
				localUser.setIntroducedBy(accestor.getUsername());
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

	public void createIncomeComponentAndRewardUtils(){

		//create reward
		Reward jackpots = new Reward();
		jackpots.setName("Jeckpots");
		jackpots.setAccumulation(true);
		jackpots.setValue(1000000);
		jackpots.setDefaultNumericReward(0);
		jackpots.setCoupleNumber(6);
		jackpots = rewardService.create(jackpots);
		LOGGER.info("Create reward {}", jackpots);

		Reward firstReward = new Reward();
		firstReward.setName("firstReward");
		firstReward.setValue(20000);
		firstReward.setDefaultNumericReward(0);
		firstReward.setCoupleNumber(5);
		rewardService.create(firstReward);

		Reward secondReward = new Reward();
		secondReward.setName("secondReward");
		secondReward.setValue(1000);
		secondReward.setDefaultNumericReward(0);
		secondReward.setCoupleNumber(4);
		rewardService.create(secondReward);

		Reward thirdReward = new Reward();
		thirdReward.setName("thirdReward");
		thirdReward.setValue(100);
		thirdReward.setDefaultNumericReward(0);
		thirdReward.setCoupleNumber(3);
		rewardService.create(thirdReward);

		Reward fourReward = new Reward();
		fourReward.setName("fourReward");
		fourReward.setValue(20);
		fourReward.setDefaultNumericReward(0);
		fourReward.setCoupleNumber(2);
		rewardService.create(fourReward);

		Reward fiveReward = new Reward();
		fiveReward.setName("fiveReward");
		fiveReward.setValue(5);
		fiveReward.setDefaultNumericReward(0);
		fiveReward.setCoupleNumber(1);
		rewardService.create(fiveReward);

		//list income comp for investment package
		List<IncomeComponent> investPacks = new ArrayList<>();

		IncomeComponent incomeComponent1 = new IncomeComponent();
		incomeComponent1.setName("Hoa hồng bán hàng F1");
		incomeComponent1.setValue(20);
		incomeComponent1.setEnabled(true);
		incomeComponent1.setDescription("Hoa hồng bán hàng F1");
		incomeComponent1 = componentService.create(incomeComponent1);

		IncomeComponent incomeComponent2 = new IncomeComponent();
		incomeComponent2.setName("Hoa hồng bán hàng F2");
		incomeComponent2.setValue(2);
		incomeComponent2.setEnabled(true);
		incomeComponent2.setDescription("Hoa hồng bán hàng F2");
		incomeComponent2 = componentService.create(incomeComponent2);


		IncomeComponent incomeComponent3 = new IncomeComponent();
		incomeComponent3.setName("Hoa hồng bán hàng F3");
		incomeComponent3.setValue(2);
		incomeComponent3.setEnabled(true);
		incomeComponent3.setDescription("Hoa hồng bán hàng F2");
		incomeComponent3 = componentService.create(incomeComponent3);

		IncomeComponent incomeComponent4 = new IncomeComponent();
		incomeComponent4.setName("Hoa hồng bán hàng F4");
		incomeComponent4.setValue(2);
		incomeComponent4.setEnabled(true);
		incomeComponent4.setDescription("Hoa hồng bán hàng F4");
		incomeComponent4 = componentService.create(incomeComponent4);

		IncomeComponent incomeComponent5 = new IncomeComponent();
		incomeComponent5.setName("Hoa hồng bán hàng F5");
		incomeComponent5.setValue(2);
		incomeComponent5.setEnabled(true);
		incomeComponent5.setDescription("Hoa hồng bán hàng F5");
		incomeComponent5 = componentService.create(incomeComponent5);

		IncomeComponent incomeComponent6 = new IncomeComponent();
		incomeComponent6.setName("Hoa hồng bán hàng F6");
		incomeComponent6.setValue(2);
		incomeComponent6.setEnabled(true);
		incomeComponent6.setDescription("Hoa hồng bán hàng F6");
		incomeComponent6 = componentService.create(incomeComponent6);

		IncomeComponent incomeComponent7 = new IncomeComponent();
		incomeComponent7.setName("Hoa hồng bán hàng F7");
		incomeComponent7.setValue(2);
		incomeComponent7.setEnabled(true);
		incomeComponent7.setDescription("Hoa hồng bán hàng F7");
		incomeComponent7 = componentService.create(incomeComponent7);

		IncomeComponent ja = new IncomeComponent();
		ja.setName("Giải đặc biệt");
		ja.setReward(jackpots);
		ja.setValue(40);
		ja.setEnabled(true);
		ja.setDescription("Giải đặc biệt");
		ja = componentService.create(ja);

		IncomeComponent oneth = new IncomeComponent();
		oneth.setName("Giải nhất");
		oneth.setReward(firstReward);
		oneth.setValue(6);
		oneth.setEnabled(true);
		oneth.setDescription("Giải nhất");
		oneth = componentService.create(oneth);

		IncomeComponent second = new IncomeComponent();
		second.setName("Giải nhì");
		second.setReward(secondReward);
		second.setValue(4);
		second.setEnabled(true);
		second.setDescription("Giải nhì");
		second = componentService.create(second);

		IncomeComponent third = new IncomeComponent();
		third.setName("Giải ba");
		third.setReward(thirdReward);
		third.setValue(4);
		third.setEnabled(true);
		third.setDescription("Giải ba");
		third = componentService.create(third);

		IncomeComponent four = new IncomeComponent();
		four.setName("Giải tư");
		four.setReward(fourReward);
		four.setValue(3);
		four.setEnabled(true);
		four.setDescription("Giải tư");
		four = componentService.create(four);

		IncomeComponent five = new IncomeComponent();
		five.setName("Giải năm");
		five.setReward(fiveReward);
		five.setValue(3);
		five.setEnabled(true);
		five.setDescription("Giải năm");
		five = componentService.create(five);

		IncomeComponent management = new IncomeComponent();
		management.setName("Quản lý web, server");
		management.setValue(5);
		management.setEnabled(true);
		management.setDescription("Quản lý web, server");
		management = componentService.create(management);

		//Bonus investment
		IncomeComponent agent = new IncomeComponent();
		agent.setName("Agent");
		agent.setValue(0);
		agent.setEnabled(true);
		agent.setDescription("The bonus agent investment package");
		agent = componentService.create(agent);
		investPacks.add(agent);

		//Bonus investment
		IncomeComponent invest = new IncomeComponent();
		invest.setName("Invest");
		invest.setValue(0.01);
		invest.setEnabled(true);
		invest.setDescription("The bonus invest investment package");
		invest = componentService.create(invest);
		investPacks.add(invest);

		invest = new IncomeComponent();
		//Bonus investment
		invest.setName("Pro Invest");
		invest.setValue(0.1);
		invest = componentService.create(invest);
		investPacks.add(invest);

		invest = new IncomeComponent();
		//Bonus investment
		invest.setName("Sliver Invest");
		invest.setValue(0.24);
		invest = componentService.create(invest);
		investPacks.add(invest);

		invest = new IncomeComponent();
		//Bonus investment
		invest.setName("Gold Invest");
		invest.setValue(0.50);
		invest = componentService.create(invest);
		investPacks.add(invest);

		invest = new IncomeComponent();
		//Bonus investment
		invest.setName("Platinum Invest");
		invest.setValue(0.75);
		invest = componentService.create(invest);
		investPacks.add(invest);

		invest = new IncomeComponent();
		//Bonus investment
		invest.setName("Diamond Invest");
		invest.setValue(1.4);
		invest = componentService.create(invest);
		investPacks.add(invest);

		//create level of network
		NetworkLevel level1 = new NetworkLevel(1, 1, "", incomeComponent1, BonusType.Lottery);
		NetworkLevel level2 = new NetworkLevel(2, 2, "", incomeComponent2, BonusType.Lottery);
		NetworkLevel level3 = new NetworkLevel(3, 3, "", incomeComponent3, BonusType.Lottery);
		NetworkLevel level4 = new NetworkLevel(4, 4, "", incomeComponent4, BonusType.Lottery);
		NetworkLevel level5 = new NetworkLevel(5, 5, "", incomeComponent5, BonusType.Lottery);
		NetworkLevel level6 = new NetworkLevel(6, 6, "", incomeComponent6, BonusType.Lottery);
		NetworkLevel level7 = new NetworkLevel(7, 7, "", incomeComponent7, BonusType.Lottery);

		networkLevelService.create(level1);
		networkLevelService.create(level2);
		networkLevelService.create(level3);
		networkLevelService.create(level4);
		networkLevelService.create(level5);
		networkLevelService.create(level6);
		networkLevelService.create(level7);

		//bonus package
		IncomeComponent package1 = new IncomeComponent();
		package1.setName("Hoa hồng mua gói F1");
		package1.setValue(20);
		package1.setEnabled(true);
		package1.setDescription("Hoa hồng mua gói F1");
		package1 = componentService.create(package1);

		IncomeComponent package2 = new IncomeComponent();
		package2.setName("Hoa hồng mua gói F2");
		package2.setValue(2);
		package2.setEnabled(true);
		package2.setDescription("Hoa hồng mua gói F2");
		package2 = componentService.create(package2);

		IncomeComponent package3 = new IncomeComponent();
		package3.setName("Hoa hồng mua gói F3");
		package3.setValue(2);
		package3.setEnabled(true);
		package3.setDescription("Hoa hồng mua gói F2");
		package3 = componentService.create(package3);

		IncomeComponent package4 = new IncomeComponent();
		package4.setName("Hoa hồng mua gói F4");
		package4.setValue(2);
		package4.setEnabled(true);
		package4.setDescription("Hoa hồng mua gói F4");
		package4 = componentService.create(package4);

		IncomeComponent package5 = new IncomeComponent();
		package5.setName("Hoa hồng mua gói F5");
		package5.setValue(2);
		package5.setEnabled(true);
		package5.setDescription("Hoa hồng mua gói F5");
		package5 = componentService.create(package5);

		IncomeComponent package6 = new IncomeComponent();
		package6.setName("Hoa hồng mua gói F6");
		package6.setValue(2);
		package6.setEnabled(true);
		package6.setDescription("Hoa hồng mua gói F6");
		package6 = componentService.create(package6);

		level1 = new NetworkLevel(8, 1, "", package1, BonusType.Package);
		level2 = new NetworkLevel(9, 2, "", package2, BonusType.Package);
		level3 = new NetworkLevel(10, 3, "", package3, BonusType.Package);
		level4 = new NetworkLevel(11, 4, "", package4, BonusType.Package);
		level5 = new NetworkLevel(12, 5, "", package5, BonusType.Package);
		level6 = new NetworkLevel(13, 6, "", package6, BonusType.Package);

		networkLevelService.create(level1);
		networkLevelService.create(level2);
		networkLevelService.create(level3);
		networkLevelService.create(level4);
		networkLevelService.create(level5);
		networkLevelService.create(level6);

		addInvestmentPackage(investPacks);

	}

	public void createLotDialHasIncome(){

		//get current lottery dialing is opening
		LotteryDialing lotteryDialing = dialingService.getOpenedLotteryDialing(true);

		List<IncomeComponent> lstInc = componentService.getAll();

		for (IncomeComponent item : lstInc){
			LotteryDialingHasIncomeComp loC = new LotteryDialingHasIncomeComp();
			loC.setValue(0);
			loC.setIncomeComponent(item);
			loC.setLotteryDialing(lotteryDialing);
			incomeComponentService.create(loC);
		}
	}


	public void addInvestmentPackage(List<IncomeComponent> list) {

		Cash cash1 = new Cash(CashEnum.CASH);
		Cash cash2 = new Cash(CashEnum.INVESTMENT);
		Cash cash3 = new Cash(CashEnum.TRADING);

		cash1 = cashService.create(cash1);
		cash2 = cashService.create(cash2);
		cash3 = cashService.create(cash3);

		List<InvestmentPackage> investmentPackages = new ArrayList<>();

		InvestmentPackage investmentPackage1 = new InvestmentPackage(InvestmentPackageEnum.CUSTOMER);
		InvestmentPackage investmentPackage2 = new InvestmentPackage(InvestmentPackageEnum.AGENT);
		InvestmentPackage investmentPackage3 = new InvestmentPackage(InvestmentPackageEnum.INVEST);
		InvestmentPackage investmentPackage4 = new InvestmentPackage(InvestmentPackageEnum.PROINVEST);
		InvestmentPackage investmentPackage5 = new InvestmentPackage(InvestmentPackageEnum.SLIVERINVEST);
		InvestmentPackage investmentPackage6 = new InvestmentPackage(InvestmentPackageEnum.GOLDINVEST);
		InvestmentPackage investmentPackage7 = new InvestmentPackage(InvestmentPackageEnum.PLATIUMINVEST);
		InvestmentPackage investmentPackage8 = new InvestmentPackage(InvestmentPackageEnum.DIAMONDINVEST);

		investmentPackage2.setIncomeComponent(list.get(0));
		investmentPackage3.setIncomeComponent(list.get(1));
		investmentPackage4.setIncomeComponent(list.get(2));
		investmentPackage5.setIncomeComponent(list.get(3));
		investmentPackage6.setIncomeComponent(list.get(4));
		investmentPackage7.setIncomeComponent(list.get(5));
		investmentPackage8.setIncomeComponent(list.get(6));

		packageService.create(investmentPackage1);
		packageService.create(investmentPackage2);
		investmentPackages.add(packageService.create(investmentPackage3));
		investmentPackages.add(packageService.create(investmentPackage4));
		investmentPackages.add(packageService.create(investmentPackage5));
		investmentPackages.add(packageService.create(investmentPackage6));
		investmentPackages.add(packageService.create(investmentPackage7));
		investmentPackages.add(packageService.create(investmentPackage8));

		for (InvestmentPackage item : investmentPackages) {
			investmentPackageCashService.create(new InvestmentPackageCash(item, cash2, 90));
			investmentPackageCashService.create(new InvestmentPackageCash(item, cash3, 10));
		}
	}

}
