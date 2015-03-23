package idv.hsiehpinghan.stockdao.repository;

import idv.hsiehpinghan.datetimeutility.utility.DateUtility;
import idv.hsiehpinghan.stockdao.entity.Xbrl;
import idv.hsiehpinghan.stockdao.entity.Xbrl.InfoFamily;
import idv.hsiehpinghan.stockdao.entity.Xbrl.InstanceFamily;
import idv.hsiehpinghan.stockdao.entity.Xbrl.InstanceFamily.InstanceValue;
import idv.hsiehpinghan.stockdao.entity.Xbrl.ItemFamily;
import idv.hsiehpinghan.stockdao.entity.Xbrl.MainItemFamily;
import idv.hsiehpinghan.stockdao.entity.Xbrl.MainRatioFamily;
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
	private ReportType reportType = ReportType.CONSOLIDATED_STATEMENT;
	private UnitType unitType = UnitType.SHARES;
	private Date endDate = DateUtility.getDate(2015, 2, 3);
	private String statementOfChangesInEquityContext = "statementOfChangesInEquityContext";
	private XbrlTaxonomyVersion version = XbrlTaxonomyVersion.TIFRS_BASI_CR_2013_03_31;
	private int season = 4;
	private BigDecimal ratio = new BigDecimal("27.27");
	private String statementOfCashFlowsContext = "statementOfCashFlowsContext";
	private String balanceSheetContext = "balanceSheetContext";
	private PeriodType periodType = PeriodType.DURATION;
	private BigDecimal value = new BigDecimal("31.31");
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
		generateMainItemFamilyContent(entity);
		generateRatioFamilyContent(entity);
		generateMainRatioFamilyContent(entity);
		repository.put(entity);
		Assert.assertTrue(repository.exists(entity.getRowKey()));
	}

	@Test(dependsOnMethods = { "get" })
	public void getWithInfoFamilyOnly() throws Exception {
		Xbrl entity = repository.getWithInfoFamilyOnly(stockCode, reportType,
				year, season);
		assertInfoFamily(entity);
		assertEmptyInstanceFamily(entity);
		assertEmptyItemFamily(entity);
		assertEmptyMainItemFamily(entity);
		assertEmptyRatioFamily(entity);
		assertEmptyMainRatioFamily(entity);
	}

	@Test(dependsOnMethods = { "get" })
	public void getWithInstanceFamilyOnly() throws Exception {
		Xbrl entity = repository.getWithInstanceFamilyOnly(stockCode,
				reportType, year, season);
		assertEmptyInfoFamily(entity);
		assertInstanceFamily(entity);
		assertEmptyItemFamily(entity);
		assertEmptyMainItemFamily(entity);
		assertEmptyRatioFamily(entity);
		assertEmptyMainRatioFamily(entity);
	}

	@Test(dependsOnMethods = { "get" })
	public void getWithItemFamilyOnly() throws Exception {
		Xbrl entity = repository.getWithItemFamilyOnly(stockCode, reportType,
				year, season);
		assertEmptyInfoFamily(entity);
		assertEmptyInstanceFamily(entity);
		assertItemFamily(entity);
		assertEmptyMainItemFamily(entity);
		assertEmptyRatioFamily(entity);
		assertEmptyMainRatioFamily(entity);
	}

	@Test(dependsOnMethods = { "get" })
	public void getWithMainItemFamilyOnly() throws Exception {
		Xbrl entity = repository.getWithMainItemFamilyOnly(stockCode,
				reportType, year, season);
		assertEmptyInfoFamily(entity);
		assertEmptyInstanceFamily(entity);
		assertEmptyItemFamily(entity);
		assertMainItemFamily(entity);
		assertEmptyRatioFamily(entity);
		assertEmptyMainRatioFamily(entity);
	}

	@Test(dependsOnMethods = { "get" })
	public void getWithRatioFamilyOnly() throws Exception {
		Xbrl entity = repository.getWithRatioFamilyOnly(stockCode, reportType,
				year, season);
		assertEmptyInfoFamily(entity);
		assertEmptyInstanceFamily(entity);
		assertEmptyItemFamily(entity);
		assertEmptyMainItemFamily(entity);
		assertRatioFamily(entity);
		assertEmptyMainRatioFamily(entity);
	}

	@Test(dependsOnMethods = { "get" })
	public void getWithMainRatioFamilyOnly() throws Exception {
		Xbrl entity = repository.getWithMainRatioFamilyOnly(stockCode,
				reportType, year, season);
		assertEmptyInfoFamily(entity);
		assertEmptyInstanceFamily(entity);
		assertEmptyItemFamily(entity);
		assertEmptyMainItemFamily(entity);
		assertEmptyRatioFamily(entity);
		assertMainRatioFamily(entity);
	}

	@Test(dependsOnMethods = { "put" })
	public void get() throws Exception {
		Xbrl entity = repository.get(stockCode, reportType, year, season);
		assertInfoFamily(entity);
		assertInstanceFamily(entity);
		assertItemFamily(entity);
		assertMainItemFamily(entity);
		assertRatioFamily(entity);
		assertMainRatioFamily(entity);
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
		Assert.assertEquals(fam.getVersion(), version);
		Assert.assertEquals(fam.getBalanceSheetContext(), balanceSheetContext);
		Assert.assertEquals(fam.getStatementOfComprehensiveIncomeContext(),
				statementOfComprehensiveIncomeContext);
		Assert.assertEquals(fam.getStatementOfCashFlowsContext(),
				statementOfCashFlowsContext);
		Assert.assertEquals(fam.getStatementOfChangesInEquityContext(),
				statementOfChangesInEquityContext);
	}

	private void assertEmptyInfoFamily(Xbrl entity) {
		InfoFamily fam = entity.getInfoFamily();
		Assert.assertEquals(fam.getLatestQualifierAndValueAsMap().size(), 0);
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

	private void assertEmptyInstanceFamily(Xbrl entity) {
		InstanceFamily fam = entity.getInstanceFamily();
		Assert.assertEquals(fam.getLatestQualifierAndValueAsMap().size(), 0);
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

	private void assertEmptyItemFamily(Xbrl entity) {
		ItemFamily fam = entity.getItemFamily();
		Assert.assertEquals(fam.getLatestQualifierAndValueAsMap().size(), 0);
	}

	private void generateMainItemFamilyContent(Xbrl entity) {
		MainItemFamily fam = entity.getMainItemFamily();
		fam.set(elementId, periodType, instant, startDate, endDate, ver, value);
	}

	private void assertMainItemFamily(Xbrl entity) {
		MainItemFamily fam = entity.getMainItemFamily();
		Assert.assertEquals(
				fam.get(elementId, periodType, instant, startDate, endDate),
				value);
	}

	private void assertEmptyMainItemFamily(Xbrl entity) {
		MainItemFamily fam = entity.getMainItemFamily();
		Assert.assertEquals(fam.getLatestQualifierAndValueAsMap().size(), 0);
	}

	private void generateRatioFamilyContent(Xbrl entity) {
		RatioFamily fam = entity.getRatioFamily();
		fam.setRatio(elementId, periodType, instant, startDate, endDate, ver,
				ratio);
	}

	private void assertRatioFamily(Xbrl entity) {
		RatioFamily fam = entity.getRatioFamily();
		Assert.assertEquals(fam.getRatio(elementId, periodType, instant,
				startDate, endDate), ratio);
	}

	private void assertEmptyRatioFamily(Xbrl entity) {
		RatioFamily fam = entity.getRatioFamily();
		Assert.assertEquals(fam.getLatestQualifierAndValueAsMap().size(), 0);
	}

	private void generateMainRatioFamilyContent(Xbrl entity) {
		MainRatioFamily fam = entity.getMainRatioFamily();
		fam.setRatio(elementId, periodType, instant, startDate, endDate, ver,
				ratio);
	}

	private void assertMainRatioFamily(Xbrl entity) {
		MainRatioFamily fam = entity.getMainRatioFamily();
		Assert.assertEquals(fam.getRatio(elementId, periodType, instant,
				startDate, endDate), ratio);
	}

	private void assertEmptyMainRatioFamily(Xbrl entity) {
		MainRatioFamily fam = entity.getMainRatioFamily();
		Assert.assertEquals(fam.getLatestQualifierAndValueAsMap().size(), 0);
	}
}
