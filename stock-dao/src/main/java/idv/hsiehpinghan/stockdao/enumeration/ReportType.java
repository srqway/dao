package idv.hsiehpinghan.stockdao.enumeration;

public enum ReportType {
	CONSOLIDATED_STATEMENT, INDIVIDUAL_STATEMENT, ENTERPRISE_STATEMENT;

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