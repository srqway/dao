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
	private BigDecimal degreeOfFreedom = new BigDecimal("12.12");
	private BigDecimal statistic = new BigDecimal("13.13");
	private String elementId = "elementId";
	private BigDecimal pValue = new BigDecimal("15.15");
	private String stockCode = "stockCode";
	private BigDecimal sampleMean = new BigDecimal("17.17");
	private ReportType reportType = ReportType.CONSOLIDATED_STATEMENT;
	private int season = 4;
	private BigDecimal confidenceInterval = new BigDecimal("20.20");
	private int year = 2015;
	private BigDecimal hypothesizedMean = new BigDecimal("22.22");
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

	@Test(dependsOnMethods = { "put" })
	public void get() throws Exception {
		RatioDifference entity = repository.get(stockCode, reportType, year,
				season);
		assertTTestFamily(entity);
	}

	private void generateTTestFamilyContent(RatioDifference entity) {
		TTestFamily fam = entity.getTTestFamily();
		fam.setStatistic(elementId, ver, statistic);
		fam.setDegreeOfFreedom(elementId, ver, degreeOfFreedom);
		fam.setConfidenceInterval(elementId, ver, confidenceInterval);
		fam.setSampleMean(elementId, ver, sampleMean);
		fam.setHypothesizedMean(elementId, ver, hypothesizedMean);
		fam.setPValue(elementId, ver, pValue);
	}

	private void assertTTestFamily(RatioDifference entity) {
		TTestFamily fam = entity.getTTestFamily();
		Assert.assertEquals(statistic, fam.getStatistic(elementId));
		Assert.assertEquals(degreeOfFreedom, fam.getDegreeOfFreedom(elementId));
		Assert.assertEquals(confidenceInterval,
				fam.getConfidenceInterval(elementId));
		Assert.assertEquals(sampleMean, fam.getSampleMean(elementId));
		Assert.assertEquals(hypothesizedMean,
				fam.getHypothesizedMean(elementId));
		Assert.assertEquals(pValue, fam.getPValue(elementId));
	}
}
