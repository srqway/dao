package idv.hsiehpinghan.stockdao.enumeration;

import java.util.Locale;

public enum ReportType {
	CONSOLIDATED_STATEMENT("合併", "consolidated"), INDIVIDUAL_STATEMENT(
			"個別", "individual"), ENTERPRISE_STATEMENT("個體",
			"enterprise");
	private String chineseName;
	private String englishName;

	private ReportType(String chineseName, String englishName) {
		this.chineseName = chineseName;
		this.englishName = englishName;
	}

	public String getChineseName() {
		return this.chineseName;
	}

	public String getEnglishName() {
		return this.englishName;
	}

	public String getName(Locale locale) {
		if (Locale.ENGLISH.getLanguage().equals(locale.getLanguage())) {
			return getEnglishName();
		} else {
			return getChineseName();
		}
	}

	public static ReportType getMopsReportType(String code) {
		switch (code) {
		case "cr":
			return CONSOLIDATED_STATEMENT;
		case "ir":
			return INDIVIDUAL_STATEMENT;
		case "er":
			return ENTERPRISE_STATEMENT;
		default:
			throw new RuntimeException("Report type(" + code
					+ ") undefined !!!");
		}
	}
}