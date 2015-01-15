package idv.hsiehpinghan.mopsdao.enumeration;

public enum ReportType {
	INDIVIDUAL_STATEMENT("cr"), CONSOLIDATED_STATEMENT("ir");

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