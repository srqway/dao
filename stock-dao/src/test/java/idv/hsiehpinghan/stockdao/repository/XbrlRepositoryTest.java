package idv.hsiehpinghan.stockdao.repository;

import idv.hsiehpinghan.datetimeutility.utility.DateUtility;
import idv.hsiehpinghan.stockdao.entity.Xbrl;
import idv.hsiehpinghan.stockdao.entity.Xbrl.GrowthFamily;
import idv.hsiehpinghan.stockdao.entity.Xbrl.InfoFamily;
import idv.hsiehpinghan.stockdao.entity.Xbrl.InstanceFamily;
import idv.hsiehpinghan.stockdao.entity.Xbrl.InstanceFamily.InstanceValue;
import idv.hsiehpinghan.stockdao.entity.Xbrl.ItemFamily;
import idv.hsiehpinghan.stockdao.entity.Xbrl.RatioDifferenceFamily;
import idv.hsiehpinghan.stockdao.entity.Xbrl.RatioFamily;
import idv.hsiehpinghan.stockdao.enumeration.PeriodType;
import idv.hsiehpinghan.stockdao.enumeration.ReportType;
import idv.hsiehpinghan.stockdao.enumeration.UnitType;
import idv.hsiehpinghan.stockdao.suit.TestngSuitSetting;
import idv.hsiehpinghan.xbrlassistant.enumeration.XbrlTaxonomyVersion;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.context.ApplicationContext;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class XbrlRepositoryTest {
	private Date ver = DateUtility.getDate(2015, 2, 3);
	private Date startDate = DateUtility.getDate(2015, 2, 3);
	private String elementId = "elementId";
	private String stockCode = "stockCode";
	private BigDecimal percent = new BigDecimal("23.23");
	private ReportType reportType = ReportType.CONSOLIDATED_STATEMENT;
	private UnitType unitType = UnitType.SHARES;
	private Date endDate = DateUtility.getDate(2015, 2, 3);
	private String statementOfChangesInEquityContext = "statementOfChangesInEquityContext";
	private XbrlTaxonomyVersion version = XbrlTaxonomyVersion.TIFRS_BASI_CR_2013_03_31;
	private BigDecimal difference = new BigDecimal("29.29");
	private int season = 3;
	private BigDecimal ratio = new BigDecimal("31.31");
	private String statementOfCashFlowsContext = "statementOfCashFlowsContext";
	private String balanceSheetContext = "balanceSheetContext";
	private PeriodType periodType = PeriodType.DURATION;
	private BigDecimal value = new BigDecimal("35.35");
	private String statementOfComprehensiveIncomeContext = "statementOfComprehensiveIncomeContext";
	private int year = 2015;
	private Date instant = DateUtility.getDate(2015, 2, 3);
	private XbrlRepository repository;

	@BeforeClass
	public void beforeClass() throws Exception {
		ApplicationContext applicationContext = TestngSuitSetting
				.getApplicationContext();
		repository = applicationContext.getBean(XbrlRepository.class);
	}

	@Test
	public void put() throws Exception {
		Xbrl entity = repository.generateEntity(stockCode, reportType, year,
				season);
		generateInfoFamilyContent(entity);
		generateInstanceFamilyContent(entity);
		generateItemFamilyContent(entity);
		generateGrowthFamilyContent(entity);
		generateRatioFamilyContent(entity);
		generateRatioDifferenceFamilyContent(entity);
		repository.put(entity);
		Assert.assertTrue(repository.exists(entity.getRowKey()));
	}

	@Test(dependsOnMethods = { "put" })
	public void get() throws Exception {
		Xbrl entity = repository.get(stockCode, reportType, year, season);
		assertInfoFamily(entity);
		assertInstanceFamily(entity);
		assertItemFamily(entity);
		assertGrowthFamily(entity);
		assertRatioFamily(entity);
		assertRatioDifferenceFamily(entity);
	}

	private void generateInfoFamilyContent(Xbrl entity) {
		InfoFamily fam = entity.getInfoFamily();
		fam.setVersion(ver, version);
		fam.setBalanceSheetContext(ver, balanceSheetContext);
		fam.setStatementOfComprehensiveIncomeContext(ver,
				statementOfComprehensiveIncomeContext);
		fam.setStatementOfCashFlowsContext(ver, statementOfCashFlowsContext);
		fam.setStatementOfChangesInEquityContext(ver,
				statementOfChangesInEquityContext);
	}

	private void assertInfoFamily(Xbrl entity) {
		InfoFamily fam = entity.getInfoFamily();
		Assert.assertEquals(version, fam.getVersion());
		Assert.assertEquals(balanceSheetContext, fam.getBalanceSheetContext());
		Assert.assertEquals(statementOfComprehensiveIncomeContext,
				fam.getStatementOfComprehensiveIncomeContext());
		Assert.assertEquals(statementOfCashFlowsContext,
				fam.getStatementOfCashFlowsContext());
		Assert.assertEquals(statementOfChangesInEquityContext,
				fam.getStatementOfChangesInEquityContext());
	}

	private void generateInstanceFamilyContent(Xbrl entity) {
		InstanceFamily fam = entity.getInstanceFamily();
		fam.setInstanceValue(elementId, periodType, instant, startDate,
				endDate, ver, unitType, value);
	}

	private void assertInstanceFamily(Xbrl entity) {
		InstanceFamily fam = entity.getInstanceFamily();
		InstanceValue val = fam.getInstanceValue(elementId, periodType,
				instant, startDate, endDate);
		Assert.assertEquals(val.getUnitType(), unitType);
		Assert.assertEquals(val.getValue(), value);
	}

	private void generateItemFamilyContent(Xbrl entity) {
		ItemFamily fam = entity.getItemFamily();
		fam.set(elementId, periodType, instant, startDate, endDate, ver, value);
	}

	private void assertItemFamily(Xbrl entity) {
		ItemFamily fam = entity.getItemFamily();
		Assert.assertEquals(
				fam.get(elementId, periodType, instant, startDate, endDate),
				value);
	}

	private void generateGrowthFamilyContent(Xbrl entity) {
		GrowthFamily fam = entity.getGrowthFamily();
		fam.setRatio(elementId, periodType, instant, startDate, endDate, ver,
				ratio);
	}

	private void assertGrowthFamily(Xbrl entity) {
		GrowthFamily fam = entity.getGrowthFamily();
		Assert.assertEquals(ratio, fam.getRatio(elementId, periodType, instant,
				startDate, endDate));
	}

	private void generateRatioFamilyContent(Xbrl entity) {
		RatioFamily fam = entity.getRatioFamily();
		fam.setPercent(elementId, periodType, instant, startDate, endDate, ver,
				percent);
	}

	private void assertRatioFamily(Xbrl entity) {
		RatioFamily fam = entity.getRatioFamily();
		Assert.assertEquals(percent, fam.getPercent(elementId, periodType,
				instant, startDate, endDate));
	}

	private void generateRatioDifferenceFamilyContent(Xbrl entity) {
		RatioDifferenceFamily fam = entity.getRatioDifferenceFamily();
		fam.setDifference(elementId, periodType, instant, startDate, endDate,
				ver, difference);
	}

	private void assertRatioDifferenceFamily(Xbrl entity) {
		RatioDifferenceFamily fam = entity.getRatioDifferenceFamily();
		Assert.assertEquals(difference, fam.getDifference(elementId,
				periodType, instant, startDate, endDate));
	}
}
