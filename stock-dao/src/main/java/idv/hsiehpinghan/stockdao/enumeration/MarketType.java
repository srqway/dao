package idv.hsiehpinghan.stockdao.enumeration;

import java.util.Locale;

public enum MarketType {
	LISTED {
		@Override
		public String getChineseName() {
			return "上市";
		}

		@Override
		public String getEnglishName() {
			return "Listed";
		}
	},
	OTC {
		@Override
		public String getChineseName() {
			return "上櫃";
		}

		@Override
		public String getEnglishName() {
			return "otc";
		}
	},
	EMERGING {
		@Override
		public String getChineseName() {
			return "興櫃";
		}

		@Override
		public String getEnglishName() {
			return "emerging";
		}
	},
	PUBLIC {
		@Override
		public String getChineseName() {
			return "公開發行";
		}

		@Override
		public String getEnglishName() {
			return "public";
		}
	};

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