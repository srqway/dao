package idv.hsiehpinghan.mopsdao.repository;

import idv.hsiehpinghan.mopsdao.entity.FinancialReportData;
import idv.hsiehpinghan.mopsdao.entity.FinancialReportData.GrowthFamily;
import idv.hsiehpinghan.mopsdao.entity.FinancialReportData.GrowthFamily.GrowthValue;
import idv.hsiehpinghan.mopsdao.entity.FinancialReportData.ItemFamily;
import idv.hsiehpinghan.mopsdao.entity.FinancialReportData.ItemFamily.ItemValue;
import idv.hsiehpinghan.mopsdao.entity.FinancialReportData.RatioFamily;
import idv.hsiehpinghan.mopsdao.entity.FinancialReportData.RatioFamily.RatioValue;
import idv.hsiehpinghan.mopsdao.enumeration.ReportType;
import idv.hsiehpinghan.mopsdao.suit.TestngSuitSetting;
import idv.hsiehpinghan.xbrlassistant.xbrl.Instance;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

import junit.framework.Assert;

import org.apache.commons.lang3.time.DateUtils;
import org.springframework.context.ApplicationContext;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class FinancialReportDataRepositoryTest {
	private FinancialReportDataRepository repository;
	private String stockCode = "1101";
	private ReportType reportType = ReportType.CONSOLIDATED_STATEMENT;
	private int year = 2013;
	private int season = 1;
	private String durationEleId = "tifrs-SCF_CashInflowOutflowGeneratedFromOperations";
	private String instantEleId = "tifrs-bsci-ci_NoncurrentFinancialAssetsAtCost";
	private BigDecimal durationValue = new BigDecimal("2490393000");
	private BigDecimal instantValue = new BigDecimal("616490000");
	private String durationName = "durationName";
	private String instantName = "instantName";
	private Date startDate;
	private Date endDate;
	private Date instant;

	@BeforeClass
	public void beforeClass() throws Exception {
		ApplicationContext applicationContext = TestngSuitSetting
				.getApplicationContext();
		repository = applicationContext
				.getBean(FinancialReportDataRepository.class);
		dropAndCreateTable();
		startDate = DateUtils.parseDate("20130101", "yyyyMMdd");
		endDate = DateUtils.parseDate("20130331", "yyyyMMdd");
		instant = DateUtils.parseDate("20130331", "yyyyMMdd");
	}

	@Test
	public void put() throws Exception {
		FinancialReportData entity = generateEntity();
		Assert.assertFalse(repository.exists(entity.getRowKey()));
		repository.put(entity);
		Assert.assertTrue(repository.exists(entity.getRowKey()));
	}

	@Test(dependsOnMethods = { "put" })
	public void get() throws Exception {
		FinancialReportData entity = repository.get(stockCode, reportType,
				year, season);
		testItemFamily(entity);
		testRatioFamily(entity);
		testGrowthFamily(entity);
	}

	private void testGrowthFamily(FinancialReportData entity) {
		GrowthFamily growthFamily = entity.getGrowthFamily();
		// Test duration
		GrowthValue durValue = growthFamily.getValue(durationEleId,
				Instance.Attribute.DURATION, startDate, endDate);
		Assert.assertEquals(durationValue.toString(), durValue.getValue()
				.toString());
		// Test instant.
		GrowthValue insValue = growthFamily.getValue(instantEleId,
				Instance.Attribute.INSTANT, instant);
		Assert.assertEquals(instantValue.toString(), insValue.getValue()
				.toString()); // TODO
	}

	private void testRatioFamily(FinancialReportData entity) {
		RatioFamily ratioFamily = entity.getRatioFamily();
		// Test duration
		RatioValue durValue = ratioFamily.getValue(durationName,
				Instance.Attribute.DURATION, startDate, endDate);
		Assert.assertEquals(durationValue.toString(), durValue.getValue()
				.toString());
		// Test instant.
		RatioValue insValue = ratioFamily.getValue(instantName,
				Instance.Attribute.INSTANT, instant);
		Assert.assertEquals(instantValue.toString(), insValue.getValue()
				.toString());
	}

	private void testItemFamily(FinancialReportData entity) {
		ItemFamily itemFamily = entity.getItemFamily();
		// Test duration
		ItemValue durValue = itemFamily.getValue(durationEleId,
				Instance.Attribute.DURATION, startDate, endDate);
		Assert.assertEquals(durationValue.toString(), durValue.getValue()
				.toString());
		// Test instant.
		ItemValue insValue = itemFamily.getValue(instantEleId,
				Instance.Attribute.INSTANT, instant);
		Assert.assertEquals(instantValue.toString(), insValue.getValue()
				.toString());
	}

	private FinancialReportData generateEntity() {
		FinancialReportData entity = new FinancialReportData();
		generateRowKey(entity);
		generateColumnFamily(entity);
		return entity;
	}

	private void generateRowKey(FinancialReportData entity) {
		entity.new Key(stockCode, reportType, year, season, entity);
	}

	private void generateColumnFamily(FinancialReportData entity) {
		Date date = Calendar.getInstance().getTime();
		generateItemFamily(entity, date);
		generateRatioFamily(entity, date);
		generateGrowthFamily(entity, date);
	}

	private void generateGrowthFamily(FinancialReportData entity, Date date) {
		GrowthFamily growthFamily = entity.getGrowthFamily();
		// Test duration.
		growthFamily.add(durationEleId, date, Instance.Attribute.DURATION,
				startDate, endDate, durationValue);
		// Test instant.
		growthFamily.add(instantEleId, date, Instance.Attribute.INSTANT,
				instant, instantValue); // TODO
	}

	private void generateRatioFamily(FinancialReportData entity, Date date) {
		RatioFamily ratioFamily = entity.getRatioFamily();
		// Test duration.
		ratioFamily.add(durationName, Instance.Attribute.DURATION, startDate,
				endDate, date, durationValue);
		// Test instant.
		ratioFamily.add(instantName, Instance.Attribute.INSTANT, instant, date,
				instantValue);
	}

	private void generateItemFamily(FinancialReportData entity, Date date) {
		ItemFamily itemFamily = entity.getItemFamily();
		// Test duration.
		itemFamily.add(durationEleId, date, Instance.Attribute.DURATION,
				startDate, endDate, durationValue);
		// Test instant.
		itemFamily.add(instantEleId, date, Instance.Attribute.INSTANT, instant,
				instantValue);
	}

	private void dropAndCreateTable() throws Exception {
		String tableName = repository.getTargetTableName();
		if (repository.isTableExists(tableName)) {
			repository.dropTable(tableName);
			repository.createTable(repository.getTargetTableClass());
		}
	}
}
