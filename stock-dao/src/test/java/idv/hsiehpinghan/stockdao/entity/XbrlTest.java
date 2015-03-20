package idv.hsiehpinghan.stockdao.entity;

import idv.hsiehpinghan.datetimeutility.utility.DateUtility;
import idv.hsiehpinghan.stockdao.entity.Xbrl.GrowthFamily;
import idv.hsiehpinghan.stockdao.entity.Xbrl.InfoFamily;
import idv.hsiehpinghan.stockdao.entity.Xbrl.InstanceFamily;
import idv.hsiehpinghan.stockdao.entity.Xbrl.InstanceFamily.InstanceValue;
import idv.hsiehpinghan.stockdao.entity.Xbrl.ItemFamily;
import idv.hsiehpinghan.stockdao.entity.Xbrl.MainRatioFamily;
import idv.hsiehpinghan.stockdao.entity.Xbrl.RatioFamily;
import idv.hsiehpinghan.stockdao.entity.Xbrl.RowKey;
import idv.hsiehpinghan.stockdao.enumeration.PeriodType;
import idv.hsiehpinghan.stockdao.enumeration.ReportType;
import idv.hsiehpinghan.stockdao.enumeration.UnitType;
import idv.hsiehpinghan.xbrlassistant.enumeration.XbrlTaxonomyVersion;

import java.math.BigDecimal;
import java.util.Date;

import org.testng.Assert;
import org.testng.annotations.Test;

public class XbrlTest {
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
	private BigDecimal ratio = new BigDecimal("10.10");
	private String statementOfCashFlowsContext = "statementOfCashFlowsContext";
	private String balanceSheetContext = "balanceSheetContext";
	private PeriodType periodType = PeriodType.DURATION;
	private BigDecimal value = new BigDecimal("14.14");
	private String statementOfComprehensiveIncomeContext = "statementOfComprehensiveIncomeContext";
	private int year = 2015;
	private Date instant = DateUtility.getDate(2015, 2, 3);

	@Test
	public void bytesConvert() {
		Xbrl entity = new Xbrl();
		testRowKey(entity);
		testInfoFamily(entity);
		testInstanceFamily(entity);
		testItemFamily(entity);
		testGrowthFamily(entity);
		testRatioFamily(entity);
		testMainRatioFamily(entity);
	}

	private void testRowKey(Xbrl entity) {
		RowKey key = entity.new RowKey(stockCode, reportType, year, season,
				entity);
		Assert.assertEquals(key.getStockCode(), stockCode);
		Assert.assertEquals(key.getReportType(), reportType);
		Assert.assertEquals(key.getYear(), year);
		Assert.assertEquals(key.getSeason(), season);
	}

	private void testInfoFamily(Xbrl entity) {
		generateInfoFamilyContent(entity);
		assertInfoFamily(entity);
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

	private void testInstanceFamily(Xbrl entity) {
		generateInstanceFamilyContent(entity);
		assertInstanceFamily(entity);
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

	private void testItemFamily(Xbrl entity) {
		generateItemFamilyContent(entity);
		assertItemFamily(entity);
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

	private void testGrowthFamily(Xbrl entity) {
		generateGrowthFamilyContent(entity);
		assertGrowthFamily(entity);
	}

	private void generateGrowthFamilyContent(Xbrl entity) {
		GrowthFamily fam = entity.getGrowthFamily();
		fam.setRatio(elementId, periodType, instant, startDate, endDate, ver,
				ratio);
	}

	private void assertGrowthFamily(Xbrl entity) {
		GrowthFamily fam = entity.getGrowthFamily();
		Assert.assertEquals(fam.getRatio(elementId, periodType, instant,
				startDate, endDate), ratio);
	}

	private void testRatioFamily(Xbrl entity) {
		generateRatioFamilyContent(entity);
		assertRatioFamily(entity);
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

	private void testMainRatioFamily(Xbrl entity) {
		generateMainRatioFamilyContent(entity);
		assertMainRatioFamily(entity);
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
}
