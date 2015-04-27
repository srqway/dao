package idv.hsiehpinghan.stockdao.enumeration;

import java.util.Locale;

public enum CurrencyType {
	TWD {
		@Override
		public String getChineseName() {
			return "新臺幣";
		}

		@Override
		public String getEnglishName() {
			return "TWD";
		}
	},
	USD {
		@Override
		public String getChineseName() {
			return "美元";
		}

		@Override
		public String getEnglishName() {
			return "USD";
		}
	},
	HKD {
		@Override
		public String getChineseName() {
			return "港幣";
		}

		@Override
		public String getEnglishName() {
			return "HKD";
		}
	},
	GBP {
		@Override
		public String getChineseName() {
			return "英鎊";
		}

		@Override
		public String getEnglishName() {
			return "GBP";
		}
	},
	AUD {
		@Override
		public String getChineseName() {
			return "澳幣";
		}

		@Override
		public String getEnglishName() {
			return "AUD";
		}
	},
	CAD {
		@Override
		public String getChineseName() {
			return "加拿大幣";
		}

		@Override
		public String getEnglishName() {
			return "CAD";
		}
	},
	SGD {
		@Override
		public String getChineseName() {
			return "新加坡幣";
		}

		@Override
		public String getEnglishName() {
			return "SGD";
		}
	},
	CHF {
		@Override
		public String getChineseName() {
			return "瑞士法郎";
		}

		@Override
		public String getEnglishName() {
			return "CHF";
		}
	},
	JPY {
		@Override
		public String getChineseName() {
			return "日元";
		}

		@Override
		public String getEnglishName() {
			return "JPY";
		}
	},
	ZAR {
		@Override
		public String getChineseName() {
			return "南非幣";
		}

		@Override
		public String getEnglishName() {
			return "ZAR";
		}
	},
	SEK {
		@Override
		public String getChineseName() {
			return "瑞典克朗";
		}

		@Override
		public String getEnglishName() {
			return "SEK";
		}
	},
	NZD {
		@Override
		public String getChineseName() {
			return "紐幣";
		}

		@Override
		public String getEnglishName() {
			return "NZD";
		}
	},
	THB {
		@Override
		public String getChineseName() {
			return "泰銖";
		}

		@Override
		public String getEnglishName() {
			return "THB";
		}
	},
	PHP {
		@Override
		public String getChineseName() {
			return "菲律賓比索";
		}

		@Override
		public String getEnglishName() {
			return "PHP";
		}
	},
	IDR {
		@Override
		public String getChineseName() {
			return "印尼盧比";
		}

		@Override
		public String getEnglishName() {
			return "IDR";
		}
	},
	EUR {
		@Override
		public String getChineseName() {
			return "歐元";
		}

		@Override
		public String getEnglishName() {
			return "EUR";
		}
	},
	KRW {
		@Override
		public String getChineseName() {
			return "韓元";
		}

		@Override
		public String getEnglishName() {
			return "KRW";
		}
	},
	VND {
		@Override
		public String getChineseName() {
			return "越南盾";
		}

		@Override
		public String getEnglishName() {
			return "VND";
		}
	},
	MYR {
		@Override
		public String getChineseName() {
			return "馬來西亞林吉特";
		}

		@Override
		public String getEnglishName() {
			return "MYR";
		}
	},
	CNY {
		@Override
		public String getChineseName() {
			return "人民幣";
		}

		@Override
		public String getEnglishName() {
			return "CNY";
		}
	};

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
