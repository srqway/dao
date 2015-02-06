package idv.hsiehpinghan.stockdao.enumeration;

public enum IndustryType {
	CEMENT, // 水泥工業
	FOOD, // 食品工業
	PLASTIC, // 塑膠工業
	TEXTILE_FIBERS, // 紡織纖維
	ELECTRICAL_MACHINERY, // 電機機械
	ELECTRICAL_CABLE, // 電器電纜
	CHEMICAL_AND_MEDICAL_BIOTECHNOLOGY, // 化學生技醫療
	GLASS_CERAMICS, // 玻璃陶瓷
	PAPER, // 造紙工業
	STEEL, // 鋼鐵工業
	RUBBER, // 橡膠工業
	CAR, // 汽車工業
	BUILDING, // 建材營造
	SHIPPING, // 航運業
	TOURISM, // 觀光事業
	FINANCE_AND_INSURANCE, // 金融保險業
	TRADE_AND_MERCHANDISE, // 貿易百貨
	CONGLOMERATES, // 綜合企業
	OTHER, // 其他
	MEDICAL_BIOTECHNOLOGY, // 生技醫療業
	OIL_AND_ELECTRICITY_GAS, // 油電燃氣業
	SEMICONDUCTOR, // 半導體業
	COMPUTER_AND_PERIPHERAL_EQUIPMENT, // 電腦及週邊設備業
	OPTICAL, // 光電業
	COMMUNICATION_NETWORK, // 通信網路業
	ELECTRONIC_COMPONENTS, // 電子零組件業
	ELECTRONIC_CHANNELS, // 電子通路業
	INFORMATION_SERVICES, // 資訊服務業
	OTHER_ELECTRONICS, // 其他電子業
	CHEMICAL, // 化學工業
	DEPOSITARY_RECEIPTS, // 存託憑證
	CULTURAL_AND_CREATIVE, // 文化創意業
	MANAGED_STOCK; // 管理股票

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
}