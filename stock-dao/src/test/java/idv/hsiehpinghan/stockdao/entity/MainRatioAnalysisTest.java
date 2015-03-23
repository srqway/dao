package idv.hsiehpinghan.stockdao.entity;

import idv.hsiehpinghan.datetimeutility.utility.DateUtility;
import idv.hsiehpinghan.stockdao.entity.MainRatioAnalysis.RowKey;
import idv.hsiehpinghan.stockdao.entity.MainRatioAnalysis.TTestFamily;
import idv.hsiehpinghan.stockdao.enumeration.ReportType;

import java.math.BigDecimal;
import java.util.Date;

import org.testng.Assert;
import org.testng.annotations.Test;

public class MainRatioAnalysisTest {
	private Date ver = DateUtility.getDate(2015, 2, 3);
	private BigDecimal degreeOfFreedom = new BigDecimal("1.1");
	private String elementId = "elementId";
	private String stockCode = "stockCode";
	private ReportType reportType = ReportType.CONSOLIDATED_STATEMENT;
	private String chineseName = "chineseName";
	private BigDecimal confidenceInterval = new BigDecimal("6.6");
	private String englishName = "englishName";
	private BigDecimal statistic = new BigDecimal("8.8");
	private BigDecimal pValue = new BigDecimal("9.9");
	private BigDecimal sampleMean = new BigDecimal("10.10");
	private int season = 2;
	private int year = 2015;
	private BigDecimal hypothesizedMean = new BigDecimal("13.13");

	@Test
	public void bytesConvert() {
		MainRatioAnalysis entity = new MainRatioAnalysis();
		testRowKey(entity);
		testTTestFamily(entity);
	}

	private void testRowKey(MainRatioAnalysis entity) {
		RowKey key = entity.new RowKey(stockCode, reportType, year, season,
				entity);
		Assert.assertEquals(key.getStockCode(), stockCode);
		Assert.assertEquals(key.getReportType(), reportType);
		Assert.assertEquals(key.getYear(), year);
		Assert.assertEquals(key.getSeason(), season);
	}

	private void testTTestFamily(MainRatioAnalysis entity) {
		generateTTestFamilyContent(entity);
		assertTTestFamily(entity);
	}

	private void generateTTestFamilyContent(MainRatioAnalysis entity) {
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

	private void assertTTestFamily(MainRatioAnalysis entity) {
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
