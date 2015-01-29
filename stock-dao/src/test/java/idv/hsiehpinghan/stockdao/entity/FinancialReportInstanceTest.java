package idv.hsiehpinghan.stockdao.entity;

import idv.hsiehpinghan.hbaseassistant.utility.HbaseEntityTestUtility;
import idv.hsiehpinghan.stockdao.entity.FinancialReportInstance.InfoFamily.InfoQualifier;
import idv.hsiehpinghan.stockdao.entity.FinancialReportInstance.InfoFamily.InfoValue;
import idv.hsiehpinghan.stockdao.entity.FinancialReportInstance.InstanceFamily.InstanceQualifier;
import idv.hsiehpinghan.stockdao.entity.FinancialReportInstance.InstanceFamily.InstanceValue;
import idv.hsiehpinghan.stockdao.entity.FinancialReportInstance.RowKey;
import idv.hsiehpinghan.stockdao.enumeration.ReportType;

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
		RowKey key = entity.new RowKey(stockCode, reportType, year, season,
				entity);
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
		InstanceQualifier instanceQualifier1 = entity.getInstanceFamily().new InstanceQualifier(
				"elementId", "instant", new Date());
		HbaseEntityTestUtility.toBytesFromBytes(instanceQualifier1);
		InstanceQualifier instanceQualifier2 = entity.getInstanceFamily().new InstanceQualifier(
				"elementId", "duration", new Date(), new Date());
		HbaseEntityTestUtility.toBytesFromBytes(instanceQualifier2);
		// Test instanceValue.
		InstanceValue instanceValue = entity.getInstanceFamily().new InstanceValue(
				"TWD", BigDecimal.TEN);
		HbaseEntityTestUtility.toBytesFromBytes(instanceValue);
	}

}
