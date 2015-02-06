package idv.hsiehpinghan.stockdao.enumeration;

public enum MarketType {
	LISTED, OTC, EMERGING, PUBLIC;

	public static MarketType getMopsMarketType(String code) {
		switch (code) {
		case "sii":
			return LISTED;
		case "otc":
			return OTC;
		case "rotc":
			return EMERGING;
		case "pub":
			return PUBLIC;
		default:
			throw new RuntimeException("Market type(" + code
					+ ") undefined !!!");
		}
	}
}