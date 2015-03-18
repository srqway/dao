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
	private BigDecimal pValue = new BigDecimal("14.14");
	private String elementId = "elementId";
	private String stockCode = "stockCode";
	private BigDecimal sampleMean = new BigDecimal("17.17");
	private ReportType reportType = ReportType.ENTERPRISE_STATEMENT;
	private int season = 2;
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
				reportType, year, season, elementId);
		generateTTestFamilyContent(entity);
		repository.put(entity);
		Assert.assertTrue(repository.exists(entity.getRowKey()));
	}

	@Test(dependsOnMethods = { "put" })
	public void get() throws Exception {
		RatioDifference entity = repository.get(stockCode, reportType, year,
				season, elementId);
		assertTTestFamily(entity);
	}

	private void generateTTestFamilyContent(RatioDifference entity) {
		TTestFamily fam = entity.getTTestFamily();
		fam.setStatistic(ver, statistic);
		fam.setDegreeOfFreedom(ver, degreeOfFreedom);
		fam.setConfidenceInterval(ver, confidenceInterval);
		fam.setSampleMean(ver, sampleMean);
		fam.setHypothesizedMean(ver, hypothesizedMean);
		fam.setPValue(ver, pValue);
	}

	private void assertTTestFamily(RatioDifference entity) {
		TTestFamily fam = entity.getTTestFamily();
		Assert.assertEquals(statistic, fam.getStatistic());
		Assert.assertEquals(degreeOfFreedom, fam.getDegreeOfFreedom());
		Assert.assertEquals(confidenceInterval, fam.getConfidenceInterval());
		Assert.assertEquals(sampleMean, fam.getSampleMean());
		Assert.assertEquals(hypothesizedMean, fam.getHypothesizedMean());
		Assert.assertEquals(pValue, fam.getPValue());
	}
}
