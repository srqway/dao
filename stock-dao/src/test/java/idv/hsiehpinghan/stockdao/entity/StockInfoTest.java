package idv.hsiehpinghan.stockdao.entity;

import idv.hsiehpinghan.stockdao.entity.StockInfo.BasicDataFamily;
import idv.hsiehpinghan.stockdao.entity.StockInfo.RowKey;
import idv.hsiehpinghan.stockdao.enumeration.IndustryType;
import idv.hsiehpinghan.stockdao.enumeration.MarketType;

import java.util.Date;

import org.apache.commons.lang3.time.DateUtils;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class StockInfoTest {
	private Date ver;
	private String stockCode = "2330";
	private String accountant1 = "accountant1";
	private String accountant2 = "accountant2";
	private String accountingFirm = "accountingFirm";
	private String actingSpokesman = "actingSpokesman";
	private String addressOfStockTransferAgency = "addressOfStockTransferAgency";
	private String amountOfOrdinaryShares = "amountOfOrdinaryShares";
	private String amountOfPreferredShares = "amountOfPreferredShares";
	private String chairman = "chairman";
	private String chineseAddress = "chineseAddress";
	private String chineseName = "chineseName";
	private String email = "email";
	private String englishAddress = "englishAddress";
	private String englishBriefName = "englishBriefName";
	private String establishmentDate = "establishmentDate";
	private String faxNumber = "faxNumber";
	private String financialReportType = "financialReportType";
	private String generalManager = "generalManager";
	private IndustryType industryType = IndustryType.BUILDING;
	private String jobTitleOfSpokesperson = "jobTitleOfSpokesperson";
	private String listingDate = "listingDate";
	private MarketType marketType = MarketType.EMERGING;
	private String paidInCapital = "paidInCapital";
	private String parValueOfOrdinaryShares = "parValueOfOrdinaryShares";
	private String privatePlacementAmountOfOrdinaryShares = "privatePlacementAmountOfOrdinaryShares";
	private String spokesperson = "spokesperson";
	private String stockTransferAgency = "stockTransferAgency";
	private String telephone = "telephone";
	private String telephoneOfStockTransferAgency = "telephoneOfStockTransferAgency";
	private String unifiedBusinessNumber = "unifiedBusinessNumber";
	private String webSite = "webSite";

	@BeforeClass
	public void beforeClass() throws Exception {
		ver = DateUtils.parseDate("2015/01/01", "yyyy/MM/dd");
	}

	@Test
	public void bytesConvert() {
		StockInfo entity = new StockInfo();
		testRowKey(entity);
		testBasicDataFamily(entity);
	}

	private void testBasicDataFamily(StockInfo entity) {
		generateBasicDataFamilyContent(entity);
		assertBasicDataFamily(entity);
	}

	private void generateBasicDataFamilyContent(StockInfo entity) {
		BasicDataFamily fam = entity.getBasicDataFamily();
		fam.setAccountant1(ver, accountant1);
		fam.setAccountant2(ver, accountant2);
		fam.setAccountingFirm(ver, accountingFirm);
		fam.setActingSpokesman(ver, actingSpokesman);
		fam.setAddressOfStockTransferAgency(ver, addressOfStockTransferAgency);
		fam.setAmountOfOrdinaryShares(ver, amountOfOrdinaryShares);
		fam.setAmountOfPreferredShares(ver, amountOfPreferredShares);
		fam.setChairman(ver, chairman);
		fam.setChineseAddress(ver, chineseAddress);
		fam.setChineseName(ver, chineseName);
		fam.setEmail(ver, email);
		fam.setEnglishAddress(ver, englishAddress);
		fam.setEnglishBriefName(ver, englishBriefName);
		fam.setEstablishmentDate(ver, establishmentDate);
		fam.setFaxNumber(ver, faxNumber);
		fam.setFinancialReportType(ver, financialReportType);
		fam.setGeneralManager(ver, generalManager);
		fam.setIndustryType(ver, industryType);
		fam.setJobTitleOfSpokesperson(ver, jobTitleOfSpokesperson);
		fam.setListingDate(ver, listingDate);
		fam.setMarketType(ver, marketType);
		fam.setPaidInCapital(ver, paidInCapital);
		fam.setParValueOfOrdinaryShares(ver, parValueOfOrdinaryShares);
		fam.setPrivatePlacementAmountOfOrdinaryShares(ver,
				privatePlacementAmountOfOrdinaryShares);
		fam.setSpokesperson(ver, spokesperson);
		fam.setStockTransferAgency(ver, stockTransferAgency);
		fam.setTelephone(ver, telephone);
		fam.setTelephoneOfStockTransferAgency(ver,
				telephoneOfStockTransferAgency);
		fam.setUnifiedBusinessNumber(ver, unifiedBusinessNumber);
		fam.setWebSite(ver, webSite);
	}

	private void assertBasicDataFamily(StockInfo entity) {
		BasicDataFamily fam = entity.getBasicDataFamily();
		Assert.assertEquals(accountant1, fam.getAccountant1());
		Assert.assertEquals(accountant2, fam.getAccountant2());
		Assert.assertEquals(accountingFirm, fam.getAccountingFirm());
		Assert.assertEquals(actingSpokesman, fam.getActingSpokesman());
		Assert.assertEquals(addressOfStockTransferAgency,
				fam.getAddressOfStockTransferAgency());
		Assert.assertEquals(amountOfOrdinaryShares,
				fam.getAmountOfOrdinaryShares());
		Assert.assertEquals(amountOfPreferredShares,
				fam.getAmountOfPreferredShares());
		Assert.assertEquals(chairman, fam.getChairman());
		Assert.assertEquals(chineseAddress, fam.getChineseAddress());
		Assert.assertEquals(chineseName, fam.getChineseName());
		Assert.assertEquals(email, fam.getEmail());
		Assert.assertEquals(englishAddress, fam.getEnglishAddress());
		Assert.assertEquals(englishBriefName, fam.getEnglishBriefName());
		Assert.assertEquals(establishmentDate, fam.getEstablishmentDate());
		Assert.assertEquals(faxNumber, fam.getFaxNumber());
		Assert.assertEquals(financialReportType, fam.getFinancialReportType());
		Assert.assertEquals(generalManager, fam.getGeneralManager());
		Assert.assertEquals(industryType, fam.getIndustryType());
		Assert.assertEquals(jobTitleOfSpokesperson,
				fam.getJobTitleOfSpokesperson());
		Assert.assertEquals(listingDate, fam.getListingDate());
		Assert.assertEquals(marketType, fam.getMarketType());
		Assert.assertEquals(paidInCapital, fam.getPaidInCapital());
		Assert.assertEquals(parValueOfOrdinaryShares,
				fam.getParValueOfOrdinaryShares());
		Assert.assertEquals(privatePlacementAmountOfOrdinaryShares,
				fam.getPrivatePlacementAmountOfOrdinaryShares());
		Assert.assertEquals(spokesperson, fam.getSpokesperson());
		Assert.assertEquals(stockTransferAgency, fam.getStockTransferAgency());
		Assert.assertEquals(telephone, fam.getTelephone());
		Assert.assertEquals(telephoneOfStockTransferAgency,
				fam.getTelephoneOfStockTransferAgency());
		Assert.assertEquals(unifiedBusinessNumber,
				fam.getUnifiedBusinessNumber());
		Assert.assertEquals(webSite, fam.getWebSite());
	}

	private void testRowKey(StockInfo entity) {
		RowKey key = entity.new RowKey(stockCode, entity);
		Assert.assertEquals(stockCode, key.getStockCode());
	}
}
