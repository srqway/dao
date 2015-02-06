package idv.hsiehpinghan.stockdao.entity;

import idv.hsiehpinghan.hbaseassistant.utility.HbaseEntityTestUtility;
import idv.hsiehpinghan.stockdao.entity.FinancialReportInstance.InfoFamily;
import idv.hsiehpinghan.stockdao.entity.FinancialReportInstance.InfoFamily.InfoQualifier;
import idv.hsiehpinghan.stockdao.entity.FinancialReportInstance.InfoFamily.InfoValue;
import idv.hsiehpinghan.stockdao.entity.FinancialReportInstance.InstanceFamily;
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
		testRowKey(entity);
		testInfoFamily(entity);
		testInstance(entity);
	}

	private void testRowKey(FinancialReportInstance entity) {
		RowKey key = entity.new RowKey(stockCode, reportType, year, season,
				entity);
		HbaseEntityTestUtility.toBytesFromBytes(key);
	}

	private void testInfoFamily(FinancialReportInstance entity) {
		InfoFamily infoFamily = entity.getInfoFamily();
		testInfoQualifier(infoFamily);
		testInfoValue(infoFamily);
	}

	private void testInfoQualifier(InfoFamily infoFamily) {
		InfoQualifier infoQualifier = infoFamily.new InfoQualifier("infoTitle");
		HbaseEntityTestUtility.toBytesFromBytes(infoQualifier);
	}

	private void testInfoValue(InfoFamily infoFamily) {
		InfoValue infoValue = infoFamily.new InfoValue("infoContent");
		HbaseEntityTestUtility.toBytesFromBytes(infoValue);
	}

	private void testInstance(FinancialReportInstance entity) {
		InstanceFamily instanceFamily = entity.getInstanceFamily();
		testInstanceQualifier(instanceFamily);
		testInstanceValue(instanceFamily);
	}

	private void testInstanceQualifier(InstanceFamily instanceFamily) {
		InstanceQualifier instanceQualifier1 = instanceFamily.new InstanceQualifier(
				"elementId", "instant", new Date());
		HbaseEntityTestUtility.toBytesFromBytes(instanceQualifier1);
		InstanceQualifier instanceQualifier2 = instanceFamily.new InstanceQualifier(
				"elementId", "duration", new Date(), new Date());
		HbaseEntityTestUtility.toBytesFromBytes(instanceQualifier2);
	}

	private void testInstanceValue(InstanceFamily instanceFamily) {
		InstanceValue instanceValue = instanceFamily.new InstanceValue("TWD",
				BigDecimal.TEN);
		HbaseEntityTestUtility.toBytesFromBytes(instanceValue);
	}
}
