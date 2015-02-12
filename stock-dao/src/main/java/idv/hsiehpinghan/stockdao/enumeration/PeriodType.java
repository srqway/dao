package idv.hsiehpinghan.stockdao.enumeration;

public enum PeriodType {
	INSTANT, DURATION;

	public static PeriodType getPeriodType(String code) {
		switch (code) {
		case "instant":
			return INSTANT;
		case "duration":
			return DURATION;
		default:
			throw new RuntimeException("Period type(" + code
					+ ") undefined !!!");
		}
	}
}
