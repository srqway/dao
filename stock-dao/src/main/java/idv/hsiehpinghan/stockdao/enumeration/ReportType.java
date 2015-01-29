package idv.hsiehpinghan.stockdao.enumeration;

public enum ReportType {
	CONSOLIDATED_STATEMENT("cr"), INDIVIDUAL_STATEMENT("ir"), ENTERPRISE_STATEMENT(
			"er");

	private String reportTypeCode;

	private ReportType(String reportTypeCode) {
		this.reportTypeCode = reportTypeCode;
	}

	public static ReportType getReportType(String reportTypeCode) {
		for (ReportType type : ReportType.values()) {
			if (type.getReportTypeCode().equals(reportTypeCode)) {
				return type;
			}
		}
		throw new RuntimeException("ReportTypeCode(" + reportTypeCode
				+ ") not defined !!!");
	}

	public String getReportTypeCode() {
		return reportTypeCode;
	}

}