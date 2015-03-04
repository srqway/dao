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
import java.math.BigInteger;
import java.text.ParseException;
import java.util.Date;
import java.util.Set;

public class StockClosingCondition extends HBaseTable {
	private static final byte[] SPACE = ByteUtility.SINGLE_SPACE_BYTE_ARRAY;
	private RowKey rowKey;
	private ClosingConditionFamily closingConditionFamily;

	@Override
	public HBaseRowKey getRowKey() {
		return rowKey;
	}

	@Override
	public void setRowKey(HBaseRowKey rowKey) {
		this.rowKey = (RowKey) rowKey;
	}

	public ClosingConditionFamily getClosingConditionFamily() {
		if (closingConditionFamily == null) {
			closingConditionFamily = this.new ClosingConditionFamily(this);
		}
		return closingConditionFamily;
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

		public RowKey(StockClosingCondition entity) {
			super(entity);
		}

		public RowKey(byte[] bytes, StockClosingCondition entity) {
			super(entity);
			setBytes(bytes);
		}

		public RowKey(String stockCode, Date date, StockClosingCondition entity) {
			super(entity);
			byte[] stockCodeBytes = ByteConvertUtility.toBytes(stockCode,
					STOCK_CODE_LENGTH);
			byte[] dateBytes = ByteConvertUtility.toBytes(date);
			super.setBytes(ArrayUtility
					.addAll(stockCodeBytes, SPACE, dateBytes));
		}

		public byte[] getFuzzyBytes(String stockCode, Date date) {
			byte[] stockCodeBytes;
			if (stockCode == null) {
				stockCodeBytes = ArrayUtility.getBytes(STOCK_CODE_LENGTH,
						ByteUtility.BYTE_ONE);
			} else {
				stockCodeBytes = ArrayUtility.getBytes(STOCK_CODE_LENGTH,
						ByteUtility.BYTE_ZERO);
			}
			byte[] dateBytes;
			if (date == null) {
				dateBytes = ArrayUtility.getBytes(DATE_LENGTH,
						ByteUtility.BYTE_ONE);
			} else {
				dateBytes = ArrayUtility.getBytes(DATE_LENGTH,
						ByteUtility.BYTE_ZERO);
			}
			return ArrayUtility.addAll(stockCodeBytes,
					ByteUtility.SINGLE_ZERO_BYTE_ARRAY, dateBytes);
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

	public class ClosingConditionFamily extends HBaseColumnFamily {
		public static final String OPENING_PRICE = "openingPrice";
		public static final String CLOSING_PRICE = "closingPrice";
		public static final String CHANGE = "change";
		public static final String HIGHEST_PRICE = "highestPrice";
		public static final String LOWEST_PRICE = "lowestPrice";
		public static final String FINAL_PURCHASE_PRICE = "finalPurchasePrice";
		public static final String FINAL_SELLING_PRICE = "finalSellingPrice";
		public static final String STOCK_AMOUNT = "stockAmount";
		public static final String MONEY_AMOUNT = "moneyAmount";
		public static final String TRANSACTION_AMOUNT = "transactionAmount";

		private ClosingConditionFamily(StockClosingCondition entity) {
			super(entity);
		}

		@SuppressWarnings("unchecked")
		public Set<ClosingConditionQualifier> getQualifiers() {
			return (Set<ClosingConditionQualifier>) (Object) super
					.getQualifierVersionValueMap().keySet();
		}

		public BigDecimal getOpeningPrice() {
			HBaseColumnQualifier qual = new ClosingConditionQualifier(
					OPENING_PRICE);
			ClosingConditionValue val = (ClosingConditionValue) super
					.getLatestValue(qual);
			if (val == null) {
				return null;
			}
			return val.getAsBigDecimal();
		}

		public void setOpeningPrice(Date ver, BigDecimal openingPrice) {
			ClosingConditionQualifier qual = new ClosingConditionQualifier(
					OPENING_PRICE);
			ClosingConditionValue val = new ClosingConditionValue();
			val.set(openingPrice);
			add(qual, ver, val);
		}

		public BigDecimal getClosingPrice() {
			HBaseColumnQualifier qual = new ClosingConditionQualifier(
					CLOSING_PRICE);
			ClosingConditionValue val = (ClosingConditionValue) super
					.getLatestValue(qual);
			if (val == null) {
				return null;
			}
			return val.getAsBigDecimal();
		}

		public void setClosingPrice(Date ver, BigDecimal closingPrice) {
			ClosingConditionQualifier qual = new ClosingConditionQualifier(
					CLOSING_PRICE);
			ClosingConditionValue val = new ClosingConditionValue();
			val.set(closingPrice);
			add(qual, ver, val);
		}

		public BigDecimal getChange() {
			HBaseColumnQualifier qual = new ClosingConditionQualifier(CHANGE);
			ClosingConditionValue val = (ClosingConditionValue) super
					.getLatestValue(qual);
			if (val == null) {
				return null;
			}
			return val.getAsBigDecimal();
		}

		public void setChange(Date ver, BigDecimal change) {
			ClosingConditionQualifier qual = new ClosingConditionQualifier(
					CHANGE);
			ClosingConditionValue val = new ClosingConditionValue();
			val.set(change);
			add(qual, ver, val);
		}

		public BigDecimal getHighestPrice() {
			HBaseColumnQualifier qual = new ClosingConditionQualifier(
					HIGHEST_PRICE);
			ClosingConditionValue val = (ClosingConditionValue) super
					.getLatestValue(qual);
			if (val == null) {
				return null;
			}
			return val.getAsBigDecimal();
		}

		public void setHighestPrice(Date ver, BigDecimal highestPrice) {
			ClosingConditionQualifier qual = new ClosingConditionQualifier(
					HIGHEST_PRICE);
			ClosingConditionValue val = new ClosingConditionValue();
			val.set(highestPrice);
			add(qual, ver, val);
		}

		public BigDecimal getLowestPrice() {
			HBaseColumnQualifier qual = new ClosingConditionQualifier(
					LOWEST_PRICE);
			ClosingConditionValue val = (ClosingConditionValue) super
					.getLatestValue(qual);
			if (val == null) {
				return null;
			}
			return val.getAsBigDecimal();
		}

		public void setLowestPrice(Date ver, BigDecimal lowestPrice) {
			ClosingConditionQualifier qual = new ClosingConditionQualifier(
					LOWEST_PRICE);
			ClosingConditionValue val = new ClosingConditionValue();
			val.set(lowestPrice);
			add(qual, ver, val);
		}

		public BigDecimal getFinalPurchasePrice() {
			HBaseColumnQualifier qual = new ClosingConditionQualifier(
					FINAL_PURCHASE_PRICE);
			ClosingConditionValue val = (ClosingConditionValue) super
					.getLatestValue(qual);
			if (val == null) {
				return null;
			}
			return val.getAsBigDecimal();
		}

		public void setFinalPurchasePrice(Date ver,
				BigDecimal finalPurchasePrice) {
			ClosingConditionQualifier qual = new ClosingConditionQualifier(
					FINAL_PURCHASE_PRICE);
			ClosingConditionValue val = new ClosingConditionValue();
			val.set(finalPurchasePrice);
			add(qual, ver, val);
		}

		public BigDecimal getFinalSellingPrice() {
			HBaseColumnQualifier qual = new ClosingConditionQualifier(
					FINAL_SELLING_PRICE);
			ClosingConditionValue val = (ClosingConditionValue) super
					.getLatestValue(qual);
			if (val == null) {
				return null;
			}
			return val.getAsBigDecimal();
		}

		public void setFinalSellingPrice(Date ver, BigDecimal finalSellingPrice) {
			ClosingConditionQualifier qual = new ClosingConditionQualifier(
					FINAL_SELLING_PRICE);
			ClosingConditionValue val = new ClosingConditionValue();
			val.set(finalSellingPrice);
			add(qual, ver, val);
		}

		public BigInteger getStockAmount() {
			HBaseColumnQualifier qual = new ClosingConditionQualifier(
					STOCK_AMOUNT);
			ClosingConditionValue val = (ClosingConditionValue) super
					.getLatestValue(qual);
			if (val == null) {
				return null;
			}
			return val.getAsBigInteger();
		}

		public void setStockAmount(Date ver, BigInteger stockAmount) {
			ClosingConditionQualifier qual = new ClosingConditionQualifier(
					STOCK_AMOUNT);
			ClosingConditionValue val = new ClosingConditionValue();
			val.set(stockAmount);
			add(qual, ver, val);
		}

		public BigInteger getMoneyAmount() {
			HBaseColumnQualifier qual = new ClosingConditionQualifier(
					MONEY_AMOUNT);
			ClosingConditionValue val = (ClosingConditionValue) super
					.getLatestValue(qual);
			if (val == null) {
				return null;
			}
			return val.getAsBigInteger();
		}

		public void setMoneyAmount(Date ver, BigInteger moneyAmount) {
			ClosingConditionQualifier qual = new ClosingConditionQualifier(
					MONEY_AMOUNT);
			ClosingConditionValue val = new ClosingConditionValue();
			val.set(moneyAmount);
			add(qual, ver, val);
		}

		public BigInteger getTransactionAmount() {
			HBaseColumnQualifier qual = new ClosingConditionQualifier(
					TRANSACTION_AMOUNT);
			ClosingConditionValue val = (ClosingConditionValue) super
					.getLatestValue(qual);
			if (val == null) {
				return null;
			}
			return val.getAsBigInteger();
		}

		public void setTransactionAmount(Date ver, BigInteger transactionAmount) {
			ClosingConditionQualifier qual = new ClosingConditionQualifier(
					TRANSACTION_AMOUNT);
			ClosingConditionValue val = new ClosingConditionValue();
			val.set(transactionAmount);
			add(qual, ver, val);
		}

		@Override
		protected HBaseColumnQualifier generateColumnQualifier(byte[] bytes) {
			return this.new ClosingConditionQualifier(bytes);
		}

		@Override
		protected HBaseValue generateValue(byte[] bytes) {
			return this.new ClosingConditionValue(bytes);
		}

		public class ClosingConditionQualifier extends HBaseColumnQualifier {
			public ClosingConditionQualifier() {
				super();
			}

			public ClosingConditionQualifier(byte[] bytes) {
				super();
				setBytes(bytes);
			}

			public ClosingConditionQualifier(String columnName) {
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

		public class ClosingConditionValue extends HBaseValue {
			public ClosingConditionValue() {
				super();
			}

			public ClosingConditionValue(byte[] bytes) {
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
