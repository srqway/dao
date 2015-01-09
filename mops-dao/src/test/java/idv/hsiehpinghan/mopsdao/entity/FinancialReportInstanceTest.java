package idv.hsiehpinghan.mopsdao.entity;

import idv.hsiehpinghan.mopsdao.entity.FinancialReportInstance.Key;
import idv.hsiehpinghan.mopsdao.enumeration.ReportType;

import java.io.IOException;

import junit.framework.Assert;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class FinancialReportInstanceTest {
	private FinancialReportInstance.Key key;
	private String stockCode = "1101";
	private ReportType reportType = ReportType.CONSOLIDATED_STATEMENT;
	private int year = 2013;
	private int season = 1;

	@BeforeClass
	public void beforeClass() throws IOException {
		FinancialReportInstance entity = new FinancialReportInstance();
		key = entity.new Key(stockCode, reportType, year, season, entity);
	}

	@Test
	public void rowKey() {
		Key newKey = getKey(key.toBytes());

		System.err.println(key);
		System.err.println(newKey);

		Assert.assertEquals(key, newKey);
	}

	private Key getKey(byte[] bytes) {
		FinancialReportInstance entity = new FinancialReportInstance();
		return entity.new Key(bytes, entity);
	}
}
