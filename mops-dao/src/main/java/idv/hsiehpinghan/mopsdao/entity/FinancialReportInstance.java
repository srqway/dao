package idv.hsiehpinghan.mopsdao.entity;

import idv.hsiehpinghan.collectionutility.utility.ArrayUtility;
import idv.hsiehpinghan.datatypeutility.utility.IntegerUtility;
import idv.hsiehpinghan.hbaseassistant.abstractclass.HBaseColumnFamily;
import idv.hsiehpinghan.hbaseassistant.abstractclass.HBaseColumnQualifier;
import idv.hsiehpinghan.hbaseassistant.abstractclass.HBaseRowKey;
import idv.hsiehpinghan.hbaseassistant.abstractclass.HBaseTable;
import idv.hsiehpinghan.hbaseassistant.abstractclass.HBaseValue;
import idv.hsiehpinghan.mopsdao.enumeration.ReportType;

import java.util.Date;
import java.util.Map;
import java.util.NavigableMap;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.hadoop.hbase.util.Bytes;

public class FinancialReportInstance extends HBaseTable {
	private InfoFamily infoFamily;

	public FinancialReportInstance() {
		super();
	}

	public FinancialReportInstance(Key rowKey, InfoFamily infoFamily) {
		super(rowKey);
		this.infoFamily = infoFamily;
	}

	public InfoFamily getInfoFamily() {
		if(infoFamily == null) {
			infoFamily = this.new InfoFamily();
		}
		return infoFamily;
	}

	public void setInfoFamily(InfoFamily infoFamily) {
		this.infoFamily = infoFamily;
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

		public Key(String stockCode, ReportType reportType, int year,
				int season, HBaseTable table) {
			super(table);
			this.stockCode = stockCode;
			this.reportType = reportType;
			this.year = year;
			this.season = season;
		}

		public Key(byte[] rowKey, HBaseTable table) {
			super(table);
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
			this.stockCode = Bytes.toString(ArrayUtils.subarray(bytes,
					STOCK_CODE_BEGIN_INDEX, STOCK_CODE_END_INDEX)).trim();
			String reportTypeStr = Bytes.toString(ArrayUtils.subarray(bytes,
					REPORT_TYPE_BEGIN_INDEX, REPORT_TYPE_END_INDEX)).trim();
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
		public void add(String infoTitle, Date date, String infoContent) {
			HBaseColumnQualifier qualifier = this.new InfoQualifier(infoTitle);
			NavigableMap<Date, HBaseValue> verMap = getVersionValueMap(qualifier);
			InfoValue val = new InfoValue(infoContent);
			verMap.put(date, val);
		}

		@Override
		public void fromMap(
				NavigableMap<byte[], NavigableMap<Long, byte[]>> qualBytesMap) {
			for (Map.Entry<byte[], NavigableMap<Long, byte[]>> qualBytesEntry : qualBytesMap
					.entrySet()) {
				InfoFamily.InfoQualifier qual = this.new InfoQualifier(
						qualBytesEntry.getKey());
				NavigableMap<Long, byte[]> verBytesMap = qualBytesEntry
						.getValue();
				NavigableMap<Date, HBaseValue> verMap = getVersionValueMap(qual);
				for (Map.Entry<Long, byte[]> verBytesEntry : verBytesMap
						.entrySet()) {
					Date date = new Date(verBytesEntry.getKey());
					InfoFamily.InfoValue val = this.new InfoValue(
							verBytesEntry.getValue());
					verMap.put(date, val);
				}
			}
		}

		public class InfoQualifier extends HBaseColumnQualifier {
			public static final String XBRL_TAXONOMY_VERSION = "xbrl_taxonomy_version";
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
	}
}
