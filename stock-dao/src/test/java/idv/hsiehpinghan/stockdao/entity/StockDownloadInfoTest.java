package idv.hsiehpinghan.stockdao.entity;

import idv.hsiehpinghan.hbaseassistant.utility.HbaseEntityTestUtility;
import idv.hsiehpinghan.stockdao.entity.StockDownloadInfo.ReportTypeFamily.ReportTypeQualifier;
import idv.hsiehpinghan.stockdao.entity.StockDownloadInfo.ReportTypeFamily.ReportTypeValue;
import idv.hsiehpinghan.stockdao.entity.StockDownloadInfo.RowKey;
import idv.hsiehpinghan.stockdao.entity.StockDownloadInfo.SeasonFamily.SeasonQualifier;
import idv.hsiehpinghan.stockdao.entity.StockDownloadInfo.SeasonFamily.SeasonValue;
import idv.hsiehpinghan.stockdao.entity.StockDownloadInfo.StockCodeFamily.StockCodeQualifier;
import idv.hsiehpinghan.stockdao.entity.StockDownloadInfo.StockCodeFamily.StockCodeValue;
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
		// Test row key.
		RowKey key = entity.new RowKey(tableName, entity);
		HbaseEntityTestUtility.toBytesFromBytes(key);
		// Test stockCodeQualifier.
		StockCodeQualifier stockCodeQualifier = entity.getStockCodeFamily().new StockCodeQualifier(
				allStockCodeType);
		HbaseEntityTestUtility.toBytesFromBytes(stockCodeQualifier);
		// Test stockCodeValue.
		StockCodeValue stockCodeValue = entity.getStockCodeFamily().new StockCodeValue(
				stockCodes);
		HbaseEntityTestUtility.toBytesFromBytes(stockCodeValue);
		// Test reportTypeQualifier.
		ReportTypeQualifier reportTypeQualifier = entity.getReportTypeFamily().new ReportTypeQualifier(
				allReportType);
		HbaseEntityTestUtility.toBytesFromBytes(reportTypeQualifier);
		// Test reportTypeValue.
		ReportTypeValue reportTypeValue = entity.getReportTypeFamily().new ReportTypeValue(
				reportTypes);
		HbaseEntityTestUtility.toBytesFromBytes(reportTypeValue);
		// Test yearQualifier.
		YearQualifier yearQualifier = entity.getYearFamily().new YearQualifier(
				allYear);
		HbaseEntityTestUtility.toBytesFromBytes(yearQualifier);
		// Test yearValue.
		YearValue yearValue = entity.getYearFamily().new YearValue(years);
		HbaseEntityTestUtility.toBytesFromBytes(yearValue);
		// Test seasonQualifier.
		SeasonQualifier seasonQualifier = entity.getSeasonFamily().new SeasonQualifier(
				allSeason);
		HbaseEntityTestUtility.toBytesFromBytes(seasonQualifier);
		// Test seasonValue.
		SeasonValue seasonValue = entity.getSeasonFamily().new SeasonValue(
				seasons);
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
