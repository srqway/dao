package idv.hsiehpinghan.mopsdao.entity;

import idv.hsiehpinghan.datatypeutility.utility.StringUtility;
import idv.hsiehpinghan.hbaseassistant.abstractclass.HBaseColumnFamily;
import idv.hsiehpinghan.hbaseassistant.abstractclass.HBaseColumnQualifier;
import idv.hsiehpinghan.hbaseassistant.abstractclass.HBaseRowKey;
import idv.hsiehpinghan.hbaseassistant.abstractclass.HBaseTable;
import idv.hsiehpinghan.hbaseassistant.abstractclass.HBaseValue;
import idv.hsiehpinghan.hbaseassistant.utility.ByteConvertUtility;
import idv.hsiehpinghan.mopsdao.enumeration.ReportType;

import java.util.Date;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeSet;

public class MopsDownloadInfo extends HBaseTable {
	private static final String COMMA = StringUtility.COMMA_STRING;
	private RowKey rowKey;
	private StockCodeFamily stockCodeFamily;
	private ReportTypeFamily reportTypeFamily;
	private YearFamily yearFamily;
	private SeasonFamily seasonFamily;

	public MopsDownloadInfo() {
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

	public StockCodeFamily getStockCodeFamily() {
		if (stockCodeFamily == null) {
			stockCodeFamily = new StockCodeFamily(this);
		}
		return stockCodeFamily;
	}

	public ReportTypeFamily getReportTypeFamily() {
		if (reportTypeFamily == null) {
			reportTypeFamily = new ReportTypeFamily(this);
		}
		return reportTypeFamily;
	}

	public YearFamily getYearFamily() {
		if (yearFamily == null) {
			yearFamily = new YearFamily(this);
		}
		return yearFamily;
	}

	public SeasonFamily getSeasonFamily() {
		if (seasonFamily == null) {
			seasonFamily = new SeasonFamily(this);
		}
		return seasonFamily;
	}

	public class RowKey extends HBaseRowKey {
		private String tableName;

		public RowKey(MopsDownloadInfo entity) {
			super(entity);
		}

		public RowKey(String tableName, MopsDownloadInfo entity) {
			super(entity);
			this.tableName = tableName;
		}

		public RowKey(byte[] rowKey, MopsDownloadInfo entity) {
			super(entity);
			fromBytes(rowKey);
		}

		@Override
		public byte[] toBytes() {
			return ByteConvertUtility.toBytes(tableName);
		}

		@Override
		public void fromBytes(byte[] bytes) {
			this.tableName = ByteConvertUtility.getStringFromBytes(bytes);
		}

		public String getTableName() {
			return tableName;
		}

		public void setTableName(String tableName) {
			this.tableName = tableName;
		}
	}

	public class StockCodeFamily extends HBaseColumnFamily {
		private StockCodeFamily(MopsDownloadInfo table) {
			super(table);
		}

		public Set<Entry<Date, HBaseValue>> getVersionValueSet(String type) {
			StockCodeQualifier qual = new StockCodeQualifier(type);
			return getVersionValueSet(qual);
		}

		public void add(String type, Date date, Set<String> stockCodes) {
			HBaseColumnQualifier qualifier = this.new StockCodeQualifier(type);
			StockCodeValue val = this.new StockCodeValue(stockCodes);
			add(qualifier, date, val);
		}

		public void addStockCode(String type, Date date, String stockCode) {
			HBaseColumnQualifier qualifier = this.new StockCodeQualifier(type);
			StockCodeValue value = (StockCodeValue) this
					.getLatestValue(qualifier);
			if (value == null) {
				value = this.new StockCodeValue(new TreeSet<String>());
			}
			value.addStockCode(stockCode);
			add(qualifier, date, value);
		}

		public StockCodeValue getLatestValue(String type) {
			HBaseColumnQualifier qualifier = this.new StockCodeQualifier(type);
			return (StockCodeValue) getLatestValue(qualifier);
		}

		public class StockCodeQualifier extends HBaseColumnQualifier {
			public static final String ALL = "ALL";
			private String type;

			public StockCodeQualifier() {
				super();
			}

			public StockCodeQualifier(String type) {
				super();
				this.type = type;
			}

			public StockCodeQualifier(byte[] bytes) {
				super();
				fromBytes(bytes);
			}

			@Override
			public byte[] toBytes() {
				return ByteConvertUtility.toBytes(type);
			}

			@Override
			public void fromBytes(byte[] bytes) {
				this.type = ByteConvertUtility.getStringFromBytes(bytes);
			}

			public String getType() {
				return type;
			}

			public void setType(String type) {
				this.type = type;
			}

		}

		public class StockCodeValue extends HBaseValue {
			private Set<String> stockCodes;

			public StockCodeValue() {
				super();
			}

			public StockCodeValue(Set<String> stockCodes) {
				super();
				this.stockCodes = stockCodes;
			}

			public StockCodeValue(byte[] stockCodeBytes) {
				super();
				fromBytes(stockCodeBytes);
			}

			public Set<String> getStockCodes() {
				return stockCodes;
			}

			public void setStockCodes(Set<String> stockCodes) {
				this.stockCodes = stockCodes;
			}

			public void addStockCode(String stockCode) {
				this.stockCodes.add(stockCode);
			}

			@Override
			public byte[] toBytes() {
				StringBuilder sb = new StringBuilder();
				for (String stockCode : stockCodes) {
					sb.append(stockCode);
					sb.append(COMMA);
				}
				return ByteConvertUtility.toBytes(sb.toString());
			}

			@Override
			public void fromBytes(byte[] bytes) {
				String totalStr = ByteConvertUtility.getStringFromBytes(bytes);
				String[] strs = totalStr.split(COMMA);
				Set<String> stockCodes = new TreeSet<String>();
				for (int i = 0; i < strs.length; i++) {
					stockCodes.add(strs[i]);
				}
				this.stockCodes = stockCodes;
			}
		}

		@Override
		protected HBaseColumnQualifier generateColumnQualifier(
				byte[] qualifierBytes) {
			return this.new StockCodeQualifier(qualifierBytes);
		}

		@Override
		protected HBaseValue generateValue(byte[] valueBytes) {
			return this.new StockCodeValue(valueBytes);
		}
	}

	public class ReportTypeFamily extends HBaseColumnFamily {
		private ReportTypeFamily(MopsDownloadInfo table) {
			super(table);
		}

		public Set<Entry<Date, HBaseValue>> getVersionValueSet(String stockCode) {
			ReportTypeQualifier qual = new ReportTypeQualifier(stockCode);
			return getVersionValueSet(qual);
		}

		public void add(String stockCode, Date date, Set<ReportType> reportTypes) {
			HBaseColumnQualifier qualifier = this.new ReportTypeQualifier(
					stockCode);
			ReportTypeValue val = this.new ReportTypeValue(reportTypes);
			add(qualifier, date, val);
		}

		public void addReportType(String stockCode, Date date,
				ReportType reportType) {
			HBaseColumnQualifier qualifier = this.new ReportTypeQualifier(
					stockCode);
			ReportTypeValue value = (ReportTypeValue) this
					.getLatestValue(qualifier);
			if (value == null) {
				value = this.new ReportTypeValue(new TreeSet<ReportType>());
			}
			value.addReportType(reportType);
			add(qualifier, date, value);
		}

		public ReportTypeValue getLatestValue(String stockCode) {
			HBaseColumnQualifier qualifier = this.new ReportTypeQualifier(
					stockCode);
			return (ReportTypeValue) getLatestValue(qualifier);
		}

		public class ReportTypeQualifier extends HBaseColumnQualifier {
			public static final String ALL = "ALL";
			private String stockCode;

			public ReportTypeQualifier() {
				super();
			}

			public ReportTypeQualifier(String stockCode) {
				super();
				this.stockCode = stockCode;
			}

			public ReportTypeQualifier(byte[] bytes) {
				super();
				fromBytes(bytes);
			}

			@Override
			public byte[] toBytes() {
				return ByteConvertUtility.toBytes(stockCode);
			}

			@Override
			public void fromBytes(byte[] bytes) {
				this.stockCode = ByteConvertUtility.getStringFromBytes(bytes);
			}

			public String getStockCode() {
				return stockCode;
			}

			public void setStockCode(String stockCode) {
				this.stockCode = stockCode;
			}
		}

		public class ReportTypeValue extends HBaseValue {
			private Set<ReportType> reportTypes;

			public ReportTypeValue() {
				super();
			}

			public ReportTypeValue(Set<ReportType> reportTypes) {
				super();
				this.reportTypes = reportTypes;
			}

			public ReportTypeValue(byte[] reportTypesBytes) {
				super();
				fromBytes(reportTypesBytes);
			}

			public Set<ReportType> getReportTypes() {
				return reportTypes;
			}

			public void setReportTypes(Set<ReportType> reportTypes) {
				this.reportTypes = reportTypes;
			}

			public void addReportType(ReportType reportType) {
				this.reportTypes.add(reportType);
			}

			@Override
			public byte[] toBytes() {
				StringBuilder sb = new StringBuilder();
				for (ReportType type : reportTypes) {
					sb.append(type.name());
					sb.append(COMMA);
				}
				return ByteConvertUtility.toBytes(sb.toString());
			}

			@Override
			public void fromBytes(byte[] bytes) {
				String totalStr = ByteConvertUtility.getStringFromBytes(bytes);
				String[] strs = totalStr.split(COMMA);
				Set<ReportType> reportTypes = new TreeSet<ReportType>();
				for (int i = 0; i < strs.length; i++) {
					reportTypes.add(ReportType.valueOf(strs[i]));
				}
				this.reportTypes = reportTypes;
			}
		}

		@Override
		protected HBaseColumnQualifier generateColumnQualifier(
				byte[] qualifierBytes) {
			return this.new ReportTypeQualifier(qualifierBytes);
		}

		@Override
		protected HBaseValue generateValue(byte[] valueBytes) {
			return this.new ReportTypeValue(valueBytes);
		}
	}

	public class YearFamily extends HBaseColumnFamily {
		private YearFamily(MopsDownloadInfo table) {
			super(table);
		}

		public Set<Entry<Date, HBaseValue>> getVersionValueSet(String stockCode) {
			YearQualifier qual = new YearQualifier(stockCode);
			return getVersionValueSet(qual);
		}

		public void add(String stockCode, Date date, Set<Integer> years) {
			HBaseColumnQualifier qualifier = this.new YearQualifier(stockCode);
			YearValue val = this.new YearValue(years);
			add(qualifier, date, val);
		}

		public void addYear(String stockCode, Date date, int year) {
			HBaseColumnQualifier qualifier = this.new YearQualifier(stockCode);
			YearValue value = (YearValue) this.getLatestValue(qualifier);
			if (value == null) {
				value = this.new YearValue(new TreeSet<Integer>());
			}
			value.addYear(year);
			add(qualifier, date, value);
		}

		public YearValue getLatestValue(String stockCode) {
			HBaseColumnQualifier qualifier = this.new YearQualifier(stockCode);
			return (YearValue) getLatestValue(qualifier);
		}

		public class YearQualifier extends HBaseColumnQualifier {
			public static final String ALL = "ALL";
			private String stockCode;

			public YearQualifier() {
				super();
			}

			public YearQualifier(String stockCode) {
				super();
				this.stockCode = stockCode;
			}

			public YearQualifier(byte[] bytes) {
				super();
				fromBytes(bytes);
			}

			@Override
			public byte[] toBytes() {
				return ByteConvertUtility.toBytes(stockCode);
			}

			@Override
			public void fromBytes(byte[] bytes) {
				this.stockCode = ByteConvertUtility.getStringFromBytes(bytes);
			}

			public String getStockCode() {
				return stockCode;
			}

			public void setStockCode(String stockCode) {
				this.stockCode = stockCode;
			}
		}

		public class YearValue extends HBaseValue {
			private Set<Integer> years;

			public YearValue() {
				super();
			}

			public YearValue(Set<Integer> years) {
				super();
				this.years = years;
			}

			public YearValue(byte[] yersBytes) {
				super();
				fromBytes(yersBytes);
			}

			public Set<Integer> getYears() {
				return years;
			}

			public void setYears(Set<Integer> years) {
				this.years = years;
			}

			public void addYear(int year) {
				this.years.add(year);
			}

			@Override
			public byte[] toBytes() {
				StringBuilder sb = new StringBuilder();
				for (Integer year : years) {
					sb.append(year);
					sb.append(COMMA);
				}
				return ByteConvertUtility.toBytes(sb.toString());
			}

			@Override
			public void fromBytes(byte[] bytes) {
				String totalStr = ByteConvertUtility.getStringFromBytes(bytes);
				String[] strs = totalStr.split(COMMA);
				Set<Integer> years = new TreeSet<Integer>();
				for (int i = 0; i < strs.length; i++) {
					years.add(Integer.valueOf(strs[i]));
				}
				this.years = years;
			}
		}

		@Override
		protected HBaseColumnQualifier generateColumnQualifier(
				byte[] qualifierBytes) {
			return this.new YearQualifier(qualifierBytes);
		}

		@Override
		protected HBaseValue generateValue(byte[] valueBytes) {
			return this.new YearValue(valueBytes);
		}
	}

	public class SeasonFamily extends HBaseColumnFamily {
		private SeasonFamily(MopsDownloadInfo table) {
			super(table);
		}

		public Set<Entry<Date, HBaseValue>> getVersionValueSet(String stockCode) {
			SeasonQualifier qual = new SeasonQualifier(stockCode);
			return getVersionValueSet(qual);
		}

		public void add(String stockCode, Date date, Set<Integer> years) {
			HBaseColumnQualifier qualifier = this.new SeasonQualifier(stockCode);
			SeasonValue val = this.new SeasonValue(years);
			add(qualifier, date, val);
		}

		public void addSeason(String stockCode, Date date, int season) {
			HBaseColumnQualifier qualifier = this.new SeasonQualifier(stockCode);
			SeasonValue value = (SeasonValue) this.getLatestValue(qualifier);
			if (value == null) {
				value = this.new SeasonValue(new TreeSet<Integer>());
			}
			value.addSeason(season);
			add(qualifier, date, value);
		}

		public SeasonValue getLatestValue(String stockCode) {
			HBaseColumnQualifier qualifier = this.new SeasonQualifier(stockCode);
			return (SeasonValue) getLatestValue(qualifier);
		}

		public class SeasonQualifier extends HBaseColumnQualifier {
			public static final String ALL = "ALL";
			private String stockCode;

			public SeasonQualifier() {
				super();
			}

			public SeasonQualifier(String stockCode) {
				super();
				this.stockCode = stockCode;
			}

			public SeasonQualifier(byte[] bytes) {
				super();
				fromBytes(bytes);
			}

			@Override
			public byte[] toBytes() {
				return ByteConvertUtility.toBytes(stockCode);
			}

			@Override
			public void fromBytes(byte[] bytes) {
				this.stockCode = ByteConvertUtility.getStringFromBytes(bytes);
			}

			public String getStockCode() {
				return stockCode;
			}

			public void setStockCode(String stockCode) {
				this.stockCode = stockCode;
			}
		}

		public class SeasonValue extends HBaseValue {
			private Set<Integer> seasons;

			public SeasonValue() {
				super();
			}

			public SeasonValue(Set<Integer> seasons) {
				super();
				this.seasons = seasons;
			}

			public SeasonValue(byte[] seasonsBytes) {
				super();
				fromBytes(seasonsBytes);
			}

			public Set<Integer> getSeasons() {
				return seasons;
			}

			public void setSeasons(Set<Integer> seasons) {
				this.seasons = seasons;
			}

			public void addSeason(int season) {
				this.seasons.add(season);
			}

			@Override
			public byte[] toBytes() {
				StringBuilder sb = new StringBuilder();
				for (Integer season : seasons) {
					sb.append(season);
					sb.append(COMMA);
				}
				return ByteConvertUtility.toBytes(sb.toString());
			}

			@Override
			public void fromBytes(byte[] bytes) {
				String totalStr = ByteConvertUtility.getStringFromBytes(bytes);
				String[] strs = totalStr.split(COMMA);
				Set<Integer> seasons = new TreeSet<Integer>();
				for (int i = 0; i < strs.length; i++) {
					seasons.add(Integer.valueOf(strs[i]));
				}
				this.seasons = seasons;
			}
		}

		@Override
		protected HBaseColumnQualifier generateColumnQualifier(
				byte[] qualifierBytes) {
			return this.new SeasonQualifier(qualifierBytes);
		}

		@Override
		protected HBaseValue generateValue(byte[] valueBytes) {
			return this.new SeasonValue(valueBytes);
		}
	}
}
