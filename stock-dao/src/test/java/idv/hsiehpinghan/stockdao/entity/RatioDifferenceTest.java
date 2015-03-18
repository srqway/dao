package idv.hsiehpinghan.stockdao.entity;

import idv.hsiehpinghan.datetimeutility.utility.DateUtility;
import idv.hsiehpinghan.stockdao.entity.RatioDifference.RowKey;
import idv.hsiehpinghan.stockdao.entity.RatioDifference.TTestFamily;
import idv.hsiehpinghan.stockdao.enumeration.ReportType;

import java.math.BigDecimal;
import java.util.Date;

import org.testng.Assert;
import org.testng.annotations.Test;

public class RatioDifferenceTest {
	private Date ver = DateUtility.getDate(2015, 2, 3);
	private BigDecimal degreeOfFreedom = new BigDecimal("1.1");
	private BigDecimal statistic = new BigDecimal("2.2");
	private BigDecimal pValue = new BigDecimal("3.3");
	private String elementId = "elementId";
	private String stockCode = "stockCode";
	private BigDecimal sampleMean = new BigDecimal("6.6");
	private ReportType reportType = ReportType.CONSOLIDATED_STATEMENT;
	private int season = 2;
	private BigDecimal confidenceInterval = new BigDecimal("9.9");
	private int year = 2015;
	private BigDecimal hypothesizedMean = new BigDecimal("11.11");

	@Test
	public void bytesConvert() {
		RatioDifference entity = new RatioDifference();
		testRowKey(entity);
		testTTestFamily(entity);
	}

	private void testRowKey(RatioDifference entity) {
		RowKey key = entity.new RowKey(stockCode, reportType, year, season,
				elementId, entity);
		Assert.assertEquals(stockCode, key.getStockCode());
		Assert.assertEquals(reportType, key.getReportType());
		Assert.assertEquals(year, key.getYear());
		Assert.assertEquals(season, key.getSeason());
		Assert.assertEquals(elementId, key.getElementId());
	}

	private void testTTestFamily(RatioDifference entity) {
		generateTTestFamilyContent(entity);
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
