package idv.hsiehpinghan.stockdao.entity;

import idv.hsiehpinghan.stockdao.entity.Xbrl.InfoFamily;
import idv.hsiehpinghan.stockdao.entity.Xbrl.InstanceFamily;
import idv.hsiehpinghan.stockdao.entity.Xbrl.InstanceFamily.InstanceValue;
import idv.hsiehpinghan.stockdao.entity.Xbrl.RowKey;
import idv.hsiehpinghan.stockdao.enumeration.PeriodType;
import idv.hsiehpinghan.stockdao.enumeration.ReportType;
import idv.hsiehpinghan.stockdao.enumeration.UnitType;

import java.math.BigDecimal;
import java.util.Date;

import junit.framework.Assert;

import org.apache.commons.lang3.time.DateUtils;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class XbrlTest {
	private Date ver;
	private String stockCode = "2330";
	private ReportType reportType = ReportType.CONSOLIDATED_STATEMENT;
	private int year = 2015;
	private int season = 1;
	private String name = "name";
	private String elementId = "elementId";
	private PeriodType periodType = PeriodType.DURATION;
	private Date instant;
	private Date startDate;
	private Date endDate;
	private UnitType unitType = UnitType.SHARES;
	private BigDecimal value = new BigDecimal("1.1");

	@BeforeClass
	public void beforeClass() throws Exception {
		ver = DateUtils.parseDate("2015/01/01", "yyyy/MM/dd");
		instant = DateUtils.parseDate("2015/02/02", "yyyy/MM/dd");
		startDate = DateUtils.parseDate("2015/02/11", "yyyy/MM/dd");
		endDate = DateUtils.parseDate("2015/02/22", "yyyy/MM/dd");
	}

	@Test
	public void bytesConvert() {
		Xbrl entity = new Xbrl();
		testRowKey(entity);
		testInfoFamily(entity);
		testInstanceFamily(entity);
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
		InstanceValue instanceValue = fam.getInstanceValue(elementId,
				periodType, instant, startDate, endDate);
		Assert.assertEquals(unitType, instanceValue.getUnitType());
		Assert.assertEquals(value, instanceValue.getValue());
	}

	private void testInfoFamily(Xbrl entity) {
		generateInfoFamilyContent(entity);
		assertInfoFamily(entity);
	}

	private void generateInfoFamilyContent(Xbrl entity) {
		InfoFamily fam = entity.getInfoFamily();
		fam.setName(ver, name);
	}

	private void assertInfoFamily(Xbrl entity) {
		InfoFamily fam = entity.getInfoFamily();
		Assert.assertEquals(name, fam.getName());
	}

	private void testRowKey(Xbrl entity) {
		RowKey key = entity.new RowKey(stockCode, reportType, year, season,
				entity);
		Assert.assertEquals(stockCode, key.getStockCode());
		Assert.assertEquals(reportType, key.getReportType());
		Assert.assertEquals(year, key.getYear());
		Assert.assertEquals(season, key.getSeason());
	}
}
