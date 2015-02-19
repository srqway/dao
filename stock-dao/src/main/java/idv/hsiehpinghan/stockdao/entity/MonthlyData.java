package idv.hsiehpinghan.stockdao.entity;

import idv.hsiehpinghan.collectionutility.utility.ArrayUtility;
import idv.hsiehpinghan.datatypeutility.utility.ByteUtility;
import idv.hsiehpinghan.hbaseassistant.abstractclass.HBaseColumnFamily;
import idv.hsiehpinghan.hbaseassistant.abstractclass.HBaseColumnQualifier;
import idv.hsiehpinghan.hbaseassistant.abstractclass.HBaseRowKey;
import idv.hsiehpinghan.hbaseassistant.abstractclass.HBaseTable;
import idv.hsiehpinghan.hbaseassistant.abstractclass.HBaseValue;
import idv.hsiehpinghan.hbaseassistant.utility.ByteConvertUtility;
import idv.hsiehpinghan.stockdao.enumeration.CurrencyType;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.Set;

public class MonthlyData extends HBaseTable {
	private static final byte[] SPACE = ByteUtility.SINGLE_SPACE_BYTE_ARRAY;
	private RowKey rowKey;
	private OperatingIncomeFamily operatingIncomeFamily;

	@Override
	public HBaseRowKey getRowKey() {
		return rowKey;
	}

	@Override
	public void setRowKey(HBaseRowKey rowKey) {
		this.rowKey = (RowKey) rowKey;
	}

	public OperatingIncomeFamily getOperatingIncomeFamily() {
		if (operatingIncomeFamily == null) {
			operatingIncomeFamily = this.new OperatingIncomeFamily(this);
		}
		return operatingIncomeFamily;
	}

	public class RowKey extends HBaseRowKey {
		private static final int STOCK_CODE_LENGTH = 15;
		private static final int YEAR_LENGTH = 4;
		private static final int MONTH_LENGTH = 2;
		private static final int STOCK_CODE_BEGIN_INDEX = 0;
		private static final int STOCK_CODE_END_INDEX = STOCK_CODE_BEGIN_INDEX
				+ STOCK_CODE_LENGTH;
		private static final int YEAR_BEGIN_INDEX = STOCK_CODE_END_INDEX + 1;
		private static final int YEAR_END_INDEX = YEAR_BEGIN_INDEX
				+ YEAR_LENGTH;
		private static final int MONTH_BEGIN_INDEX = YEAR_END_INDEX + 1;
		private static final int MONTH_END_INDEX = MONTH_BEGIN_INDEX
				+ MONTH_LENGTH;

		public RowKey(MonthlyData entity) {
			super(entity);
		}

		public RowKey(byte[] bytes, MonthlyData entity) {
			super(entity);
			setBytes(bytes);
		}

		public RowKey(String stockCode, int year, int month, MonthlyData entity) {
			super(entity);
			byte[] stockCodeBytes = ByteConvertUtility.toBytes(stockCode,
					STOCK_CODE_LENGTH);
			byte[] yearBytes = ByteConvertUtility.toBytes(year, YEAR_LENGTH);
			byte[] monthBytes = ByteConvertUtility.toBytes(month, MONTH_LENGTH);
			super.setBytes(ArrayUtility.addAll(stockCodeBytes, SPACE,
					yearBytes, SPACE, monthBytes));
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

		public int getMonth() {
			return ByteConvertUtility.getIntFromBytes(getBytes(),
					MONTH_BEGIN_INDEX, MONTH_END_INDEX);
		}

		public void setMonth(int month) {
			byte[] bytes = getBytes();
			byte[] subBytes = ByteConvertUtility.toBytes(month, MONTH_LENGTH);
			ArrayUtility.replace(bytes, subBytes, MONTH_BEGIN_INDEX,
					MONTH_END_INDEX);
		}
	}

	public class OperatingIncomeFamily extends HBaseColumnFamily {
		public static final String CURRENCY = "currency";
		public static final String CURRENT_MONTH = "currentMonth";
		public static final String CURRENT_MONTH_OF_LAST_YEAR = "currentMonthOfLastYear";
		public static final String DIFFERENT_AMOUNT = "differentAmount";
		public static final String DIFFERENT_PERCENT = "differentPercent";
		public static final String CUMULATIVE_AMOUNT_OF_THIS_YEAR = "cumulativeAmountOfThisYear";
		public static final String CUMULATIVE_AMOUNT_OF_LAST_YEAR = "cumulativeAmountOfLastYear";
		public static final String CUMULATIVE_DIFFERENT_AMOUNT = "cumulativeDifferentAmount";
		public static final String CUMULATIVE_DIFFERENT_PERCENT = "cumulativeDifferentPercent";
		public static final String EXCHANGE_RATE_OF_CURRENT_MONTH = "exchangeRateOfCurrentMonth";
		public static final String CUMULATIVE_EXCHANGE_RATE_OF_THIS_YEAR = "cumulativeExchangeRateOfThisYear";
		public static final String COMMENT = "comment";

		private OperatingIncomeFamily(MonthlyData entity) {
			super(entity);
		}

		@SuppressWarnings("unchecked")
		public Set<OperatingIncomeQualifier> getQualifiers() {
			return (Set<OperatingIncomeQualifier>) (Object) super
					.getQualifierVersionValueMap().keySet();
		}

		public CurrencyType getCurrency() {
			HBaseColumnQualifier qual = new OperatingIncomeQualifier(CURRENCY);
			OperatingIncomeValue val = (OperatingIncomeValue) super
					.getLatestValue(qual);
			return val.getAsCurrencyType();
		}

		public void setCurrency(Date ver, CurrencyType currency) {
			OperatingIncomeQualifier qual = new OperatingIncomeQualifier(
					CURRENCY);
			OperatingIncomeValue val = new OperatingIncomeValue();
			val.set(currency);
			add(qual, ver, val);
		}

		public BigInteger getCurrentMonth() {
			HBaseColumnQualifier qual = new OperatingIncomeQualifier(
					CURRENT_MONTH);
			OperatingIncomeValue val = (OperatingIncomeValue) super
					.getLatestValue(qual);
			return val.getAsBigInteger();
		}

		public void setCurrentMonth(Date ver, BigInteger currentMonth) {
			OperatingIncomeQualifier qual = new OperatingIncomeQualifier(
					CURRENT_MONTH);
			OperatingIncomeValue val = new OperatingIncomeValue();
			val.set(currentMonth);
			add(qual, ver, val);
		}

		public BigInteger getCurrentMonthOfLastYear() {
			HBaseColumnQualifier qual = new OperatingIncomeQualifier(
					CURRENT_MONTH_OF_LAST_YEAR);
			OperatingIncomeValue val = (OperatingIncomeValue) super
					.getLatestValue(qual);
			return val.getAsBigInteger();
		}

		public void setCurrentMonthOfLastYear(Date ver,
				BigInteger currentMonthOfLastYear) {
			OperatingIncomeQualifier qual = new OperatingIncomeQualifier(
					CURRENT_MONTH_OF_LAST_YEAR);
			OperatingIncomeValue val = new OperatingIncomeValue();
			val.set(currentMonthOfLastYear);
			add(qual, ver, val);
		}

		public BigInteger getDifferentAmount() {
			HBaseColumnQualifier qual = new OperatingIncomeQualifier(
					DIFFERENT_AMOUNT);
			OperatingIncomeValue val = (OperatingIncomeValue) super
					.getLatestValue(qual);
			return val.getAsBigInteger();
		}

		public void setDifferentAmount(Date ver, BigInteger differentAmount) {
			OperatingIncomeQualifier qual = new OperatingIncomeQualifier(
					DIFFERENT_AMOUNT);
			OperatingIncomeValue val = new OperatingIncomeValue();
			val.set(differentAmount);
			add(qual, ver, val);
		}

		public BigDecimal getDifferentPercent() {
			HBaseColumnQualifier qual = new OperatingIncomeQualifier(
					DIFFERENT_PERCENT);
			OperatingIncomeValue val = (OperatingIncomeValue) super
					.getLatestValue(qual);
			return val.getAsBigDecimal();
		}

		public void setDifferentPercent(Date ver, BigDecimal differentPercent) {
			OperatingIncomeQualifier qual = new OperatingIncomeQualifier(
					DIFFERENT_PERCENT);
			OperatingIncomeValue val = new OperatingIncomeValue();
			val.set(differentPercent);
			add(qual, ver, val);
		}

		public BigInteger getCumulativeAmountOfThisYear() {
			HBaseColumnQualifier qual = new OperatingIncomeQualifier(
					CUMULATIVE_AMOUNT_OF_THIS_YEAR);
			OperatingIncomeValue val = (OperatingIncomeValue) super
					.getLatestValue(qual);
			return val.getAsBigInteger();
		}

		public void setCumulativeAmountOfThisYear(Date ver,
				BigInteger cumulativeAmountOfThisYear) {
			OperatingIncomeQualifier qual = new OperatingIncomeQualifier(
					CUMULATIVE_AMOUNT_OF_THIS_YEAR);
			OperatingIncomeValue val = new OperatingIncomeValue();
			val.set(cumulativeAmountOfThisYear);
			add(qual, ver, val);
		}

		public BigInteger getCumulativeAmountOfLastYear() {
			HBaseColumnQualifier qual = new OperatingIncomeQualifier(
					CUMULATIVE_AMOUNT_OF_LAST_YEAR);
			OperatingIncomeValue val = (OperatingIncomeValue) super
					.getLatestValue(qual);
			return val.getAsBigInteger();
		}

		public void setCumulativeAmountOfLastYear(Date ver,
				BigInteger cumulativeAmountOfLastYear) {
			OperatingIncomeQualifier qual = new OperatingIncomeQualifier(
					CUMULATIVE_AMOUNT_OF_LAST_YEAR);
			OperatingIncomeValue val = new OperatingIncomeValue();
			val.set(cumulativeAmountOfLastYear);
			add(qual, ver, val);
		}

		public BigInteger getCumulativeDifferentAmount() {
			HBaseColumnQualifier qual = new OperatingIncomeQualifier(
					CUMULATIVE_DIFFERENT_AMOUNT);
			OperatingIncomeValue val = (OperatingIncomeValue) super
					.getLatestValue(qual);
			return val.getAsBigInteger();
		}

		public void setCumulativeDifferentAmount(Date ver,
				BigInteger cumulativeDifferentAmount) {
			OperatingIncomeQualifier qual = new OperatingIncomeQualifier(
					CUMULATIVE_DIFFERENT_AMOUNT);
			OperatingIncomeValue val = new OperatingIncomeValue();
			val.set(cumulativeDifferentAmount);
			add(qual, ver, val);
		}

		public BigDecimal getCumulativeDifferentPercent() {
			HBaseColumnQualifier qual = new OperatingIncomeQualifier(
					CUMULATIVE_DIFFERENT_PERCENT);
			OperatingIncomeValue val = (OperatingIncomeValue) super
					.getLatestValue(qual);
			return val.getAsBigDecimal();
		}

		public void setCumulativeDifferentPercent(Date ver,
				BigDecimal cumulativeDifferentPercent) {
			OperatingIncomeQualifier qual = new OperatingIncomeQualifier(
					CUMULATIVE_DIFFERENT_PERCENT);
			OperatingIncomeValue val = new OperatingIncomeValue();
			val.set(cumulativeDifferentPercent);
			add(qual, ver, val);
		}

		public BigDecimal getExchangeRateOfCurrentMonth() {
			HBaseColumnQualifier qual = new OperatingIncomeQualifier(
					EXCHANGE_RATE_OF_CURRENT_MONTH);
			OperatingIncomeValue val = (OperatingIncomeValue) super
					.getLatestValue(qual);
			return val.getAsBigDecimal();
		}

		public void setExchangeRateOfCurrentMonth(Date ver,
				BigDecimal exchangeRateOfCurrentMonth) {
			OperatingIncomeQualifier qual = new OperatingIncomeQualifier(
					EXCHANGE_RATE_OF_CURRENT_MONTH);
			OperatingIncomeValue val = new OperatingIncomeValue();
			val.set(exchangeRateOfCurrentMonth);
			add(qual, ver, val);
		}

		public BigDecimal getCumulativeExchangeRateOfThisYear() {
			HBaseColumnQualifier qual = new OperatingIncomeQualifier(
					CUMULATIVE_EXCHANGE_RATE_OF_THIS_YEAR);
			OperatingIncomeValue val = (OperatingIncomeValue) super
					.getLatestValue(qual);
			return val.getAsBigDecimal();
		}

		public void setCumulativeExchangeRateOfThisYear(Date ver,
				BigDecimal cumulativeExchangeRateOfThisYear) {
			OperatingIncomeQualifier qual = new OperatingIncomeQualifier(
					CUMULATIVE_EXCHANGE_RATE_OF_THIS_YEAR);
			OperatingIncomeValue val = new OperatingIncomeValue();
			val.set(cumulativeExchangeRateOfThisYear);
			add(qual, ver, val);
		}

		public String getComment() {
			HBaseColumnQualifier qual = new OperatingIncomeQualifier(COMMENT);
			OperatingIncomeValue val = (OperatingIncomeValue) super
					.getLatestValue(qual);
			return val.getAsString();
		}

		public void setComment(Date ver, String comment) {
			OperatingIncomeQualifier qual = new OperatingIncomeQualifier(
					COMMENT);
			OperatingIncomeValue val = new OperatingIncomeValue();
			val.set(comment);
			add(qual, ver, val);
		}

		@Override
		protected HBaseColumnQualifier generateColumnQualifier(byte[] bytes) {
			return this.new OperatingIncomeQualifier(bytes);
		}

		@Override
		protected HBaseValue generateValue(byte[] bytes) {
			return this.new OperatingIncomeValue(bytes);
		}

		public class OperatingIncomeQualifier extends HBaseColumnQualifier {
			public OperatingIncomeQualifier() {
				super();
			}

			public OperatingIncomeQualifier(byte[] bytes) {
				super();
				setBytes(bytes);
			}

			public OperatingIncomeQualifier(String columnName) {
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

		public class OperatingIncomeValue extends HBaseValue {
			public OperatingIncomeValue() {
				super();
			}

			public OperatingIncomeValue(byte[] bytes) {
				super();
				setBytes(bytes);
			}

			public String getAsString() {
				return ByteConvertUtility.getStringFromBytes(getBytes());
			}

			public void set(String value) {
				setBytes(ByteConvertUtility.toBytes(value));
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

			public CurrencyType getAsCurrencyType() {
				return CurrencyType.valueOf(ByteConvertUtility
						.getStringFromBytes(getBytes()));
			}

			public void set(CurrencyType value) {
				setBytes(ByteConvertUtility.toBytes(value.name()));
			}
		}
	}
}
