package idv.hsiehpinghan.stockdao.repository;

import idv.hsiehpinghan.datetimeutility.utility.DateUtility;
import idv.hsiehpinghan.stockdao.entity.StockInfo;
import idv.hsiehpinghan.stockdao.entity.StockInfo.CompanyFamily;
import idv.hsiehpinghan.stockdao.enumeration.IndustryType;
import idv.hsiehpinghan.stockdao.enumeration.MarketType;
import idv.hsiehpinghan.stockdao.suit.TestngSuitSetting;

import java.util.Date;

import org.springframework.context.ApplicationContext;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class StockInfoRepositoryTest {
	private Date ver = DateUtility.getDate(2015, 2, 3);
	private String stockCode = "stockCode";
	private MarketType marketType = MarketType.EMERGING;
	private IndustryType industryType = IndustryType.BUILDING;
	private String chineseName = "chineseName";
	private String englishBriefName = "englishBriefName";
	private String unifiedBusinessNumber = "unifiedBusinessNumber";
	private String establishmentDate = "establishmentDate";
	private String listingDate = "listingDate";
	private String chairman = "chairman";
	private String generalManager = "generalManager";
	private String spokesperson = "spokesperson";
	private String jobTitleOfSpokesperson = "jobTitleOfSpokesperson";
	private String actingSpokesman = "actingSpokesman";
	private String chineseAddress = "chineseAddress";
	private String telephone = "telephone";
	private String stockTransferAgency = "stockTransferAgency";
	private String telephoneOfStockTransferAgency = "telephoneOfStockTransferAgency";
	private String addressOfStockTransferAgency = "addressOfStockTransferAgency";
	private String englishAddress = "englishAddress";
	private String faxNumber = "faxNumber";
	private String email = "email";
	private String webSite = "webSite";
	private String financialReportType = "financialReportType";
	private String parValueOfOrdinaryShares = "parValueOfOrdinaryShares";
	private String paidInCapital = "paidInCapital";
	private String amountOfOrdinaryShares = "amountOfOrdinaryShares";
	private String privatePlacementAmountOfOrdinaryShares = "privatePlacementAmountOfOrdinaryShares";
	private String amountOfPreferredShares = "amountOfPreferredShares";
	private String accountingFirm = "accountingFirm";
	private String accountant1 = "accountant1";
	private String accountant2 = "accountant2";
	private StockInfoRepository repository;

	@BeforeClass
	public void beforeClass() throws Exception {
		ApplicationContext applicationContext = TestngSuitSetting
				.getApplicationContext();
		repository = applicationContext.getBean(StockInfoRepository.class);
	}

	@Test
	public void put() throws Exception {
		StockInfo entity = repository.generateEntity(stockCode);
		generateCompanyFamilyContent(entity);
		repository.put(entity);
		Assert.assertTrue(repository.exists(entity.getRowKey()));
	}

	@Test(dependsOnMethods = { "put" })
	public void get() throws Exception {
		StockInfo entity = repository.get(stockCode);
		assertCompanyFamily(entity);
	}

	private void generateCompanyFamilyContent(StockInfo entity) {
		CompanyFamily fam = entity.getCompanyFamily();
		fam.setMarketType(ver, marketType);
		fam.setIndustryType(ver, industryType);
		fam.setChineseName(ver, chineseName);
		fam.setEnglishBriefName(ver, englishBriefName);
		fam.setUnifiedBusinessNumber(ver, unifiedBusinessNumber);
		fam.setEstablishmentDate(ver, establishmentDate);
		fam.setListingDate(ver, listingDate);
		fam.setChairman(ver, chairman);
		fam.setGeneralManager(ver, generalManager);
		fam.setSpokesperson(ver, spokesperson);
		fam.setJobTitleOfSpokesperson(ver, jobTitleOfSpokesperson);
		fam.setActingSpokesman(ver, actingSpokesman);
		fam.setChineseAddress(ver, chineseAddress);
		fam.setTelephone(ver, telephone);
		fam.setStockTransferAgency(ver, stockTransferAgency);
		fam.setTelephoneOfStockTransferAgency(ver,
				telephoneOfStockTransferAgency);
		fam.setAddressOfStockTransferAgency(ver, addressOfStockTransferAgency);
		fam.setEnglishAddress(ver, englishAddress);
		fam.setFaxNumber(ver, faxNumber);
		fam.setEmail(ver, email);
		fam.setWebSite(ver, webSite);
		fam.setFinancialReportType(ver, financialReportType);
		fam.setParValueOfOrdinaryShares(ver, parValueOfOrdinaryShares);
		fam.setPaidInCapital(ver, paidInCapital);
		fam.setAmountOfOrdinaryShares(ver, amountOfOrdinaryShares);
		fam.setPrivatePlacementAmountOfOrdinaryShares(ver,
				privatePlacementAmountOfOrdinaryShares);
		fam.setAmountOfPreferredShares(ver, amountOfPreferredShares);
		fam.setAccountingFirm(ver, accountingFirm);
		fam.setAccountant1(ver, accountant1);
		fam.setAccountant2(ver, accountant2);
	}

	private void assertCompanyFamily(StockInfo entity) {
		CompanyFamily fam = entity.getCompanyFamily();
		Assert.assertEquals(marketType, fam.getMarketType());
		Assert.assertEquals(industryType, fam.getIndustryType());
		Assert.assertEquals(chineseName, fam.getChineseName());
		Assert.assertEquals(englishBriefName, fam.getEnglishBriefName());
		Assert.assertEquals(unifiedBusinessNumber,
				fam.getUnifiedBusinessNumber());
		Assert.assertEquals(establishmentDate, fam.getEstablishmentDate());
		Assert.assertEquals(listingDate, fam.getListingDate());
		Assert.assertEquals(chairman, fam.getChairman());
		Assert.assertEquals(generalManager, fam.getGeneralManager());
		Assert.assertEquals(spokesperson, fam.getSpokesperson());
		Assert.assertEquals(jobTitleOfSpokesperson,
				fam.getJobTitleOfSpokesperson());
		Assert.assertEquals(actingSpokesman, fam.getActingSpokesman());
		Assert.assertEquals(chineseAddress, fam.getChineseAddress());
		Assert.assertEquals(telephone, fam.getTelephone());
		Assert.assertEquals(stockTransferAgency, fam.getStockTransferAgency());
		Assert.assertEquals(telephoneOfStockTransferAgency,
				fam.getTelephoneOfStockTransferAgency());
		Assert.assertEquals(addressOfStockTransferAgency,
				fam.getAddressOfStockTransferAgency());
		Assert.assertEquals(englishAddress, fam.getEnglishAddress());
		Assert.assertEquals(faxNumber, fam.getFaxNumber());
		Assert.assertEquals(email, fam.getEmail());
		Assert.assertEquals(webSite, fam.getWebSite());
		Assert.assertEquals(financialReportType, fam.getFinancialReportType());
		Assert.assertEquals(parValueOfOrdinaryShares,
				fam.getParValueOfOrdinaryShares());
		Assert.assertEquals(paidInCapital, fam.getPaidInCapital());
		Assert.assertEquals(amountOfOrdinaryShares,
				fam.getAmountOfOrdinaryShares());
		Assert.assertEquals(privatePlacementAmountOfOrdinaryShares,
				fam.getPrivatePlacementAmountOfOrdinaryShares());
		Assert.assertEquals(amountOfPreferredShares,
				fam.getAmountOfPreferredShares());
		Assert.assertEquals(accountingFirm, fam.getAccountingFirm());
		Assert.assertEquals(accountant1, fam.getAccountant1());
		Assert.assertEquals(accountant2, fam.getAccountant2());
	}
}
