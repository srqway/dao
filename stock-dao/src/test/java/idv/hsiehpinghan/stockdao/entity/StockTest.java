package idv.hsiehpinghan.stockdao.entity;

import idv.hsiehpinghan.stockdao.entity.Stock.DailyFamily;
import idv.hsiehpinghan.stockdao.entity.Stock.FinancialReportFamily;
import idv.hsiehpinghan.stockdao.entity.Stock.InfoFamily;
import idv.hsiehpinghan.stockdao.entity.Stock.MonthlyFamily;
import idv.hsiehpinghan.stockdao.entity.Stock.RowKey;
import idv.hsiehpinghan.stockdao.entity.Stock.XbrlInstanceFamily;
import idv.hsiehpinghan.stockdao.enumeration.ElementType;
import idv.hsiehpinghan.stockdao.enumeration.IndustryType;
import idv.hsiehpinghan.stockdao.enumeration.MarketType;
import idv.hsiehpinghan.stockdao.enumeration.PeriodType;
import idv.hsiehpinghan.stockdao.enumeration.UnitType;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

import junit.framework.Assert;

import org.apache.commons.lang3.time.DateUtils;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class StockTest {
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
	private String elementId = "elementId";
	private PeriodType periodType = PeriodType.DURATION;
	private Date instant;
	private Date startDate;
	private Date endDate;
	private UnitType unitType = UnitType.SHARES;
	private BigDecimal value = BigDecimal.TEN;
	private ElementType elementType = ElementType.GROWTH;
	private int year = 2015;
	private int month = 1;
	private String operatingIncomeOfComment = "operatingIncomeOfComment";
	private BigInteger operatingIncomeOfCumulativeAmountOfLastYear = new BigInteger(
			"1");
	private BigInteger operatingIncomeOfCumulativeAmountOfThisYear = new BigInteger(
			"2");
	private BigInteger operatingIncomeOfCumulativeDifferentAmount = new BigInteger(
			"3");
	private BigDecimal operatingIncomeOfCumulativeDifferentPercent = new BigDecimal(
			"4.4");
	private BigInteger operatingIncomeOfCurrentMonth = new BigInteger("5");
	private BigInteger operatingIncomeOfCurrentMonthOfLastYear = new BigInteger(
			"6");
	private BigInteger operatingIncomeOfDifferentAmount = new BigInteger("7");
	private BigDecimal operatingIncomeOfDifferentPercent = new BigDecimal("8.8");
	private Date date;
	private BigDecimal closingConditionOfChange = new BigDecimal("1.11");
	private BigDecimal closingConditionOfClosingPrice = new BigDecimal("2.22");
	private BigDecimal closingConditionOfFinalPurchasePrice = new BigDecimal(
			"3.33");
	private BigDecimal closingConditionOfFinalSellingPrice = new BigDecimal(
			"4.44");
	private BigDecimal closingConditionOfHighestPrice = new BigDecimal("5.55");
	private BigDecimal closingConditionOfLowestPrice = new BigDecimal("6.66");
	private BigInteger closingConditionOfMoneyAmount = new BigInteger("7");
	private BigDecimal closingConditionOfOpeningPrice = new BigDecimal("8");
	private BigInteger closingConditionOfStockAmount = new BigInteger("9");
	private BigInteger closingConditionOfTransactionAmount = new BigInteger("10");

	@BeforeClass
	public void beforeClass() throws Exception {
		ver = DateUtils.parseDate("2015/01/01", "yyyy/MM/dd");
		instant = DateUtils.parseDate("2015/02/02", "yyyy/MM/dd");
		startDate = DateUtils.parseDate("2015/02/11", "yyyy/MM/dd");
		endDate = DateUtils.parseDate("2015/02/22", "yyyy/MM/dd");
		date = DateUtils.parseDate("2015/05/22", "yyyy/MM/dd");
	}

	@Test
	public void bytesConvert() {
		Stock entity = new Stock();
		testRowKey(entity);
		testInfoFamily(entity);
		testXbrlInstanceFamily(entity);
		testFinancialReportFamily(entity);
		testMonthlyFamily(entity);
		testDailyFamily(entity);
	}

	private void testRowKey(Stock entity) {
		RowKey key = entity.new RowKey(stockCode, entity);
		Assert.assertEquals(stockCode, key.getStockCode());
	}

	private void testDailyFamily(Stock entity) {
		generateDailyFamilyContent(entity);
		assertDailyFamily(entity);
	}

	private void generateDailyFamilyContent(Stock entity) {
		DailyFamily fam = entity.getDailyFamily();
		fam.setClosingConditionOfChange(date, ver, closingConditionOfChange);
		fam.setClosingConditionOfClosingPrice(date, ver,
				closingConditionOfClosingPrice);
		fam.setClosingConditionOfFinalPurchasePrice(date, ver,
				closingConditionOfFinalPurchasePrice);
		fam.setClosingConditionOfFinalSellingPrice(date, ver,
				closingConditionOfFinalSellingPrice);
		fam.setClosingConditionOfHighestPrice(date, ver,
				closingConditionOfHighestPrice);
		fam.setClosingConditionOfLowestPrice(date, ver,
				closingConditionOfLowestPrice);
		fam.setClosingConditionOfMoneyAmount(date, ver,
				closingConditionOfMoneyAmount);
		fam.setClosingConditionOfOpeningPrice(date, ver,
				closingConditionOfOpeningPrice);
		fam.setClosingConditionOfStockAmount(date, ver,
				closingConditionOfStockAmount);
		fam.setClosingConditionOfTransactionAmount(date, ver,
				closingConditionOfTransactionAmount);
	}

	private void assertDailyFamily(Stock entity) {
		DailyFamily fam = entity.getDailyFamily();
		Assert.assertEquals(closingConditionOfChange,
				fam.getClosingConditionOfChange(date));
		Assert.assertEquals(closingConditionOfClosingPrice,
				fam.getClosingConditionOfClosingPrice(date));
		Assert.assertEquals(closingConditionOfFinalPurchasePrice,
				fam.getClosingConditionOfFinalPurchasePrice(date));
		Assert.assertEquals(closingConditionOfFinalSellingPrice,
				fam.getClosingConditionOfFinalSellingPrice(date));
		Assert.assertEquals(closingConditionOfHighestPrice,
				fam.getClosingConditionOfHighestPrice(date));
		Assert.assertEquals(closingConditionOfLowestPrice,
				fam.getClosingConditionOfLowestPrice(date));
		Assert.assertEquals(closingConditionOfMoneyAmount,
				fam.getClosingConditionOfMoneyAmount(date));
		Assert.assertEquals(closingConditionOfOpeningPrice,
				fam.getClosingConditionOfOpeningPrice(date));
		Assert.assertEquals(closingConditionOfStockAmount,
				fam.getClosingConditionOfStockAmount(date));
		Assert.assertEquals(closingConditionOfTransactionAmount,
				fam.getClosingConditionOfTransactionAmount(date));
	}

	private void testMonthlyFamily(Stock entity) {
		generateMonthlyFamilyContent(entity);
		assertMonthlyFamily(entity);
	}

	private void generateMonthlyFamilyContent(Stock entity) {
		MonthlyFamily fam = entity.getMonthlyFamily();
		fam.setOperatingIncomeOfComment(year, month, ver,
				operatingIncomeOfComment);
		fam.setOperatingIncomeOfCumulativeAmountOfLastYear(year, month, ver,
				operatingIncomeOfCumulativeAmountOfLastYear);
		fam.setOperatingIncomeOfCumulativeAmountOfThisYear(year, month, ver,
				operatingIncomeOfCumulativeAmountOfThisYear);
		fam.setOperatingIncomeOfCumulativeDifferentAmount(year, month, ver,
				operatingIncomeOfCumulativeDifferentAmount);
		fam.setOperatingIncomeOfCumulativeDifferentPercent(year, month, ver,
				operatingIncomeOfCumulativeDifferentPercent);
		fam.setOperatingIncomeOfCurrentMonth(year, month, ver,
				operatingIncomeOfCurrentMonth);
		fam.setOperatingIncomeOfCurrentMonthOfLastYear(year, month, ver,
				operatingIncomeOfCurrentMonthOfLastYear);
		fam.setOperatingIncomeOfDifferentAmount(year, month, ver,
				operatingIncomeOfDifferentAmount);
		fam.setOperatingIncomeOfDifferentPercent(year, month, ver,
				operatingIncomeOfDifferentPercent);
	}

	private void assertMonthlyFamily(Stock entity) {
		MonthlyFamily fam = entity.getMonthlyFamily();
		Assert.assertEquals(operatingIncomeOfComment,
				fam.getOperatingIncomeOfComment(year, month));
		Assert.assertEquals(operatingIncomeOfCumulativeAmountOfLastYear,
				fam.getOperatingIncomeOfCumulativeAmountOfLastYear(year, month));
		Assert.assertEquals(operatingIncomeOfCumulativeAmountOfThisYear,
				fam.getOperatingIncomeOfCumulativeAmountOfThisYear(year, month));
		Assert.assertEquals(operatingIncomeOfCumulativeDifferentAmount,
				fam.getOperatingIncomeOfCumulativeDifferentAmount(year, month));
		Assert.assertEquals(operatingIncomeOfCumulativeDifferentPercent,
				fam.getOperatingIncomeOfCumulativeDifferentPercent(year, month));
		Assert.assertEquals(operatingIncomeOfCurrentMonth,
				fam.getOperatingIncomeOfCurrentMonth(year, month));
		Assert.assertEquals(operatingIncomeOfCurrentMonthOfLastYear,
				fam.getOperatingIncomeOfCurrentMonthOfLastYear(year, month));
		Assert.assertEquals(operatingIncomeOfDifferentAmount,
				fam.getOperatingIncomeOfDifferentAmount(year, month));
		Assert.assertEquals(operatingIncomeOfDifferentPercent,
				fam.getOperatingIncomeOfDifferentPercent(year, month));
	}

	private void testFinancialReportFamily(Stock entity) {
		generateFinancialReportFamilyContent(entity);
		assertFinancialReportFamily(entity);
	}

	private void generateFinancialReportFamilyContent(Stock entity) {
		FinancialReportFamily fam = entity.getFinancialReportFamily();
		fam.set(elementId, elementType, periodType, instant, startDate,
				endDate, ver, value);
	}

	private void assertFinancialReportFamily(Stock entity) {
		FinancialReportFamily fam = entity.getFinancialReportFamily();
		Assert.assertEquals(value, fam.getAsBigDecimal(elementId, elementType,
				periodType, instant, startDate, endDate));
	}

	private void testXbrlInstanceFamily(Stock entity) {
		generateXbrlInstanceFamilyContent(entity);
		assertXbrlInstanceFamily(entity);
	}

	private void generateXbrlInstanceFamilyContent(Stock entity) {
		XbrlInstanceFamily fam = entity.getXbrlInstanceFamily();
		fam.set(elementId, periodType, instant, startDate, endDate, unitType,
				ver, value);
	}

	private void assertXbrlInstanceFamily(Stock entity) {
		XbrlInstanceFamily fam = entity.getXbrlInstanceFamily();
		Assert.assertEquals(value, fam.getAsBigDecimal(elementId, periodType,
				instant, startDate, endDate, unitType));
	}

	private void testInfoFamily(Stock entity) {
		generateInfoFamilyContent(entity);
		assertInfoFamily(entity);
	}

	private void generateInfoFamilyContent(Stock entity) {
		InfoFamily fam = entity.getInfoFamily();
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

	private void assertInfoFamily(Stock entity) {
		InfoFamily fam = entity.getInfoFamily();
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
}
