package idv.hsiehpinghan.stockdao.enumeration;

import java.util.Locale;

public enum IndustryType {
	CEMENT {
		@Override
		public String getChineseName() {
			return "水泥工業";
		}

		@Override
		public String getEnglishName() {
			return "cement";
		}
	},
	FOOD {
		@Override
		public String getChineseName() {
			return "食品工業";
		}

		@Override
		public String getEnglishName() {
			return "food";
		}
	},
	PLASTIC {
		@Override
		public String getChineseName() {
			return "塑膠工業";
		}

		@Override
		public String getEnglishName() {
			return "plastic";
		}
	},
	TEXTILE_FIBERS {
		@Override
		public String getChineseName() {
			return "紡織纖維";
		}

		@Override
		public String getEnglishName() {
			return "textile fibers";
		}
	},
	ELECTRICAL_MACHINERY {
		@Override
		public String getChineseName() {
			return "電機機械";
		}

		@Override
		public String getEnglishName() {
			return "electrical machinery";
		}
	},
	ELECTRICAL_CABLE {
		@Override
		public String getChineseName() {
			return "電器電纜";
		}

		@Override
		public String getEnglishName() {
			return "electrical cable";
		}
	},
	CHEMICAL_AND_MEDICAL_BIOTECHNOLOGY {
		@Override
		public String getChineseName() {
			return "化學生技醫療";
		}

		@Override
		public String getEnglishName() {
			return "chemical and medical biotechnology";
		}
	},
	GLASS_CERAMICS {
		@Override
		public String getChineseName() {
			return "玻璃陶瓷";
		}

		@Override
		public String getEnglishName() {
			return "glass ceramics";
		}
	},
	PAPER {
		@Override
		public String getChineseName() {
			return "造紙工業";
		}

		@Override
		public String getEnglishName() {
			return "paper";
		}
	},
	STEEL {
		@Override
		public String getChineseName() {
			return "鋼鐵工業";
		}

		@Override
		public String getEnglishName() {
			return "steel";
		}
	},
	RUBBER {
		@Override
		public String getChineseName() {
			return "橡膠工業";
		}

		@Override
		public String getEnglishName() {
			return "rubber";
		}
	},
	CAR {
		@Override
		public String getChineseName() {
			return "汽車工業";
		}

		@Override
		public String getEnglishName() {
			return "car";
		}
	},
	ELECTRONICS {
		@Override
		public String getChineseName() {
			return "電子工業";
		}

		@Override
		public String getEnglishName() {
			return "electronics";
		}
	},
	BUILDING {
		@Override
		public String getChineseName() {
			return "建材營造";
		}

		@Override
		public String getEnglishName() {
			return "building";
		}
	},
	SHIPPING {
		@Override
		public String getChineseName() {
			return "航運業";
		}

		@Override
		public String getEnglishName() {
			return "shipping";
		}
	},
	TOURISM {
		@Override
		public String getChineseName() {
			return "觀光事業";
		}

		@Override
		public String getEnglishName() {
			return "tourism";
		}
	},
	FINANCE_AND_INSURANCE {
		@Override
		public String getChineseName() {
			return "金融保險業";
		}

		@Override
		public String getEnglishName() {
			return "finance and insurance";
		}
	},
	TRADE_AND_MERCHANDISE {
		@Override
		public String getChineseName() {
			return "貿易百貨";
		}

		@Override
		public String getEnglishName() {
			return "trade and merchandise";
		}
	},
	CONGLOMERATES {
		@Override
		public String getChineseName() {
			return "綜合企業";
		}

		@Override
		public String getEnglishName() {
			return "conglomerates";
		}
	},
	OTHER {
		@Override
		public String getChineseName() {
			return "其他";
		}

		@Override
		public String getEnglishName() {
			return "other";
		}
	},
	MEDICAL_BIOTECHNOLOGY {
		@Override
		public String getChineseName() {
			return "生技醫療業";
		}

		@Override
		public String getEnglishName() {
			return "medical biotechnology";
		}
	},
	OIL_AND_ELECTRICITY_GAS {
		@Override
		public String getChineseName() {
			return "油電燃氣業";
		}

		@Override
		public String getEnglishName() {
			return "oil and electricity gas";
		}
	},
	SEMICONDUCTOR {
		@Override
		public String getChineseName() {
			return "半導體業";
		}

		@Override
		public String getEnglishName() {
			return "semiconductor";
		}
	},
	COMPUTER_AND_PERIPHERAL_EQUIPMENT {
		@Override
		public String getChineseName() {
			return "電腦及週邊設備業";
		}

		@Override
		public String getEnglishName() {
			return "computer and peripheral equipment";
		}
	},
	OPTICAL {
		@Override
		public String getChineseName() {
			return "光電業";
		}

		@Override
		public String getEnglishName() {
			return "optical";
		}
	},
	COMMUNICATION_NETWORK {
		@Override
		public String getChineseName() {
			return "通信網路業";
		}

		@Override
		public String getEnglishName() {
			return "communication network";
		}
	},
	ELECTRONIC_COMPONENTS {
		@Override
		public String getChineseName() {
			return "電子零組件業";
		}

		@Override
		public String getEnglishName() {
			return "electronic components";
		}
	},
	ELECTRONIC_CHANNELS {
		@Override
		public String getChineseName() {
			return "電子通路業";
		}

		@Override
		public String getEnglishName() {
			return "electronic channels";
		}
	},
	INFORMATION_SERVICES {
		@Override
		public String getChineseName() {
			return "資訊服務業";
		}

		@Override
		public String getEnglishName() {
			return "information services";
		}
	},
	OTHER_ELECTRONICS {
		@Override
		public String getChineseName() {
			return "其他電子業";
		}

		@Override
		public String getEnglishName() {
			return "other electronics";
		}
	},
	CHEMICAL {
		@Override
		public String getChineseName() {
			return "化學工業";
		}

		@Override
		public String getEnglishName() {
			return "chemical";
		}
	},
	DEPOSITARY_RECEIPTS {
		@Override
		public String getChineseName() {
			return "存託憑證";
		}

		@Override
		public String getEnglishName() {
			return "depositary receipts";
		}
	},
	CULTURAL_AND_CREATIVE {
		@Override
		public String getChineseName() {
			return "文化創意業";
		}

		@Override
		public String getEnglishName() {
			return "cultural and creative";
		}
	},
	MANAGED_STOCK {
		@Override
		public String getChineseName() {
			return "管理股票";
		}

		@Override
		public String getEnglishName() {
			return "managed stock";
		}
	};

	public static IndustryType getMopsIndustryType(String code) {
		switch (code) {
		case "01":
			return CEMENT;
		case "02":
			return FOOD;
		case "03":
			return PLASTIC;
		case "04":
			return TEXTILE_FIBERS;
		case "05":
			return ELECTRICAL_MACHINERY;
		case "06":
			return ELECTRICAL_CABLE;
		case "07":
			return CHEMICAL_AND_MEDICAL_BIOTECHNOLOGY;
		case "08":
			return GLASS_CERAMICS;
		case "09":
			return PAPER;
		case "10":
			return STEEL;
		case "11":
			return RUBBER;
		case "12":
			return CAR;
		case "13":
			return ELECTRONICS;
		case "14":
			return BUILDING;
		case "15":
			return SHIPPING;
		case "16":
			return TOURISM;
		case "17":
			return FINANCE_AND_INSURANCE;
		case "18":
			return TRADE_AND_MERCHANDISE;
		case "19":
			return CONGLOMERATES;
		case "20":
			return OTHER;
		case "22":
			return MEDICAL_BIOTECHNOLOGY;
		case "23":
			return OIL_AND_ELECTRICITY_GAS;
		case "24":
			return SEMICONDUCTOR;
		case "25":
			return COMPUTER_AND_PERIPHERAL_EQUIPMENT;
		case "26":
			return OPTICAL;
		case "27":
			return COMMUNICATION_NETWORK;
		case "28":
			return ELECTRONIC_COMPONENTS;
		case "29":
			return ELECTRONIC_CHANNELS;
		case "30":
			return INFORMATION_SERVICES;
		case "31":
			return OTHER_ELECTRONICS;
		case "32":
			return CULTURAL_AND_CREATIVE;
		case "21":
			return CHEMICAL;
		case "80":
			return MANAGED_STOCK;
		case "91":
			return DEPOSITARY_RECEIPTS;
		default:
			throw new RuntimeException("Industry type(" + code
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