package idv.hsiehpinghan.stockdao.entity;

import idv.hsiehpinghan.hbaseassistant.abstractclass.HBaseColumnFamily;
import idv.hsiehpinghan.hbaseassistant.abstractclass.HBaseColumnQualifier;
import idv.hsiehpinghan.hbaseassistant.abstractclass.HBaseRowKey;
import idv.hsiehpinghan.hbaseassistant.abstractclass.HBaseTable;
import idv.hsiehpinghan.hbaseassistant.abstractclass.HBaseValue;
import idv.hsiehpinghan.hbaseassistant.utility.ByteConvertUtility;
import idv.hsiehpinghan.stockdao.enumeration.IndustryType;
import idv.hsiehpinghan.stockdao.enumeration.MarketType;

import java.util.Date;
import java.util.Set;

public class StockInfo extends HBaseTable {
	private RowKey rowKey;
	private CompanyFamily companyFamily;

	@Override
	public HBaseRowKey getRowKey() {
		return rowKey;
	}

	@Override
	public void setRowKey(HBaseRowKey rowKey) {
		this.rowKey = (RowKey) rowKey;
	}

	public CompanyFamily getCompanyFamily() {
		if (companyFamily == null) {
			companyFamily = this.new CompanyFamily(this);
		}
		return companyFamily;
	}

	public class RowKey extends HBaseRowKey {
		public RowKey(StockInfo entity) {
			super(entity);
		}

		public RowKey(byte[] bytes, StockInfo entity) {
			super(entity);
			setBytes(bytes);
		}

		public RowKey(String stockCode, StockInfo entity) {
			super(entity);
			setStockCode(stockCode);
		}

		public String getStockCode() {
			return ByteConvertUtility.getStringFromBytes(getBytes());
		}

		public void setStockCode(String stockCode) {
			byte[] bytes = ByteConvertUtility.toBytes(stockCode);
			setBytes(bytes);
		}
	}

	public class CompanyFamily extends HBaseColumnFamily {
		public static final String MARKET_TYPE = "marketType";
		public static final String INDUSTRY_TYPE = "industryType";
		public static final String CHINESE_NAME = "chineseName";
		public static final String ENGLISH_BRIEF_NAME = "englishBriefName";
		public static final String UNIFIED_BUSINESS_NUMBER = "unifiedBusinessNumber";
		public static final String ESTABLISHMENT_DATE = "establishmentDate";
		public static final String LISTING_DATE = "listingDate";
		public static final String CHAIRMAN = "chairman";
		public static final String GENERAL_MANAGER = "generalManager";
		public static final String SPOKESPERSON = "spokesperson";
		public static final String JOB_TITLE_OF_SPOKESPERSON = "jobTitleOfSpokesperson";
		public static final String ACTING_SPOKESMAN = "actingSpokesman";
		public static final String CHINESE_ADDRESS = "chineseAddress";
		public static final String TELEPHONE = "telephone";
		public static final String STOCK_TRANSFER_AGENCY = "stockTransferAgency";
		public static final String TELEPHONE_OF_STOCK_TRANSFER_AGENCY = "telephoneOfStockTransferAgency";
		public static final String ADDRESS_OF_STOCK_TRANSFER_AGENCY = "addressOfStockTransferAgency";
		public static final String ENGLISH_ADDRESS = "englishAddress";
		public static final String FAX_NUMBER = "faxNumber";
		public static final String EMAIL = "email";
		public static final String WEB_SITE = "webSite";
		public static final String FINANCIAL_REPORT_TYPE = "financialReportType";
		public static final String PAR_VALUE_OF_ORDINARY_SHARES = "parValueOfOrdinaryShares";
		public static final String PAID_IN_CAPITAL = "paidInCapital";
		public static final String AMOUNT_OF_ORDINARY_SHARES = "amountOfOrdinaryShares";
		public static final String PRIVATE_PLACEMENT_AMOUNT_OF_ORDINARY_SHARES = "privatePlacementAmountOfOrdinaryShares";
		public static final String AMOUNT_OF_PREFERRED_SHARES = "amountOfPreferredShares";
		public static final String ACCOUNTING_FIRM = "accountingFirm";
		public static final String ACCOUNTANT1 = "accountant1";
		public static final String ACCOUNTANT2 = "accountant2";

		private CompanyFamily(StockInfo entity) {
			super(entity);
		}

		@SuppressWarnings("unchecked")
		public Set<CompanyQualifier> getQualifiers() {
			return (Set<CompanyQualifier>) (Object) super
					.getQualifierVersionValueMap().keySet();
		}

		public MarketType getMarketType() {
			HBaseColumnQualifier qual = new CompanyQualifier(MARKET_TYPE);
			CompanyValue val = (CompanyValue) super.getLatestValue(qual);
			if (val == null) {
				return null;
			}
			return val.getAsMarketType();
		}

		public void setMarketType(Date ver, MarketType marketType) {
			CompanyQualifier qual = new CompanyQualifier(MARKET_TYPE);
			CompanyValue val = new CompanyValue();
			val.set(marketType);
			add(qual, ver, val);
		}

		public IndustryType getIndustryType() {
			HBaseColumnQualifier qual = new CompanyQualifier(INDUSTRY_TYPE);
			CompanyValue val = (CompanyValue) super.getLatestValue(qual);
			if (val == null) {
				return null;
			}
			return val.getAsIndustryType();
		}

		public void setIndustryType(Date ver, IndustryType industryType) {
			CompanyQualifier qual = new CompanyQualifier(INDUSTRY_TYPE);
			CompanyValue val = new CompanyValue();
			val.set(industryType);
			add(qual, ver, val);
		}

		public String getChineseName() {
			HBaseColumnQualifier qual = new CompanyQualifier(CHINESE_NAME);
			CompanyValue val = (CompanyValue) super.getLatestValue(qual);
			if (val == null) {
				return null;
			}
			return val.getAsString();
		}

		public void setChineseName(Date ver, String chineseName) {
			CompanyQualifier qual = new CompanyQualifier(CHINESE_NAME);
			CompanyValue val = new CompanyValue();
			val.set(chineseName);
			add(qual, ver, val);
		}

		public String getEnglishBriefName() {
			HBaseColumnQualifier qual = new CompanyQualifier(ENGLISH_BRIEF_NAME);
			CompanyValue val = (CompanyValue) super.getLatestValue(qual);
			if (val == null) {
				return null;
			}
			return val.getAsString();
		}

		public void setEnglishBriefName(Date ver, String englishBriefName) {
			CompanyQualifier qual = new CompanyQualifier(ENGLISH_BRIEF_NAME);
			CompanyValue val = new CompanyValue();
			val.set(englishBriefName);
			add(qual, ver, val);
		}

		public String getUnifiedBusinessNumber() {
			HBaseColumnQualifier qual = new CompanyQualifier(
					UNIFIED_BUSINESS_NUMBER);
			CompanyValue val = (CompanyValue) super.getLatestValue(qual);
			if (val == null) {
				return null;
			}
			return val.getAsString();
		}

		public void setUnifiedBusinessNumber(Date ver,
				String unifiedBusinessNumber) {
			CompanyQualifier qual = new CompanyQualifier(
					UNIFIED_BUSINESS_NUMBER);
			CompanyValue val = new CompanyValue();
			val.set(unifiedBusinessNumber);
			add(qual, ver, val);
		}

		public String getEstablishmentDate() {
			HBaseColumnQualifier qual = new CompanyQualifier(ESTABLISHMENT_DATE);
			CompanyValue val = (CompanyValue) super.getLatestValue(qual);
			if (val == null) {
				return null;
			}
			return val.getAsString();
		}

		public void setEstablishmentDate(Date ver, String establishmentDate) {
			CompanyQualifier qual = new CompanyQualifier(ESTABLISHMENT_DATE);
			CompanyValue val = new CompanyValue();
			val.set(establishmentDate);
			add(qual, ver, val);
		}

		public String getListingDate() {
			HBaseColumnQualifier qual = new CompanyQualifier(LISTING_DATE);
			CompanyValue val = (CompanyValue) super.getLatestValue(qual);
			if (val == null) {
				return null;
			}
			return val.getAsString();
		}

		public void setListingDate(Date ver, String listingDate) {
			CompanyQualifier qual = new CompanyQualifier(LISTING_DATE);
			CompanyValue val = new CompanyValue();
			val.set(listingDate);
			add(qual, ver, val);
		}

		public String getChairman() {
			HBaseColumnQualifier qual = new CompanyQualifier(CHAIRMAN);
			CompanyValue val = (CompanyValue) super.getLatestValue(qual);
			if (val == null) {
				return null;
			}
			return val.getAsString();
		}

		public void setChairman(Date ver, String chairman) {
			CompanyQualifier qual = new CompanyQualifier(CHAIRMAN);
			CompanyValue val = new CompanyValue();
			val.set(chairman);
			add(qual, ver, val);
		}

		public String getGeneralManager() {
			HBaseColumnQualifier qual = new CompanyQualifier(GENERAL_MANAGER);
			CompanyValue val = (CompanyValue) super.getLatestValue(qual);
			if (val == null) {
				return null;
			}
			return val.getAsString();
		}

		public void setGeneralManager(Date ver, String generalManager) {
			CompanyQualifier qual = new CompanyQualifier(GENERAL_MANAGER);
			CompanyValue val = new CompanyValue();
			val.set(generalManager);
			add(qual, ver, val);
		}

		public String getSpokesperson() {
			HBaseColumnQualifier qual = new CompanyQualifier(SPOKESPERSON);
			CompanyValue val = (CompanyValue) super.getLatestValue(qual);
			if (val == null) {
				return null;
			}
			return val.getAsString();
		}

		public void setSpokesperson(Date ver, String spokesperson) {
			CompanyQualifier qual = new CompanyQualifier(SPOKESPERSON);
			CompanyValue val = new CompanyValue();
			val.set(spokesperson);
			add(qual, ver, val);
		}

		public String getJobTitleOfSpokesperson() {
			HBaseColumnQualifier qual = new CompanyQualifier(
					JOB_TITLE_OF_SPOKESPERSON);
			CompanyValue val = (CompanyValue) super.getLatestValue(qual);
			if (val == null) {
				return null;
			}
			return val.getAsString();
		}

		public void setJobTitleOfSpokesperson(Date ver,
				String jobTitleOfSpokesperson) {
			CompanyQualifier qual = new CompanyQualifier(
					JOB_TITLE_OF_SPOKESPERSON);
			CompanyValue val = new CompanyValue();
			val.set(jobTitleOfSpokesperson);
			add(qual, ver, val);
		}

		public String getActingSpokesman() {
			HBaseColumnQualifier qual = new CompanyQualifier(ACTING_SPOKESMAN);
			CompanyValue val = (CompanyValue) super.getLatestValue(qual);
			if (val == null) {
				return null;
			}
			return val.getAsString();
		}

		public void setActingSpokesman(Date ver, String actingSpokesman) {
			CompanyQualifier qual = new CompanyQualifier(ACTING_SPOKESMAN);
			CompanyValue val = new CompanyValue();
			val.set(actingSpokesman);
			add(qual, ver, val);
		}

		public String getChineseAddress() {
			HBaseColumnQualifier qual = new CompanyQualifier(CHINESE_ADDRESS);
			CompanyValue val = (CompanyValue) super.getLatestValue(qual);
			if (val == null) {
				return null;
			}
			return val.getAsString();
		}

		public void setChineseAddress(Date ver, String chineseAddress) {
			CompanyQualifier qual = new CompanyQualifier(CHINESE_ADDRESS);
			CompanyValue val = new CompanyValue();
			val.set(chineseAddress);
			add(qual, ver, val);
		}

		public String getTelephone() {
			HBaseColumnQualifier qual = new CompanyQualifier(TELEPHONE);
			CompanyValue val = (CompanyValue) super.getLatestValue(qual);
			if (val == null) {
				return null;
			}
			return val.getAsString();
		}

		public void setTelephone(Date ver, String telephone) {
			CompanyQualifier qual = new CompanyQualifier(TELEPHONE);
			CompanyValue val = new CompanyValue();
			val.set(telephone);
			add(qual, ver, val);
		}

		public String getStockTransferAgency() {
			HBaseColumnQualifier qual = new CompanyQualifier(
					STOCK_TRANSFER_AGENCY);
			CompanyValue val = (CompanyValue) super.getLatestValue(qual);
			if (val == null) {
				return null;
			}
			return val.getAsString();
		}

		public void setStockTransferAgency(Date ver, String stockTransferAgency) {
			CompanyQualifier qual = new CompanyQualifier(STOCK_TRANSFER_AGENCY);
			CompanyValue val = new CompanyValue();
			val.set(stockTransferAgency);
			add(qual, ver, val);
		}

		public String getTelephoneOfStockTransferAgency() {
			HBaseColumnQualifier qual = new CompanyQualifier(
					TELEPHONE_OF_STOCK_TRANSFER_AGENCY);
			CompanyValue val = (CompanyValue) super.getLatestValue(qual);
			if (val == null) {
				return null;
			}
			return val.getAsString();
		}

		public void setTelephoneOfStockTransferAgency(Date ver,
				String telephoneOfStockTransferAgency) {
			CompanyQualifier qual = new CompanyQualifier(
					TELEPHONE_OF_STOCK_TRANSFER_AGENCY);
			CompanyValue val = new CompanyValue();
			val.set(telephoneOfStockTransferAgency);
			add(qual, ver, val);
		}

		public String getAddressOfStockTransferAgency() {
			HBaseColumnQualifier qual = new CompanyQualifier(
					ADDRESS_OF_STOCK_TRANSFER_AGENCY);
			CompanyValue val = (CompanyValue) super.getLatestValue(qual);
			if (val == null) {
				return null;
			}
			return val.getAsString();
		}

		public void setAddressOfStockTransferAgency(Date ver,
				String addressOfStockTransferAgency) {
			CompanyQualifier qual = new CompanyQualifier(
					ADDRESS_OF_STOCK_TRANSFER_AGENCY);
			CompanyValue val = new CompanyValue();
			val.set(addressOfStockTransferAgency);
			add(qual, ver, val);
		}

		public String getEnglishAddress() {
			HBaseColumnQualifier qual = new CompanyQualifier(ENGLISH_ADDRESS);
			CompanyValue val = (CompanyValue) super.getLatestValue(qual);
			if (val == null) {
				return null;
			}
			return val.getAsString();
		}

		public void setEnglishAddress(Date ver, String englishAddress) {
			CompanyQualifier qual = new CompanyQualifier(ENGLISH_ADDRESS);
			CompanyValue val = new CompanyValue();
			val.set(englishAddress);
			add(qual, ver, val);
		}

		public String getFaxNumber() {
			HBaseColumnQualifier qual = new CompanyQualifier(FAX_NUMBER);
			CompanyValue val = (CompanyValue) super.getLatestValue(qual);
			if (val == null) {
				return null;
			}
			return val.getAsString();
		}

		public void setFaxNumber(Date ver, String faxNumber) {
			CompanyQualifier qual = new CompanyQualifier(FAX_NUMBER);
			CompanyValue val = new CompanyValue();
			val.set(faxNumber);
			add(qual, ver, val);
		}

		public String getEmail() {
			HBaseColumnQualifier qual = new CompanyQualifier(EMAIL);
			CompanyValue val = (CompanyValue) super.getLatestValue(qual);
			if (val == null) {
				return null;
			}
			return val.getAsString();
		}

		public void setEmail(Date ver, String email) {
			CompanyQualifier qual = new CompanyQualifier(EMAIL);
			CompanyValue val = new CompanyValue();
			val.set(email);
			add(qual, ver, val);
		}

		public String getWebSite() {
			HBaseColumnQualifier qual = new CompanyQualifier(WEB_SITE);
			CompanyValue val = (CompanyValue) super.getLatestValue(qual);
			if (val == null) {
				return null;
			}
			return val.getAsString();
		}

		public void setWebSite(Date ver, String webSite) {
			CompanyQualifier qual = new CompanyQualifier(WEB_SITE);
			CompanyValue val = new CompanyValue();
			val.set(webSite);
			add(qual, ver, val);
		}

		public String getFinancialReportType() {
			HBaseColumnQualifier qual = new CompanyQualifier(
					FINANCIAL_REPORT_TYPE);
			CompanyValue val = (CompanyValue) super.getLatestValue(qual);
			if (val == null) {
				return null;
			}
			return val.getAsString();
		}

		public void setFinancialReportType(Date ver, String financialReportType) {
			CompanyQualifier qual = new CompanyQualifier(FINANCIAL_REPORT_TYPE);
			CompanyValue val = new CompanyValue();
			val.set(financialReportType);
			add(qual, ver, val);
		}

		public String getParValueOfOrdinaryShares() {
			HBaseColumnQualifier qual = new CompanyQualifier(
					PAR_VALUE_OF_ORDINARY_SHARES);
			CompanyValue val = (CompanyValue) super.getLatestValue(qual);
			if (val == null) {
				return null;
			}
			return val.getAsString();
		}

		public void setParValueOfOrdinaryShares(Date ver,
				String parValueOfOrdinaryShares) {
			CompanyQualifier qual = new CompanyQualifier(
					PAR_VALUE_OF_ORDINARY_SHARES);
			CompanyValue val = new CompanyValue();
			val.set(parValueOfOrdinaryShares);
			add(qual, ver, val);
		}

		public String getPaidInCapital() {
			HBaseColumnQualifier qual = new CompanyQualifier(PAID_IN_CAPITAL);
			CompanyValue val = (CompanyValue) super.getLatestValue(qual);
			if (val == null) {
				return null;
			}
			return val.getAsString();
		}

		public void setPaidInCapital(Date ver, String paidInCapital) {
			CompanyQualifier qual = new CompanyQualifier(PAID_IN_CAPITAL);
			CompanyValue val = new CompanyValue();
			val.set(paidInCapital);
			add(qual, ver, val);
		}

		public String getAmountOfOrdinaryShares() {
			HBaseColumnQualifier qual = new CompanyQualifier(
					AMOUNT_OF_ORDINARY_SHARES);
			CompanyValue val = (CompanyValue) super.getLatestValue(qual);
			if (val == null) {
				return null;
			}
			return val.getAsString();
		}

		public void setAmountOfOrdinaryShares(Date ver,
				String amountOfOrdinaryShares) {
			CompanyQualifier qual = new CompanyQualifier(
					AMOUNT_OF_ORDINARY_SHARES);
			CompanyValue val = new CompanyValue();
			val.set(amountOfOrdinaryShares);
			add(qual, ver, val);
		}

		public String getPrivatePlacementAmountOfOrdinaryShares() {
			HBaseColumnQualifier qual = new CompanyQualifier(
					PRIVATE_PLACEMENT_AMOUNT_OF_ORDINARY_SHARES);
			CompanyValue val = (CompanyValue) super.getLatestValue(qual);
			if (val == null) {
				return null;
			}
			return val.getAsString();
		}

		public void setPrivatePlacementAmountOfOrdinaryShares(Date ver,
				String privatePlacementAmountOfOrdinaryShares) {
			CompanyQualifier qual = new CompanyQualifier(
					PRIVATE_PLACEMENT_AMOUNT_OF_ORDINARY_SHARES);
			CompanyValue val = new CompanyValue();
			val.set(privatePlacementAmountOfOrdinaryShares);
			add(qual, ver, val);
		}

		public String getAmountOfPreferredShares() {
			HBaseColumnQualifier qual = new CompanyQualifier(
					AMOUNT_OF_PREFERRED_SHARES);
			CompanyValue val = (CompanyValue) super.getLatestValue(qual);
			if (val == null) {
				return null;
			}
			return val.getAsString();
		}

		public void setAmountOfPreferredShares(Date ver,
				String amountOfPreferredShares) {
			CompanyQualifier qual = new CompanyQualifier(
					AMOUNT_OF_PREFERRED_SHARES);
			CompanyValue val = new CompanyValue();
			val.set(amountOfPreferredShares);
			add(qual, ver, val);
		}

		public String getAccountingFirm() {
			HBaseColumnQualifier qual = new CompanyQualifier(ACCOUNTING_FIRM);
			CompanyValue val = (CompanyValue) super.getLatestValue(qual);
			if (val == null) {
				return null;
			}
			return val.getAsString();
		}

		public void setAccountingFirm(Date ver, String accountingFirm) {
			CompanyQualifier qual = new CompanyQualifier(ACCOUNTING_FIRM);
			CompanyValue val = new CompanyValue();
			val.set(accountingFirm);
			add(qual, ver, val);
		}

		public String getAccountant1() {
			HBaseColumnQualifier qual = new CompanyQualifier(ACCOUNTANT1);
			CompanyValue val = (CompanyValue) super.getLatestValue(qual);
			if (val == null) {
				return null;
			}
			return val.getAsString();
		}

		public void setAccountant1(Date ver, String accountant1) {
			CompanyQualifier qual = new CompanyQualifier(ACCOUNTANT1);
			CompanyValue val = new CompanyValue();
			val.set(accountant1);
			add(qual, ver, val);
		}

		public String getAccountant2() {
			HBaseColumnQualifier qual = new CompanyQualifier(ACCOUNTANT2);
			CompanyValue val = (CompanyValue) super.getLatestValue(qual);
			if (val == null) {
				return null;
			}
			return val.getAsString();
		}

		public void setAccountant2(Date ver, String accountant2) {
			CompanyQualifier qual = new CompanyQualifier(ACCOUNTANT2);
			CompanyValue val = new CompanyValue();
			val.set(accountant2);
			add(qual, ver, val);
		}

		@Override
		protected HBaseColumnQualifier generateColumnQualifier(byte[] bytes) {
			return this.new CompanyQualifier(bytes);
		}

		@Override
		protected HBaseValue generateValue(byte[] bytes) {
			return this.new CompanyValue(bytes);
		}

		public class CompanyQualifier extends HBaseColumnQualifier {
			public CompanyQualifier() {
				super();
			}

			public CompanyQualifier(byte[] bytes) {
				super();
				setBytes(bytes);
			}

			public CompanyQualifier(String columnName) {
				super();
				setColumnName(columnName);
			}

			public String getColumnName() {
				return ByteConvertUtility.getStringFromBytes(getBytes());
			}

			public void setColumnName(String columnName) {
				byte[] bytes = ByteConvertUtility.toBytes(columnName);
				setBytes(bytes);
			}
		}

		public class CompanyValue extends HBaseValue {
			public CompanyValue() {
				super();
			}

			public CompanyValue(byte[] bytes) {
				super();
				setBytes(bytes);
			}

			public MarketType getAsMarketType() {
				return MarketType.valueOf(ByteConvertUtility
						.getStringFromBytes(getBytes()));
			}

			public void set(MarketType value) {
				setBytes(ByteConvertUtility.toBytes(value.name()));
			}

			public String getAsString() {
				return ByteConvertUtility.getStringFromBytes(getBytes());
			}

			public void set(String value) {
				setBytes(ByteConvertUtility.toBytes(value));
			}

			public IndustryType getAsIndustryType() {
				return IndustryType.valueOf(ByteConvertUtility
						.getStringFromBytes(getBytes()));
			}

			public void set(IndustryType value) {
				setBytes(ByteConvertUtility.toBytes(value.name()));
			}
		}
	}
}
