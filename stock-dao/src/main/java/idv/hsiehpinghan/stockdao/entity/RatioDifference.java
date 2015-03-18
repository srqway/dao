package idv.hsiehpinghan.stockdao.entity;

import idv.hsiehpinghan.collectionutility.utility.ArrayUtility;
import idv.hsiehpinghan.datatypeutility.utility.ByteUtility;
import idv.hsiehpinghan.hbaseassistant.abstractclass.HBaseColumnFamily;
import idv.hsiehpinghan.hbaseassistant.abstractclass.HBaseColumnQualifier;
import idv.hsiehpinghan.hbaseassistant.abstractclass.HBaseRowKey;
import idv.hsiehpinghan.hbaseassistant.abstractclass.HBaseTable;
import idv.hsiehpinghan.hbaseassistant.abstractclass.HBaseValue;
import idv.hsiehpinghan.hbaseassistant.utility.ByteConvertUtility;
import idv.hsiehpinghan.stockdao.enumeration.ReportType;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

public class RatioDifference extends HBaseTable {
	private static final byte[] SPACE = ByteUtility.SINGLE_SPACE_BYTE_ARRAY;
	private RowKey rowKey;
	private TTestFamily tTestFamily;

	@Override
	public HBaseRowKey getRowKey() {
		return rowKey;
	}

	@Override
	public void setRowKey(HBaseRowKey rowKey) {
		this.rowKey = (RowKey) rowKey;
	}

	public TTestFamily getTTestFamily() {
		if (tTestFamily == null) {
			tTestFamily = this.new TTestFamily(this);
		}
		return tTestFamily;
	}

	public class RowKey extends HBaseRowKey {
		private static final int STOCK_CODE_LENGTH = 10;
		private static final int REPORT_TYPE_LENGTH = 30;
		private static final int YEAR_LENGTH = 4;
		private static final int SEASON_LENGTH = 1;
		private static final int ELEMENT_ID_LENGTH = 300;
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
		private static final int ELEMENT_ID_BEGIN_INDEX = SEASON_END_INDEX + 1;
		private static final int ELEMENT_ID_END_INDEX = ELEMENT_ID_BEGIN_INDEX
				+ ELEMENT_ID_LENGTH;

		public RowKey(RatioDifference entity) {
			super(entity);
		}

		public RowKey(byte[] bytes, RatioDifference entity) {
			super(entity);
			setBytes(bytes);
		}

		public RowKey(String stockCode, ReportType reportType, int year,
				int season, String elementId, RatioDifference entity) {
			super(entity);
			byte[] stockCodeBytes = ByteConvertUtility.toBytes(stockCode,
					STOCK_CODE_LENGTH);
			byte[] reportTypeBytes = ByteConvertUtility.toBytes(
					reportType == null ? null : reportType.name(),
					REPORT_TYPE_LENGTH);
			byte[] yearBytes = ByteConvertUtility.toBytes(year, YEAR_LENGTH);
			byte[] seasonBytes = ByteConvertUtility.toBytes(season,
					SEASON_LENGTH);
			byte[] elementIdBytes = ByteConvertUtility.toBytes(elementId,
					ELEMENT_ID_LENGTH);
			super.setBytes(ArrayUtility.addAll(stockCodeBytes, SPACE,
					reportTypeBytes, SPACE, yearBytes, SPACE, seasonBytes,
					SPACE, elementIdBytes));
		}

		public byte[] getFuzzyBytes(String stockCode, ReportType reportType,
				Integer year, Integer season, String elementId) {
			byte[] stockCodeBytes;
			if (stockCode == null) {
				stockCodeBytes = ArrayUtility.getBytes(STOCK_CODE_LENGTH,
						ByteUtility.BYTE_ONE);
			} else {
				stockCodeBytes = ArrayUtility.getBytes(STOCK_CODE_LENGTH,
						ByteUtility.BYTE_ZERO);
			}
			byte[] reportTypeBytes;
			if (reportType == null) {
				reportTypeBytes = ArrayUtility.getBytes(REPORT_TYPE_LENGTH,
						ByteUtility.BYTE_ONE);
			} else {
				reportTypeBytes = ArrayUtility.getBytes(REPORT_TYPE_LENGTH,
						ByteUtility.BYTE_ZERO);
			}
			byte[] yearBytes;
			if (year == null) {
				yearBytes = ArrayUtility.getBytes(YEAR_LENGTH,
						ByteUtility.BYTE_ONE);
			} else {
				yearBytes = ArrayUtility.getBytes(YEAR_LENGTH,
						ByteUtility.BYTE_ZERO);
			}
			byte[] seasonBytes;
			if (season == null) {
				seasonBytes = ArrayUtility.getBytes(SEASON_LENGTH,
						ByteUtility.BYTE_ONE);
			} else {
				seasonBytes = ArrayUtility.getBytes(SEASON_LENGTH,
						ByteUtility.BYTE_ZERO);
			}
			byte[] elementIdBytes;
			if (elementId == null) {
				elementIdBytes = ArrayUtility.getBytes(ELEMENT_ID_LENGTH,
						ByteUtility.BYTE_ONE);
			} else {
				elementIdBytes = ArrayUtility.getBytes(ELEMENT_ID_LENGTH,
						ByteUtility.BYTE_ZERO);
			}
			return ArrayUtility.addAll(stockCodeBytes,
					ByteUtility.SINGLE_ZERO_BYTE_ARRAY, reportTypeBytes,
					ByteUtility.SINGLE_ZERO_BYTE_ARRAY, yearBytes,
					ByteUtility.SINGLE_ZERO_BYTE_ARRAY, seasonBytes,
					ByteUtility.SINGLE_ZERO_BYTE_ARRAY, elementIdBytes);
		}

		public String getStockCode() {
			return ByteConvertUtility.getStringFromBytes(getBytes(),
					STOCK_CODE_BEGIN_INDEX, STOCK_CODE_END_INDEX);
		}

		public void setStockCode(String stockCode) {
			byte[] bytes = getBytes();
			byte[] subBytes = ByteConvertUtility.toBytes(stockCode,
					STOCK_CODE_LENGTH);
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
			byte[] subBytes = ByteConvertUtility.toBytes(
					reportType == null ? null : reportType.name(),
					REPORT_TYPE_LENGTH);
			ArrayUtility.replace(bytes, subBytes, REPORT_TYPE_BEGIN_INDEX,
					REPORT_TYPE_END_INDEX);
		}

		public int getYear() {
			return ByteConvertUtility.getIntFromBytes(getBytes(),
					YEAR_BEGIN_INDEX, YEAR_END_INDEX);
		}

		public void setYear(int year) {
			byte[] bytes = getBytes();
			byte[] subBytes = ByteConvertUtility.toBytes(year, YEAR_LENGTH);
			ArrayUtility.replace(bytes, subBytes, YEAR_BEGIN_INDEX,
					YEAR_END_INDEX);
		}

		public int getSeason() {
			return ByteConvertUtility.getIntFromBytes(getBytes(),
					SEASON_BEGIN_INDEX, SEASON_END_INDEX);
		}

		public void setSeason(int season) {
			byte[] bytes = getBytes();
			byte[] subBytes = ByteConvertUtility.toBytes(season, SEASON_LENGTH);
			ArrayUtility.replace(bytes, subBytes, SEASON_BEGIN_INDEX,
					SEASON_END_INDEX);
		}

		public String getElementId() {
			return ByteConvertUtility.getStringFromBytes(getBytes(),
					ELEMENT_ID_BEGIN_INDEX, ELEMENT_ID_END_INDEX);
		}

		public void setElementId(String elementId) {
			byte[] bytes = getBytes();
			byte[] subBytes = ByteConvertUtility.toBytes(elementId,
					ELEMENT_ID_LENGTH);
			ArrayUtility.replace(bytes, subBytes, ELEMENT_ID_BEGIN_INDEX,
					ELEMENT_ID_END_INDEX);
		}
	}

	public class TTestFamily extends HBaseColumnFamily {
		public static final String STATISTIC = "statistic";
		public static final String DEGREE_OF_FREEDOM = "degreeOfFreedom";
		public static final String CONFIDENCE_INTERVAL = "confidenceInterval";
		public static final String SAMPLE_MEAN = "sampleMean";
		public static final String HYPOTHESIZED_MEAN = "hypothesizedMean";
		public static final String P_VALUE = "pValue";

		private TTestFamily(RatioDifference entity) {
			super(entity);
		}

		@SuppressWarnings("unchecked")
		public Set<TTestQualifier> getQualifiers() {
			return (Set<TTestQualifier>) (Object) super
					.getQualifierVersionValueMap().keySet();
		}

		public BigDecimal getStatistic() {
			HBaseColumnQualifier qual = new TTestQualifier(STATISTIC);
			TTestValue val = (TTestValue) super.getLatestValue(qual);
			if (val == null) {
				return null;
			}
			return val.getAsBigDecimal();
		}

		public void setStatistic(Date ver, BigDecimal statistic) {
			TTestQualifier qual = new TTestQualifier(STATISTIC);
			TTestValue val = new TTestValue();
			val.set(statistic);
			add(qual, ver, val);
		}

		public BigDecimal getDegreeOfFreedom() {
			HBaseColumnQualifier qual = new TTestQualifier(DEGREE_OF_FREEDOM);
			TTestValue val = (TTestValue) super.getLatestValue(qual);
			if (val == null) {
				return null;
			}
			return val.getAsBigDecimal();
		}

		public void setDegreeOfFreedom(Date ver, BigDecimal degreeOfFreedom) {
			TTestQualifier qual = new TTestQualifier(DEGREE_OF_FREEDOM);
			TTestValue val = new TTestValue();
			val.set(degreeOfFreedom);
			add(qual, ver, val);
		}

		public BigDecimal getConfidenceInterval() {
			HBaseColumnQualifier qual = new TTestQualifier(CONFIDENCE_INTERVAL);
			TTestValue val = (TTestValue) super.getLatestValue(qual);
			if (val == null) {
				return null;
			}
			return val.getAsBigDecimal();
		}

		public void setConfidenceInterval(Date ver,
				BigDecimal confidenceInterval) {
			TTestQualifier qual = new TTestQualifier(CONFIDENCE_INTERVAL);
			TTestValue val = new TTestValue();
			val.set(confidenceInterval);
			add(qual, ver, val);
		}

		public BigDecimal getSampleMean() {
			HBaseColumnQualifier qual = new TTestQualifier(SAMPLE_MEAN);
			TTestValue val = (TTestValue) super.getLatestValue(qual);
			if (val == null) {
				return null;
			}
			return val.getAsBigDecimal();
		}

		public void setSampleMean(Date ver, BigDecimal sampleMean) {
			TTestQualifier qual = new TTestQualifier(SAMPLE_MEAN);
			TTestValue val = new TTestValue();
			val.set(sampleMean);
			add(qual, ver, val);
		}

		public BigDecimal getHypothesizedMean() {
			HBaseColumnQualifier qual = new TTestQualifier(HYPOTHESIZED_MEAN);
			TTestValue val = (TTestValue) super.getLatestValue(qual);
			if (val == null) {
				return null;
			}
			return val.getAsBigDecimal();
		}

		public void setHypothesizedMean(Date ver, BigDecimal hypothesizedMean) {
			TTestQualifier qual = new TTestQualifier(HYPOTHESIZED_MEAN);
			TTestValue val = new TTestValue();
			val.set(hypothesizedMean);
			add(qual, ver, val);
		}

		public BigDecimal getPValue() {
			HBaseColumnQualifier qual = new TTestQualifier(P_VALUE);
			TTestValue val = (TTestValue) super.getLatestValue(qual);
			if (val == null) {
				return null;
			}
			return val.getAsBigDecimal();
		}

		public void setPValue(Date ver, BigDecimal pValue) {
			TTestQualifier qual = new TTestQualifier(P_VALUE);
			TTestValue val = new TTestValue();
			val.set(pValue);
			add(qual, ver, val);
		}

		@Override
		protected HBaseColumnQualifier generateColumnQualifier(byte[] bytes) {
			return this.new TTestQualifier(bytes);
		}

		@Override
		protected HBaseValue generateValue(byte[] bytes) {
			return this.new TTestValue(bytes);
		}

		public class TTestQualifier extends HBaseColumnQualifier {
			public TTestQualifier() {
				super();
			}

			public TTestQualifier(byte[] bytes) {
				super();
				setBytes(bytes);
			}

			public TTestQualifier(String columnName) {
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

		public class TTestValue extends HBaseValue {
			public TTestValue() {
				super();
			}

			public TTestValue(byte[] bytes) {
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
	}
}
