package idv.hsiehpinghan.stockdao.entity;

import idv.hsiehpinghan.hbaseassistant.utility.HbaseEntityTestUtility;
import idv.hsiehpinghan.stockdao.entity.StockDownloadInfo.ReportTypeFamily;
import idv.hsiehpinghan.stockdao.entity.StockDownloadInfo.ReportTypeFamily.ReportTypeQualifier;
import idv.hsiehpinghan.stockdao.entity.StockDownloadInfo.ReportTypeFamily.ReportTypeValue;
import idv.hsiehpinghan.stockdao.entity.StockDownloadInfo.RowKey;
import idv.hsiehpinghan.stockdao.entity.StockDownloadInfo.SeasonFamily;
import idv.hsiehpinghan.stockdao.entity.StockDownloadInfo.SeasonFamily.SeasonQualifier;
import idv.hsiehpinghan.stockdao.entity.StockDownloadInfo.SeasonFamily.SeasonValue;
import idv.hsiehpinghan.stockdao.entity.StockDownloadInfo.StockCodeFamily;
import idv.hsiehpinghan.stockdao.entity.StockDownloadInfo.StockCodeFamily.StockCodeQualifier;
import idv.hsiehpinghan.stockdao.entity.StockDownloadInfo.StockCodeFamily.StockCodeValue;
import idv.hsiehpinghan.stockdao.entity.StockDownloadInfo.YearFamily;
import idv.hsiehpinghan.stockdao.entity.StockDownloadInfo.YearFamily.YearQualifier;
import idv.hsiehpinghan.stockdao.entity.StockDownloadInfo.YearFamily.YearValue;
import idv.hsiehpinghan.stockdao.enumeration.ReportType;
import idv.hsiehpinghan.stockdao.repository.hbase.FinancialReportInstanceRepository;

import java.util.Set;
import java.util.TreeSet;

import org.testng.annotations.Test;

public class StockDownloadInfoTest {
	private String tableName = FinancialReportInstanceRepository.class
			.getSimpleName();
	private String allStockCodeType = StockDownloadInfo.StockCodeFamily.StockCodeQualifier.ALL;
	private String allReportType = StockDownloadInfo.ReportTypeFamily.ReportTypeQualifier.ALL;
	private String allYear = StockDownloadInfo.YearFamily.YearQualifier.ALL;
	private String allSeason = StockDownloadInfo.SeasonFamily.SeasonQualifier.ALL;
	private Set<String> stockCodes = generateStockCodes();
	private Set<ReportType> reportTypes = generateReportTypes();
	private Set<Integer> years = generateYears();
	private Set<Integer> seasons = generateSesons();

	@Test
	public void toBytesFromBytes() {
		StockDownloadInfo entity = new StockDownloadInfo();
		testRowKey(entity);
		testStockCodeFamily(entity);
		testReportTypeFamily(entity);
		testYearFamily(entity);
		testSeasonFamily(entity);
	}

	private void testRowKey(StockDownloadInfo entity) {
		RowKey key = entity.new RowKey(tableName, entity);
		HbaseEntityTestUtility.toBytesFromBytes(key);
	}

	private void testStockCodeFamily(StockDownloadInfo entity) {
		StockCodeFamily stockCodeFamily = entity.getStockCodeFamily();
		;
		testStockCodeQualifier(stockCodeFamily);
		testStockCodeValue(stockCodeFamily);
	}

	private void testStockCodeQualifier(StockCodeFamily stockCodeFamily) {
		StockCodeQualifier stockCodeQualifier = stockCodeFamily.new StockCodeQualifier(
				allStockCodeType);
		HbaseEntityTestUtility.toBytesFromBytes(stockCodeQualifier);
	}

	private void testStockCodeValue(StockCodeFamily stockCodeFamily) {
		StockCodeValue stockCodeValue = stockCodeFamily.new StockCodeValue(
				stockCodes);
		HbaseEntityTestUtility.toBytesFromBytes(stockCodeValue);
	}

	private void testReportTypeFamily(StockDownloadInfo entity) {
		ReportTypeFamily reportTypeFamily = entity.getReportTypeFamily();
		testReportTypeQualifier(reportTypeFamily);
		testReportTypeValue(reportTypeFamily);
	}

	private void testReportTypeQualifier(ReportTypeFamily reportTypeFamily) {
		ReportTypeQualifier reportTypeQualifier = reportTypeFamily.new ReportTypeQualifier(
				allReportType);
		HbaseEntityTestUtility.toBytesFromBytes(reportTypeQualifier);
	}

	private void testReportTypeValue(ReportTypeFamily reportTypeFamily) {
		ReportTypeValue reportTypeValue = reportTypeFamily.new ReportTypeValue(
				reportTypes);
		HbaseEntityTestUtility.toBytesFromBytes(reportTypeValue);
	}

	private void testYearFamily(StockDownloadInfo entity) {
		YearFamily yearFamily = entity.getYearFamily();
		testYearQualifier(yearFamily);
		testYearValue(yearFamily);
	}

	private void testYearQualifier(YearFamily yearFamily) {
		YearQualifier yearQualifier = yearFamily.new YearQualifier(allYear);
		HbaseEntityTestUtility.toBytesFromBytes(yearQualifier);
	}

	private void testYearValue(YearFamily yearFamily) {
		YearValue yearValue = yearFamily.new YearValue(years);
		HbaseEntityTestUtility.toBytesFromBytes(yearValue);
	}

	private void testSeasonFamily(StockDownloadInfo entity) {
		SeasonFamily seasonFamily = entity.getSeasonFamily();
		testSeasonQualifier(seasonFamily);
		testSeasonValue(seasonFamily);
	}

	private void testSeasonQualifier(SeasonFamily seasonFamily) {
		SeasonQualifier seasonQualifier = seasonFamily.new SeasonQualifier(
				allSeason);
		HbaseEntityTestUtility.toBytesFromBytes(seasonQualifier);
	}

	private void testSeasonValue(SeasonFamily seasonFamily) {
		SeasonValue seasonValue = seasonFamily.new SeasonValue(seasons);
		HbaseEntityTestUtility.toBytesFromBytes(seasonValue);
	}

	private Set<String> generateStockCodes() {
		Set<String> stockCodes = new TreeSet<String>();
		stockCodes.add("1101");
		stockCodes.add("2330");
		return stockCodes;
	}

	private Set<ReportType> generateReportTypes() {
		Set<ReportType> reportTypes = new TreeSet<ReportType>();
		reportTypes.add(ReportType.CONSOLIDATED_STATEMENT);
		reportTypes.add(ReportType.INDIVIDUAL_STATEMENT);
		return reportTypes;
	}

	private Set<Integer> generateYears() {
		Set<Integer> years = new TreeSet<Integer>();
		years.add(2013);
		years.add(2014);
		years.add(2015);
		return years;
	}

	private Set<Integer> generateSesons() {
		Set<Integer> seasons = new TreeSet<Integer>();
		seasons.add(1);
		seasons.add(2);
		seasons.add(3);
		seasons.add(4);
		return seasons;
	}
}
