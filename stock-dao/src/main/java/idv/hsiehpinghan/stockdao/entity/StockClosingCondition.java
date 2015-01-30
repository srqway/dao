package idv.hsiehpinghan.stockdao.entity;

import idv.hsiehpinghan.collectionutility.utility.ArrayUtility;
import idv.hsiehpinghan.datatypeutility.utility.ByteUtility;
import idv.hsiehpinghan.hbaseassistant.abstractclass.HBaseColumnFamily;
import idv.hsiehpinghan.hbaseassistant.abstractclass.HBaseColumnQualifier;
import idv.hsiehpinghan.hbaseassistant.abstractclass.HBaseRowKey;
import idv.hsiehpinghan.hbaseassistant.abstractclass.HBaseTable;
import idv.hsiehpinghan.hbaseassistant.abstractclass.HBaseValue;
import idv.hsiehpinghan.hbaseassistant.utility.ByteConvertUtility;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Date;

public class StockClosingCondition extends HBaseTable {
	private static final byte[] SPACE = ByteUtility.SINGLE_SPACE_BYTE_ARRAY;
	private RowKey rowKey;
	private PriceFamily priceFamily;
	private VolumeFamily volumeFamily;

	@Override
	public HBaseRowKey getRowKey() {
		return rowKey;
	}

	@Override
	public void setRowKey(HBaseRowKey rowKey) {
		this.rowKey = (RowKey) rowKey;
	}

	public PriceFamily getPriceFamily() {
		if (priceFamily == null) {
			priceFamily = this.new PriceFamily(this);
		}
		return priceFamily;
	}

	public VolumeFamily getVolumeFamily() {
		if (volumeFamily == null) {
			volumeFamily = this.new VolumeFamily(this);
		}
		return volumeFamily;
	}

	public class RowKey extends HBaseRowKey {
		private static final int STOCK_CODE_LENGTH = 15;
		private static final int DATE_LENGTH = ByteConvertUtility.DEFAULT_DATE_PATTERN_LENGTH;
		private static final int STOCK_CODE_BEGIN_INDEX = 0;
		private static final int STOCK_CODE_END_INDEX = STOCK_CODE_BEGIN_INDEX
				+ STOCK_CODE_LENGTH;
		private static final int DATE_BEGIN_INDEX = STOCK_CODE_END_INDEX + 1;
		private static final int DATE_END_INDEX = DATE_BEGIN_INDEX
				+ DATE_LENGTH;
		private String stockCode;
		private Date date;

		public RowKey(StockClosingCondition entity) {
			super(entity);
		}

		public RowKey(String stockCode, Date date, StockClosingCondition entity) {
			super(entity);
			this.stockCode = stockCode;
			this.date = date;
		}

		public RowKey(byte[] bytes, StockClosingCondition entity) {
			super(entity);
			fromBytes(bytes);
		}

		@Override
		public byte[] toBytes() {
			byte[] stockCodeBytes = ByteConvertUtility.toBytes(stockCode, 15);
			byte[] dateBytes = ByteConvertUtility.toBytes(date);
			return ArrayUtility.addAll(stockCodeBytes, SPACE, dateBytes);
		}

		@Override
		public void fromBytes(byte[] bytes) {
			this.stockCode = ByteConvertUtility.getStringFromBytes(bytes,
					STOCK_CODE_BEGIN_INDEX, STOCK_CODE_END_INDEX);
			try {
				this.date = ByteConvertUtility.getDateFromBytes(bytes,
						DATE_BEGIN_INDEX, DATE_END_INDEX);
			} catch (ParseException e) {
				throw new RuntimeException(e);
			}
		}

		public String getStockCode() {
			return stockCode;
		}

		public void setStockCode(String stockCode) {
			this.stockCode = stockCode;
		}

		public Date getDate() {
			return date;
		}

		public void setDate(Date date) {
			this.date = date;
		}
	}

	public class PriceFamily extends HBaseColumnFamily {
		private PriceFamily(StockClosingCondition entity) {
			super(entity);
		}

		public PriceValue getLatestValue(String name) {
			PriceQualifier qual = this.new PriceQualifier(name);
			return (PriceValue) super.getLatestValue(qual);
		}

		public class PriceQualifier extends HBaseColumnQualifier {
			public static final String OPENING_PRICE = "openingPrice";
			public static final String CLOSING_PRICE = "closingPrice";
			public static final String CHANGE = "change";
			public static final String HIGHEST_PRICE = "highestPrice";
			public static final String LOWEST_PRICE = "lowestPrice";
			public static final String FINAL_PURCHASE_PRICE = "finalPurchasePrice";
			public static final String FINAL_SELLING_PRICE = "finalSellingPrice";
			private String name;

			public PriceQualifier() {
				super();
			}

			public PriceQualifier(String name) {
				super();
				this.name = name;
			}

			public PriceQualifier(byte[] bytes) {
				super();
				fromBytes(bytes);
			}

			@Override
			public byte[] toBytes() {
				byte[] nameBytes = ByteConvertUtility.toBytes(name);
				return ArrayUtility.addAll(nameBytes);
			}

			@Override
			public void fromBytes(byte[] bytes) {
				this.name = ByteConvertUtility.getStringFromBytes(bytes);
			}

			public String getName() {
				return name;
			}

			public void setName(String name) {
				this.name = name;
			}
		}

		public class PriceValue extends HBaseValue {
			private BigDecimal value;

			public PriceValue() {
				super();
			}

			public PriceValue(BigDecimal value) {
				super();
				this.value = value;
			}

			public PriceValue(byte[] bytes) {
				super();
				fromBytes(bytes);
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

			public BigDecimal getValue() {
				return value;
			}

			public void setValue(BigDecimal value) {
				this.value = value;
			}
		}

		@Override
		protected HBaseColumnQualifier generateColumnQualifier(byte[] bytes) {
			return this.new PriceQualifier(bytes);
		}

		@Override
		protected HBaseValue generateValue(byte[] bytes) {
			return this.new PriceValue(bytes);
		}
	}

	public class VolumeFamily extends HBaseColumnFamily {
		private VolumeFamily(StockClosingCondition entity) {
			super(entity);
		}

		public VolumeValue getLatestValue(String name) {
			VolumeQualifier qual = this.new VolumeQualifier(name);
			return (VolumeValue) super.getLatestValue(qual);
		}

		public class VolumeQualifier extends HBaseColumnQualifier {
			public static final String STOCK_AMOUNT = "stockAmount";
			public static final String MONEY_AMOUNT = "moneyAmount";
			public static final String TRANSACTION_AMOUNT = "transactionAmount";

			private String name;

			public VolumeQualifier() {
				super();
			}

			public VolumeQualifier(String name) {
				super();
				this.name = name;
			}

			public VolumeQualifier(byte[] bytes) {
				super();
				fromBytes(bytes);
			}

			@Override
			public byte[] toBytes() {
				byte[] nameBytes = ByteConvertUtility.toBytes(name);
				return ArrayUtility.addAll(nameBytes);
			}

			@Override
			public void fromBytes(byte[] bytes) {
				this.name = ByteConvertUtility.getStringFromBytes(bytes);
			}

			public String getName() {
				return name;
			}

			public void setName(String name) {
				this.name = name;
			}
		}

		public class VolumeValue extends HBaseValue {
			private Integer value;

			public VolumeValue() {
				super();
			}

			public VolumeValue(Integer value) {
				super();
				this.value = value;
			}

			public VolumeValue(byte[] bytes) {
				super();
				fromBytes(bytes);
			}

			@Override
			public byte[] toBytes() {
				byte[] valueBytes = ByteConvertUtility.toBytes(value);
				return ArrayUtility.addAll(valueBytes);
			}

			@Override
			public void fromBytes(byte[] bytes) {
				this.value = ByteConvertUtility.getIntegerFromBytes(bytes);
			}

			public Integer getValue() {
				return value;
			}

			public void setValue(Integer value) {
				this.value = value;
			}
		}

		@Override
		protected HBaseColumnQualifier generateColumnQualifier(byte[] bytes) {
			return this.new VolumeQualifier(bytes);
		}

		@Override
		protected HBaseValue generateValue(byte[] bytes) {
			return this.new VolumeValue(bytes);
		}
	}
}
