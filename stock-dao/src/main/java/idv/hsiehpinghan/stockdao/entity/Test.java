package idv.hsiehpinghan.stockdao.entity;

import idv.hsiehpinghan.collectionutility.utility.ArrayUtility;
import idv.hsiehpinghan.datatypeutility.utility.ByteUtility;
import idv.hsiehpinghan.hbaseassistant.abstractclass.HBaseColumnFamily;
import idv.hsiehpinghan.hbaseassistant.abstractclass.HBaseColumnQualifier;
import idv.hsiehpinghan.hbaseassistant.abstractclass.HBaseRowKey;
import idv.hsiehpinghan.hbaseassistant.abstractclass.HBaseTable;
import idv.hsiehpinghan.hbaseassistant.abstractclass.HBaseValue;
import idv.hsiehpinghan.hbaseassistant.utility.ByteConvertUtility;
import idv.hsiehpinghan.stockdao.enumeration.IndustryType;
import idv.hsiehpinghan.stockdao.enumeration.MarketType;
import idv.hsiehpinghan.stockdao.enumeration.PeriodType;

import java.lang.annotation.ElementType;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.ParseException;
import java.util.Date;

public class Test extends HBaseTable {
	private static final byte[] SPACE = ByteUtility.SINGLE_SPACE_BYTE_ARRAY;
	private RowKey rowKey;
	private InfoFamily infoFamily;
	private FinancialReportFamily financialReportFamily;
	private MonthlyFamily monthlyFamily;
	private DailyFamily dailyFamily;

	@Override
	public HBaseRowKey getRowKey() {
		return rowKey;
	}

	@Override
	public void setRowKey(HBaseRowKey rowKey) {
		this.rowKey = (RowKey) rowKey;
	}

	public InfoFamily getInfoFamily() {
		if (infoFamily == null) {
			infoFamily = this.new InfoFamily(this);
		}
		return infoFamily;
	}

	public FinancialReportFamily getFinancialReportFamily() {
		if (financialReportFamily == null) {
			financialReportFamily = this.new FinancialReportFamily(this);
		}
		return financialReportFamily;
	}

	public MonthlyFamily getMonthlyFamily() {
		if (monthlyFamily == null) {
			monthlyFamily = this.new MonthlyFamily(this);
		}
		return monthlyFamily;
	}

	public DailyFamily getDailyFamily() {
		if (dailyFamily == null) {
			dailyFamily = this.new DailyFamily(this);
		}
		return dailyFamily;
	}

	public class RowKey extends HBaseRowKey {
		public RowKey(Test entity) {
			super(entity);
		}

		public RowKey(byte[] bytes, Test entity) {
			super(entity);
			setBytes(bytes);
		}

		public RowKey(String stockCode, Test entity) {
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

	public class InfoFamily extends HBaseColumnFamily {
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

		private InfoFamily(Test entity) {
			super(entity);
		}

		@Override
		protected HBaseColumnQualifier generateColumnQualifier(byte[] bytes) {
			return this.new InfoQualifier(bytes);
		}

		@Override
		protected HBaseValue generateValue(byte[] bytes) {
			return this.new InfoValue(bytes);
		}

		public MarketType getMarketType() {
			HBaseColumnQualifier qual = new InfoQualifier(MARKET_TYPE);
			InfoValue val = (InfoValue) super.getLatestValue(qual);
			return val.getAsMarketType();
		}

		public void setMarketType(Date ver, MarketType marketType) {
			InfoQualifier qual = new InfoQualifier(MARKET_TYPE);
			InfoValue val = new InfoValue();
			val.set(marketType);
			add(qual, ver, val);
		}

		public IndustryType getIndustryType() {
			HBaseColumnQualifier qual = new InfoQualifier(INDUSTRY_TYPE);
			InfoValue val = (InfoValue) super.getLatestValue(qual);
			return val.getAsIndustryType();
		}

		public void setIndustryType(Date ver, IndustryType industryType) {
			InfoQualifier qual = new InfoQualifier(INDUSTRY_TYPE);
			InfoValue val = new InfoValue();
			val.set(industryType);
			add(qual, ver, val);
		}

		public String getChineseName() {
			HBaseColumnQualifier qual = new InfoQualifier(CHINESE_NAME);
			InfoValue val = (InfoValue) super.getLatestValue(qual);
			return val.getAsString();
		}

		public void setChineseName(Date ver, String chineseName) {
			InfoQualifier qual = new InfoQualifier(CHINESE_NAME);
			InfoValue val = new InfoValue();
			val.set(chineseName);
			add(qual, ver, val);
		}

		public String getEnglishBriefName() {
			HBaseColumnQualifier qual = new InfoQualifier(ENGLISH_BRIEF_NAME);
			InfoValue val = (InfoValue) super.getLatestValue(qual);
			return val.getAsString();
		}

		public void setEnglishBriefName(Date ver, String englishBriefName) {
			InfoQualifier qual = new InfoQualifier(ENGLISH_BRIEF_NAME);
			InfoValue val = new InfoValue();
			val.set(englishBriefName);
			add(qual, ver, val);
		}

		public String getUnifiedBusinessNumber() {
			HBaseColumnQualifier qual = new InfoQualifier(
					UNIFIED_BUSINESS_NUMBER);
			InfoValue val = (InfoValue) super.getLatestValue(qual);
			return val.getAsString();
		}

		public void setUnifiedBusinessNumber(Date ver,
				String unifiedBusinessNumber) {
			InfoQualifier qual = new InfoQualifier(UNIFIED_BUSINESS_NUMBER);
			InfoValue val = new InfoValue();
			val.set(unifiedBusinessNumber);
			add(qual, ver, val);
		}

		public String getEstablishmentDate() {
			HBaseColumnQualifier qual = new InfoQualifier(ESTABLISHMENT_DATE);
			InfoValue val = (InfoValue) super.getLatestValue(qual);
			return val.getAsString();
		}

		public void setEstablishmentDate(Date ver, String establishmentDate) {
			InfoQualifier qual = new InfoQualifier(ESTABLISHMENT_DATE);
			InfoValue val = new InfoValue();
			val.set(establishmentDate);
			add(qual, ver, val);
		}

		public String getListingDate() {
			HBaseColumnQualifier qual = new InfoQualifier(LISTING_DATE);
			InfoValue val = (InfoValue) super.getLatestValue(qual);
			return val.getAsString();
		}

		public void setListingDate(Date ver, String listingDate) {
			InfoQualifier qual = new InfoQualifier(LISTING_DATE);
			InfoValue val = new InfoValue();
			val.set(listingDate);
			add(qual, ver, val);
		}

		public String getChairman() {
			HBaseColumnQualifier qual = new InfoQualifier(CHAIRMAN);
			InfoValue val = (InfoValue) super.getLatestValue(qual);
			return val.getAsString();
		}

		public void setChairman(Date ver, String chairman) {
			InfoQualifier qual = new InfoQualifier(CHAIRMAN);
			InfoValue val = new InfoValue();
			val.set(chairman);
			add(qual, ver, val);
		}

		public String getGeneralManager() {
			HBaseColumnQualifier qual = new InfoQualifier(GENERAL_MANAGER);
			InfoValue val = (InfoValue) super.getLatestValue(qual);
			return val.getAsString();
		}

		public void setGeneralManager(Date ver, String generalManager) {
			InfoQualifier qual = new InfoQualifier(GENERAL_MANAGER);
			InfoValue val = new InfoValue();
			val.set(generalManager);
			add(qual, ver, val);
		}

		public String getSpokesperson() {
			HBaseColumnQualifier qual = new InfoQualifier(SPOKESPERSON);
			InfoValue val = (InfoValue) super.getLatestValue(qual);
			return val.getAsString();
		}

		public void setSpokesperson(Date ver, String spokesperson) {
			InfoQualifier qual = new InfoQualifier(SPOKESPERSON);
			InfoValue val = new InfoValue();
			val.set(spokesperson);
			add(qual, ver, val);
		}

		public String getJobTitleOfSpokesperson() {
			HBaseColumnQualifier qual = new InfoQualifier(
					JOB_TITLE_OF_SPOKESPERSON);
			InfoValue val = (InfoValue) super.getLatestValue(qual);
			return val.getAsString();
		}

		public void setJobTitleOfSpokesperson(Date ver,
				String jobTitleOfSpokesperson) {
			InfoQualifier qual = new InfoQualifier(JOB_TITLE_OF_SPOKESPERSON);
			InfoValue val = new InfoValue();
			val.set(jobTitleOfSpokesperson);
			add(qual, ver, val);
		}

		public String getActingSpokesman() {
			HBaseColumnQualifier qual = new InfoQualifier(ACTING_SPOKESMAN);
			InfoValue val = (InfoValue) super.getLatestValue(qual);
			return val.getAsString();
		}

		public void setActingSpokesman(Date ver, String actingSpokesman) {
			InfoQualifier qual = new InfoQualifier(ACTING_SPOKESMAN);
			InfoValue val = new InfoValue();
			val.set(actingSpokesman);
			add(qual, ver, val);
		}

		public String getChineseAddress() {
			HBaseColumnQualifier qual = new InfoQualifier(CHINESE_ADDRESS);
			InfoValue val = (InfoValue) super.getLatestValue(qual);
			return val.getAsString();
		}

		public void setChineseAddress(Date ver, String chineseAddress) {
			InfoQualifier qual = new InfoQualifier(CHINESE_ADDRESS);
			InfoValue val = new InfoValue();
			val.set(chineseAddress);
			add(qual, ver, val);
		}

		public String getTelephone() {
			HBaseColumnQualifier qual = new InfoQualifier(TELEPHONE);
			InfoValue val = (InfoValue) super.getLatestValue(qual);
			return val.getAsString();
		}

		public void setTelephone(Date ver, String telephone) {
			InfoQualifier qual = new InfoQualifier(TELEPHONE);
			InfoValue val = new InfoValue();
			val.set(telephone);
			add(qual, ver, val);
		}

		public String getStockTransferAgency() {
			HBaseColumnQualifier qual = new InfoQualifier(STOCK_TRANSFER_AGENCY);
			InfoValue val = (InfoValue) super.getLatestValue(qual);
			return val.getAsString();
		}

		public void setStockTransferAgency(Date ver, String stockTransferAgency) {
			InfoQualifier qual = new InfoQualifier(STOCK_TRANSFER_AGENCY);
			InfoValue val = new InfoValue();
			val.set(stockTransferAgency);
			add(qual, ver, val);
		}

		public String getTelephoneOfStockTransferAgency() {
			HBaseColumnQualifier qual = new InfoQualifier(
					TELEPHONE_OF_STOCK_TRANSFER_AGENCY);
			InfoValue val = (InfoValue) super.getLatestValue(qual);
			return val.getAsString();
		}

		public void setTelephoneOfStockTransferAgency(Date ver,
				String telephoneOfStockTransferAgency) {
			InfoQualifier qual = new InfoQualifier(
					TELEPHONE_OF_STOCK_TRANSFER_AGENCY);
			InfoValue val = new InfoValue();
			val.set(telephoneOfStockTransferAgency);
			add(qual, ver, val);
		}

		public String getAddressOfStockTransferAgency() {
			HBaseColumnQualifier qual = new InfoQualifier(
					ADDRESS_OF_STOCK_TRANSFER_AGENCY);
			InfoValue val = (InfoValue) super.getLatestValue(qual);
			return val.getAsString();
		}

		public void setAddressOfStockTransferAgency(Date ver,
				String addressOfStockTransferAgency) {
			InfoQualifier qual = new InfoQualifier(
					ADDRESS_OF_STOCK_TRANSFER_AGENCY);
			InfoValue val = new InfoValue();
			val.set(addressOfStockTransferAgency);
			add(qual, ver, val);
		}

		public String getEnglishAddress() {
			HBaseColumnQualifier qual = new InfoQualifier(ENGLISH_ADDRESS);
			InfoValue val = (InfoValue) super.getLatestValue(qual);
			return val.getAsString();
		}

		public void setEnglishAddress(Date ver, String englishAddress) {
			InfoQualifier qual = new InfoQualifier(ENGLISH_ADDRESS);
			InfoValue val = new InfoValue();
			val.set(englishAddress);
			add(qual, ver, val);
		}

		public String getFaxNumber() {
			HBaseColumnQualifier qual = new InfoQualifier(FAX_NUMBER);
			InfoValue val = (InfoValue) super.getLatestValue(qual);
			return val.getAsString();
		}

		public void setFaxNumber(Date ver, String faxNumber) {
			InfoQualifier qual = new InfoQualifier(FAX_NUMBER);
			InfoValue val = new InfoValue();
			val.set(faxNumber);
			add(qual, ver, val);
		}

		public String getEmail() {
			HBaseColumnQualifier qual = new InfoQualifier(EMAIL);
			InfoValue val = (InfoValue) super.getLatestValue(qual);
			return val.getAsString();
		}

		public void setEmail(Date ver, String email) {
			InfoQualifier qual = new InfoQualifier(EMAIL);
			InfoValue val = new InfoValue();
			val.set(email);
			add(qual, ver, val);
		}

		public String getWebSite() {
			HBaseColumnQualifier qual = new InfoQualifier(WEB_SITE);
			InfoValue val = (InfoValue) super.getLatestValue(qual);
			return val.getAsString();
		}

		public void setWebSite(Date ver, String webSite) {
			InfoQualifier qual = new InfoQualifier(WEB_SITE);
			InfoValue val = new InfoValue();
			val.set(webSite);
			add(qual, ver, val);
		}

		public String getFinancialReportType() {
			HBaseColumnQualifier qual = new InfoQualifier(FINANCIAL_REPORT_TYPE);
			InfoValue val = (InfoValue) super.getLatestValue(qual);
			return val.getAsString();
		}

		public void setFinancialReportType(Date ver, String financialReportType) {
			InfoQualifier qual = new InfoQualifier(FINANCIAL_REPORT_TYPE);
			InfoValue val = new InfoValue();
			val.set(financialReportType);
			add(qual, ver, val);
		}

		public String getParValueOfOrdinaryShares() {
			HBaseColumnQualifier qual = new InfoQualifier(
					PAR_VALUE_OF_ORDINARY_SHARES);
			InfoValue val = (InfoValue) super.getLatestValue(qual);
			return val.getAsString();
		}

		public void setParValueOfOrdinaryShares(Date ver,
				String parValueOfOrdinaryShares) {
			InfoQualifier qual = new InfoQualifier(PAR_VALUE_OF_ORDINARY_SHARES);
			InfoValue val = new InfoValue();
			val.set(parValueOfOrdinaryShares);
			add(qual, ver, val);
		}

		public String getPaidInCapital() {
			HBaseColumnQualifier qual = new InfoQualifier(PAID_IN_CAPITAL);
			InfoValue val = (InfoValue) super.getLatestValue(qual);
			return val.getAsString();
		}

		public void setPaidInCapital(Date ver, String paidInCapital) {
			InfoQualifier qual = new InfoQualifier(PAID_IN_CAPITAL);
			InfoValue val = new InfoValue();
			val.set(paidInCapital);
			add(qual, ver, val);
		}

		public String getAmountOfOrdinaryShares() {
			HBaseColumnQualifier qual = new InfoQualifier(
					AMOUNT_OF_ORDINARY_SHARES);
			InfoValue val = (InfoValue) super.getLatestValue(qual);
			return val.getAsString();
		}

		public void setAmountOfOrdinaryShares(Date ver,
				String amountOfOrdinaryShares) {
			InfoQualifier qual = new InfoQualifier(AMOUNT_OF_ORDINARY_SHARES);
			InfoValue val = new InfoValue();
			val.set(amountOfOrdinaryShares);
			add(qual, ver, val);
		}

		public String getPrivatePlacementAmountOfOrdinaryShares() {
			HBaseColumnQualifier qual = new InfoQualifier(
					PRIVATE_PLACEMENT_AMOUNT_OF_ORDINARY_SHARES);
			InfoValue val = (InfoValue) super.getLatestValue(qual);
			return val.getAsString();
		}

		public void setPrivatePlacementAmountOfOrdinaryShares(Date ver,
				String privatePlacementAmountOfOrdinaryShares) {
			InfoQualifier qual = new InfoQualifier(
					PRIVATE_PLACEMENT_AMOUNT_OF_ORDINARY_SHARES);
			InfoValue val = new InfoValue();
			val.set(privatePlacementAmountOfOrdinaryShares);
			add(qual, ver, val);
		}

		public String getAmountOfPreferredShares() {
			HBaseColumnQualifier qual = new InfoQualifier(
					AMOUNT_OF_PREFERRED_SHARES);
			InfoValue val = (InfoValue) super.getLatestValue(qual);
			return val.getAsString();
		}

		public void setAmountOfPreferredShares(Date ver,
				String amountOfPreferredShares) {
			InfoQualifier qual = new InfoQualifier(AMOUNT_OF_PREFERRED_SHARES);
			InfoValue val = new InfoValue();
			val.set(amountOfPreferredShares);
			add(qual, ver, val);
		}

		public String getAccountingFirm() {
			HBaseColumnQualifier qual = new InfoQualifier(ACCOUNTING_FIRM);
			InfoValue val = (InfoValue) super.getLatestValue(qual);
			return val.getAsString();
		}

		public void setAccountingFirm(Date ver, String accountingFirm) {
			InfoQualifier qual = new InfoQualifier(ACCOUNTING_FIRM);
			InfoValue val = new InfoValue();
			val.set(accountingFirm);
			add(qual, ver, val);
		}

		public String getAccountant1() {
			HBaseColumnQualifier qual = new InfoQualifier(ACCOUNTANT1);
			InfoValue val = (InfoValue) super.getLatestValue(qual);
			return val.getAsString();
		}

		public void setAccountant1(Date ver, String accountant1) {
			InfoQualifier qual = new InfoQualifier(ACCOUNTANT1);
			InfoValue val = new InfoValue();
			val.set(accountant1);
			add(qual, ver, val);
		}

		public String getAccountant2() {
			HBaseColumnQualifier qual = new InfoQualifier(ACCOUNTANT2);
			InfoValue val = (InfoValue) super.getLatestValue(qual);
			return val.getAsString();
		}

		public void setAccountant2(Date ver, String accountant2) {
			InfoQualifier qual = new InfoQualifier(ACCOUNTANT2);
			InfoValue val = new InfoValue();
			val.set(accountant2);
			add(qual, ver, val);
		}

		public class InfoQualifier extends HBaseColumnQualifier {
			public InfoQualifier() {
				super();
			}

			public InfoQualifier(byte[] bytes) {
				super();
				setBytes(bytes);
			}

			public InfoQualifier(String columnName) {
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

		public class InfoValue extends HBaseValue {
			public InfoValue() {
				super();
			}

			public InfoValue(byte[] bytes) {
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

	public class FinancialReportFamily extends HBaseColumnFamily {
		private FinancialReportFamily(Test entity) {
			super(entity);
		}

		@Override
		protected HBaseColumnQualifier generateColumnQualifier(byte[] bytes) {
			return this.new FinancialReportQualifier(bytes);
		}

		@Override
		protected HBaseValue generateValue(byte[] bytes) {
			return this.new FinancialReportValue(bytes);
		}

		public class FinancialReportQualifier extends HBaseColumnQualifier {
			private static final int ELEMENT_ID_LENGTH = 300;
			private static final int ELEMENT_TYPE_LENGTH = 10;
			private static final int PERIOD_TYPE_LENGTH = 10;
			private static final int INSTANT_LENGTH = ByteConvertUtility.DEFAULT_DATE_PATTERN_LENGTH;
			private static final int START_DATE_LENGTH = ByteConvertUtility.DEFAULT_DATE_PATTERN_LENGTH;
			private static final int END_DATE_LENGTH = ByteConvertUtility.DEFAULT_DATE_PATTERN_LENGTH;
			private static final int ELEMENT_ID_BEGIN_INDEX = 0;
			private static final int ELEMENT_ID_END_INDEX = ELEMENT_ID_BEGIN_INDEX
					+ ELEMENT_ID_LENGTH;
			private static final int ELEMENT_TYPE_BEGIN_INDEX = ELEMENT_ID_END_INDEX + 1;
			private static final int ELEMENT_TYPE_END_INDEX = ELEMENT_TYPE_BEGIN_INDEX
					+ ELEMENT_TYPE_LENGTH;
			private static final int PERIOD_TYPE_BEGIN_INDEX = ELEMENT_TYPE_END_INDEX + 1;
			private static final int PERIOD_TYPE_END_INDEX = PERIOD_TYPE_BEGIN_INDEX
					+ PERIOD_TYPE_LENGTH;
			private static final int INSTANT_BEGIN_INDEX = PERIOD_TYPE_END_INDEX + 1;
			private static final int INSTANT_END_INDEX = INSTANT_BEGIN_INDEX
					+ INSTANT_LENGTH;
			private static final int START_DATE_BEGIN_INDEX = INSTANT_END_INDEX + 1;
			private static final int START_DATE_END_INDEX = START_DATE_BEGIN_INDEX
					+ START_DATE_LENGTH;
			private static final int END_DATE_BEGIN_INDEX = START_DATE_END_INDEX + 1;
			private static final int END_DATE_END_INDEX = END_DATE_BEGIN_INDEX
					+ END_DATE_LENGTH;

			public FinancialReportQualifier() {
				super();
			}

			public FinancialReportQualifier(byte[] bytes) {
				super();
				setBytes(bytes);
			}

			public FinancialReportQualifier(String elementId,
					ElementType elementType, PeriodType periodType,
					Date instant, Date startDate, Date endDate) {
				super();
				byte[] elementIdBytes = ByteConvertUtility.toBytes(elementId,
						300);
				byte[] elementTypeBytes = ByteConvertUtility.toBytes(
						elementType.name(), 10);
				byte[] periodTypeBytes = ByteConvertUtility.toBytes(
						periodType.name(), 10);
				byte[] instantBytes = ByteConvertUtility.toBytes(instant);
				byte[] startDateBytes = ByteConvertUtility.toBytes(startDate);
				byte[] endDateBytes = ByteConvertUtility.toBytes(endDate);
				super.setBytes(ArrayUtility.addAll(elementIdBytes, SPACE,
						elementTypeBytes, SPACE, periodTypeBytes, SPACE,
						instantBytes, SPACE, startDateBytes, SPACE,
						endDateBytes));
			}

			public String getElementId() {
				return ByteConvertUtility.getStringFromBytes(getBytes(),
						ELEMENT_ID_BEGIN_INDEX, ELEMENT_ID_END_INDEX);
			}

			public void setElementId(String elementId) {
				byte[] bytes = getBytes();
				byte[] subBytes = ByteConvertUtility.toBytes(elementId, 300);
				ArrayUtility.replace(bytes, subBytes, ELEMENT_ID_BEGIN_INDEX,
						ELEMENT_ID_END_INDEX);
			}

			public ElementType getElementType() {
				return ElementType.valueOf(ByteConvertUtility
						.getStringFromBytes(getBytes(),
								ELEMENT_TYPE_BEGIN_INDEX,
								ELEMENT_TYPE_END_INDEX));
			}

			public void setElementType(ElementType elementType) {
				byte[] bytes = getBytes();
				byte[] subBytes = ByteConvertUtility.toBytes(
						elementType.name(), 10);
				ArrayUtility.replace(bytes, subBytes, ELEMENT_TYPE_BEGIN_INDEX,
						ELEMENT_TYPE_END_INDEX);
			}

			public PeriodType getPeriodType() {
				return PeriodType
						.valueOf(ByteConvertUtility.getStringFromBytes(
								getBytes(), PERIOD_TYPE_BEGIN_INDEX,
								PERIOD_TYPE_END_INDEX));
			}

			public void setPeriodType(PeriodType periodType) {
				byte[] bytes = getBytes();
				byte[] subBytes = ByteConvertUtility.toBytes(periodType.name(),
						10);
				ArrayUtility.replace(bytes, subBytes, PERIOD_TYPE_BEGIN_INDEX,
						PERIOD_TYPE_END_INDEX);
			}

			public Date getInstant() {
				try {
					return ByteConvertUtility.getDateFromBytes(getBytes(),
							INSTANT_BEGIN_INDEX, INSTANT_END_INDEX);
				} catch (ParseException e) {
					throw new RuntimeException(e);
				}
			}

			public void setInstant(Date instant) {
				byte[] bytes = getBytes();
				byte[] subBytes = ByteConvertUtility.toBytes(instant);
				ArrayUtility.replace(bytes, subBytes, INSTANT_BEGIN_INDEX,
						INSTANT_END_INDEX);
			}

			public Date getStartDate() {
				try {
					return ByteConvertUtility.getDateFromBytes(getBytes(),
							START_DATE_BEGIN_INDEX, START_DATE_END_INDEX);
				} catch (ParseException e) {
					throw new RuntimeException(e);
				}
			}

			public void setStartDate(Date startDate) {
				byte[] bytes = getBytes();
				byte[] subBytes = ByteConvertUtility.toBytes(startDate);
				ArrayUtility.replace(bytes, subBytes, START_DATE_BEGIN_INDEX,
						START_DATE_END_INDEX);
			}

			public Date getEndDate() {
				try {
					return ByteConvertUtility.getDateFromBytes(getBytes(),
							END_DATE_BEGIN_INDEX, END_DATE_END_INDEX);
				} catch (ParseException e) {
					throw new RuntimeException(e);
				}
			}

			public void setEndDate(Date endDate) {
				byte[] bytes = getBytes();
				byte[] subBytes = ByteConvertUtility.toBytes(endDate);
				ArrayUtility.replace(bytes, subBytes, END_DATE_BEGIN_INDEX,
						END_DATE_END_INDEX);
			}
		}

		public class FinancialReportValue extends HBaseValue {
			public FinancialReportValue() {
				super();
			}

			public FinancialReportValue(byte[] bytes) {
				super();
				setBytes(bytes);
			}
		}
	}

	public class MonthlyFamily extends HBaseColumnFamily {
		public static final String OPERATING_INCOME_OF_CURRENT_MONTH = "operatingIncomeOfCurrentMonth";
		public static final String OPERATING_INCOME_OF_CURRENT_MONTH_OF_LAST_YEAR = "operatingIncomeOfCurrentMonthOfLastYear";
		public static final String OPERATING_INCOME_OF_DIFFERENT_AMOUNT = "operatingIncomeOfDifferentAmount";
		public static final String OPERATING_INCOME_OF_DIFFERENT_PERCENT = "operatingIncomeOfDifferentPercent";
		public static final String OPERATING_INCOME_OF_CUMULATIVE_AMOUNT_OF_THIS_YEAR = "operatingIncomeOfCumulativeAmountOfThisYear";
		public static final String OPERATING_INCOME_OF_CUMULATIVE_AMOUNT_OF_LAST_YEAR = "operatingIncomeOfCumulativeAmountOfLastYear";
		public static final String OPERATING_INCOME_OF_CUMULATIVE_DIFFERENT_AMOUNT = "operatingIncomeOfCumulativeDifferentAmount";
		public static final String OPERATING_INCOME_OF_CUMULATIVE_DIFFERENT_PERCENT = "operatingIncomeOfCumulativeDifferentPercent";
		public static final String OPERATING_INCOME_OF_COMMENT = "operatingIncomeOfComment";

		private MonthlyFamily(Test entity) {
			super(entity);
		}

		@Override
		protected HBaseColumnQualifier generateColumnQualifier(byte[] bytes) {
			return this.new MonthlyQualifier(bytes);
		}

		@Override
		protected HBaseValue generateValue(byte[] bytes) {
			return this.new MonthlyValue(bytes);
		}

		public BigInteger getOperatingIncomeOfCurrentMonth(String year,
				String month) {
			HBaseColumnQualifier qual = new MonthlyQualifier(
					OPERATING_INCOME_OF_CURRENT_MONTH, year, month);
			MonthlyValue val = (MonthlyValue) super.getLatestValue(qual);
			return val.getAsBigInteger();
		}

		public void setOperatingIncomeOfCurrentMonth(String year, String month,
				Date ver, BigInteger operatingIncomeOfCurrentMonth) {
			MonthlyQualifier qual = new MonthlyQualifier(
					OPERATING_INCOME_OF_CURRENT_MONTH, year, month);
			MonthlyValue val = new MonthlyValue();
			val.set(operatingIncomeOfCurrentMonth);
			add(qual, ver, val);
		}

		public BigInteger getOperatingIncomeOfCurrentMonthOfLastYear(
				String year, String month) {
			HBaseColumnQualifier qual = new MonthlyQualifier(
					OPERATING_INCOME_OF_CURRENT_MONTH_OF_LAST_YEAR, year, month);
			MonthlyValue val = (MonthlyValue) super.getLatestValue(qual);
			return val.getAsBigInteger();
		}

		public void setOperatingIncomeOfCurrentMonthOfLastYear(String year,
				String month, Date ver,
				BigInteger operatingIncomeOfCurrentMonthOfLastYear) {
			MonthlyQualifier qual = new MonthlyQualifier(
					OPERATING_INCOME_OF_CURRENT_MONTH_OF_LAST_YEAR, year, month);
			MonthlyValue val = new MonthlyValue();
			val.set(operatingIncomeOfCurrentMonthOfLastYear);
			add(qual, ver, val);
		}

		public BigInteger getOperatingIncomeOfDifferentAmount(String year,
				String month) {
			HBaseColumnQualifier qual = new MonthlyQualifier(
					OPERATING_INCOME_OF_DIFFERENT_AMOUNT, year, month);
			MonthlyValue val = (MonthlyValue) super.getLatestValue(qual);
			return val.getAsBigInteger();
		}

		public void setOperatingIncomeOfDifferentAmount(String year,
				String month, Date ver,
				BigInteger operatingIncomeOfDifferentAmount) {
			MonthlyQualifier qual = new MonthlyQualifier(
					OPERATING_INCOME_OF_DIFFERENT_AMOUNT, year, month);
			MonthlyValue val = new MonthlyValue();
			val.set(operatingIncomeOfDifferentAmount);
			add(qual, ver, val);
		}

		public BigDecimal getOperatingIncomeOfDifferentPercent(String year,
				String month) {
			HBaseColumnQualifier qual = new MonthlyQualifier(
					OPERATING_INCOME_OF_DIFFERENT_PERCENT, year, month);
			MonthlyValue val = (MonthlyValue) super.getLatestValue(qual);
			return val.getAsBigDecimal();
		}

		public void setOperatingIncomeOfDifferentPercent(String year,
				String month, Date ver,
				BigDecimal operatingIncomeOfDifferentPercent) {
			MonthlyQualifier qual = new MonthlyQualifier(
					OPERATING_INCOME_OF_DIFFERENT_PERCENT, year, month);
			MonthlyValue val = new MonthlyValue();
			val.set(operatingIncomeOfDifferentPercent);
			add(qual, ver, val);
		}

		public BigInteger getOperatingIncomeOfCumulativeAmountOfThisYear(
				String year, String month) {
			HBaseColumnQualifier qual = new MonthlyQualifier(
					OPERATING_INCOME_OF_CUMULATIVE_AMOUNT_OF_THIS_YEAR, year,
					month);
			MonthlyValue val = (MonthlyValue) super.getLatestValue(qual);
			return val.getAsBigInteger();
		}

		public void setOperatingIncomeOfCumulativeAmountOfThisYear(String year,
				String month, Date ver,
				BigInteger operatingIncomeOfCumulativeAmountOfThisYear) {
			MonthlyQualifier qual = new MonthlyQualifier(
					OPERATING_INCOME_OF_CUMULATIVE_AMOUNT_OF_THIS_YEAR, year,
					month);
			MonthlyValue val = new MonthlyValue();
			val.set(operatingIncomeOfCumulativeAmountOfThisYear);
			add(qual, ver, val);
		}

		public BigInteger getOperatingIncomeOfCumulativeAmountOfLastYear(
				String year, String month) {
			HBaseColumnQualifier qual = new MonthlyQualifier(
					OPERATING_INCOME_OF_CUMULATIVE_AMOUNT_OF_LAST_YEAR, year,
					month);
			MonthlyValue val = (MonthlyValue) super.getLatestValue(qual);
			return val.getAsBigInteger();
		}

		public void setOperatingIncomeOfCumulativeAmountOfLastYear(String year,
				String month, Date ver,
				BigInteger operatingIncomeOfCumulativeAmountOfLastYear) {
			MonthlyQualifier qual = new MonthlyQualifier(
					OPERATING_INCOME_OF_CUMULATIVE_AMOUNT_OF_LAST_YEAR, year,
					month);
			MonthlyValue val = new MonthlyValue();
			val.set(operatingIncomeOfCumulativeAmountOfLastYear);
			add(qual, ver, val);
		}

		public BigInteger getOperatingIncomeOfCumulativeDifferentAmount(
				String year, String month) {
			HBaseColumnQualifier qual = new MonthlyQualifier(
					OPERATING_INCOME_OF_CUMULATIVE_DIFFERENT_AMOUNT, year,
					month);
			MonthlyValue val = (MonthlyValue) super.getLatestValue(qual);
			return val.getAsBigInteger();
		}

		public void setOperatingIncomeOfCumulativeDifferentAmount(String year,
				String month, Date ver,
				BigInteger operatingIncomeOfCumulativeDifferentAmount) {
			MonthlyQualifier qual = new MonthlyQualifier(
					OPERATING_INCOME_OF_CUMULATIVE_DIFFERENT_AMOUNT, year,
					month);
			MonthlyValue val = new MonthlyValue();
			val.set(operatingIncomeOfCumulativeDifferentAmount);
			add(qual, ver, val);
		}

		public BigDecimal getOperatingIncomeOfCumulativeDifferentPercent(
				String year, String month) {
			HBaseColumnQualifier qual = new MonthlyQualifier(
					OPERATING_INCOME_OF_CUMULATIVE_DIFFERENT_PERCENT, year,
					month);
			MonthlyValue val = (MonthlyValue) super.getLatestValue(qual);
			return val.getAsBigDecimal();
		}

		public void setOperatingIncomeOfCumulativeDifferentPercent(String year,
				String month, Date ver,
				BigDecimal operatingIncomeOfCumulativeDifferentPercent) {
			MonthlyQualifier qual = new MonthlyQualifier(
					OPERATING_INCOME_OF_CUMULATIVE_DIFFERENT_PERCENT, year,
					month);
			MonthlyValue val = new MonthlyValue();
			val.set(operatingIncomeOfCumulativeDifferentPercent);
			add(qual, ver, val);
		}

		public String getOperatingIncomeOfComment(String year, String month) {
			HBaseColumnQualifier qual = new MonthlyQualifier(
					OPERATING_INCOME_OF_COMMENT, year, month);
			MonthlyValue val = (MonthlyValue) super.getLatestValue(qual);
			return val.getAsString();
		}

		public void setOperatingIncomeOfComment(String year, String month,
				Date ver, String operatingIncomeOfComment) {
			MonthlyQualifier qual = new MonthlyQualifier(
					OPERATING_INCOME_OF_COMMENT, year, month);
			MonthlyValue val = new MonthlyValue();
			val.set(operatingIncomeOfComment);
			add(qual, ver, val);
		}

		public class MonthlyQualifier extends HBaseColumnQualifier {
			private static final int COLUMN_NAME_LENGTH = 100;
			private static final int YEAR_LENGTH = 4;
			private static final int MONTH_LENGTH = 2;
			private static final int COLUMN_NAME_BEGIN_INDEX = 0;
			private static final int COLUMN_NAME_END_INDEX = COLUMN_NAME_BEGIN_INDEX
					+ COLUMN_NAME_LENGTH;
			private static final int YEAR_BEGIN_INDEX = COLUMN_NAME_END_INDEX + 1;
			private static final int YEAR_END_INDEX = YEAR_BEGIN_INDEX
					+ YEAR_LENGTH;
			private static final int MONTH_BEGIN_INDEX = YEAR_END_INDEX + 1;
			private static final int MONTH_END_INDEX = MONTH_BEGIN_INDEX
					+ MONTH_LENGTH;

			public MonthlyQualifier() {
				super();
			}

			public MonthlyQualifier(byte[] bytes) {
				super();
				setBytes(bytes);
			}

			public MonthlyQualifier(String columnName, String year, String month) {
				super();
				byte[] columnNameBytes = ByteConvertUtility.toBytes(columnName,
						100);
				byte[] yearBytes = ByteConvertUtility.toBytes(year, 4);
				byte[] monthBytes = ByteConvertUtility.toBytes(month, 2);
				super.setBytes(ArrayUtility.addAll(columnNameBytes, SPACE,
						yearBytes, SPACE, monthBytes));
			}

			public String getColumnName() {
				return ByteConvertUtility.getStringFromBytes(getBytes(),
						COLUMN_NAME_BEGIN_INDEX, COLUMN_NAME_END_INDEX);
			}

			public void setColumnName(String columnName) {
				byte[] bytes = getBytes();
				byte[] subBytes = ByteConvertUtility.toBytes(columnName, 100);
				ArrayUtility.replace(bytes, subBytes, COLUMN_NAME_BEGIN_INDEX,
						COLUMN_NAME_END_INDEX);
			}

			public String getYear() {
				return ByteConvertUtility.getStringFromBytes(getBytes(),
						YEAR_BEGIN_INDEX, YEAR_END_INDEX);
			}

			public void setYear(String year) {
				byte[] bytes = getBytes();
				byte[] subBytes = ByteConvertUtility.toBytes(year, 4);
				ArrayUtility.replace(bytes, subBytes, YEAR_BEGIN_INDEX,
						YEAR_END_INDEX);
			}

			public String getMonth() {
				return ByteConvertUtility.getStringFromBytes(getBytes(),
						MONTH_BEGIN_INDEX, MONTH_END_INDEX);
			}

			public void setMonth(String month) {
				byte[] bytes = getBytes();
				byte[] subBytes = ByteConvertUtility.toBytes(month, 2);
				ArrayUtility.replace(bytes, subBytes, MONTH_BEGIN_INDEX,
						MONTH_END_INDEX);
			}
		}

		public class MonthlyValue extends HBaseValue {
			public MonthlyValue() {
				super();
			}

			public MonthlyValue(byte[] bytes) {
				super();
				setBytes(bytes);
			}

			public String getAsString() {
				return ByteConvertUtility.getStringFromBytes(getBytes());
			}

			public void set(String value) {
				setBytes(ByteConvertUtility.toBytes(value));
			}

			public BigDecimal getAsBigDecimal() {
				return ByteConvertUtility.getBigDecimalFromBytes(getBytes());
			}

			public void set(BigDecimal value) {
				setBytes(ByteConvertUtility.toBytes(value));
			}

			public BigInteger getAsBigInteger() {
				return ByteConvertUtility.getBigIntegerFromBytes(getBytes());
			}

			public void set(BigInteger value) {
				setBytes(ByteConvertUtility.toBytes(value));
			}
		}
	}

	public class DailyFamily extends HBaseColumnFamily {
		public static final String CLOSING_CONDITION_OF_OPENING_PRICE = "closingConditionOfOpeningPrice";
		public static final String CLOSING_CONDITION_OF_CLOSING_PRICE = "closingConditionOfClosingPrice";
		public static final String CLOSING_CONDITION_OF_CHANGE = "closingConditionOfChange";
		public static final String CLOSING_CONDITION_OF_HIGHEST_PRICE = "closingConditionOfHighestPrice";
		public static final String CLOSING_CONDITION_OF_LOWEST_PRICE = "closingConditionOfLowestPrice";
		public static final String CLOSING_CONDITION_OF_FINAL_PURCHASE_PRICE = "closingConditionOfFinalPurchasePrice";
		public static final String CLOSING_CONDITION_OF_FINAL_SELLING_PRICE = "closingConditionOfFinalSellingPrice";
		public static final String CLOSING_CONDITION_OF_STOCK_AMOUNT = "closingConditionOfStockAmount";
		public static final String CLOSING_CONDITION_OF_MONEY_AMOUNT = "closingConditionOfMoneyAmount";
		public static final String CLOSING_CONDITION_OF_TRANSACTION_AMOUNT = "closingConditionOfTransactionAmount";

		private DailyFamily(Test entity) {
			super(entity);
		}

		@Override
		protected HBaseColumnQualifier generateColumnQualifier(byte[] bytes) {
			return this.new DailyQualifier(bytes);
		}

		@Override
		protected HBaseValue generateValue(byte[] bytes) {
			return this.new DailyValue(bytes);
		}

		public BigDecimal getClosingConditionOfOpeningPrice(Date date) {
			HBaseColumnQualifier qual = new DailyQualifier(
					CLOSING_CONDITION_OF_OPENING_PRICE, date);
			DailyValue val = (DailyValue) super.getLatestValue(qual);
			return val.getAsBigDecimal();
		}

		public void setClosingConditionOfOpeningPrice(Date date, Date ver,
				BigDecimal closingConditionOfOpeningPrice) {
			DailyQualifier qual = new DailyQualifier(
					CLOSING_CONDITION_OF_OPENING_PRICE, date);
			DailyValue val = new DailyValue();
			val.set(closingConditionOfOpeningPrice);
			add(qual, ver, val);
		}

		public BigDecimal getClosingConditionOfClosingPrice(Date date) {
			HBaseColumnQualifier qual = new DailyQualifier(
					CLOSING_CONDITION_OF_CLOSING_PRICE, date);
			DailyValue val = (DailyValue) super.getLatestValue(qual);
			return val.getAsBigDecimal();
		}

		public void setClosingConditionOfClosingPrice(Date date, Date ver,
				BigDecimal closingConditionOfClosingPrice) {
			DailyQualifier qual = new DailyQualifier(
					CLOSING_CONDITION_OF_CLOSING_PRICE, date);
			DailyValue val = new DailyValue();
			val.set(closingConditionOfClosingPrice);
			add(qual, ver, val);
		}

		public BigDecimal getClosingConditionOfChange(Date date) {
			HBaseColumnQualifier qual = new DailyQualifier(
					CLOSING_CONDITION_OF_CHANGE, date);
			DailyValue val = (DailyValue) super.getLatestValue(qual);
			return val.getAsBigDecimal();
		}

		public void setClosingConditionOfChange(Date date, Date ver,
				BigDecimal closingConditionOfChange) {
			DailyQualifier qual = new DailyQualifier(
					CLOSING_CONDITION_OF_CHANGE, date);
			DailyValue val = new DailyValue();
			val.set(closingConditionOfChange);
			add(qual, ver, val);
		}

		public BigDecimal getClosingConditionOfHighestPrice(Date date) {
			HBaseColumnQualifier qual = new DailyQualifier(
					CLOSING_CONDITION_OF_HIGHEST_PRICE, date);
			DailyValue val = (DailyValue) super.getLatestValue(qual);
			return val.getAsBigDecimal();
		}

		public void setClosingConditionOfHighestPrice(Date date, Date ver,
				BigDecimal closingConditionOfHighestPrice) {
			DailyQualifier qual = new DailyQualifier(
					CLOSING_CONDITION_OF_HIGHEST_PRICE, date);
			DailyValue val = new DailyValue();
			val.set(closingConditionOfHighestPrice);
			add(qual, ver, val);
		}

		public BigDecimal getClosingConditionOfLowestPrice(Date date) {
			HBaseColumnQualifier qual = new DailyQualifier(
					CLOSING_CONDITION_OF_LOWEST_PRICE, date);
			DailyValue val = (DailyValue) super.getLatestValue(qual);
			return val.getAsBigDecimal();
		}

		public void setClosingConditionOfLowestPrice(Date date, Date ver,
				BigDecimal closingConditionOfLowestPrice) {
			DailyQualifier qual = new DailyQualifier(
					CLOSING_CONDITION_OF_LOWEST_PRICE, date);
			DailyValue val = new DailyValue();
			val.set(closingConditionOfLowestPrice);
			add(qual, ver, val);
		}

		public BigDecimal getClosingConditionOfFinalPurchasePrice(Date date) {
			HBaseColumnQualifier qual = new DailyQualifier(
					CLOSING_CONDITION_OF_FINAL_PURCHASE_PRICE, date);
			DailyValue val = (DailyValue) super.getLatestValue(qual);
			return val.getAsBigDecimal();
		}

		public void setClosingConditionOfFinalPurchasePrice(Date date,
				Date ver, BigDecimal closingConditionOfFinalPurchasePrice) {
			DailyQualifier qual = new DailyQualifier(
					CLOSING_CONDITION_OF_FINAL_PURCHASE_PRICE, date);
			DailyValue val = new DailyValue();
			val.set(closingConditionOfFinalPurchasePrice);
			add(qual, ver, val);
		}

		public BigDecimal getClosingConditionOfFinalSellingPrice(Date date) {
			HBaseColumnQualifier qual = new DailyQualifier(
					CLOSING_CONDITION_OF_FINAL_SELLING_PRICE, date);
			DailyValue val = (DailyValue) super.getLatestValue(qual);
			return val.getAsBigDecimal();
		}

		public void setClosingConditionOfFinalSellingPrice(Date date, Date ver,
				BigDecimal closingConditionOfFinalSellingPrice) {
			DailyQualifier qual = new DailyQualifier(
					CLOSING_CONDITION_OF_FINAL_SELLING_PRICE, date);
			DailyValue val = new DailyValue();
			val.set(closingConditionOfFinalSellingPrice);
			add(qual, ver, val);
		}

		public BigInteger getClosingConditionOfStockAmount(Date date) {
			HBaseColumnQualifier qual = new DailyQualifier(
					CLOSING_CONDITION_OF_STOCK_AMOUNT, date);
			DailyValue val = (DailyValue) super.getLatestValue(qual);
			return val.getAsBigInteger();
		}

		public void setClosingConditionOfStockAmount(Date date, Date ver,
				BigInteger closingConditionOfStockAmount) {
			DailyQualifier qual = new DailyQualifier(
					CLOSING_CONDITION_OF_STOCK_AMOUNT, date);
			DailyValue val = new DailyValue();
			val.set(closingConditionOfStockAmount);
			add(qual, ver, val);
		}

		public BigInteger getClosingConditionOfMoneyAmount(Date date) {
			HBaseColumnQualifier qual = new DailyQualifier(
					CLOSING_CONDITION_OF_MONEY_AMOUNT, date);
			DailyValue val = (DailyValue) super.getLatestValue(qual);
			return val.getAsBigInteger();
		}

		public void setClosingConditionOfMoneyAmount(Date date, Date ver,
				BigInteger closingConditionOfMoneyAmount) {
			DailyQualifier qual = new DailyQualifier(
					CLOSING_CONDITION_OF_MONEY_AMOUNT, date);
			DailyValue val = new DailyValue();
			val.set(closingConditionOfMoneyAmount);
			add(qual, ver, val);
		}

		public BigInteger getClosingConditionOfTransactionAmount(Date date) {
			HBaseColumnQualifier qual = new DailyQualifier(
					CLOSING_CONDITION_OF_TRANSACTION_AMOUNT, date);
			DailyValue val = (DailyValue) super.getLatestValue(qual);
			return val.getAsBigInteger();
		}

		public void setClosingConditionOfTransactionAmount(Date date, Date ver,
				BigInteger closingConditionOfTransactionAmount) {
			DailyQualifier qual = new DailyQualifier(
					CLOSING_CONDITION_OF_TRANSACTION_AMOUNT, date);
			DailyValue val = new DailyValue();
			val.set(closingConditionOfTransactionAmount);
			add(qual, ver, val);
		}

		public class DailyQualifier extends HBaseColumnQualifier {
			private static final int COLUMN_NAME_LENGTH = 100;
			private static final int DATE_LENGTH = ByteConvertUtility.DEFAULT_DATE_PATTERN_LENGTH;
			private static final int COLUMN_NAME_BEGIN_INDEX = 0;
			private static final int COLUMN_NAME_END_INDEX = COLUMN_NAME_BEGIN_INDEX
					+ COLUMN_NAME_LENGTH;
			private static final int DATE_BEGIN_INDEX = COLUMN_NAME_END_INDEX + 1;
			private static final int DATE_END_INDEX = DATE_BEGIN_INDEX
					+ DATE_LENGTH;

			public DailyQualifier() {
				super();
			}

			public DailyQualifier(byte[] bytes) {
				super();
				setBytes(bytes);
			}

			public DailyQualifier(String columnName, Date date) {
				super();
				byte[] columnNameBytes = ByteConvertUtility.toBytes(columnName,
						100);
				byte[] dateBytes = ByteConvertUtility.toBytes(date);
				super.setBytes(ArrayUtility.addAll(columnNameBytes, SPACE,
						dateBytes));
			}

			public String getColumnName() {
				return ByteConvertUtility.getStringFromBytes(getBytes(),
						COLUMN_NAME_BEGIN_INDEX, COLUMN_NAME_END_INDEX);
			}

			public void setColumnName(String columnName) {
				byte[] bytes = getBytes();
				byte[] subBytes = ByteConvertUtility.toBytes(columnName, 100);
				ArrayUtility.replace(bytes, subBytes, COLUMN_NAME_BEGIN_INDEX,
						COLUMN_NAME_END_INDEX);
			}

			public Date getDate() {
				try {
					return ByteConvertUtility.getDateFromBytes(getBytes(),
							DATE_BEGIN_INDEX, DATE_END_INDEX);
				} catch (ParseException e) {
					throw new RuntimeException(e);
				}
			}

			public void setDate(Date date) {
				byte[] bytes = getBytes();
				byte[] subBytes = ByteConvertUtility.toBytes(date);
				ArrayUtility.replace(bytes, subBytes, DATE_BEGIN_INDEX,
						DATE_END_INDEX);
			}
		}

		public class DailyValue extends HBaseValue {
			public DailyValue() {
				super();
			}

			public DailyValue(byte[] bytes) {
				super();
				setBytes(bytes);
			}

			public BigDecimal getAsBigDecimal() {
				return ByteConvertUtility.getBigDecimalFromBytes(getBytes());
			}

			public void set(BigDecimal value) {
				setBytes(ByteConvertUtility.toBytes(value));
			}

			public BigInteger getAsBigInteger() {
				return ByteConvertUtility.getBigIntegerFromBytes(getBytes());
			}

			public void set(BigInteger value) {
				setBytes(ByteConvertUtility.toBytes(value));
			}
		}
	}
}
