package idv.hsiehpinghan.mopsdao.entity;

import idv.hsiehpinghan.hbaseassistant.utility.HbaseEntityTestUtility;
import idv.hsiehpinghan.mopsdao.entity.MopsDownloadInfo.ReportTypeFamily.ReportTypeQualifier;
import idv.hsiehpinghan.mopsdao.entity.MopsDownloadInfo.ReportTypeFamily.ReportTypeValue;
import idv.hsiehpinghan.mopsdao.entity.MopsDownloadInfo.RowKey;
import idv.hsiehpinghan.mopsdao.entity.MopsDownloadInfo.SeasonFamily.SeasonQualifier;
import idv.hsiehpinghan.mopsdao.entity.MopsDownloadInfo.SeasonFamily.SeasonValue;
import idv.hsiehpinghan.mopsdao.entity.MopsDownloadInfo.YearFamily.YearQualifier;
import idv.hsiehpinghan.mopsdao.entity.MopsDownloadInfo.YearFamily.YearValue;
import idv.hsiehpinghan.mopsdao.enumeration.ReportType;
import idv.hsiehpinghan.mopsdao.repository.FinancialReportInstanceRepository;

import java.util.Set;
import java.util.TreeSet;

import org.testng.annotations.Test;

public class MopsDownloadInfoTest {
	private String tableName = FinancialReportInstanceRepository.class
			.getSimpleName();
	private String allReportType = MopsDownloadInfo.ReportTypeFamily.ReportTypeQualifier.ALL;
	private String allYear = MopsDownloadInfo.YearFamily.YearQualifier.ALL;
	private String allSeason = MopsDownloadInfo.SeasonFamily.SeasonQualifier.ALL;
	private Set<ReportType> reportTypes = generateReportTypes();
	private Set<Integer> years = generateYears();
	private Set<Integer> seasons = generateSesons();

	@Test
	public void toBytesFromBytes() {
		MopsDownloadInfo entity = new MopsDownloadInfo();
		// Test row key.
		RowKey key = entity.new RowKey(tableName, entity);
		HbaseEntityTestUtility.toBytesFromBytes(key);
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
