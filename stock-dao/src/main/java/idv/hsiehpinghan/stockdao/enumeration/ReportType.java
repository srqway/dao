package idv.hsiehpinghan.stockdao.enumeration;

import java.util.Locale;

public enum ReportType {
	CONSOLIDATED_STATEMENT {
		@Override
		public String getChineseName() {
			return "合併";
		}

		@Override
		public String getEnglishName() {
			return "consolidated";
		}
	},
	INDIVIDUAL_STATEMENT {
		@Override
		public String getChineseName() {
			return "個別";
		}

		@Override
		public String getEnglishName() {
			return "individual";
		}
	},
	ENTERPRISE_STATEMENT {
		@Override
		public String getChineseName() {
			return "個體";
		}

		@Override
		public String getEnglishName() {
			return "enterprise";
		}
	};

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

	public String getName(Locale locale) {
		if (Locale.ENGLISH.getLanguage().equals(locale.getLanguage())) {
			return getEnglishName();
		} else {
			return getChineseName();
		}
	}

	public abstract String getChineseName();

	public abstract String getEnglishName();

}