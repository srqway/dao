package idv.hsiehpinghan.mopsdao.entity;

import idv.hsiehpinghan.collectionutility.utility.ArrayUtility;
import idv.hsiehpinghan.datatypeutility.utility.ByteUtility;
import idv.hsiehpinghan.hbaseassistant.abstractclass.HBaseColumnFamily;
import idv.hsiehpinghan.hbaseassistant.abstractclass.HBaseColumnQualifier;
import idv.hsiehpinghan.hbaseassistant.abstractclass.HBaseRowKey;
import idv.hsiehpinghan.hbaseassistant.abstractclass.HBaseTable;
import idv.hsiehpinghan.hbaseassistant.abstractclass.HBaseValue;
import idv.hsiehpinghan.hbaseassistant.utility.ByteConvertUtility;
import idv.hsiehpinghan.mopsdao.enumeration.ReportType;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Date;
import java.util.Map.Entry;
import java.util.Set;

public class FinancialReportInstance extends HBaseTable {
	private static final byte[] SPACE = ByteUtility.SINGLE_SPACE_BYTE_ARRAY;
	private InfoFamily infoFamily;
	private InstanceFamily instanceFamily;

	public FinancialReportInstance() {
		super();
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

	public class Key extends HBaseRowKey {
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

		private String stockCode;
		private ReportType reportType;
		private int year;
		private int season;

		public Key(FinancialReportInstance entity) {
			super(entity);
		}

		public Key(String stockCode, ReportType reportType, int year,
				int season, FinancialReportInstance entity) {
			super(entity);
			this.stockCode = stockCode;
			this.reportType = reportType;
			this.year = year;
			this.season = season;
		}

		public Key(byte[] rowKey, FinancialReportInstance entity) {
			super(entity);
			fromBytes(rowKey);
		}

		@Override
		public byte[] toBytes() {
			byte[] stockCodeBytes = ByteConvertUtility.toBytes(stockCode,
					STOCK_CODE_LENGTH);
			byte[] reportTypeBytes = ByteConvertUtility.toBytes(
					reportType.name(), REPORT_TYPE_LENGTH);
			byte[] yearBytes = ByteConvertUtility.toBytes(year, YEAR_LENGTH);
			byte[] seasonBytes = ByteConvertUtility.toBytes(season,
					SEASON_LENGTH);
			return ArrayUtility.addAll(stockCodeBytes, SPACE, reportTypeBytes,
					SPACE, yearBytes, SPACE, seasonBytes);
		}

		@Override
		public void fromBytes(byte[] bytes) {
			this.stockCode = ByteConvertUtility.getStringFromBytes(bytes,
					STOCK_CODE_BEGIN_INDEX, STOCK_CODE_END_INDEX);
			String reportTypeStr = ByteConvertUtility.getStringFromBytes(bytes,
					REPORT_TYPE_BEGIN_INDEX, REPORT_TYPE_END_INDEX);
			this.reportType = ReportType.valueOf(reportTypeStr);
			this.year = ByteConvertUtility.getIntFromBytes(bytes,
					YEAR_BEGIN_INDEX, YEAR_END_INDEX);
			this.season = ByteConvertUtility.getIntFromBytes(bytes,
					SEASON_BEGIN_INDEX, SEASON_END_INDEX);
		}

		public String getStockCode() {
			return stockCode;
		}

		public void setStockCode(String stockCode) {
			this.stockCode = stockCode;
		}

		public ReportType getReportType() {
			return reportType;
		}

		public void setReportType(ReportType reportType) {
			this.reportType = reportType;
		}

		public int getYear() {
			return year;
		}

		public void setYear(int year) {
			this.year = year;
		}

		public int getSeason() {
			return season;
		}

		public void setSeason(int season) {
			this.season = season;
		}

	}

	public class InfoFamily extends HBaseColumnFamily {
		private InfoFamily(FinancialReportInstance table) {
			super(table);
		}

		public InfoValue getValue(String presentationId, String periodType) {
			return getValue(presentationId + SPACE + periodType);
		}

		public InfoValue getValue(String infoTitle) {
			InfoQualifier qual = new InfoQualifier(infoTitle);
			for (Entry<Date, HBaseValue> ent : getVersionValueSet(qual)) {
				return (InfoValue) ent.getValue();
			}
			return null;
		}

		public Set<Entry<Date, HBaseValue>> getVersionValueSet(String infoTitle) {
			InfoQualifier qual = new InfoQualifier(infoTitle);
			return getVersionValueSet(qual);
		}

		public void add(String infoTitle, Date date, String infoContent) {
			HBaseColumnQualifier qualifier = this.new InfoQualifier(infoTitle);
			InfoValue val = this.new InfoValue(infoContent);
			add(qualifier, date, val);
		}

		public class InfoQualifier extends HBaseColumnQualifier {
			private String infoTitle;

			public InfoQualifier() {
				super();
			}

			public InfoQualifier(String infoTitle) {
				super();
				this.infoTitle = infoTitle;
			}

			public InfoQualifier(byte[] infoTitleBytes) {
				super();
				fromBytes(infoTitleBytes);
			}

			@Override
			public byte[] toBytes() {
				return ByteConvertUtility.toBytes(infoTitle);
			}

			@Override
			public void fromBytes(byte[] bytes) {
				this.infoTitle = ByteConvertUtility.getStringFromBytes(bytes);
			}

			public String getInfoTitle() {
				return infoTitle;
			}

			public void setInfoTitle(String infoTitle) {
				this.infoTitle = infoTitle;
			}
		}

		public class InfoValue extends HBaseValue {
			private String infoContent;

			public InfoValue() {
				super();
			}

			public InfoValue(String infoContent) {
				super();
				this.infoContent = infoContent;
			}

			public InfoValue(byte[] infoContentBytes) {
				super();
				fromBytes(infoContentBytes);
			}

			public String getInfoContent() {
				return infoContent;
			}

			public void setInfoContent(String infoContent) {
				this.infoContent = infoContent;
			}

			@Override
			public byte[] toBytes() {
				return ByteConvertUtility.toBytes(infoContent);
			}

			@Override
			public void fromBytes(byte[] bytes) {
				this.infoContent = ByteConvertUtility.getStringFromBytes(bytes);
			}
		}

		@Override
		protected HBaseColumnQualifier generateColumnQualifier(
				byte[] qualifierBytes) {
			return this.new InfoQualifier(qualifierBytes);
		}

		@Override
		protected HBaseValue generateValue(byte[] valueBytes) {
			return this.new InfoValue(valueBytes);
		}
	}

	public class InstanceFamily extends HBaseColumnFamily {
		private InstanceFamily(FinancialReportInstance table) {
			super(table);
		}

		public InstanceValue getValue(String elementId, String periodType,
				Date instant) {
			InstanceQualifier qual = this.new InstanceQualifier(elementId,
					periodType, instant);
			return getValue(qual);
		}

		public InstanceValue getValue(String elementId, String periodType,
				Date startDate, Date endDate) {
			InstanceQualifier qual = this.new InstanceQualifier(elementId,
					periodType, startDate, endDate);
			return getValue(qual);
		}

		public void add(String elementId, Date date, String periodType,
				Date instant, String unit, BigDecimal value) {
			HBaseColumnQualifier qual = this.new InstanceQualifier(elementId,
					periodType, instant);
			add(qual, date, unit, value);
		}

		public void add(String elementId, Date date, String periodType,
				Date startDate, Date endDate, String unit, BigDecimal value) {
			HBaseColumnQualifier qual = this.new InstanceQualifier(elementId,
					periodType, startDate, endDate);
			add(qual, date, unit, value);
		}

		private void add(HBaseColumnQualifier qualifier, Date date,
				String unit, BigDecimal value) {
			InstanceValue val = this.new InstanceValue(unit, value);
			add(qualifier, date, val);
		}

		private InstanceValue getValue(InstanceQualifier qual) {
			for (Entry<Date, HBaseValue> verEnt : getVersionValueSet(qual)) {
				return (InstanceValue) verEnt.getValue();
			}
			return null;
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

			private String elementId;
			private String periodType;
			private Date instant;
			private Date startDate;
			private Date endDate;

			public InstanceQualifier() {
				super();
			}

			public InstanceQualifier(String elementId, String periodType,
					Date instant) {
				super();
				this.elementId = elementId;
				this.periodType = periodType;
				this.instant = instant;
			}

			public InstanceQualifier(String elementId, String periodType,
					Date startDate, Date endDate) {
				super();
				this.elementId = elementId;
				this.periodType = periodType;
				this.startDate = startDate;
				this.endDate = endDate;
			}

			public InstanceQualifier(byte[] elementIdBytes) {
				super();
				fromBytes(elementIdBytes);
			}

			@Override
			public byte[] toBytes() {
				byte[] elementIdBytes = ByteConvertUtility.toBytes(elementId,
						ELEMENT_ID_LENGTH);
				byte[] periodTypeBytes = ByteConvertUtility.toBytes(periodType,
						PERIOD_TYPE_LENGTH);
				byte[] instantBytes = ByteConvertUtility.toBytes(instant);
				byte[] startDateBytes = ByteConvertUtility.toBytes(startDate);
				byte[] endDateBytes = ByteConvertUtility.toBytes(endDate);
				return ArrayUtility.addAll(elementIdBytes, SPACE,
						periodTypeBytes, SPACE, instantBytes, SPACE,
						startDateBytes, SPACE, endDateBytes);
			}

			@Override
			public void fromBytes(byte[] bytes) {
				try {
					this.elementId = ByteConvertUtility
							.getStringFromBytes(bytes, ELEMENT_ID_BEGIN_INDEX,
									ELEMENT_ID_END_INDEX);
					this.periodType = ByteConvertUtility.getStringFromBytes(
							bytes, PERIOD_TYPE_BEGIN_INDEX,
							PERIOD_TYPE_END_INDEX);
					this.instant = ByteConvertUtility.getDateFromBytes(bytes,
							INSTANT_BEGIN_INDEX, INSTANT_END_INDEX);
					this.startDate = ByteConvertUtility.getDateFromBytes(bytes,
							START_DATE_BEGIN_INDEX, START_DATE_END_INDEX);
					this.endDate = ByteConvertUtility.getDateFromBytes(bytes,
							END_DATE_BEGIN_INDEX, END_DATE_END_INDEX);
				} catch (ParseException e) {
					throw new RuntimeException(e);
				}
			}

			public String getElementId() {
				return elementId;
			}

			public void setElementId(String elementId) {
				this.elementId = elementId;
			}

			public String getPeriodType() {
				return periodType;
			}

			public void setPeriodType(String periodType) {
				this.periodType = periodType;
			}

			public Date getInstant() {
				return instant;
			}

			public void setInstant(Date instant) {
				this.instant = instant;
			}

			public Date getStartDate() {
				return startDate;
			}

			public void setStartDate(Date startDate) {
				this.startDate = startDate;
			}

			public Date getEndDate() {
				return endDate;
			}

			public void setEndDate(Date endDate) {
				this.endDate = endDate;
			}
		}

		public class InstanceValue extends HBaseValue {
			private static final int UNIT_LENGTH = 10;
			private static final int VALUE_LENGTH = 20;

			private static final int UNIT_BEGIN_INDEX = 0;
			private static final int UNIT_END_INDEX = UNIT_BEGIN_INDEX
					+ UNIT_LENGTH;
			private static final int VALUE_BEGIN_INDEX = UNIT_END_INDEX + 1;
			private static final int VALUE_END_INDEX = VALUE_BEGIN_INDEX
					+ VALUE_LENGTH;

			private String unit;
			private BigDecimal value;

			public InstanceValue() {
				super();
			}

			public InstanceValue(String unit, BigDecimal value) {
				super();
				this.unit = unit;
				this.value = value;
			}

			public InstanceValue(byte[] bytes) {
				super();
				fromBytes(bytes);
			}

			public String getUnit() {
				return unit;
			}

			public void setUnit(String unit) {
				this.unit = unit;
			}

			public BigDecimal getValue() {
				return value;
			}

			public void setValue(BigDecimal value) {
				this.value = value;
			}

			@Override
			public byte[] toBytes() {
				byte[] unitBytes = ByteConvertUtility
						.toBytes(unit, UNIT_LENGTH);
				byte[] valueBytes = ByteConvertUtility.toBytes(value,
						VALUE_LENGTH);
				return ArrayUtility.addAll(unitBytes, SPACE, valueBytes);
			}

			@Override
			public void fromBytes(byte[] bytes) {
				this.unit = ByteConvertUtility.getStringFromBytes(bytes,
						UNIT_BEGIN_INDEX, UNIT_END_INDEX);
				try {
					this.value = ByteConvertUtility.getBigDecimalFromBytes(
							bytes, VALUE_BEGIN_INDEX, VALUE_END_INDEX);
				} catch (ParseException e) {
					throw new RuntimeException(e);
				}
			}
		}

		@Override
		protected HBaseColumnQualifier generateColumnQualifier(byte[] bytes) {
			return this.new InstanceQualifier(bytes);
		}

		@Override
		protected HBaseValue generateValue(byte[] bytes) {
			return this.new InstanceValue(bytes);
		}
	}
}
