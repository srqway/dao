package idv.hsiehpinghan.stockdao.enumeration;

import java.util.Locale;

public enum MarketType {
	LISTED("上市", "Listed"), OTC("上櫃", "otc"), EMERGING("興櫃", "emerging"), PUBLIC(
			"公開發行", "public");
	private String chineseName;
	private String englishName;

	private MarketType(String chineseName, String englishName) {
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