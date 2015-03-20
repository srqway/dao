package idv.hsiehpinghan.stockdao.repository;

import idv.hsiehpinghan.datetimeutility.utility.DateUtility;
import idv.hsiehpinghan.stockdao.entity.RatioDifference;
import idv.hsiehpinghan.stockdao.entity.RatioDifference.TTestFamily;
import idv.hsiehpinghan.stockdao.enumeration.ReportType;
import idv.hsiehpinghan.stockdao.suit.TestngSuitSetting;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.context.ApplicationContext;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class RatioDifferenceRepositoryTest {
	private Date ver = DateUtility.getDate(2015, 2, 3);
	private BigDecimal degreeOfFreedom = new BigDecimal("14.14");
	private String elementId = "elementId";
	private String stockCode = "stockCode";
	private ReportType reportType = ReportType.CONSOLIDATED_STATEMENT;
	private String chineseName = "chineseName";
	private BigDecimal confidenceInterval = new BigDecimal("19.19");
	private String englishName = "englishName";
	private BigDecimal statistic = new BigDecimal("21.21");
	private BigDecimal pValue = new BigDecimal("22.22");
	private BigDecimal sampleMean = new BigDecimal("23.23");
	private int season = 4;
	private int year = 2015;
	private BigDecimal hypothesizedMean = new BigDecimal("26.26");
	private RatioDifferenceRepository repository;

	@BeforeClass
	public void beforeClass() throws Exception {
		ApplicationContext applicationContext = TestngSuitSetting
				.getApplicationContext();
		repository = applicationContext
				.getBean(RatioDifferenceRepository.class);
	}

	@Test
	public void put() throws Exception {
		RatioDifference entity = repository.generateEntity(stockCode,
				reportType, year, season);
		generateTTestFamilyContent(entity);
		repository.put(entity);
		Assert.assertTrue(repository.exists(entity.getRowKey()));
	}

	@Test(dependsOnMethods = { "get" })
	public void getWithTTestFamilyOnly() throws Exception {
		RatioDifference entity = repository.getWithTTestFamilyOnly(stockCode,
				reportType, year, season);
		assertTTestFamily(entity);
	}

	@Test(dependsOnMethods = { "put" })
	public void get() throws Exception {
		RatioDifference entity = repository.get(stockCode, reportType, year,
				season);
		assertTTestFamily(entity);
	}

	private void generateTTestFamilyContent(RatioDifference entity) {
		TTestFamily fam = entity.getTTestFamily();
		fam.setChineseName(elementId, ver, chineseName);
		fam.setEnglishName(elementId, ver, englishName);
		fam.setStatistic(elementId, ver, statistic);
		fam.setDegreeOfFreedom(elementId, ver, degreeOfFreedom);
		fam.setConfidenceInterval(elementId, ver, confidenceInterval);
		fam.setSampleMean(elementId, ver, sampleMean);
		fam.setHypothesizedMean(elementId, ver, hypothesizedMean);
		fam.setPValue(elementId, ver, pValue);
	}

	private void assertTTestFamily(RatioDifference entity) {
		TTestFamily fam = entity.getTTestFamily();
		Assert.assertEquals(fam.getChineseName(elementId), chineseName);
		Assert.assertEquals(fam.getEnglishName(elementId), englishName);
		Assert.assertEquals(fam.getStatistic(elementId), statistic);
		Assert.assertEquals(fam.getDegreeOfFreedom(elementId), degreeOfFreedom);
		Assert.assertEquals(fam.getConfidenceInterval(elementId),
				confidenceInterval);
		Assert.assertEquals(fam.getSampleMean(elementId), sampleMean);
		Assert.assertEquals(fam.getHypothesizedMean(elementId),
				hypothesizedMean);
		Assert.assertEquals(fam.getPValue(elementId), pValue);
	}
}
