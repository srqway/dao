package idv.hsiehpinghan.mopsdao.entity;

import idv.hsiehpinghan.mopsdao.entity.FinancialReportInstance.InfoFamily.InfoQualifier;
import idv.hsiehpinghan.mopsdao.entity.FinancialReportInstance.InfoFamily.InfoValue;
import idv.hsiehpinghan.mopsdao.entity.FinancialReportInstance.InstanceFamily.InstanceQualifier;
import idv.hsiehpinghan.mopsdao.entity.FinancialReportInstance.InstanceFamily.InstanceValue;
import idv.hsiehpinghan.mopsdao.entity.FinancialReportInstance.Key;
import idv.hsiehpinghan.mopsdao.enumeration.ReportType;
import idv.hsiehpinghan.testutility.utility.HbaseEntityTestUtility;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class FinancialReportInstanceTest {
	private String stockCode = "1101";
	private ReportType reportType = ReportType.CONSOLIDATED_STATEMENT;
	private int year = 2013;
	private int season = 1;

	@BeforeClass
	public void beforeClass() throws IOException {
	}

	@Test
	public void toBytesFromBytes() {
		FinancialReportInstance entity = new FinancialReportInstance();
		// Test row key.
		Key key = entity.new Key(stockCode, reportType, year, season, entity);
		HbaseEntityTestUtility.toBytesFromBytes(key);
		// Test infoQualifier.
		InfoQualifier infoQualifier = entity.getInfoFamily().new InfoQualifier(
				"infoTitle");
		HbaseEntityTestUtility.toBytesFromBytes(infoQualifier);
		// Test infoValue.
		InfoValue infoValue = entity.getInfoFamily().new InfoValue(
				"infoContent");
		HbaseEntityTestUtility.toBytesFromBytes(infoValue);
		// Test instanceQualifier.
		InstanceQualifier instanceQualifier = entity.getInstanceFamily().new InstanceQualifier(
				"elementId");
		HbaseEntityTestUtility.toBytesFromBytes(instanceQualifier);
		// Test instanceValue.
		InstanceValue instantValue = entity.getInstanceFamily().new InstanceValue(
				"instant", new Date(), "TWD", BigDecimal.TEN);
		HbaseEntityTestUtility.toBytesFromBytes(instantValue);
		InstanceValue durationValue = entity.getInstanceFamily().new InstanceValue(
				"duration", new Date(), new Date(), "TWD", BigDecimal.TEN);
		HbaseEntityTestUtility.toBytesFromBytes(durationValue);
	}

	// private Key getKey(byte[] bytes) {
	// FinancialReportInstance entity = new FinancialReportInstance();
	// return entity.new Key(bytes, entity);
	// }
}
