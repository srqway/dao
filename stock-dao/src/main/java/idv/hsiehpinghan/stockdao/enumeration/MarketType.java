package idv.hsiehpinghan.stockdao.enumeration;

public enum MarketType {
	LISTED, OTC, EMERGING, PUBLIC;

	public static MarketType getMarketType(String code) {
		if ("sii".equals(code)) {
			return LISTED;
		} else if ("otc".equals(code)) {
			return OTC;
		} else if ("rotc".equals(code)) {
			return EMERGING;
		} else if ("pub".equals(code)) {
			return PUBLIC;
		} else {
			throw new RuntimeException("Market type(" + code
					+ ") undefined !!!");
		}
	}
}