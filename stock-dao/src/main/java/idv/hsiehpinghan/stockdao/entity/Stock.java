package idv.hsiehpinghan.stockdao.entity;

import idv.hsiehpinghan.collectionutility.utility.ArrayUtility;
import idv.hsiehpinghan.datatypeutility.utility.ByteUtility;
import idv.hsiehpinghan.hbaseassistant.abstractclass.HBaseColumnFamily;
import idv.hsiehpinghan.hbaseassistant.abstractclass.HBaseColumnQualifier;
import idv.hsiehpinghan.hbaseassistant.abstractclass.HBaseRowKey;
import idv.hsiehpinghan.hbaseassistant.abstractclass.HBaseTable;
import idv.hsiehpinghan.hbaseassistant.abstractclass.HBaseValue;
import idv.hsiehpinghan.hbaseassistant.utility.ByteConvertUtility;
import idv.hsiehpinghan.stockdao.enumeration.ElementType;
import idv.hsiehpinghan.stockdao.enumeration.IndustryType;
import idv.hsiehpinghan.stockdao.enumeration.MarketType;
import idv.hsiehpinghan.stockdao.enumeration.PeriodType;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.ParseException;
import java.util.Date;

public class Stock extends HBaseTable {
	private static final byte[] SPACE = ByteUtility.SINGLE_SPACE_BYTE_ARRAY;
	private static final int DEFAULT_DATE_LENGTH = ByteConvertUtility.DEFAULT_DATE_PATTERN_LENGTH;
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
		public RowKey(HBaseTable table) {
			super(table);
		}

		public RowKey(byte[] bytes, Stock entity) {
			super(entity);
			setBytes(bytes);
		}

		public RowKey(String stockCode, Stock entity) {
			super(entity);
			setBytes(ByteConvertUtility.toBytes(stockCode));
		}
		
		public String getStockCode() {
			return ByteConvertUtility.getStringFromBytes(getBytes());
		}

		public void setStockCode(String stockCode) {
			setBytes(ByteConvertUtility.toBytes(stockCode));
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
		public static final String ACCOUNTANT_1 = "accountant_1";
		public static final String ACCOUNTANT_2 = "accountant_2";

		private InfoFamily(Stock entity) {
			super(entity);
		}

		public MarketType getLatestMarketType() {
			HBaseColumnQualifier qual = new InfoQualifier(MARKET_TYPE);
			InfoValue val = (InfoValue) super.getLatestValue(qual);
			return val.getAsMarketType();
		}

		public void addMarketType(Date date, MarketType marketType) {
			InfoQualifier qual = new InfoQualifier(MARKET_TYPE);
			InfoValue val = new InfoValue();
			val.set(marketType);
			add(qual, date, val);
		}

		public IndustryType getIndustryType() {
			HBaseColumnQualifier qual = new InfoQualifier(INDUSTRY_TYPE);
			InfoValue val = (InfoValue) super.getLatestValue(qual);
			return val.getAsIndustryType();
		}

		public void addIndustryType(Date date, IndustryType industryType) {
			InfoQualifier qual = new InfoQualifier(INDUSTRY_TYPE);
			InfoValue val = new InfoValue();
			val.set(industryType);
			add(qual, date, val);
		}

		public String getChineseName() {
			HBaseColumnQualifier qual = new InfoQualifier(CHINESE_NAME);
			InfoValue val = (InfoValue) super.getLatestValue(qual);
			return val.getAsString();
		}

		public void addChineseName(Date date, String chineseName) {
			InfoQualifier qual = new InfoQualifier(CHINESE_NAME);
			InfoValue val = new InfoValue();
			val.set(chineseName);
			add(qual, date, val);
		}

		public String getEnglishBriefName() {
			HBaseColumnQualifier qual = new InfoQualifier(ENGLISH_BRIEF_NAME);
			InfoValue val = (InfoValue) super.getLatestValue(qual);
			return val.getAsString();
		}

		public void addEnglishBriefName(Date date, String englishBriefName) {
			InfoQualifier qual = new InfoQualifier(ENGLISH_BRIEF_NAME);
			InfoValue val = new InfoValue();
			val.set(englishBriefName);
			add(qual, date, val);
		}

		public String getUnifiedBusinessNumber() {
			HBaseColumnQualifier qual = new InfoQualifier(
					UNIFIED_BUSINESS_NUMBER);
			InfoValue val = (InfoValue) super.getLatestValue(qual);
			return val.getAsString();
		}

		public void addUnifiedBusinessNumber(Date date,
				String unifiedBusinessNumber) {
			InfoQualifier qual = new InfoQualifier(UNIFIED_BUSINESS_NUMBER);
			InfoValue val = new InfoValue();
			val.set(unifiedBusinessNumber);
			add(qual, date, val);
		}

		public String getEstablishmentDate() {
			HBaseColumnQualifier qual = new InfoQualifier(ESTABLISHMENT_DATE);
			InfoValue val = (InfoValue) super.getLatestValue(qual);
			return val.getAsString();
		}

		public void addEstablishmentDate(Date date, String establishmentDate) {
			InfoQualifier qual = new InfoQualifier(ESTABLISHMENT_DATE);
			InfoValue val = new InfoValue();
			val.set(establishmentDate);
			add(qual, date, val);
		}

		public String getListingDate() {
			HBaseColumnQualifier qual = new InfoQualifier(LISTING_DATE);
			InfoValue val = (InfoValue) super.getLatestValue(qual);
			return val.getAsString();
		}

		public void addListingDate(Date date, String listingDate) {
			InfoQualifier qual = new InfoQualifier(LISTING_DATE);
			InfoValue val = new InfoValue();
			val.set(listingDate);
			add(qual, date, val);
		}

		public String getChairman() {
			HBaseColumnQualifier qual = new InfoQualifier(CHAIRMAN);
			InfoValue val = (InfoValue) super.getLatestValue(qual);
			return val.getAsString();
		}

		public void addChairman(Date date, String chairman) {
			InfoQualifier qual = new InfoQualifier(CHAIRMAN);
			InfoValue val = new InfoValue();
			val.set(chairman);
			add(qual, date, val);
		}

		public String getGeneralManager() {
			HBaseColumnQualifier qual = new InfoQualifier(GENERAL_MANAGER);
			InfoValue val = (InfoValue) super.getLatestValue(qual);
			return val.getAsString();
		}

		public void addGeneralManager(Date date, String generalManager) {
			InfoQualifier qual = new InfoQualifier(GENERAL_MANAGER);
			InfoValue val = new InfoValue();
			val.set(generalManager);
			add(qual, date, val);
		}

		public String getSpokesperson() {
			HBaseColumnQualifier qual = new InfoQualifier(SPOKESPERSON);
			InfoValue val = (InfoValue) super.getLatestValue(qual);
			return val.getAsString();
		}

		public void addSpokesperson(Date date, String spokesperson) {
			InfoQualifier qual = new InfoQualifier(SPOKESPERSON);
			InfoValue val = new InfoValue();
			val.set(spokesperson);
			add(qual, date, val);
		}

		public String getJobTitleOfSpokesperson() {
			HBaseColumnQualifier qual = new InfoQualifier(
					JOB_TITLE_OF_SPOKESPERSON);
			InfoValue val = (InfoValue) super.getLatestValue(qual);
			return val.getAsString();
		}

		public void addJobTitleOfSpokesperson(Date date,
				String jobTitleOfSpokesperson) {
			InfoQualifier qual = new InfoQualifier(JOB_TITLE_OF_SPOKESPERSON);
			InfoValue val = new InfoValue();
			val.set(jobTitleOfSpokesperson);
			add(qual, date, val);
		}

		public String getActingSpokesman() {
			HBaseColumnQualifier qual = new InfoQualifier(ACTING_SPOKESMAN);
			InfoValue val = (InfoValue) super.getLatestValue(qual);
			return val.getAsString();
		}

		public void addActingSpokesman(Date date, String actingSpokesman) {
			InfoQualifier qual = new InfoQualifier(ACTING_SPOKESMAN);
			InfoValue val = new InfoValue();
			val.set(actingSpokesman);
			add(qual, date, val);
		}

		public String getChineseAddress() {
			HBaseColumnQualifier qual = new InfoQualifier(CHINESE_ADDRESS);
			InfoValue val = (InfoValue) super.getLatestValue(qual);
			return val.getAsString();
		}

		public void addChineseAddress(Date date, String chineseAddress) {
			InfoQualifier qual = new InfoQualifier(CHINESE_ADDRESS);
			InfoValue val = new InfoValue();
			val.set(chineseAddress);
			add(qual, date, val);
		}

		public String getTelephone() {
			HBaseColumnQualifier qual = new InfoQualifier(TELEPHONE);
			InfoValue val = (InfoValue) super.getLatestValue(qual);
			return val.getAsString();
		}

		public void addTelephone(Date date, String telephone) {
			InfoQualifier qual = new InfoQualifier(TELEPHONE);
			InfoValue val = new InfoValue();
			val.set(telephone);
			add(qual, date, val);
		}

		public String getStockTransferAgency() {
			HBaseColumnQualifier qual = new InfoQualifier(STOCK_TRANSFER_AGENCY);
			InfoValue val = (InfoValue) super.getLatestValue(qual);
			return val.getAsString();
		}

		public void addStockTransferAgency(Date date, String stockTransferAgency) {
			InfoQualifier qual = new InfoQualifier(STOCK_TRANSFER_AGENCY);
			InfoValue val = new InfoValue();
			val.set(stockTransferAgency);
			add(qual, date, val);
		}

		public String getTelephoneOfStockTransferAgency() {
			HBaseColumnQualifier qual = new InfoQualifier(
					TELEPHONE_OF_STOCK_TRANSFER_AGENCY);
			InfoValue val = (InfoValue) super.getLatestValue(qual);
			return val.getAsString();
		}

		public void addTelephoneOfStockTransferAgency(Date date,
				String telephoneOfStockTransferAgency) {
			InfoQualifier qual = new InfoQualifier(
					TELEPHONE_OF_STOCK_TRANSFER_AGENCY);
			InfoValue val = new InfoValue();
			val.set(telephoneOfStockTransferAgency);
			add(qual, date, val);
		}

		public String getAddressOfStockTransferAgency() {
			HBaseColumnQualifier qual = new InfoQualifier(
					ADDRESS_OF_STOCK_TRANSFER_AGENCY);
			InfoValue val = (InfoValue) super.getLatestValue(qual);
			return val.getAsString();
		}

		public void addAddressOfStockTransferAgency(Date date,
				String addressOfStockTransferAgency) {
			InfoQualifier qual = new InfoQualifier(
					ADDRESS_OF_STOCK_TRANSFER_AGENCY);
			InfoValue val = new InfoValue();
			val.set(addressOfStockTransferAgency);
			add(qual, date, val);
		}

		public String getEnglishAddress() {
			HBaseColumnQualifier qual = new InfoQualifier(ENGLISH_ADDRESS);
			InfoValue val = (InfoValue) super.getLatestValue(qual);
			return val.getAsString();
		}

		public void addEnglishAddress(Date date, String englishAddress) {
			InfoQualifier qual = new InfoQualifier(ENGLISH_ADDRESS);
			InfoValue val = new InfoValue();
			val.set(englishAddress);
			add(qual, date, val);
		}

		public String getFaxNumber() {
			HBaseColumnQualifier qual = new InfoQualifier(FAX_NUMBER);
			InfoValue val = (InfoValue) super.getLatestValue(qual);
			return val.getAsString();
		}

		public void addFaxNumber(Date date, String faxNumber) {
			InfoQualifier qual = new InfoQualifier(FAX_NUMBER);
			InfoValue val = new InfoValue();
			val.set(faxNumber);
			add(qual, date, val);
		}

		public String getEmail() {
			HBaseColumnQualifier qual = new InfoQualifier(EMAIL);
			InfoValue val = (InfoValue) super.getLatestValue(qual);
			return val.getAsString();
		}

		public void setEmail(Date date, String email) {
			InfoQualifier qual = new InfoQualifier(EMAIL);
			InfoValue val = new InfoValue();
			val.set(email);
			add(qual, date, val);
		}

		public String getWebSite() {
			HBaseColumnQualifier qual = new InfoQualifier(WEB_SITE);
			InfoValue val = (InfoValue) super.getLatestValue(qual);
			return val.getAsString();
		}

		public void addWebSite(Date date, String webSite) {
			InfoQualifier qual = new InfoQualifier(WEB_SITE);
			InfoValue val = new InfoValue();
			val.set(webSite);
			add(qual, date, val);
		}

		public String getFinancialReportType() {
			HBaseColumnQualifier qual = new InfoQualifier(FINANCIAL_REPORT_TYPE);
			InfoValue val = (InfoValue) super.getLatestValue(qual);
			return val.getAsString();
		}

		public void addFinancialReportType(Date date, String financialReportType) {
			InfoQualifier qual = new InfoQualifier(FINANCIAL_REPORT_TYPE);
			InfoValue val = new InfoValue();
			val.set(financialReportType);
			add(qual, date, val);
		}

		public String getParValueOfOrdinaryShares() {
			HBaseColumnQualifier qual = new InfoQualifier(
					PAR_VALUE_OF_ORDINARY_SHARES);
			InfoValue val = (InfoValue) super.getLatestValue(qual);
			return val.getAsString();
		}

		public void addParValueOfOrdinaryShares(Date date,
				String parValueOfOrdinaryShares) {
			InfoQualifier qual = new InfoQualifier(PAR_VALUE_OF_ORDINARY_SHARES);
			InfoValue val = new InfoValue();
			val.set(parValueOfOrdinaryShares);
			add(qual, date, val);
		}

		public String getPaidInCapital() {
			HBaseColumnQualifier qual = new InfoQualifier(PAID_IN_CAPITAL);
			InfoValue val = (InfoValue) super.getLatestValue(qual);
			return val.getAsString();
		}

		public void addPaidInCapital(Date date, String paidInCapital) {
			InfoQualifier qual = new InfoQualifier(PAID_IN_CAPITAL);
			InfoValue val = new InfoValue();
			val.set(paidInCapital);
			add(qual, date, val);
		}

		public String getAmountOfOrdinaryShares() {
			HBaseColumnQualifier qual = new InfoQualifier(
					AMOUNT_OF_ORDINARY_SHARES);
			InfoValue val = (InfoValue) super.getLatestValue(qual);
			return val.getAsString();
		}

		public void addAmountOfOrdinaryShares(Date date,
				String amountOfOrdinaryShares) {
			InfoQualifier qual = new InfoQualifier(AMOUNT_OF_ORDINARY_SHARES);
			InfoValue val = new InfoValue();
			val.set(amountOfOrdinaryShares);
			add(qual, date, val);
		}

		public String getPrivatePlacementAmountOfOrdinaryShares() {
			HBaseColumnQualifier qual = new InfoQualifier(
					PRIVATE_PLACEMENT_AMOUNT_OF_ORDINARY_SHARES);
			InfoValue val = (InfoValue) super.getLatestValue(qual);
			return val.getAsString();
		}

		public void addPrivatePlacementAmountOfOrdinaryShares(Date date,
				String privatePlacementAmountOfOrdinaryShares) {
			InfoQualifier qual = new InfoQualifier(
					PRIVATE_PLACEMENT_AMOUNT_OF_ORDINARY_SHARES);
			InfoValue val = new InfoValue();
			val.set(privatePlacementAmountOfOrdinaryShares);
			add(qual, date, val);
		}

		public String getAmountOfPreferredShares() {
			HBaseColumnQualifier qual = new InfoQualifier(
					AMOUNT_OF_PREFERRED_SHARES);
			InfoValue val = (InfoValue) super.getLatestValue(qual);
			return val.getAsString();
		}

		public void addAmountOfPreferredShares(Date date,
				String amountOfPreferredShares) {
			InfoQualifier qual = new InfoQualifier(AMOUNT_OF_PREFERRED_SHARES);
			InfoValue val = new InfoValue();
			val.set(amountOfPreferredShares);
			add(qual, date, val);
		}

		public String getAccountingFirm() {
			HBaseColumnQualifier qual = new InfoQualifier(ACCOUNTING_FIRM);
			InfoValue val = (InfoValue) super.getLatestValue(qual);
			return val.getAsString();
		}

		public void addAccountingFirm(Date date, String accountingFirm) {
			InfoQualifier qual = new InfoQualifier(ACCOUNTING_FIRM);
			InfoValue val = new InfoValue();
			val.set(accountingFirm);
			add(qual, date, val);
		}

		public String getAccountant1() {
			HBaseColumnQualifier qual = new InfoQualifier(ACCOUNTANT_1);
			InfoValue val = (InfoValue) super.getLatestValue(qual);
			return val.getAsString();
		}

		public void addAccountant1(Date date, String accountant1) {
			InfoQualifier qual = new InfoQualifier(ACCOUNTANT_1);
			InfoValue val = new InfoValue();
			val.set(accountant1);
			add(qual, date, val);
		}

		public String getAccountant2() {
			HBaseColumnQualifier qual = new InfoQualifier(ACCOUNTANT_2);
			InfoValue val = (InfoValue) super.getLatestValue(qual);
			return val.getAsString();
		}

		public void addAccountant2(Date date, String accountant2) {
			InfoQualifier qual = new InfoQualifier(ACCOUNTANT_1);
			InfoValue val = new InfoValue();
			val.set(accountant2);
			add(qual, date, val);
		}

		public class InfoQualifier extends HBaseColumnQualifier {
			public InfoQualifier() {
				super();
			}

			public InfoQualifier(String name) {
				super();
				setBytes(ByteConvertUtility.toBytes(name));
			}

			public InfoQualifier(byte[] bytes) {
				super();
				setBytes(bytes);
			}

			public String getName() {
				return ByteConvertUtility.getStringFromBytes(getBytes());
			}

			public void setName(String name) {
				setBytes(ByteConvertUtility.toBytes(name));
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

			private MarketType getAsMarketType() {
				return MarketType.valueOf(ByteConvertUtility
						.getStringFromBytes(getBytes()));
			}

			public void set(MarketType marketType) {
				setBytes(ByteConvertUtility.toBytes(marketType.name()));
			}

			public IndustryType getAsIndustryType() {
				return IndustryType.valueOf(ByteConvertUtility
						.getStringFromBytes(getBytes()));
			}

			public void set(IndustryType industryType) {
				setBytes(ByteConvertUtility.toBytes(industryType.name()));
			}

			public String getAsString() {
				return ByteConvertUtility.getStringFromBytes(getBytes());
			}

			public void set(String chineseName) {
				setBytes(ByteConvertUtility.toBytes(chineseName));
			}
		}

		@Override
		protected HBaseColumnQualifier generateColumnQualifier(byte[] bytes) {
			return this.new InfoQualifier(bytes);
		}

		@Override
		protected HBaseValue generateValue(byte[] bytes) {
			return this.new InfoValue(bytes);
		}

	}

	public class FinancialReportFamily extends HBaseColumnFamily {
		protected FinancialReportFamily(Stock entity) {
			super(entity);
		}

		public BigDecimal getValueAsBigDecimal(String elementId,
				ElementType elementType, PeriodType periodType, Date instant,
				Date startDate, Date endDate) {
			FinancialReportQualifier qual = new FinancialReportQualifier(
					elementId, elementType, periodType, instant, startDate,
					endDate);
			FinancialReportValue val = (FinancialReportValue) super
					.getLatestValue(qual);
			return val.getAsBigDecimal();
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
					+ PERIOD_TYPE_LENGTH;
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

			public FinancialReportQualifier(String elementId,
					ElementType elementType, PeriodType periodType,
					Date instant, Date startDate, Date endDate) {
				super();
				byte[] elementIdBytes = ByteConvertUtility.toBytes(elementId,
						ELEMENT_ID_LENGTH);
				byte[] elementTypeBytes = ByteConvertUtility.toBytes(
						elementType.name(), ELEMENT_TYPE_LENGTH);
				byte[] periodTypeBytes = ByteConvertUtility.toBytes(
						periodType.name(), PERIOD_TYPE_LENGTH);
				byte[] instantBytes = ByteConvertUtility.toBytes(instant);
				byte[] startDateBytes = ByteConvertUtility.toBytes(startDate);
				byte[] endDateBytes = ByteConvertUtility.toBytes(endDate);
				super.setBytes(ArrayUtility.addAll(elementIdBytes, SPACE,
						elementTypeBytes, SPACE, periodTypeBytes, SPACE,
						instantBytes, SPACE, startDateBytes, SPACE,
						endDateBytes));
			}

			public FinancialReportQualifier(byte[] bytes) {
				super();
				setBytes(bytes);
			}

			public String getElementId() {
				return ByteConvertUtility.getStringFromBytes(getBytes(),
						ELEMENT_ID_BEGIN_INDEX, ELEMENT_ID_END_INDEX);
			}

			public void setElementId(String elementId) {
				byte[] bytes = getBytes();
				byte[] subBytes = ByteConvertUtility.toBytes(elementId);
				ArrayUtility.replace(bytes, subBytes, ELEMENT_ID_BEGIN_INDEX,
						ELEMENT_ID_END_INDEX);
			}

			public ElementType getElementType() {
				return ElementType.valueOf(ByteConvertUtility
						.getStringFromBytes(getBytes(), ELEMENT_ID_BEGIN_INDEX,
								ELEMENT_ID_END_INDEX));
			}

			public void setElementType(ElementType elementType) {
				byte[] bytes = getBytes();
				byte[] subBytes = ByteConvertUtility.toBytes(elementType
						.toString());
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
				byte[] subBytes = ByteConvertUtility.toBytes(periodType
						.toString());
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
							START_DATE_BEGIN_INDEX, START_DATE_END_INDEX);
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

			public BigDecimal getAsBigDecimal() {
				return ByteConvertUtility.getBigDecimalFromBytes(getBytes());
			}

			public void set(BigDecimal value) {
				setBytes(ByteConvertUtility.toBytes(value));
			}
		}

		@Override
		protected HBaseColumnQualifier generateColumnQualifier(byte[] bytes) {
			return this.new FinancialReportQualifier(bytes);
		}

		@Override
		protected HBaseValue generateValue(byte[] valueBytes) {
			return this.new FinancialReportValue();
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

		private MonthlyFamily(Stock entity) {
			super(entity);
		}

		public BigInteger getOperatingIncomeOfCurrentMonth(int year, int month) {
			HBaseColumnQualifier qual = new MonthlyQualifier(year, month,
					OPERATING_INCOME_OF_CURRENT_MONTH);
			MonthlyValue val = (MonthlyValue) super.getLatestValue(qual);
			return val.getAsBigInteger();
		}

		public void addOperatingIncomeOfCurrentMonth(int year, int month,
				Date date, BigInteger value) {
			MonthlyQualifier qual = new MonthlyQualifier(year, month,
					OPERATING_INCOME_OF_CURRENT_MONTH);
			MonthlyValue val = new MonthlyValue();
			val.set(value);
			add(qual, date, val);
		}

		public BigInteger getOperatingIncomeOfCurrentMonthOfLastYear(int year,
				int month) {
			HBaseColumnQualifier qual = new MonthlyQualifier(year, month,
					OPERATING_INCOME_OF_CURRENT_MONTH_OF_LAST_YEAR);
			MonthlyValue val = (MonthlyValue) super.getLatestValue(qual);
			return val.getAsBigInteger();
		}

		public void addOperatingIncomeOfCurrentMonthOfLastYear(int year,
				int month, Date date, BigInteger value) {
			MonthlyQualifier qual = new MonthlyQualifier(year, month,
					OPERATING_INCOME_OF_CURRENT_MONTH_OF_LAST_YEAR);
			MonthlyValue val = new MonthlyValue();
			val.set(value);
			add(qual, date, val);
		}

		public BigInteger getOperatingIncomeOfDifferentAmount(int year,
				int month) {
			HBaseColumnQualifier qual = new MonthlyQualifier(year, month,
					OPERATING_INCOME_OF_DIFFERENT_AMOUNT);
			MonthlyValue val = (MonthlyValue) super.getLatestValue(qual);
			return val.getAsBigInteger();
		}

		public void addOperatingIncomeOfDifferentAmount(int year, int month,
				Date date, BigInteger value) {
			MonthlyQualifier qual = new MonthlyQualifier(year, month,
					OPERATING_INCOME_OF_DIFFERENT_AMOUNT);
			MonthlyValue val = new MonthlyValue();
			val.set(value);
			add(qual, date, val);
		}

		public BigDecimal getOperatingIncomeOfDifferentPercent(int year,
				int month) {
			HBaseColumnQualifier qual = new MonthlyQualifier(year, month,
					OPERATING_INCOME_OF_DIFFERENT_PERCENT);
			MonthlyValue val = (MonthlyValue) super.getLatestValue(qual);
			return val.getAsBigDecimal();
		}

		public void addOperatingIncomeOfDifferentPercent(int year, int month,
				Date date, BigDecimal value) {
			MonthlyQualifier qual = new MonthlyQualifier(year, month,
					OPERATING_INCOME_OF_DIFFERENT_PERCENT);
			MonthlyValue val = new MonthlyValue();
			val.set(value);
			add(qual, date, val);
		}

		public BigInteger getOperatingIncomeOfCumulativeAmountOfThisYear(
				int year, int month) {
			HBaseColumnQualifier qual = new MonthlyQualifier(year, month,
					OPERATING_INCOME_OF_CUMULATIVE_AMOUNT_OF_THIS_YEAR);
			MonthlyValue val = (MonthlyValue) super.getLatestValue(qual);
			return val.getAsBigInteger();
		}

		public void addOperatingIncomeOfCumulativeAmountOfThisYear(int year,
				int month, Date date, BigInteger value) {
			MonthlyQualifier qual = new MonthlyQualifier(year, month,
					OPERATING_INCOME_OF_CUMULATIVE_AMOUNT_OF_THIS_YEAR);
			MonthlyValue val = new MonthlyValue();
			val.set(value);
			add(qual, date, val);
		}

		public BigInteger getOperatingIncomeOfCumulativeAmountOfLastYear(
				int year, int month) {
			HBaseColumnQualifier qual = new MonthlyQualifier(year, month,
					OPERATING_INCOME_OF_CUMULATIVE_AMOUNT_OF_LAST_YEAR);
			MonthlyValue val = (MonthlyValue) super.getLatestValue(qual);
			return val.getAsBigInteger();
		}

		public void addOperatingIncomeOfCumulativeAmountOfLastYear(int year,
				int month, Date date, BigInteger value) {
			MonthlyQualifier qual = new MonthlyQualifier(year, month,
					OPERATING_INCOME_OF_CUMULATIVE_AMOUNT_OF_LAST_YEAR);
			MonthlyValue val = new MonthlyValue();
			val.set(value);
			add(qual, date, val);
		}

		public BigInteger getOperatingIncomeOfCumulativeDifferentAmount(
				int year, int month) {
			HBaseColumnQualifier qual = new MonthlyQualifier(year, month,
					OPERATING_INCOME_OF_CUMULATIVE_DIFFERENT_AMOUNT);
			MonthlyValue val = (MonthlyValue) super.getLatestValue(qual);
			return val.getAsBigInteger();
		}

		public void addOperatingIncomeOfCumulativeDifferentAmount(int year,
				int month, Date date, BigInteger value) {
			MonthlyQualifier qual = new MonthlyQualifier(year, month,
					OPERATING_INCOME_OF_CUMULATIVE_DIFFERENT_AMOUNT);
			MonthlyValue val = new MonthlyValue();
			val.set(value);
			add(qual, date, val);
		}

		public BigDecimal getOperatingIncomeOfCumulativeDifferentPercent(
				int year, int month) {
			HBaseColumnQualifier qual = new MonthlyQualifier(year, month,
					OPERATING_INCOME_OF_CUMULATIVE_DIFFERENT_PERCENT);
			MonthlyValue val = (MonthlyValue) super.getLatestValue(qual);
			return val.getAsBigDecimal();
		}

		public void addOperatingIncomeOfCumulativeDifferentPercent(int year,
				int month, Date date, BigDecimal value) {
			MonthlyQualifier qual = new MonthlyQualifier(year, month,
					OPERATING_INCOME_OF_CUMULATIVE_DIFFERENT_PERCENT);
			MonthlyValue val = new MonthlyValue();
			val.set(value);
			add(qual, date, val);
		}

		public String getOperatingIncomeOfComment(int year, int month) {
			HBaseColumnQualifier qual = new MonthlyQualifier(year, month,
					OPERATING_INCOME_OF_COMMENT);
			MonthlyValue val = (MonthlyValue) super.getLatestValue(qual);
			return val.getAsString();
		}

		public void addOperatingIncomeOfComment(int year, int month, Date date,
				String value) {
			MonthlyQualifier qual = new MonthlyQualifier(year, month,
					OPERATING_INCOME_OF_COMMENT);
			MonthlyValue val = new MonthlyValue();
			val.set(value);
			add(qual, date, val);
		}

		public class MonthlyQualifier extends HBaseColumnQualifier {
			private static final int YEAR_LENGTH = 4;
			private static final int MONTH_LENGTH = 2;
			private static final int NAME_LENGTH = 50;
			private static final int YEAR_BEGIN_INDEX = 0;
			private static final int YEAR_END_INDEX = YEAR_BEGIN_INDEX
					+ YEAR_LENGTH;
			private static final int MONTH_BEGIN_INDEX = YEAR_END_INDEX + 1;
			private static final int MONTH_END_INDEX = MONTH_BEGIN_INDEX
					+ MONTH_LENGTH;
			private static final int NAME_BEGIN_INDEX = MONTH_END_INDEX + 1;
			private static final int NAME_END_INDEX = NAME_BEGIN_INDEX
					+ NAME_LENGTH;

			public MonthlyQualifier() {
				super();
			}

			public MonthlyQualifier(int year, int month, String name) {
				super();
				byte[] yearBytes = ByteConvertUtility
						.toBytes(year, YEAR_LENGTH);
				byte[] monthBytes = ByteConvertUtility.toBytes(month,
						MONTH_LENGTH);
				byte[] nameBytes = ByteConvertUtility
						.toBytes(name, NAME_LENGTH);
				super.setBytes(ArrayUtility.addAll(yearBytes, SPACE,
						monthBytes, SPACE, nameBytes));
			}

			public MonthlyQualifier(byte[] bytes) {
				super();
				setBytes(bytes);
			}

			public Integer getYear() {
				return ByteConvertUtility.getIntegerFromBytes(getBytes(),
						YEAR_BEGIN_INDEX, YEAR_END_INDEX);
			}

			public void setYear(int year) {
				byte[] bytes = getBytes();
				byte[] subBytes = ByteConvertUtility.toBytes(year);
				ArrayUtility.replace(bytes, subBytes, YEAR_BEGIN_INDEX,
						YEAR_END_INDEX);
			}

			public Integer getMonth() {
				return ByteConvertUtility.getIntegerFromBytes(getBytes(),
						MONTH_BEGIN_INDEX, MONTH_END_INDEX);
			}

			public void setMonth(int month) {
				byte[] bytes = getBytes();
				byte[] subBytes = ByteConvertUtility.toBytes(month);
				ArrayUtility.replace(bytes, subBytes, MONTH_BEGIN_INDEX,
						MONTH_END_INDEX);
			}

			public String getName() {
				return ByteConvertUtility.getStringFromBytes(getBytes(),
						NAME_BEGIN_INDEX, NAME_END_INDEX);
			}

			public void setName(String name) {
				byte[] bytes = getBytes();
				byte[] subBytes = ByteConvertUtility.toBytes(name);
				ArrayUtility.replace(bytes, subBytes, NAME_BEGIN_INDEX,
						NAME_END_INDEX);
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

			public void set(BigInteger value) {
				setBytes(ByteConvertUtility.toBytes(value));
			}

			public BigInteger getAsBigInteger() {
				return ByteConvertUtility.getBigIntegerFromBytes(getBytes());
			}

			public void set(BigDecimal value) {
				setBytes(ByteConvertUtility.toBytes(value));
			}

			public BigDecimal getAsBigDecimal() {
				return ByteConvertUtility.getBigDecimalFromBytes(getBytes());
			}

			public void set(String value) {
				setBytes(ByteConvertUtility.toBytes(value));
			}

			public String getAsString() {
				return ByteConvertUtility.getStringFromBytes(getBytes());
			}
		}

		@Override
		protected HBaseColumnQualifier generateColumnQualifier(byte[] bytes) {
			return this.new MonthlyQualifier(bytes);
		}

		@Override
		protected HBaseValue generateValue(byte[] bytes) {
			return this.new MonthlyValue(bytes);
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

		public DailyFamily(Stock entity) {
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

		public class DailyQualifier extends HBaseColumnQualifier {
			private static final int NAME_LENGTH = 100;
			private static final int DATE_LENGTH = DEFAULT_DATE_LENGTH;

			private static final int NAME_BEGIN_INDEX = 0;
			private static final int NAME_END_INDEX = NAME_BEGIN_INDEX
					+ NAME_LENGTH;
			private static final int DATE_BEGIN_INDEX = NAME_END_INDEX + 1;
			private static final int DATE_END_INDEX = DATE_BEGIN_INDEX
					+ DATE_LENGTH;

			public DailyQualifier() {
				super();
			}

			public DailyQualifier(String name, Date date) {
				super();
				byte[] nameBytes = ByteConvertUtility
						.toBytes(name, NAME_LENGTH);
				byte[] dateBytes = ByteConvertUtility.toBytes(date);
				super.setBytes(ArrayUtility.addAll(nameBytes, SPACE, dateBytes));
			}

			public DailyQualifier(byte[] bytes) {
				super();
				setBytes(bytes);
			}

			public String getName() {
				return ByteConvertUtility.getStringFromBytes(getBytes(),
						NAME_BEGIN_INDEX, NAME_END_INDEX);
			}

			public void setName(String name) {
				byte[] bytes = getBytes();
				byte[] subBytes = ByteConvertUtility.toBytes(name);
				ArrayUtility.replace(bytes, subBytes, NAME_BEGIN_INDEX,
						NAME_END_INDEX);
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

			public String getAsString() {
				return ByteConvertUtility.getStringFromBytes(getBytes());
			}

			public void set(String value) {
				setBytes(ByteConvertUtility.toBytes(value));
			}

			public Date getAsDate() {
				try {
					return ByteConvertUtility.getDateFromBytes(getBytes());
				} catch (ParseException e) {
					throw new RuntimeException(e);
				}
			}

			public void set(Date value) {
				setBytes(ByteConvertUtility.toBytes(value));
			}
		}
	}

}
