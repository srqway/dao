package idv.hsiehpinghan.mopsdao.entity;

import idv.hsiehpinghan.collectionutility.utility.ArrayUtility;
import idv.hsiehpinghan.datatypeutility.utility.IntegerUtility;
import idv.hsiehpinghan.datatypeutility.utility.LongUtility;
import idv.hsiehpinghan.hbaseassistant.abstractclass.HBaseColumnFamily;
import idv.hsiehpinghan.hbaseassistant.abstractclass.HBaseColumnQualifier;
import idv.hsiehpinghan.hbaseassistant.abstractclass.HBaseRowKey;
import idv.hsiehpinghan.hbaseassistant.abstractclass.HBaseTable;
import idv.hsiehpinghan.hbaseassistant.abstractclass.HBaseValue;
import idv.hsiehpinghan.mopsdao.enumeration.ReportType;

import java.math.BigDecimal;
import java.util.Date;
import java.util.NavigableMap;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.hadoop.hbase.util.Bytes;

public class FinancialReportInstance extends HBaseTable {
	private InfoFamily infoFamily;
	private InstanceFamily instanceFamily;

	public FinancialReportInstance() {
		super();
	}

	public FinancialReportInstance(Key rowKey, InfoFamily infoFamily,
			InstanceFamily instanceFamily) {
		super(rowKey);
		this.infoFamily = infoFamily;
		this.instanceFamily = instanceFamily;
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
		private static final int STOCK_CODE_LENTH = 10;
		private static final int REPORT_TYPE_LENTH = 30;
		private static final int YEAR_LENTH = IntegerUtility.INT_BYTE_AMOUNT;
		private static final int SEASON_LENTH = IntegerUtility.INT_BYTE_AMOUNT;
		private static final int STOCK_CODE_BEGIN_INDEX = 0;
		private static final int STOCK_CODE_END_INDEX = STOCK_CODE_BEGIN_INDEX
				+ STOCK_CODE_LENTH;
		private static final int REPORT_TYPE_BEGIN_INDEX = STOCK_CODE_END_INDEX;
		private static final int REPORT_TYPE_END_INDEX = REPORT_TYPE_BEGIN_INDEX
				+ REPORT_TYPE_LENTH;
		private static final int YEAR_BEGIN_INDEX = REPORT_TYPE_END_INDEX;
		private static final int YEAR_END_INDEX = YEAR_BEGIN_INDEX + YEAR_LENTH;
		private static final int SEASON_BEGIN_INDEX = YEAR_END_INDEX;
		private static final int SEASON_END_INDEX = SEASON_BEGIN_INDEX
				+ SEASON_LENTH;

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
			byte[] stockCodeBytes = Bytes.toBytes(StringUtils.leftPad(
					stockCode, STOCK_CODE_LENTH));
			byte[] reportTypeBytes = Bytes.toBytes(StringUtils.leftPad(
					reportType.name(), REPORT_TYPE_LENTH));
			byte[] yearBytes = Bytes.toBytes(year);
			byte[] seasonBytes = Bytes.toBytes(season);
			return ArrayUtility.addAll(stockCodeBytes, reportTypeBytes,
					yearBytes, seasonBytes);
		}

		@Override
		public void fromBytes(byte[] bytes) {
			this.stockCode = Bytes.toString(
					ArrayUtils.subarray(bytes, STOCK_CODE_BEGIN_INDEX,
							STOCK_CODE_END_INDEX)).trim();
			String reportTypeStr = Bytes.toString(
					ArrayUtils.subarray(bytes, REPORT_TYPE_BEGIN_INDEX,
							REPORT_TYPE_END_INDEX)).trim();
			this.reportType = ReportType.valueOf(reportTypeStr);
			this.year = Bytes.toInt(ArrayUtils.subarray(bytes,
					YEAR_BEGIN_INDEX, YEAR_END_INDEX));
			this.season = Bytes.toInt(ArrayUtils.subarray(bytes,
					SEASON_BEGIN_INDEX, SEASON_END_INDEX));
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

		public void add(String infoTitle, Date date, String infoContent) {
			HBaseColumnQualifier qualifier = this.new InfoQualifier(infoTitle);
			NavigableMap<Date, HBaseValue> verMap = getVersionValueMap(qualifier);
			InfoValue val = this.new InfoValue(infoContent);
			verMap.put(date, val);
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
				return Bytes.toBytes(infoTitle);
			}

			@Override
			public void fromBytes(byte[] infoTitleBytes) {
				this.infoTitle = Bytes.toString(infoTitleBytes);
			}

			public String getInfoTitle() {
				return infoTitle;
			}

			public void setInfoTitle(String infoTitle) {
				this.infoTitle = infoTitle;
			}

			@Override
			public int compareTo(HBaseColumnQualifier o) {
				String infoTitle = this.getClass().cast(o).getInfoTitle();
				return this.getInfoTitle().compareTo(infoTitle);
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
				return Bytes.toBytes(infoContent);
			}

			@Override
			public void fromBytes(byte[] infoContentBytes) {
				this.infoContent = Bytes.toString(infoContentBytes);
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

		public void add(String elementId, Date date, String periodType,
				Date instant, String unit, BigDecimal value) {
			HBaseColumnQualifier qualifier = this.new InstanceQualifier(
					elementId);
			NavigableMap<Date, HBaseValue> verMap = getVersionValueMap(qualifier);
			InstanceValue val = this.new InstanceValue(periodType, instant,
					unit, value);
			verMap.put(date, val);
		}

		public void add(String elementId, Date date, String periodType,
				Date startDate, Date endDate, String unit, BigDecimal value) {
			HBaseColumnQualifier qualifier = this.new InstanceQualifier(
					elementId);
			NavigableMap<Date, HBaseValue> verMap = getVersionValueMap(qualifier);
			InstanceValue val = this.new InstanceValue(periodType, startDate,
					endDate, unit, value);
			verMap.put(date, val);
		}

		public class InstanceQualifier extends HBaseColumnQualifier {
			private String elementId;

			public InstanceQualifier() {
				super();
			}

			public InstanceQualifier(String elementId) {
				super();
				this.elementId = elementId;
			}

			public InstanceQualifier(byte[] elementIdBytes) {
				super();
				fromBytes(elementIdBytes);
			}

			@Override
			public byte[] toBytes() {
				return Bytes.toBytes(elementId);
			}

			@Override
			public void fromBytes(byte[] elementIdBytes) {
				this.elementId = Bytes.toString(elementIdBytes);
			}

			public String getElementId() {
				return elementId;
			}

			public void setElementId(String elementId) {
				this.elementId = elementId;
			}

			@Override
			public int compareTo(HBaseColumnQualifier o) {
				String elementId = this.getClass().cast(o).getElementId();
				return this.getElementId().compareTo(elementId);
			}

		}

		public class InstanceValue extends HBaseValue {
			private static final int PERIOD_TYPE_LENTH = 10;
			private static final int INSTANT_LENTH = LongUtility.LONG_BYTE_AMOUNT;
			private static final int START_DATE_LENTH = LongUtility.LONG_BYTE_AMOUNT;
			private static final int END_DATE_LENTH = LongUtility.LONG_BYTE_AMOUNT;
			private static final int UNIT_LENTH = 3;
			private static final int VALUE_LENTH = 20;

			private static final int PERIOD_TYPE_BEGIN_INDEX = 0;
			private static final int PERIOD_TYPE_END_INDEX = PERIOD_TYPE_BEGIN_INDEX
					+ PERIOD_TYPE_LENTH;
			private static final int INSTANT_BEGIN_INDEX = PERIOD_TYPE_END_INDEX;
			private static final int INSTANT_END_INDEX = INSTANT_BEGIN_INDEX
					+ INSTANT_LENTH;
			private static final int START_DATE_BEGIN_INDEX = INSTANT_END_INDEX;
			private static final int START_DATE_END_INDEX = START_DATE_BEGIN_INDEX
					+ START_DATE_LENTH;
			private static final int END_DATE_BEGIN_INDEX = START_DATE_END_INDEX;
			private static final int END_DATE_END_INDEX = END_DATE_BEGIN_INDEX
					+ END_DATE_LENTH;
			private static final int UNIT_BEGIN_INDEX = END_DATE_END_INDEX;
			private static final int UNIT_END_INDEX = UNIT_BEGIN_INDEX
					+ UNIT_LENTH;
			private static final int VALUE_BEGIN_INDEX = UNIT_END_INDEX;
			private static final int VALUE_END_INDEX = VALUE_BEGIN_INDEX
					+ VALUE_LENTH;

			/**
			 * duration, instant.
			 */
			private String periodType;
			private Date instant;
			private Date startDate;
			private Date endDate;
			private String unit;
			private BigDecimal value;

			public InstanceValue() {
				super();
			}

			public InstanceValue(String periodType, Date startDate,
					Date endDate, String unit, BigDecimal value) {
				super();
				this.periodType = periodType;
				this.startDate = startDate;
				this.endDate = endDate;
				this.unit = unit;
				this.value = value;
			}

			public InstanceValue(String periodType, Date instant, String unit,
					BigDecimal value) {
				super();
				this.periodType = periodType;
				this.instant = instant;
				this.unit = unit;
				this.value = value;
			}

			public InstanceValue(byte[] bytes) {
				super();
				fromBytes(bytes);
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
				byte[] periodTypeBytes = Bytes.toBytes(StringUtils.leftPad(
						periodType, PERIOD_TYPE_LENTH));
				byte[] instantBytes;
				if (instant == null) {
					instantBytes = Bytes.toBytes(0L);
				} else {
					instantBytes = Bytes.toBytes(instant.getTime());
				}
				byte[] startDateBytes;
				if (startDate == null) {
					startDateBytes = Bytes.toBytes(0L);
				} else {
					startDateBytes = Bytes.toBytes(startDate.getTime());
				}
				byte[] endDateBytes;
				if (startDate == null) {
					endDateBytes = Bytes.toBytes(0L);
				} else {
					endDateBytes = Bytes.toBytes(endDate.getTime());
				}
				byte[] unitBytes = Bytes.toBytes(StringUtils.leftPad(unit,
						UNIT_LENTH));
				byte[] valueBytes = Bytes.toBytes(StringUtils.leftPad(
						value.toString(), VALUE_LENTH));
				return ArrayUtility.addAll(periodTypeBytes, instantBytes,
						startDateBytes, endDateBytes, unitBytes, valueBytes);
			}

			@Override
			public void fromBytes(byte[] bytes) {
				this.periodType = Bytes.toString(
						ArrayUtils.subarray(bytes, PERIOD_TYPE_BEGIN_INDEX,
								PERIOD_TYPE_END_INDEX)).trim();
				long inst = Bytes.toLong(ArrayUtils.subarray(bytes,
						INSTANT_BEGIN_INDEX, INSTANT_END_INDEX));
				if (inst != 0) {
					this.instant = new Date(inst);
				}
				long stDate = Bytes.toLong(ArrayUtils.subarray(bytes,
						START_DATE_BEGIN_INDEX, START_DATE_END_INDEX));
				if (stDate != 0) {
					this.startDate = new Date(stDate);
				}
				long edDate = Bytes.toLong(ArrayUtils.subarray(bytes,
						END_DATE_BEGIN_INDEX, END_DATE_END_INDEX));
				if (edDate != 0) {
					this.endDate = new Date(edDate);
				}
				this.unit = Bytes.toString(
						ArrayUtils.subarray(bytes, UNIT_BEGIN_INDEX,
								UNIT_END_INDEX)).trim();
				this.value = new BigDecimal(Bytes.toString(ArrayUtils.subarray(
						bytes, VALUE_BEGIN_INDEX, VALUE_END_INDEX)).trim());
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
