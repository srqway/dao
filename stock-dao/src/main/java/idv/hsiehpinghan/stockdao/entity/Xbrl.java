package idv.hsiehpinghan.stockdao.entity;

import idv.hsiehpinghan.collectionutility.utility.ArrayUtility;
import idv.hsiehpinghan.datatypeutility.utility.ByteUtility;
import idv.hsiehpinghan.hbaseassistant.abstractclass.HBaseColumnFamily;
import idv.hsiehpinghan.hbaseassistant.abstractclass.HBaseColumnQualifier;
import idv.hsiehpinghan.hbaseassistant.abstractclass.HBaseRowKey;
import idv.hsiehpinghan.hbaseassistant.abstractclass.HBaseTable;
import idv.hsiehpinghan.hbaseassistant.abstractclass.HBaseValue;
import idv.hsiehpinghan.hbaseassistant.utility.ByteConvertUtility;
import idv.hsiehpinghan.stockdao.enumeration.PeriodType;
import idv.hsiehpinghan.stockdao.enumeration.ReportType;
import idv.hsiehpinghan.stockdao.enumeration.UnitType;
import idv.hsiehpinghan.xbrlassistant.enumeration.XbrlTaxonomyVersion;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Date;
import java.util.Set;

public class Xbrl extends HBaseTable {
	private static final byte[] SPACE = ByteUtility.SINGLE_SPACE_BYTE_ARRAY;
	private RowKey rowKey;
	private InfoFamily infoFamily;
	private InstanceFamily instanceFamily;
	private ItemFamily itemFamily;

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

	public InstanceFamily getInstanceFamily() {
		if (instanceFamily == null) {
			instanceFamily = this.new InstanceFamily(this);
		}
		return instanceFamily;
	}

	public ItemFamily getItemFamily() {
		if (itemFamily == null) {
			itemFamily = this.new ItemFamily(this);
		}
		return itemFamily;
	}

	public class RowKey extends HBaseRowKey {
		private static final int STOCK_CODE_LENGTH = 10;
		private static final int REPORT_TYPE_LENGTH = 30;
		private static final int YEAR_LENGTH = 4;
		private static final int SEASON_LENGTH = 1;
		private static final int STOCK_CODE_BEGIN_INDEX = 0;
		private static final int STOCK_CODE_END_INDEX = STOCK_CODE_BEGIN_INDEX
				+ STOCK_CODE_LENGTH;
		private static final int REPORT_TYPE_BEGIN_INDEX = STOCK_CODE_END_INDEX + 1;
		private static final int REPORT_TYPE_END_INDEX = REPORT_TYPE_BEGIN_INDEX
				+ REPORT_TYPE_LENGTH;
		private static final int YEAR_BEGIN_INDEX = REPORT_TYPE_END_INDEX + 1;
		private static final int YEAR_END_INDEX = YEAR_BEGIN_INDEX
				+ YEAR_LENGTH;
		private static final int SEASON_BEGIN_INDEX = YEAR_END_INDEX + 1;
		private static final int SEASON_END_INDEX = SEASON_BEGIN_INDEX
				+ SEASON_LENGTH;

		public RowKey(Xbrl entity) {
			super(entity);
		}

		public RowKey(byte[] bytes, Xbrl entity) {
			super(entity);
			setBytes(bytes);
		}

		public RowKey(String stockCode, ReportType reportType, int year,
				int season, Xbrl entity) {
			super(entity);
			byte[] stockCodeBytes = ByteConvertUtility.toBytes(stockCode, 10);
			byte[] reportTypeBytes = ByteConvertUtility.toBytes(
					reportType.name(), 30);
			byte[] yearBytes = ByteConvertUtility.toBytes(year, 4);
			byte[] seasonBytes = ByteConvertUtility.toBytes(season, 1);
			super.setBytes(ArrayUtility.addAll(stockCodeBytes, SPACE,
					reportTypeBytes, SPACE, yearBytes, SPACE, seasonBytes));
		}

		public String getStockCode() {
			return ByteConvertUtility.getStringFromBytes(getBytes(),
					STOCK_CODE_BEGIN_INDEX, STOCK_CODE_END_INDEX);
		}

		public void setStockCode(String stockCode) {
			byte[] bytes = getBytes();
			byte[] subBytes = ByteConvertUtility.toBytes(stockCode, 10);
			ArrayUtility.replace(bytes, subBytes, STOCK_CODE_BEGIN_INDEX,
					STOCK_CODE_END_INDEX);
		}

		public ReportType getReportType() {
			return ReportType
					.valueOf(ByteConvertUtility.getStringFromBytes(getBytes(),
							REPORT_TYPE_BEGIN_INDEX, REPORT_TYPE_END_INDEX));
		}

		public void setReportType(ReportType reportType) {
			byte[] bytes = getBytes();
			byte[] subBytes = ByteConvertUtility.toBytes(reportType.name(), 30);
			ArrayUtility.replace(bytes, subBytes, REPORT_TYPE_BEGIN_INDEX,
					REPORT_TYPE_END_INDEX);
		}

		public int getYear() {
			return ByteConvertUtility.getIntFromBytes(getBytes(),
					YEAR_BEGIN_INDEX, YEAR_END_INDEX);
		}

		public void setYear(int year) {
			byte[] bytes = getBytes();
			byte[] subBytes = ByteConvertUtility.toBytes(year, 4);
			ArrayUtility.replace(bytes, subBytes, YEAR_BEGIN_INDEX,
					YEAR_END_INDEX);
		}

		public int getSeason() {
			return ByteConvertUtility.getIntFromBytes(getBytes(),
					SEASON_BEGIN_INDEX, SEASON_END_INDEX);
		}

		public void setSeason(int season) {
			byte[] bytes = getBytes();
			byte[] subBytes = ByteConvertUtility.toBytes(season, 1);
			ArrayUtility.replace(bytes, subBytes, SEASON_BEGIN_INDEX,
					SEASON_END_INDEX);
		}
	}

	public class InfoFamily extends HBaseColumnFamily {
		public static final String VERSION = "version";
		public static final String BALANCE_SHEET_CONTEXT = "balanceSheetContext";
		public static final String STATEMENT_OF_COMPREHENSIVE_INCOME_CONTEXT = "statementOfComprehensiveIncomeContext";
		public static final String STATEMENT_OF_CASH_FLOWS_CONTEXT = "statementOfCashFlowsContext";
		public static final String STATEMENT_OF_CHANGES_IN_EQUITY_CONTEXT = "statementOfChangesInEquityContext";

		private InfoFamily(Xbrl entity) {
			super(entity);
		}

		@SuppressWarnings("unchecked")
		public Set<InfoQualifier> getQualifiers() {
			return (Set<InfoQualifier>) (Object) super
					.getQualifierVersionValueMap().keySet();
		}

		public XbrlTaxonomyVersion getVersion() {
			HBaseColumnQualifier qual = new InfoQualifier(VERSION);
			InfoValue val = (InfoValue) super.getLatestValue(qual);
			return val.getAsXbrlTaxonomyVersion();
		}

		public void setVersion(Date ver, XbrlTaxonomyVersion version) {
			InfoQualifier qual = new InfoQualifier(VERSION);
			InfoValue val = new InfoValue();
			val.set(version);
			add(qual, ver, val);
		}

		public String getBalanceSheetContext() {
			HBaseColumnQualifier qual = new InfoQualifier(BALANCE_SHEET_CONTEXT);
			InfoValue val = (InfoValue) super.getLatestValue(qual);
			return val.getAsString();
		}

		public void setBalanceSheetContext(Date ver, String balanceSheetContext) {
			InfoQualifier qual = new InfoQualifier(BALANCE_SHEET_CONTEXT);
			InfoValue val = new InfoValue();
			val.set(balanceSheetContext);
			add(qual, ver, val);
		}

		public String getStatementOfComprehensiveIncomeContext() {
			HBaseColumnQualifier qual = new InfoQualifier(
					STATEMENT_OF_COMPREHENSIVE_INCOME_CONTEXT);
			InfoValue val = (InfoValue) super.getLatestValue(qual);
			return val.getAsString();
		}

		public void setStatementOfComprehensiveIncomeContext(Date ver,
				String statementOfComprehensiveIncomeContext) {
			InfoQualifier qual = new InfoQualifier(
					STATEMENT_OF_COMPREHENSIVE_INCOME_CONTEXT);
			InfoValue val = new InfoValue();
			val.set(statementOfComprehensiveIncomeContext);
			add(qual, ver, val);
		}

		public String getStatementOfCashFlowsContext() {
			HBaseColumnQualifier qual = new InfoQualifier(
					STATEMENT_OF_CASH_FLOWS_CONTEXT);
			InfoValue val = (InfoValue) super.getLatestValue(qual);
			return val.getAsString();
		}

		public void setStatementOfCashFlowsContext(Date ver,
				String statementOfCashFlowsContext) {
			InfoQualifier qual = new InfoQualifier(
					STATEMENT_OF_CASH_FLOWS_CONTEXT);
			InfoValue val = new InfoValue();
			val.set(statementOfCashFlowsContext);
			add(qual, ver, val);
		}

		public String getStatementOfChangesInEquityContext() {
			HBaseColumnQualifier qual = new InfoQualifier(
					STATEMENT_OF_CHANGES_IN_EQUITY_CONTEXT);
			InfoValue val = (InfoValue) super.getLatestValue(qual);
			return val.getAsString();
		}

		public void setStatementOfChangesInEquityContext(Date ver,
				String statementOfChangesInEquityContext) {
			InfoQualifier qual = new InfoQualifier(
					STATEMENT_OF_CHANGES_IN_EQUITY_CONTEXT);
			InfoValue val = new InfoValue();
			val.set(statementOfChangesInEquityContext);
			add(qual, ver, val);
		}

		@Override
		protected HBaseColumnQualifier generateColumnQualifier(byte[] bytes) {
			return this.new InfoQualifier(bytes);
		}

		@Override
		protected HBaseValue generateValue(byte[] bytes) {
			return this.new InfoValue(bytes);
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

			public XbrlTaxonomyVersion getAsXbrlTaxonomyVersion() {
				return XbrlTaxonomyVersion.valueOf(ByteConvertUtility
						.getStringFromBytes(getBytes()));
			}

			public void set(XbrlTaxonomyVersion value) {
				setBytes(ByteConvertUtility.toBytes(value.name()));
			}

			public String getAsString() {
				return ByteConvertUtility.getStringFromBytes(getBytes());
			}

			public void set(String value) {
				setBytes(ByteConvertUtility.toBytes(value));
			}
		}
	}

	public class InstanceFamily extends HBaseColumnFamily {
		private InstanceFamily(Xbrl entity) {
			super(entity);
		}

		@SuppressWarnings("unchecked")
		public Set<InstanceQualifier> getQualifiers() {
			return (Set<InstanceQualifier>) (Object) super
					.getQualifierVersionValueMap().keySet();
		}

		public InstanceValue getInstanceValue(String elementId,
				PeriodType periodType, Date startDate, Date endDate) {
			return getInstanceValue(elementId, periodType, null, startDate,
					endDate);
		}

		public InstanceValue getInstanceValue(String elementId,
				PeriodType periodType, Date instant) {
			return getInstanceValue(elementId, periodType, instant, null, null);
		}

		public void setInstanceValue(String elementId, PeriodType periodType,
				Date instant, Date startDate, Date endDate, Date ver,
				UnitType unitType, BigDecimal value) {
			HBaseColumnQualifier qual = new InstanceQualifier(elementId,
					periodType, instant, startDate, endDate);
			InstanceValue val = new InstanceValue(unitType, value);
			add(qual, ver, val);
		}

		@Override
		protected HBaseColumnQualifier generateColumnQualifier(byte[] bytes) {
			return this.new InstanceQualifier(bytes);
		}

		@Override
		protected HBaseValue generateValue(byte[] bytes) {
			return this.new InstanceValue(bytes);
		}

		private InstanceValue getInstanceValue(String elementId,
				PeriodType periodType, Date instant, Date startDate,
				Date endDate) {
			HBaseColumnQualifier qual = new InstanceQualifier(elementId,
					periodType, instant, startDate, endDate);
			return (InstanceValue) super.getLatestValue(qual);
		}

		public class InstanceQualifier extends HBaseColumnQualifier {
			private static final int ELEMENT_ID_LENGTH = 300;
			private static final int PERIOD_TYPE_LENGTH = 10;
			private static final int INSTANT_LENGTH = ByteConvertUtility.DEFAULT_DATE_PATTERN_LENGTH;
			private static final int START_DATE_LENGTH = ByteConvertUtility.DEFAULT_DATE_PATTERN_LENGTH;
			private static final int END_DATE_LENGTH = ByteConvertUtility.DEFAULT_DATE_PATTERN_LENGTH;
			private static final int ELEMENT_ID_BEGIN_INDEX = 0;
			private static final int ELEMENT_ID_END_INDEX = ELEMENT_ID_BEGIN_INDEX
					+ ELEMENT_ID_LENGTH;
			private static final int PERIOD_TYPE_BEGIN_INDEX = ELEMENT_ID_END_INDEX + 1;
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

			public InstanceQualifier() {
				super();
			}

			public InstanceQualifier(byte[] bytes) {
				super();
				setBytes(bytes);
			}

			public InstanceQualifier(String elementId, PeriodType periodType,
					Date instant, Date startDate, Date endDate) {
				super();
				byte[] elementIdBytes = ByteConvertUtility.toBytes(elementId,
						300);
				byte[] periodTypeBytes = ByteConvertUtility.toBytes(
						periodType.name(), 10);
				byte[] instantBytes = ByteConvertUtility.toBytes(instant);
				byte[] startDateBytes = ByteConvertUtility.toBytes(startDate);
				byte[] endDateBytes = ByteConvertUtility.toBytes(endDate);
				super.setBytes(ArrayUtility.addAll(elementIdBytes, SPACE,
						periodTypeBytes, SPACE, instantBytes, SPACE,
						startDateBytes, SPACE, endDateBytes));
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

		public class InstanceValue extends HBaseValue {
			private static final int UNIT_TYPE_LENGTH = 10;
			private static final int VALUE_LENGTH = 20;
			private static final int UNIT_TYPE_BEGIN_INDEX = 0;
			private static final int UNIT_TYPE_END_INDEX = UNIT_TYPE_BEGIN_INDEX
					+ UNIT_TYPE_LENGTH;
			private static final int VALUE_BEGIN_INDEX = UNIT_TYPE_END_INDEX + 1;
			private static final int VALUE_END_INDEX = VALUE_BEGIN_INDEX
					+ VALUE_LENGTH;

			public InstanceValue() {
				super();
			}

			public InstanceValue(byte[] bytes) {
				super();
				setBytes(bytes);
			}

			public InstanceValue(UnitType unitType, BigDecimal value) {
				super();
				byte[] unitTypeBytes = ByteConvertUtility.toBytes(
						unitType.name(), 10);
				byte[] valueBytes = ByteConvertUtility.toBytes(value, 20);
				super.setBytes(ArrayUtility.addAll(unitTypeBytes, SPACE,
						valueBytes));
			}

			public UnitType getUnitType() {
				return UnitType
						.valueOf(ByteConvertUtility.getStringFromBytes(
								getBytes(), UNIT_TYPE_BEGIN_INDEX,
								UNIT_TYPE_END_INDEX));
			}

			public void setUnitType(UnitType unitType) {
				byte[] bytes = getBytes();
				byte[] subBytes = ByteConvertUtility.toBytes(unitType.name(),
						10);
				ArrayUtility.replace(bytes, subBytes, UNIT_TYPE_BEGIN_INDEX,
						UNIT_TYPE_END_INDEX);
			}

			public BigDecimal getValue() {
				return ByteConvertUtility.getBigDecimalFromBytes(getBytes(),
						VALUE_BEGIN_INDEX, VALUE_END_INDEX);
			}

			public void setValue(BigDecimal value) {
				byte[] bytes = getBytes();
				byte[] subBytes = ByteConvertUtility.toBytes(value, 20);
				ArrayUtility.replace(bytes, subBytes, VALUE_BEGIN_INDEX,
						VALUE_END_INDEX);
			}
		}
	}

	public class ItemFamily extends HBaseColumnFamily {
		private ItemFamily(Xbrl entity) {
			super(entity);
		}

		@SuppressWarnings("unchecked")
		public Set<ItemQualifier> getQualifiers() {
			return (Set<ItemQualifier>) (Object) super
					.getQualifierVersionValueMap().keySet();
		}

		public ItemValue getItemValue(String elementId, PeriodType periodType,
				Date instant, Date startDate, Date endDate) {
			HBaseColumnQualifier qual = new ItemQualifier(elementId,
					periodType, instant, startDate, endDate);
			return (ItemValue) super.getLatestValue(qual);
		}

		public void setItemValue(String elementId, PeriodType periodType,
				Date instant, Date startDate, Date endDate, Date ver,
				BigDecimal value) {
			HBaseColumnQualifier qual = new ItemQualifier(elementId,
					periodType, instant, startDate, endDate);
			ItemValue val = new ItemValue(value);
			add(qual, ver, val);
		}

		@Override
		protected HBaseColumnQualifier generateColumnQualifier(byte[] bytes) {
			return this.new ItemQualifier(bytes);
		}

		@Override
		protected HBaseValue generateValue(byte[] bytes) {
			return this.new ItemValue(bytes);
		}

		public class ItemQualifier extends HBaseColumnQualifier {
			private static final int ELEMENT_ID_LENGTH = 300;
			private static final int PERIOD_TYPE_LENGTH = 10;
			private static final int INSTANT_LENGTH = ByteConvertUtility.DEFAULT_DATE_PATTERN_LENGTH;
			private static final int START_DATE_LENGTH = ByteConvertUtility.DEFAULT_DATE_PATTERN_LENGTH;
			private static final int END_DATE_LENGTH = ByteConvertUtility.DEFAULT_DATE_PATTERN_LENGTH;
			private static final int ELEMENT_ID_BEGIN_INDEX = 0;
			private static final int ELEMENT_ID_END_INDEX = ELEMENT_ID_BEGIN_INDEX
					+ ELEMENT_ID_LENGTH;
			private static final int PERIOD_TYPE_BEGIN_INDEX = ELEMENT_ID_END_INDEX + 1;
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

			public ItemQualifier() {
				super();
			}

			public ItemQualifier(byte[] bytes) {
				super();
				setBytes(bytes);
			}

			public ItemQualifier(String elementId, PeriodType periodType,
					Date instant, Date startDate, Date endDate) {
				super();
				byte[] elementIdBytes = ByteConvertUtility.toBytes(elementId,
						300);
				byte[] periodTypeBytes = ByteConvertUtility.toBytes(
						periodType.name(), 10);
				byte[] instantBytes = ByteConvertUtility.toBytes(instant);
				byte[] startDateBytes = ByteConvertUtility.toBytes(startDate);
				byte[] endDateBytes = ByteConvertUtility.toBytes(endDate);
				super.setBytes(ArrayUtility.addAll(elementIdBytes, SPACE,
						periodTypeBytes, SPACE, instantBytes, SPACE,
						startDateBytes, SPACE, endDateBytes));
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

		public class ItemValue extends HBaseValue {
			public ItemValue() {
				super();
			}

			public ItemValue(byte[] bytes) {
				super();
				setBytes(bytes);
			}

			public ItemValue(BigDecimal value) {
				super();
				setValue(value);
			}

			public BigDecimal getValue() {
				return ByteConvertUtility.getBigDecimalFromBytes(getBytes());
			}

			public void setValue(BigDecimal value) {
				byte[] bytes = ByteConvertUtility.toBytes(value, 20);
				setBytes(bytes);
			}
		}
	}
}
