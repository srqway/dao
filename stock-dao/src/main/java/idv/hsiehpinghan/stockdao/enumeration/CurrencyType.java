package idv.hsiehpinghan.stockdao.enumeration;

import java.util.Locale;

public enum CurrencyType {
	TWD("新臺幣", "TWD"), USD("美元", "USD"), HKD("港幣", "HKD"), GBP("英鎊", "GBP"), AUD(
			"澳幣", "AUD"), CAD("加拿大幣", "CAD"), SGD("新加坡幣", "SGD"), CHF("瑞士法郎",
			"CHF"), JPY("日元", "JPY"), ZAR("南非幣", "ZAR"), SEK("瑞典克朗", "SEK"), NZD(
			"紐幣", "NZD"), THB("泰銖", "THB"), PHP("菲律賓比索", "PHP"), IDR("印尼盧比",
			"IDR"), EUR("歐元", "EUR"), KRW("韓元", "KRW"), VND("越南盾", "VND"), MYR(
			"馬來西亞林吉特", "MYR"), CNY("人民幣", "CNY");
	private String chineseName;
	private String englishName;

	private CurrencyType(String chineseName, String englishName) {
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
}
