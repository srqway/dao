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

public class FinancialReportData extends HBaseTable {
	private static final byte[] SPACE = ByteUtility.SINGLE_SPACE_BYTE_ARRAY;
	private RowKey rowKey;
	private ItemFamily itemFamily;
	private RatioFamily ratioFamily;
	private GrowthFamily growthFamily;

	public FinancialReportData() {
		super();
	}

	@Override
	public HBaseRowKey getRowKey() {
		return rowKey;
	}

	@Override
	public void setRowKey(HBaseRowKey rowKey) {
		this.rowKey = (RowKey) rowKey;
	}

	public ItemFamily getItemFamily() {
		if (itemFamily == null) {
			itemFamily = this.new ItemFamily(this);
		}
		return itemFamily;
	}

	public RatioFamily getRatioFamily() {
		if (ratioFamily == null) {
			ratioFamily = this.new RatioFamily(this);
		}
		return ratioFamily;
	}

	public GrowthFamily getGrowthFamily() {
		if (growthFamily == null) {
			growthFamily = this.new GrowthFamily(this);
		}
		return growthFamily;
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

		private String stockCode;
		private ReportType reportType;
		private int year;
		private int season;

		public RowKey(FinancialReportData entity) {
			super(entity);
		}

		public RowKey(String stockCode, ReportType reportType, int year,
				int season, FinancialReportData entity) {
			super(entity);
			this.stockCode = stockCode;
			this.reportType = reportType;
			this.year = year;
			this.season = season;
		}

		public RowKey(byte[] rowKey, FinancialReportData entity) {
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

	public class ItemFamily extends HBaseColumnFamily {
		private ItemFamily(FinancialReportData table) {
			super(table);
		}

		public ItemValue getValue(String elementId, String periodType,
				Date instant) {
			ItemQualifier qual = this.new ItemQualifier(elementId, periodType,
					instant);
			return getValue(qual);
		}

		public ItemValue getValue(String elementId, String periodType,
				Date startDate, Date endDate) {
			ItemQualifier qual = this.new ItemQualifier(elementId, periodType,
					startDate, endDate);
			return getValue(qual);
		}

		public void add(String elementId, Date date, String periodType,
				Date instant, BigDecimal value) {
			HBaseColumnQualifier qual = this.new ItemQualifier(elementId,
					periodType, instant);
			add(qual, date, value);
		}

		public void add(String elementId, Date date, String periodType,
				Date startDate, Date endDate, BigDecimal value) {
			HBaseColumnQualifier qual = this.new ItemQualifier(elementId,
					periodType, startDate, endDate);
			add(qual, date, value);
		}

		private void add(HBaseColumnQualifier qualifier, Date date,
				BigDecimal value) {
			ItemValue val = this.new ItemValue(value);
			add(qualifier, date, val);
		}

		private ItemValue getValue(ItemQualifier qual) {
			for (Entry<Date, HBaseValue> verEnt : getVersionValueSet(qual)) {
				return (ItemValue) verEnt.getValue();
			}
			return null;
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

			private String elementId;
			private String periodType;
			private Date instant;
			private Date startDate;
			private Date endDate;

			public ItemQualifier() {
				super();
			}

			public ItemQualifier(String elementId, String periodType,
					Date instant) {
				super();
				this.elementId = elementId;
				this.periodType = periodType;
				this.instant = instant;
			}

			public ItemQualifier(String elementId, String periodType,
					Date startDate, Date endDate) {
				super();
				this.elementId = elementId;
				this.periodType = periodType;
				this.startDate = startDate;
				this.endDate = endDate;
			}

			public ItemQualifier(byte[] elementIdBytes) {
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

		public class ItemValue extends HBaseValue {
			private BigDecimal value;

			public ItemValue() {
				super();
			}

			public ItemValue(BigDecimal value) {
				super();
				this.value = value;
			}

			public ItemValue(byte[] bytes) {
				super();
				fromBytes(bytes);
			}

			public BigDecimal getValue() {
				return value;
			}

			public void setValue(BigDecimal value) {
				this.value = value;
			}

			@Override
			public byte[] toBytes() {
				byte[] valueBytes = ByteConvertUtility.toBytes(value);
				return ArrayUtility.addAll(valueBytes);
			}

			@Override
			public void fromBytes(byte[] bytes) {
				this.value = ByteConvertUtility.getBigDecimalFromBytes(bytes);
			}
		}

		@Override
		protected HBaseColumnQualifier generateColumnQualifier(byte[] bytes) {
			return this.new ItemQualifier(bytes);
		}

		@Override
		protected HBaseValue generateValue(byte[] bytes) {
			return this.new ItemValue(bytes);
		}
	}

	public class RatioFamily extends HBaseColumnFamily {
		private RatioFamily(FinancialReportData table) {
			super(table);
		}

		public RatioValue getValue(String name, String periodType, Date instant) {
			RatioQualifier qual = this.new RatioQualifier(name, periodType,
					instant);
			return getValue(qual);
		}

		public RatioValue getValue(String name, String periodType,
				Date startDate, Date endDate) {
			RatioQualifier qual = this.new RatioQualifier(name, periodType,
					startDate, endDate);
			return getValue(qual);
		}

		public void add(String name, String periodType, Date instant,
				Date date, BigDecimal value) {
			HBaseColumnQualifier qual = this.new RatioQualifier(name,
					periodType, instant);
			add(qual, date, value);
		}

		public void add(String name, String periodType, Date startDate,
				Date endDate, Date date, BigDecimal value) {
			HBaseColumnQualifier qual = this.new RatioQualifier(name,
					periodType, startDate, endDate);
			add(qual, date, value);
		}

		private void add(HBaseColumnQualifier qualifier, Date date,
				BigDecimal value) {
			RatioValue val = this.new RatioValue(value);
			add(qualifier, date, val);
		}

		private RatioValue getValue(RatioQualifier qual) {
			for (Entry<Date, HBaseValue> verEnt : getVersionValueSet(qual)) {
				return (RatioValue) verEnt.getValue();
			}
			return null;
		}

		public class RatioQualifier extends HBaseColumnQualifier {
			private static final int NAME_LENGTH = 300;
			private static final int PERIOD_TYPE_LENGTH = 10;
			private static final int INSTANT_LENGTH = ByteConvertUtility.DEFAULT_DATE_PATTERN_LENGTH;
			private static final int START_DATE_LENGTH = ByteConvertUtility.DEFAULT_DATE_PATTERN_LENGTH;
			private static final int END_DATE_LENGTH = ByteConvertUtility.DEFAULT_DATE_PATTERN_LENGTH;

			private static final int NAME_BEGIN_INDEX = 0;
			private static final int NAME_END_INDEX = NAME_BEGIN_INDEX
					+ NAME_LENGTH;
			private static final int PERIOD_TYPE_BEGIN_INDEX = NAME_END_INDEX + 1;
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

			private String name;
			private String periodType;
			private Date instant;
			private Date startDate;
			private Date endDate;

			public RatioQualifier() {
				super();
			}

			public RatioQualifier(String name, String periodType, Date instant) {
				super();
				this.name = name;
				this.periodType = periodType;
				this.instant = instant;
			}

			public RatioQualifier(String name, String periodType,
					Date startDate, Date endDate) {
				super();
				this.name = name;
				this.periodType = periodType;
				this.startDate = startDate;
				this.endDate = endDate;
			}

			public RatioQualifier(byte[] elementIdBytes) {
				super();
				fromBytes(elementIdBytes);
			}

			@Override
			public byte[] toBytes() {
				byte[] nameBytes = ByteConvertUtility
						.toBytes(name, NAME_LENGTH);
				byte[] periodTypeBytes = ByteConvertUtility.toBytes(periodType,
						PERIOD_TYPE_LENGTH);
				byte[] instantBytes = ByteConvertUtility.toBytes(instant);
				byte[] startDateBytes = ByteConvertUtility.toBytes(startDate);
				byte[] endDateBytes = ByteConvertUtility.toBytes(endDate);
				return ArrayUtility.addAll(nameBytes, SPACE, periodTypeBytes,
						SPACE, instantBytes, SPACE, startDateBytes, SPACE,
						endDateBytes);
			}

			@Override
			public void fromBytes(byte[] bytes) {
				try {
					this.name = ByteConvertUtility.getStringFromBytes(bytes,
							NAME_BEGIN_INDEX, NAME_END_INDEX);
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

			public String getName() {
				return name;
			}

			public void setName(String name) {
				this.name = name;
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

		public class RatioValue extends HBaseValue {
			private BigDecimal value;

			public RatioValue() {
				super();
			}

			public RatioValue(BigDecimal value) {
				super();
				this.value = value;
			}

			public RatioValue(byte[] bytes) {
				super();
				fromBytes(bytes);
			}

			public BigDecimal getValue() {
				return value;
			}

			public void setValue(BigDecimal value) {
				this.value = value;
			}

			@Override
			public byte[] toBytes() {
				return ByteConvertUtility.toBytes(value);
			}

			@Override
			public void fromBytes(byte[] bytes) {
				this.value = ByteConvertUtility.getBigDecimalFromBytes(bytes);
			}
		}

		@Override
		protected HBaseColumnQualifier generateColumnQualifier(byte[] bytes) {
			return this.new RatioQualifier(bytes);
		}

		@Override
		protected HBaseValue generateValue(byte[] bytes) {
			return this.new RatioValue(bytes);
		}
	}

	public class GrowthFamily extends HBaseColumnFamily {
		private GrowthFamily(FinancialReportData table) {
			super(table);
		}

		public GrowthValue getValue(String elementId, String periodType,
				Date instant) {
			GrowthQualifier qual = this.new GrowthQualifier(elementId,
					periodType, instant);
			return getValue(qual);
		}

		public GrowthValue getValue(String elementId, String periodType,
				Date startDate, Date endDate) {
			GrowthQualifier qual = this.new GrowthQualifier(elementId,
					periodType, startDate, endDate);
			return getValue(qual);
		}

		public void add(String elementId, Date date, String periodType,
				Date instant, BigDecimal value) {
			HBaseColumnQualifier qual = this.new GrowthQualifier(elementId,
					periodType, instant);
			add(qual, date, value);
		}

		public void add(String elementId, Date date, String periodType,
				Date startDate, Date endDate, BigDecimal value) {
			HBaseColumnQualifier qual = this.new GrowthQualifier(elementId,
					periodType, startDate, endDate);
			add(qual, date, value);
		}

		private void add(HBaseColumnQualifier qualifier, Date date,
				BigDecimal value) {
			GrowthValue val = this.new GrowthValue(value);
			add(qualifier, date, val);
		}

		private GrowthValue getValue(GrowthQualifier qual) {
			for (Entry<Date, HBaseValue> verEnt : getVersionValueSet(qual)) {
				return (GrowthValue) verEnt.getValue();
			}
			return null;
		}

		public class GrowthQualifier extends HBaseColumnQualifier {
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

			public GrowthQualifier() {
				super();
			}

			public GrowthQualifier(String elementId, String periodType,
					Date instant) {
				super();
				this.elementId = elementId;
				this.periodType = periodType;
				this.instant = instant;
			}

			public GrowthQualifier(String elementId, String periodType,
					Date startDate, Date endDate) {
				super();
				this.elementId = elementId;
				this.periodType = periodType;
				this.startDate = startDate;
				this.endDate = endDate;
			}

			public GrowthQualifier(byte[] elementIdBytes) {
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

		public class GrowthValue extends HBaseValue {
			private BigDecimal value;

			public GrowthValue() {
				super();
			}

			public GrowthValue(BigDecimal value) {
				super();
				this.value = value;
			}

			public GrowthValue(byte[] bytes) {
				super();
				fromBytes(bytes);
			}

			public BigDecimal getValue() {
				return value;
			}

			public void setValue(BigDecimal value) {
				this.value = value;
			}

			@Override
			public byte[] toBytes() {
				return ByteConvertUtility.toBytes(value);
			}

			@Override
			public void fromBytes(byte[] bytes) {
				this.value = ByteConvertUtility.getBigDecimalFromBytes(bytes);
			}
		}

		@Override
		protected HBaseColumnQualifier generateColumnQualifier(byte[] bytes) {
			return this.new GrowthQualifier(bytes);
		}

		@Override
		protected HBaseValue generateValue(byte[] bytes) {
			return this.new GrowthValue(bytes);
		}
	}
}
