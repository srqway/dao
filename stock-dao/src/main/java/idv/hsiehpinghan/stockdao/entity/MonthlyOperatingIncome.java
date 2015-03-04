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
import java.util.Date;
import java.util.Set;

public class MonthlyOperatingIncome extends HBaseTable {
	private static final byte[] SPACE = ByteUtility.SINGLE_SPACE_BYTE_ARRAY;
	private RowKey rowKey;
	private IncomeFamily incomeFamily;

	@Override
	public HBaseRowKey getRowKey() {
		return rowKey;
	}

	@Override
	public void setRowKey(HBaseRowKey rowKey) {
		this.rowKey = (RowKey) rowKey;
	}

	public IncomeFamily getIncomeFamily() {
		if (incomeFamily == null) {
			incomeFamily = this.new IncomeFamily(this);
		}
		return incomeFamily;
	}

	public class RowKey extends HBaseRowKey {
		private static final int STOCK_CODE_LENGTH = 15;
		private static final int IS_FUNCTIONAL_CURRENCY_LENGTH = ByteConvertUtility.DEFAULT_BOOLEAN_LENGTH;
		private static final int CURRENCY_LENGTH = 3;
		private static final int YEAR_LENGTH = 4;
		private static final int MONTH_LENGTH = 2;
		private static final int STOCK_CODE_BEGIN_INDEX = 0;
		private static final int STOCK_CODE_END_INDEX = STOCK_CODE_BEGIN_INDEX
				+ STOCK_CODE_LENGTH;
		private static final int IS_FUNCTIONAL_CURRENCY_BEGIN_INDEX = STOCK_CODE_END_INDEX + 1;
		private static final int IS_FUNCTIONAL_CURRENCY_END_INDEX = IS_FUNCTIONAL_CURRENCY_BEGIN_INDEX
				+ IS_FUNCTIONAL_CURRENCY_LENGTH;
		private static final int CURRENCY_BEGIN_INDEX = IS_FUNCTIONAL_CURRENCY_END_INDEX + 1;
		private static final int CURRENCY_END_INDEX = CURRENCY_BEGIN_INDEX
				+ CURRENCY_LENGTH;
		private static final int YEAR_BEGIN_INDEX = CURRENCY_END_INDEX + 1;
		private static final int YEAR_END_INDEX = YEAR_BEGIN_INDEX
				+ YEAR_LENGTH;
		private static final int MONTH_BEGIN_INDEX = YEAR_END_INDEX + 1;
		private static final int MONTH_END_INDEX = MONTH_BEGIN_INDEX
				+ MONTH_LENGTH;

		public RowKey(MonthlyOperatingIncome entity) {
			super(entity);
		}

		public RowKey(byte[] bytes, MonthlyOperatingIncome entity) {
			super(entity);
			setBytes(bytes);
		}

		public RowKey(String stockCode, boolean isFunctionalCurrency,
				CurrencyType currency, int year, int month,
				MonthlyOperatingIncome entity) {
			super(entity);
			byte[] stockCodeBytes = ByteConvertUtility.toBytes(stockCode,
					STOCK_CODE_LENGTH);
			byte[] isFunctionalCurrencyBytes = ByteConvertUtility
					.toBytes(isFunctionalCurrency);
			byte[] currencyBytes = ByteConvertUtility.toBytes(
					currency == null ? null : currency.name(), CURRENCY_LENGTH);
			byte[] yearBytes = ByteConvertUtility.toBytes(year, YEAR_LENGTH);
			byte[] monthBytes = ByteConvertUtility.toBytes(month, MONTH_LENGTH);
			super.setBytes(ArrayUtility.addAll(stockCodeBytes, SPACE,
					isFunctionalCurrencyBytes, SPACE, currencyBytes, SPACE,
					yearBytes, SPACE, monthBytes));
		}

		public byte[] getFuzzyBytes(String stockCode,
				Boolean isFunctionalCurrency, CurrencyType currency,
				Integer year, Integer month) {
			byte[] stockCodeBytes;
			if (stockCode == null) {
				stockCodeBytes = ArrayUtility.getBytes(STOCK_CODE_LENGTH,
						ByteUtility.BYTE_ONE);
			} else {
				stockCodeBytes = ArrayUtility.getBytes(STOCK_CODE_LENGTH,
						ByteUtility.BYTE_ZERO);
			}
			byte[] isFunctionalCurrencyBytes;
			if (isFunctionalCurrency == null) {
				isFunctionalCurrencyBytes = ArrayUtility.getBytes(
						IS_FUNCTIONAL_CURRENCY_LENGTH, ByteUtility.BYTE_ONE);
			} else {
				isFunctionalCurrencyBytes = ArrayUtility.getBytes(
						IS_FUNCTIONAL_CURRENCY_LENGTH, ByteUtility.BYTE_ZERO);
			}
			byte[] currencyBytes;
			if (currency == null) {
				currencyBytes = ArrayUtility.getBytes(CURRENCY_LENGTH,
						ByteUtility.BYTE_ONE);
			} else {
				currencyBytes = ArrayUtility.getBytes(CURRENCY_LENGTH,
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
			byte[] monthBytes;
			if (month == null) {
				monthBytes = ArrayUtility.getBytes(MONTH_LENGTH,
						ByteUtility.BYTE_ONE);
			} else {
				monthBytes = ArrayUtility.getBytes(MONTH_LENGTH,
						ByteUtility.BYTE_ZERO);
			}
			return ArrayUtility.addAll(stockCodeBytes,
					ByteUtility.SINGLE_ZERO_BYTE_ARRAY,
					isFunctionalCurrencyBytes,
					ByteUtility.SINGLE_ZERO_BYTE_ARRAY, currencyBytes,
					ByteUtility.SINGLE_ZERO_BYTE_ARRAY, yearBytes,
					ByteUtility.SINGLE_ZERO_BYTE_ARRAY, monthBytes);
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

		public boolean getIsFunctionalCurrency() {
			return ByteConvertUtility.getBooleanFromBytes(getBytes(),
					IS_FUNCTIONAL_CURRENCY_BEGIN_INDEX,
					IS_FUNCTIONAL_CURRENCY_END_INDEX);
		}

		public void setIsFunctionalCurrency(boolean isFunctionalCurrency) {
			byte[] bytes = getBytes();
			byte[] subBytes = ByteConvertUtility.toBytes(isFunctionalCurrency);
			ArrayUtility.replace(bytes, subBytes,
					IS_FUNCTIONAL_CURRENCY_BEGIN_INDEX,
					IS_FUNCTIONAL_CURRENCY_END_INDEX);
		}

		public CurrencyType getCurrency() {
			return CurrencyType.valueOf(ByteConvertUtility.getStringFromBytes(
					getBytes(), CURRENCY_BEGIN_INDEX, CURRENCY_END_INDEX));
		}

		public void setCurrency(CurrencyType currency) {
			byte[] bytes = getBytes();
			byte[] subBytes = ByteConvertUtility.toBytes(
					currency == null ? null : currency.name(), CURRENCY_LENGTH);
			ArrayUtility.replace(bytes, subBytes, CURRENCY_BEGIN_INDEX,
					CURRENCY_END_INDEX);
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

	public class IncomeFamily extends HBaseColumnFamily {
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

		private IncomeFamily(MonthlyOperatingIncome entity) {
			super(entity);
		}

		@SuppressWarnings("unchecked")
		public Set<IncomeQualifier> getQualifiers() {
			return (Set<IncomeQualifier>) (Object) super
					.getQualifierVersionValueMap().keySet();
		}

		public BigDecimal getCurrentMonth() {
			HBaseColumnQualifier qual = new IncomeQualifier(CURRENT_MONTH);
			IncomeValue val = (IncomeValue) super.getLatestValue(qual);
			if (val == null) {
				return null;
			}
			return val.getAsBigDecimal();
		}

		public void setCurrentMonth(Date ver, BigDecimal currentMonth) {
			IncomeQualifier qual = new IncomeQualifier(CURRENT_MONTH);
			IncomeValue val = new IncomeValue();
			val.set(currentMonth);
			add(qual, ver, val);
		}

		public BigDecimal getCurrentMonthOfLastYear() {
			HBaseColumnQualifier qual = new IncomeQualifier(
					CURRENT_MONTH_OF_LAST_YEAR);
			IncomeValue val = (IncomeValue) super.getLatestValue(qual);
			if (val == null) {
				return null;
			}
			return val.getAsBigDecimal();
		}

		public void setCurrentMonthOfLastYear(Date ver,
				BigDecimal currentMonthOfLastYear) {
			IncomeQualifier qual = new IncomeQualifier(
					CURRENT_MONTH_OF_LAST_YEAR);
			IncomeValue val = new IncomeValue();
			val.set(currentMonthOfLastYear);
			add(qual, ver, val);
		}

		public BigDecimal getDifferentAmount() {
			HBaseColumnQualifier qual = new IncomeQualifier(DIFFERENT_AMOUNT);
			IncomeValue val = (IncomeValue) super.getLatestValue(qual);
			if (val == null) {
				return null;
			}
			return val.getAsBigDecimal();
		}

		public void setDifferentAmount(Date ver, BigDecimal differentAmount) {
			IncomeQualifier qual = new IncomeQualifier(DIFFERENT_AMOUNT);
			IncomeValue val = new IncomeValue();
			val.set(differentAmount);
			add(qual, ver, val);
		}

		public BigDecimal getDifferentPercent() {
			HBaseColumnQualifier qual = new IncomeQualifier(DIFFERENT_PERCENT);
			IncomeValue val = (IncomeValue) super.getLatestValue(qual);
			if (val == null) {
				return null;
			}
			return val.getAsBigDecimal();
		}

		public void setDifferentPercent(Date ver, BigDecimal differentPercent) {
			IncomeQualifier qual = new IncomeQualifier(DIFFERENT_PERCENT);
			IncomeValue val = new IncomeValue();
			val.set(differentPercent);
			add(qual, ver, val);
		}

		public BigDecimal getCumulativeAmountOfThisYear() {
			HBaseColumnQualifier qual = new IncomeQualifier(
					CUMULATIVE_AMOUNT_OF_THIS_YEAR);
			IncomeValue val = (IncomeValue) super.getLatestValue(qual);
			if (val == null) {
				return null;
			}
			return val.getAsBigDecimal();
		}

		public void setCumulativeAmountOfThisYear(Date ver,
				BigDecimal cumulativeAmountOfThisYear) {
			IncomeQualifier qual = new IncomeQualifier(
					CUMULATIVE_AMOUNT_OF_THIS_YEAR);
			IncomeValue val = new IncomeValue();
			val.set(cumulativeAmountOfThisYear);
			add(qual, ver, val);
		}

		public BigDecimal getCumulativeAmountOfLastYear() {
			HBaseColumnQualifier qual = new IncomeQualifier(
					CUMULATIVE_AMOUNT_OF_LAST_YEAR);
			IncomeValue val = (IncomeValue) super.getLatestValue(qual);
			if (val == null) {
				return null;
			}
			return val.getAsBigDecimal();
		}

		public void setCumulativeAmountOfLastYear(Date ver,
				BigDecimal cumulativeAmountOfLastYear) {
			IncomeQualifier qual = new IncomeQualifier(
					CUMULATIVE_AMOUNT_OF_LAST_YEAR);
			IncomeValue val = new IncomeValue();
			val.set(cumulativeAmountOfLastYear);
			add(qual, ver, val);
		}

		public BigDecimal getCumulativeDifferentAmount() {
			HBaseColumnQualifier qual = new IncomeQualifier(
					CUMULATIVE_DIFFERENT_AMOUNT);
			IncomeValue val = (IncomeValue) super.getLatestValue(qual);
			if (val == null) {
				return null;
			}
			return val.getAsBigDecimal();
		}

		public void setCumulativeDifferentAmount(Date ver,
				BigDecimal cumulativeDifferentAmount) {
			IncomeQualifier qual = new IncomeQualifier(
					CUMULATIVE_DIFFERENT_AMOUNT);
			IncomeValue val = new IncomeValue();
			val.set(cumulativeDifferentAmount);
			add(qual, ver, val);
		}

		public BigDecimal getCumulativeDifferentPercent() {
			HBaseColumnQualifier qual = new IncomeQualifier(
					CUMULATIVE_DIFFERENT_PERCENT);
			IncomeValue val = (IncomeValue) super.getLatestValue(qual);
			if (val == null) {
				return null;
			}
			return val.getAsBigDecimal();
		}

		public void setCumulativeDifferentPercent(Date ver,
				BigDecimal cumulativeDifferentPercent) {
			IncomeQualifier qual = new IncomeQualifier(
					CUMULATIVE_DIFFERENT_PERCENT);
			IncomeValue val = new IncomeValue();
			val.set(cumulativeDifferentPercent);
			add(qual, ver, val);
		}

		public BigDecimal getExchangeRateOfCurrentMonth() {
			HBaseColumnQualifier qual = new IncomeQualifier(
					EXCHANGE_RATE_OF_CURRENT_MONTH);
			IncomeValue val = (IncomeValue) super.getLatestValue(qual);
			if (val == null) {
				return null;
			}
			return val.getAsBigDecimal();
		}

		public void setExchangeRateOfCurrentMonth(Date ver,
				BigDecimal exchangeRateOfCurrentMonth) {
			IncomeQualifier qual = new IncomeQualifier(
					EXCHANGE_RATE_OF_CURRENT_MONTH);
			IncomeValue val = new IncomeValue();
			val.set(exchangeRateOfCurrentMonth);
			add(qual, ver, val);
		}

		public BigDecimal getCumulativeExchangeRateOfThisYear() {
			HBaseColumnQualifier qual = new IncomeQualifier(
					CUMULATIVE_EXCHANGE_RATE_OF_THIS_YEAR);
			IncomeValue val = (IncomeValue) super.getLatestValue(qual);
			if (val == null) {
				return null;
			}
			return val.getAsBigDecimal();
		}

		public void setCumulativeExchangeRateOfThisYear(Date ver,
				BigDecimal cumulativeExchangeRateOfThisYear) {
			IncomeQualifier qual = new IncomeQualifier(
					CUMULATIVE_EXCHANGE_RATE_OF_THIS_YEAR);
			IncomeValue val = new IncomeValue();
			val.set(cumulativeExchangeRateOfThisYear);
			add(qual, ver, val);
		}

		public String getComment() {
			HBaseColumnQualifier qual = new IncomeQualifier(COMMENT);
			IncomeValue val = (IncomeValue) super.getLatestValue(qual);
			if (val == null) {
				return null;
			}
			return val.getAsString();
		}

		public void setComment(Date ver, String comment) {
			IncomeQualifier qual = new IncomeQualifier(COMMENT);
			IncomeValue val = new IncomeValue();
			val.set(comment);
			add(qual, ver, val);
		}

		@Override
		protected HBaseColumnQualifier generateColumnQualifier(byte[] bytes) {
			return this.new IncomeQualifier(bytes);
		}

		@Override
		protected HBaseValue generateValue(byte[] bytes) {
			return this.new IncomeValue(bytes);
		}

		public class IncomeQualifier extends HBaseColumnQualifier {
			public IncomeQualifier() {
				super();
			}

			public IncomeQualifier(byte[] bytes) {
				super();
				setBytes(bytes);
			}

			public IncomeQualifier(String columnName) {
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

		public class IncomeValue extends HBaseValue {
			public IncomeValue() {
				super();
			}

			public IncomeValue(byte[] bytes) {
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
		}
	}
}
