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
	private BasicDataFamily basicDataFamily;

	@Override
	public HBaseRowKey getRowKey() {
		return rowKey;
	}

	@Override
	public void setRowKey(HBaseRowKey rowKey) {
		this.rowKey = (RowKey) rowKey;
	}

	public BasicDataFamily getBasicDataFamily() {
		if (basicDataFamily == null) {
			basicDataFamily = this.new BasicDataFamily(this);
		}
		return basicDataFamily;
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

	public class BasicDataFamily extends HBaseColumnFamily {
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

		private BasicDataFamily(StockInfo entity) {
			super(entity);
		}

		@SuppressWarnings("unchecked")
		public Set<BasicDataQualifier> getQualifiers() {
			return (Set<BasicDataQualifier>) (Object) super
					.getQualifierVersionValueMap().keySet();
		}

		public MarketType getMarketType() {
			HBaseColumnQualifier qual = new BasicDataQualifier(MARKET_TYPE);
			BasicDataValue val = (BasicDataValue) super.getLatestValue(qual);
			return val.getAsMarketType();
		}

		public void setMarketType(Date ver, MarketType marketType) {
			BasicDataQualifier qual = new BasicDataQualifier(MARKET_TYPE);
			BasicDataValue val = new BasicDataValue();
			val.set(marketType);
			add(qual, ver, val);
		}

		public IndustryType getIndustryType() {
			HBaseColumnQualifier qual = new BasicDataQualifier(INDUSTRY_TYPE);
			BasicDataValue val = (BasicDataValue) super.getLatestValue(qual);
			return val.getAsIndustryType();
		}

		public void setIndustryType(Date ver, IndustryType industryType) {
			BasicDataQualifier qual = new BasicDataQualifier(INDUSTRY_TYPE);
			BasicDataValue val = new BasicDataValue();
			val.set(industryType);
			add(qual, ver, val);
		}

		public String getChineseName() {
			HBaseColumnQualifier qual = new BasicDataQualifier(CHINESE_NAME);
			BasicDataValue val = (BasicDataValue) super.getLatestValue(qual);
			return val.getAsString();
		}

		public void setChineseName(Date ver, String chineseName) {
			BasicDataQualifier qual = new BasicDataQualifier(CHINESE_NAME);
			BasicDataValue val = new BasicDataValue();
			val.set(chineseName);
			add(qual, ver, val);
		}

		public String getEnglishBriefName() {
			HBaseColumnQualifier qual = new BasicDataQualifier(
					ENGLISH_BRIEF_NAME);
			BasicDataValue val = (BasicDataValue) super.getLatestValue(qual);
			return val.getAsString();
		}

		public void setEnglishBriefName(Date ver, String englishBriefName) {
			BasicDataQualifier qual = new BasicDataQualifier(ENGLISH_BRIEF_NAME);
			BasicDataValue val = new BasicDataValue();
			val.set(englishBriefName);
			add(qual, ver, val);
		}

		public String getUnifiedBusinessNumber() {
			HBaseColumnQualifier qual = new BasicDataQualifier(
					UNIFIED_BUSINESS_NUMBER);
			BasicDataValue val = (BasicDataValue) super.getLatestValue(qual);
			return val.getAsString();
		}

		public void setUnifiedBusinessNumber(Date ver,
				String unifiedBusinessNumber) {
			BasicDataQualifier qual = new BasicDataQualifier(
					UNIFIED_BUSINESS_NUMBER);
			BasicDataValue val = new BasicDataValue();
			val.set(unifiedBusinessNumber);
			add(qual, ver, val);
		}

		public String getEstablishmentDate() {
			HBaseColumnQualifier qual = new BasicDataQualifier(
					ESTABLISHMENT_DATE);
			BasicDataValue val = (BasicDataValue) super.getLatestValue(qual);
			return val.getAsString();
		}

		public void setEstablishmentDate(Date ver, String establishmentDate) {
			BasicDataQualifier qual = new BasicDataQualifier(ESTABLISHMENT_DATE);
			BasicDataValue val = new BasicDataValue();
			val.set(establishmentDate);
			add(qual, ver, val);
		}

		public String getListingDate() {
			HBaseColumnQualifier qual = new BasicDataQualifier(LISTING_DATE);
			BasicDataValue val = (BasicDataValue) super.getLatestValue(qual);
			return val.getAsString();
		}

		public void setListingDate(Date ver, String listingDate) {
			BasicDataQualifier qual = new BasicDataQualifier(LISTING_DATE);
			BasicDataValue val = new BasicDataValue();
			val.set(listingDate);
			add(qual, ver, val);
		}

		public String getChairman() {
			HBaseColumnQualifier qual = new BasicDataQualifier(CHAIRMAN);
			BasicDataValue val = (BasicDataValue) super.getLatestValue(qual);
			return val.getAsString();
		}

		public void setChairman(Date ver, String chairman) {
			BasicDataQualifier qual = new BasicDataQualifier(CHAIRMAN);
			BasicDataValue val = new BasicDataValue();
			val.set(chairman);
			add(qual, ver, val);
		}

		public String getGeneralManager() {
			HBaseColumnQualifier qual = new BasicDataQualifier(GENERAL_MANAGER);
			BasicDataValue val = (BasicDataValue) super.getLatestValue(qual);
			return val.getAsString();
		}

		public void setGeneralManager(Date ver, String generalManager) {
			BasicDataQualifier qual = new BasicDataQualifier(GENERAL_MANAGER);
			BasicDataValue val = new BasicDataValue();
			val.set(generalManager);
			add(qual, ver, val);
		}

		public String getSpokesperson() {
			HBaseColumnQualifier qual = new BasicDataQualifier(SPOKESPERSON);
			BasicDataValue val = (BasicDataValue) super.getLatestValue(qual);
			return val.getAsString();
		}

		public void setSpokesperson(Date ver, String spokesperson) {
			BasicDataQualifier qual = new BasicDataQualifier(SPOKESPERSON);
			BasicDataValue val = new BasicDataValue();
			val.set(spokesperson);
			add(qual, ver, val);
		}

		public String getJobTitleOfSpokesperson() {
			HBaseColumnQualifier qual = new BasicDataQualifier(
					JOB_TITLE_OF_SPOKESPERSON);
			BasicDataValue val = (BasicDataValue) super.getLatestValue(qual);
			return val.getAsString();
		}

		public void setJobTitleOfSpokesperson(Date ver,
				String jobTitleOfSpokesperson) {
			BasicDataQualifier qual = new BasicDataQualifier(
					JOB_TITLE_OF_SPOKESPERSON);
			BasicDataValue val = new BasicDataValue();
			val.set(jobTitleOfSpokesperson);
			add(qual, ver, val);
		}

		public String getActingSpokesman() {
			HBaseColumnQualifier qual = new BasicDataQualifier(ACTING_SPOKESMAN);
			BasicDataValue val = (BasicDataValue) super.getLatestValue(qual);
			return val.getAsString();
		}

		public void setActingSpokesman(Date ver, String actingSpokesman) {
			BasicDataQualifier qual = new BasicDataQualifier(ACTING_SPOKESMAN);
			BasicDataValue val = new BasicDataValue();
			val.set(actingSpokesman);
			add(qual, ver, val);
		}

		public String getChineseAddress() {
			HBaseColumnQualifier qual = new BasicDataQualifier(CHINESE_ADDRESS);
			BasicDataValue val = (BasicDataValue) super.getLatestValue(qual);
			return val.getAsString();
		}

		public void setChineseAddress(Date ver, String chineseAddress) {
			BasicDataQualifier qual = new BasicDataQualifier(CHINESE_ADDRESS);
			BasicDataValue val = new BasicDataValue();
			val.set(chineseAddress);
			add(qual, ver, val);
		}

		public String getTelephone() {
			HBaseColumnQualifier qual = new BasicDataQualifier(TELEPHONE);
			BasicDataValue val = (BasicDataValue) super.getLatestValue(qual);
			return val.getAsString();
		}

		public void setTelephone(Date ver, String telephone) {
			BasicDataQualifier qual = new BasicDataQualifier(TELEPHONE);
			BasicDataValue val = new BasicDataValue();
			val.set(telephone);
			add(qual, ver, val);
		}

		public String getStockTransferAgency() {
			HBaseColumnQualifier qual = new BasicDataQualifier(
					STOCK_TRANSFER_AGENCY);
			BasicDataValue val = (BasicDataValue) super.getLatestValue(qual);
			return val.getAsString();
		}

		public void setStockTransferAgency(Date ver, String stockTransferAgency) {
			BasicDataQualifier qual = new BasicDataQualifier(
					STOCK_TRANSFER_AGENCY);
			BasicDataValue val = new BasicDataValue();
			val.set(stockTransferAgency);
			add(qual, ver, val);
		}

		public String getTelephoneOfStockTransferAgency() {
			HBaseColumnQualifier qual = new BasicDataQualifier(
					TELEPHONE_OF_STOCK_TRANSFER_AGENCY);
			BasicDataValue val = (BasicDataValue) super.getLatestValue(qual);
			return val.getAsString();
		}

		public void setTelephoneOfStockTransferAgency(Date ver,
				String telephoneOfStockTransferAgency) {
			BasicDataQualifier qual = new BasicDataQualifier(
					TELEPHONE_OF_STOCK_TRANSFER_AGENCY);
			BasicDataValue val = new BasicDataValue();
			val.set(telephoneOfStockTransferAgency);
			add(qual, ver, val);
		}

		public String getAddressOfStockTransferAgency() {
			HBaseColumnQualifier qual = new BasicDataQualifier(
					ADDRESS_OF_STOCK_TRANSFER_AGENCY);
			BasicDataValue val = (BasicDataValue) super.getLatestValue(qual);
			return val.getAsString();
		}

		public void setAddressOfStockTransferAgency(Date ver,
				String addressOfStockTransferAgency) {
			BasicDataQualifier qual = new BasicDataQualifier(
					ADDRESS_OF_STOCK_TRANSFER_AGENCY);
			BasicDataValue val = new BasicDataValue();
			val.set(addressOfStockTransferAgency);
			add(qual, ver, val);
		}

		public String getEnglishAddress() {
			HBaseColumnQualifier qual = new BasicDataQualifier(ENGLISH_ADDRESS);
			BasicDataValue val = (BasicDataValue) super.getLatestValue(qual);
			return val.getAsString();
		}

		public void setEnglishAddress(Date ver, String englishAddress) {
			BasicDataQualifier qual = new BasicDataQualifier(ENGLISH_ADDRESS);
			BasicDataValue val = new BasicDataValue();
			val.set(englishAddress);
			add(qual, ver, val);
		}

		public String getFaxNumber() {
			HBaseColumnQualifier qual = new BasicDataQualifier(FAX_NUMBER);
			BasicDataValue val = (BasicDataValue) super.getLatestValue(qual);
			return val.getAsString();
		}

		public void setFaxNumber(Date ver, String faxNumber) {
			BasicDataQualifier qual = new BasicDataQualifier(FAX_NUMBER);
			BasicDataValue val = new BasicDataValue();
			val.set(faxNumber);
			add(qual, ver, val);
		}

		public String getEmail() {
			HBaseColumnQualifier qual = new BasicDataQualifier(EMAIL);
			BasicDataValue val = (BasicDataValue) super.getLatestValue(qual);
			return val.getAsString();
		}

		public void setEmail(Date ver, String email) {
			BasicDataQualifier qual = new BasicDataQualifier(EMAIL);
			BasicDataValue val = new BasicDataValue();
			val.set(email);
			add(qual, ver, val);
		}

		public String getWebSite() {
			HBaseColumnQualifier qual = new BasicDataQualifier(WEB_SITE);
			BasicDataValue val = (BasicDataValue) super.getLatestValue(qual);
			return val.getAsString();
		}

		public void setWebSite(Date ver, String webSite) {
			BasicDataQualifier qual = new BasicDataQualifier(WEB_SITE);
			BasicDataValue val = new BasicDataValue();
			val.set(webSite);
			add(qual, ver, val);
		}

		public String getFinancialReportType() {
			HBaseColumnQualifier qual = new BasicDataQualifier(
					FINANCIAL_REPORT_TYPE);
			BasicDataValue val = (BasicDataValue) super.getLatestValue(qual);
			return val.getAsString();
		}

		public void setFinancialReportType(Date ver, String financialReportType) {
			BasicDataQualifier qual = new BasicDataQualifier(
					FINANCIAL_REPORT_TYPE);
			BasicDataValue val = new BasicDataValue();
			val.set(financialReportType);
			add(qual, ver, val);
		}

		public String getParValueOfOrdinaryShares() {
			HBaseColumnQualifier qual = new BasicDataQualifier(
					PAR_VALUE_OF_ORDINARY_SHARES);
			BasicDataValue val = (BasicDataValue) super.getLatestValue(qual);
			return val.getAsString();
		}

		public void setParValueOfOrdinaryShares(Date ver,
				String parValueOfOrdinaryShares) {
			BasicDataQualifier qual = new BasicDataQualifier(
					PAR_VALUE_OF_ORDINARY_SHARES);
			BasicDataValue val = new BasicDataValue();
			val.set(parValueOfOrdinaryShares);
			add(qual, ver, val);
		}

		public String getPaidInCapital() {
			HBaseColumnQualifier qual = new BasicDataQualifier(PAID_IN_CAPITAL);
			BasicDataValue val = (BasicDataValue) super.getLatestValue(qual);
			return val.getAsString();
		}

		public void setPaidInCapital(Date ver, String paidInCapital) {
			BasicDataQualifier qual = new BasicDataQualifier(PAID_IN_CAPITAL);
			BasicDataValue val = new BasicDataValue();
			val.set(paidInCapital);
			add(qual, ver, val);
		}

		public String getAmountOfOrdinaryShares() {
			HBaseColumnQualifier qual = new BasicDataQualifier(
					AMOUNT_OF_ORDINARY_SHARES);
			BasicDataValue val = (BasicDataValue) super.getLatestValue(qual);
			return val.getAsString();
		}

		public void setAmountOfOrdinaryShares(Date ver,
				String amountOfOrdinaryShares) {
			BasicDataQualifier qual = new BasicDataQualifier(
					AMOUNT_OF_ORDINARY_SHARES);
			BasicDataValue val = new BasicDataValue();
			val.set(amountOfOrdinaryShares);
			add(qual, ver, val);
		}

		public String getPrivatePlacementAmountOfOrdinaryShares() {
			HBaseColumnQualifier qual = new BasicDataQualifier(
					PRIVATE_PLACEMENT_AMOUNT_OF_ORDINARY_SHARES);
			BasicDataValue val = (BasicDataValue) super.getLatestValue(qual);
			return val.getAsString();
		}

		public void setPrivatePlacementAmountOfOrdinaryShares(Date ver,
				String privatePlacementAmountOfOrdinaryShares) {
			BasicDataQualifier qual = new BasicDataQualifier(
					PRIVATE_PLACEMENT_AMOUNT_OF_ORDINARY_SHARES);
			BasicDataValue val = new BasicDataValue();
			val.set(privatePlacementAmountOfOrdinaryShares);
			add(qual, ver, val);
		}

		public String getAmountOfPreferredShares() {
			HBaseColumnQualifier qual = new BasicDataQualifier(
					AMOUNT_OF_PREFERRED_SHARES);
			BasicDataValue val = (BasicDataValue) super.getLatestValue(qual);
			return val.getAsString();
		}

		public void setAmountOfPreferredShares(Date ver,
				String amountOfPreferredShares) {
			BasicDataQualifier qual = new BasicDataQualifier(
					AMOUNT_OF_PREFERRED_SHARES);
			BasicDataValue val = new BasicDataValue();
			val.set(amountOfPreferredShares);
			add(qual, ver, val);
		}

		public String getAccountingFirm() {
			HBaseColumnQualifier qual = new BasicDataQualifier(ACCOUNTING_FIRM);
			BasicDataValue val = (BasicDataValue) super.getLatestValue(qual);
			return val.getAsString();
		}

		public void setAccountingFirm(Date ver, String accountingFirm) {
			BasicDataQualifier qual = new BasicDataQualifier(ACCOUNTING_FIRM);
			BasicDataValue val = new BasicDataValue();
			val.set(accountingFirm);
			add(qual, ver, val);
		}

		public String getAccountant1() {
			HBaseColumnQualifier qual = new BasicDataQualifier(ACCOUNTANT1);
			BasicDataValue val = (BasicDataValue) super.getLatestValue(qual);
			return val.getAsString();
		}

		public void setAccountant1(Date ver, String accountant1) {
			BasicDataQualifier qual = new BasicDataQualifier(ACCOUNTANT1);
			BasicDataValue val = new BasicDataValue();
			val.set(accountant1);
			add(qual, ver, val);
		}

		public String getAccountant2() {
			HBaseColumnQualifier qual = new BasicDataQualifier(ACCOUNTANT2);
			BasicDataValue val = (BasicDataValue) super.getLatestValue(qual);
			return val.getAsString();
		}

		public void setAccountant2(Date ver, String accountant2) {
			BasicDataQualifier qual = new BasicDataQualifier(ACCOUNTANT2);
			BasicDataValue val = new BasicDataValue();
			val.set(accountant2);
			add(qual, ver, val);
		}

		@Override
		protected HBaseColumnQualifier generateColumnQualifier(byte[] bytes) {
			return this.new BasicDataQualifier(bytes);
		}

		@Override
		protected HBaseValue generateValue(byte[] bytes) {
			return this.new BasicDataValue(bytes);
		}

		public class BasicDataQualifier extends HBaseColumnQualifier {
			public BasicDataQualifier() {
				super();
			}

			public BasicDataQualifier(byte[] bytes) {
				super();
				setBytes(bytes);
			}

			public BasicDataQualifier(String columnName) {
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

		public class BasicDataValue extends HBaseValue {
			public BasicDataValue() {
				super();
			}

			public BasicDataValue(byte[] bytes) {
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
