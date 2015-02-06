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
import java.util.Date;

public class MonthlyOperatingIncome extends HBaseTable {
	private static final byte[] SPACE = ByteUtility.SINGLE_SPACE_BYTE_ARRAY;
	private RowKey rowKey;
	private DataFamily dataFamily;
	private CommentFamily commentFamily;

	@Override
	public HBaseRowKey getRowKey() {
		return rowKey;
	}

	@Override
	public void setRowKey(HBaseRowKey rowKey) {
		this.rowKey = (RowKey) rowKey;
	}

	public DataFamily getDataFamily() {
		if (dataFamily == null) {
			dataFamily = this.new DataFamily(this);
		}
		return dataFamily;
	}

	public CommentFamily getCommentFamily() {
		if (commentFamily == null) {
			commentFamily = this.new CommentFamily(this);
		}
		return commentFamily;
	}

	public class RowKey extends HBaseRowKey {
		private String stockCode;

		public RowKey(MonthlyOperatingIncome entity) {
			super(entity);
		}

		public RowKey(String stockCode, MonthlyOperatingIncome entity) {
			super(entity);
			this.stockCode = stockCode;
		}

		public RowKey(byte[] bytes, MonthlyOperatingIncome entity) {
			super(entity);
			fromBytes(bytes);
		}

		@Override
		public byte[] toBytes() {
			byte[] stockCodeBytes = ByteConvertUtility.toBytes(stockCode, 15);
			return ArrayUtility.addAll(stockCodeBytes);
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

	public class DataFamily extends HBaseColumnFamily {
		private DataFamily(MonthlyOperatingIncome entity) {
			super(entity);
		}

		public DataValue getLatestValue(int year, int month) {
			DataQualifier qual = this.new DataQualifier(year, month);
			return (DataValue) super.getLatestValue(qual);
		}

		public void add(int year, int month, Date date,
				BigInteger currentMonth, BigInteger currentMonthOfLastYear,
				BigInteger differentAmount, BigDecimal differentPercent,
				BigInteger cumulativeAmountOfThisYear,
				BigInteger cumulativeAmountOfLastYear,
				BigInteger cumulativeDifferentAmount,
				BigDecimal cumulativeDifferentPercent) {
			HBaseColumnQualifier qualifier = this.new DataQualifier(year, month);
			DataValue val = this.new DataValue(currentMonth,
					currentMonthOfLastYear, differentAmount, differentPercent,
					cumulativeAmountOfThisYear, cumulativeAmountOfLastYear,
					cumulativeDifferentAmount, cumulativeDifferentPercent);
			add(qualifier, date, val);
		}

		public class DataQualifier extends HBaseColumnQualifier {
			private static final int YEAR_LENGTH = 4;
			private static final int MONTH_LENGTH = 2;
			private static final int YEAR_BEGIN_INDEX = 0;
			private static final int YEAR_END_INDEX = YEAR_BEGIN_INDEX
					+ YEAR_LENGTH;
			private static final int MONTH_BEGIN_INDEX = YEAR_END_INDEX + 1;
			private static final int MONTH_END_INDEX = MONTH_BEGIN_INDEX
					+ MONTH_LENGTH;
			private int year;
			private int month;

			public DataQualifier() {
				super();
			}

			public DataQualifier(int year, int month) {
				super();
				this.year = year;
				this.month = month;
			}

			public DataQualifier(byte[] bytes) {
				super();
				fromBytes(bytes);
			}

			@Override
			public byte[] toBytes() {
				byte[] yearBytes = ByteConvertUtility.toBytes(year, 4);
				byte[] monthBytes = ByteConvertUtility.toBytes(month, 2);
				return ArrayUtility.addAll(yearBytes, SPACE, monthBytes);
			}

			@Override
			public void fromBytes(byte[] bytes) {
				this.year = ByteConvertUtility.getIntFromBytes(bytes,
						YEAR_BEGIN_INDEX, YEAR_END_INDEX);
				this.month = ByteConvertUtility.getIntFromBytes(bytes,
						MONTH_BEGIN_INDEX, MONTH_END_INDEX);
			}

			public int getYear() {
				return year;
			}

			public void setYear(int year) {
				this.year = year;
			}

			public int getMonth() {
				return month;
			}

			public void setMonth(int month) {
				this.month = month;
			}
		}

		public class DataValue extends HBaseValue {
			private static final int CURRENT_MONTH_LENGTH = 15;
			private static final int CURRENT_MONTH_OF_LAST_YEAR_LENGTH = 15;
			private static final int DIFFERENT_AMOUNT_LENGTH = 15;
			private static final int DIFFERENT_PERCENT_LENGTH = 10;
			private static final int CUMULATIVE_AMOUNT_OF_THIS_YEAR_LENGTH = 15;
			private static final int CUMULATIVE_AMOUNT_OF_LAST_YEAR_LENGTH = 15;
			private static final int CUMULATIVE_DIFFERENT_AMOUNT_LENGTH = 15;
			private static final int CUMULATIVE_DIFFERENT_PERCENT_LENGTH = 10;
			private static final int CURRENT_MONTH_BEGIN_INDEX = 0;
			private static final int CURRENT_MONTH_END_INDEX = CURRENT_MONTH_BEGIN_INDEX
					+ CURRENT_MONTH_LENGTH;
			private static final int CURRENT_MONTH_OF_LAST_YEAR_BEGIN_INDEX = CURRENT_MONTH_END_INDEX + 1;
			private static final int CURRENT_MONTH_OF_LAST_YEAR_END_INDEX = CURRENT_MONTH_OF_LAST_YEAR_BEGIN_INDEX
					+ CURRENT_MONTH_OF_LAST_YEAR_LENGTH;
			private static final int DIFFERENT_AMOUNT_BEGIN_INDEX = CURRENT_MONTH_OF_LAST_YEAR_END_INDEX + 1;
			private static final int DIFFERENT_AMOUNT_END_INDEX = DIFFERENT_AMOUNT_BEGIN_INDEX
					+ DIFFERENT_AMOUNT_LENGTH;
			private static final int DIFFERENT_PERCENT_BEGIN_INDEX = DIFFERENT_AMOUNT_END_INDEX + 1;
			private static final int DIFFERENT_PERCENT_END_INDEX = DIFFERENT_PERCENT_BEGIN_INDEX
					+ DIFFERENT_PERCENT_LENGTH;
			private static final int CUMULATIVE_AMOUNT_OF_THIS_YEAR_BEGIN_INDEX = DIFFERENT_PERCENT_END_INDEX + 1;
			private static final int CUMULATIVE_AMOUNT_OF_THIS_YEAR_END_INDEX = CUMULATIVE_AMOUNT_OF_THIS_YEAR_BEGIN_INDEX
					+ CUMULATIVE_AMOUNT_OF_THIS_YEAR_LENGTH;
			private static final int CUMULATIVE_AMOUNT_OF_LAST_YEAR_BEGIN_INDEX = CUMULATIVE_AMOUNT_OF_THIS_YEAR_END_INDEX + 1;
			private static final int CUMULATIVE_AMOUNT_OF_LAST_YEAR_END_INDEX = CUMULATIVE_AMOUNT_OF_LAST_YEAR_BEGIN_INDEX
					+ CUMULATIVE_AMOUNT_OF_LAST_YEAR_LENGTH;
			private static final int CUMULATIVE_DIFFERENT_AMOUNT_BEGIN_INDEX = CUMULATIVE_AMOUNT_OF_LAST_YEAR_END_INDEX + 1;
			private static final int CUMULATIVE_DIFFERENT_AMOUNT_END_INDEX = CUMULATIVE_DIFFERENT_AMOUNT_BEGIN_INDEX
					+ CUMULATIVE_DIFFERENT_AMOUNT_LENGTH;
			private static final int CUMULATIVE_DIFFERENT_PERCENT_BEGIN_INDEX = CUMULATIVE_DIFFERENT_AMOUNT_END_INDEX + 1;
			private static final int CUMULATIVE_DIFFERENT_PERCENT_END_INDEX = CUMULATIVE_DIFFERENT_PERCENT_BEGIN_INDEX
					+ CUMULATIVE_DIFFERENT_PERCENT_LENGTH;
			private BigInteger currentMonth;
			private BigInteger currentMonthOfLastYear;
			private BigInteger differentAmount;
			private BigDecimal differentPercent;
			private BigInteger cumulativeAmountOfThisYear;
			private BigInteger cumulativeAmountOfLastYear;
			private BigInteger cumulativeDifferentAmount;
			private BigDecimal cumulativeDifferentPercent;

			public DataValue() {
				super();
			}

			public DataValue(BigInteger currentMonth,
					BigInteger currentMonthOfLastYear,
					BigInteger differentAmount, BigDecimal differentPercent,
					BigInteger cumulativeAmountOfThisYear,
					BigInteger cumulativeAmountOfLastYear,
					BigInteger cumulativeDifferentAmount,
					BigDecimal cumulativeDifferentPercent) {
				super();
				this.currentMonth = currentMonth;
				this.currentMonthOfLastYear = currentMonthOfLastYear;
				this.differentAmount = differentAmount;
				this.differentPercent = differentPercent;
				this.cumulativeAmountOfThisYear = cumulativeAmountOfThisYear;
				this.cumulativeAmountOfLastYear = cumulativeAmountOfLastYear;
				this.cumulativeDifferentAmount = cumulativeDifferentAmount;
				this.cumulativeDifferentPercent = cumulativeDifferentPercent;
			}

			public DataValue(byte[] bytes) {
				super();
				fromBytes(bytes);
			}

			@Override
			public byte[] toBytes() {
				byte[] currentMonthBytes = ByteConvertUtility.toBytes(
						currentMonth, 15);
				byte[] currentMonthOfLastYearBytes = ByteConvertUtility
						.toBytes(currentMonthOfLastYear, 15);
				byte[] differentAmountBytes = ByteConvertUtility.toBytes(
						differentAmount, 15);
				byte[] differentPercentBytes = ByteConvertUtility.toBytes(
						differentPercent, 10);
				byte[] cumulativeAmountOfThisYearBytes = ByteConvertUtility
						.toBytes(cumulativeAmountOfThisYear, 15);
				byte[] cumulativeAmountOfLastYearBytes = ByteConvertUtility
						.toBytes(cumulativeAmountOfLastYear, 15);
				byte[] cumulativeDifferentAmountBytes = ByteConvertUtility
						.toBytes(cumulativeDifferentAmount, 15);
				byte[] cumulativeDifferentPercentBytes = ByteConvertUtility
						.toBytes(cumulativeDifferentPercent, 10);
				return ArrayUtility.addAll(currentMonthBytes, SPACE,
						currentMonthOfLastYearBytes, SPACE,
						differentAmountBytes, SPACE, differentPercentBytes,
						SPACE, cumulativeAmountOfThisYearBytes, SPACE,
						cumulativeAmountOfLastYearBytes, SPACE,
						cumulativeDifferentAmountBytes, SPACE,
						cumulativeDifferentPercentBytes);
			}

			@Override
			public void fromBytes(byte[] bytes) {
				this.currentMonth = ByteConvertUtility.getBigIntegerFromBytes(
						bytes, CURRENT_MONTH_BEGIN_INDEX,
						CURRENT_MONTH_END_INDEX);
				this.currentMonthOfLastYear = ByteConvertUtility
						.getBigIntegerFromBytes(bytes,
								CURRENT_MONTH_OF_LAST_YEAR_BEGIN_INDEX,
								CURRENT_MONTH_OF_LAST_YEAR_END_INDEX);
				this.differentAmount = ByteConvertUtility
						.getBigIntegerFromBytes(bytes,
								DIFFERENT_AMOUNT_BEGIN_INDEX,
								DIFFERENT_AMOUNT_END_INDEX);
				this.differentPercent = ByteConvertUtility
						.getBigDecimalFromBytes(bytes,
								DIFFERENT_PERCENT_BEGIN_INDEX,
								DIFFERENT_PERCENT_END_INDEX);
				this.cumulativeAmountOfThisYear = ByteConvertUtility
						.getBigIntegerFromBytes(bytes,
								CUMULATIVE_AMOUNT_OF_THIS_YEAR_BEGIN_INDEX,
								CUMULATIVE_AMOUNT_OF_THIS_YEAR_END_INDEX);
				this.cumulativeAmountOfLastYear = ByteConvertUtility
						.getBigIntegerFromBytes(bytes,
								CUMULATIVE_AMOUNT_OF_LAST_YEAR_BEGIN_INDEX,
								CUMULATIVE_AMOUNT_OF_LAST_YEAR_END_INDEX);
				this.cumulativeDifferentAmount = ByteConvertUtility
						.getBigIntegerFromBytes(bytes,
								CUMULATIVE_DIFFERENT_AMOUNT_BEGIN_INDEX,
								CUMULATIVE_DIFFERENT_AMOUNT_END_INDEX);
				this.cumulativeDifferentPercent = ByteConvertUtility
						.getBigDecimalFromBytes(bytes,
								CUMULATIVE_DIFFERENT_PERCENT_BEGIN_INDEX,
								CUMULATIVE_DIFFERENT_PERCENT_END_INDEX);
			}

			public BigInteger getCurrentMonth() {
				return currentMonth;
			}

			public void setCurrentMonth(BigInteger currentMonth) {
				this.currentMonth = currentMonth;
			}

			public BigInteger getCurrentMonthOfLastYear() {
				return currentMonthOfLastYear;
			}

			public void setCurrentMonthOfLastYear(
					BigInteger currentMonthOfLastYear) {
				this.currentMonthOfLastYear = currentMonthOfLastYear;
			}

			public BigInteger getDifferentAmount() {
				return differentAmount;
			}

			public void setDifferentAmount(BigInteger differentAmount) {
				this.differentAmount = differentAmount;
			}

			public BigDecimal getDifferentPercent() {
				return differentPercent;
			}

			public void setDifferentPercent(BigDecimal differentPercent) {
				this.differentPercent = differentPercent;
			}

			public BigInteger getCumulativeAmountOfThisYear() {
				return cumulativeAmountOfThisYear;
			}

			public void setCumulativeAmountOfThisYear(
					BigInteger cumulativeAmountOfThisYear) {
				this.cumulativeAmountOfThisYear = cumulativeAmountOfThisYear;
			}

			public BigInteger getCumulativeAmountOfLastYear() {
				return cumulativeAmountOfLastYear;
			}

			public void setCumulativeAmountOfLastYear(
					BigInteger cumulativeAmountOfLastYear) {
				this.cumulativeAmountOfLastYear = cumulativeAmountOfLastYear;
			}

			public BigInteger getCumulativeDifferentAmount() {
				return cumulativeDifferentAmount;
			}

			public void setCumulativeDifferentAmount(
					BigInteger cumulativeDifferentAmount) {
				this.cumulativeDifferentAmount = cumulativeDifferentAmount;
			}

			public BigDecimal getCumulativeDifferentPercent() {
				return cumulativeDifferentPercent;
			}

			public void setCumulativeDifferentPercent(
					BigDecimal cumulativeDifferentPercent) {
				this.cumulativeDifferentPercent = cumulativeDifferentPercent;
			}
		}

		@Override
		protected HBaseColumnQualifier generateColumnQualifier(byte[] bytes) {
			return this.new DataQualifier(bytes);
		}

		@Override
		protected HBaseValue generateValue(byte[] bytes) {
			return this.new DataValue(bytes);
		}
	}

	public class CommentFamily extends HBaseColumnFamily {
		private CommentFamily(MonthlyOperatingIncome entity) {
			super(entity);
		}

		public CommentValue getLatestValue(int year, int month) {
			CommentQualifier qual = this.new CommentQualifier(year, month);
			return (CommentValue) super.getLatestValue(qual);
		}

		public void add(int year, int month, Date date, String comment) {
			HBaseColumnQualifier qualifier = this.new CommentQualifier(year,
					month);
			CommentValue val = this.new CommentValue(comment);
			add(qualifier, date, val);
		}

		public class CommentQualifier extends HBaseColumnQualifier {
			private static final int YEAR_LENGTH = 4;
			private static final int MONTH_LENGTH = 2;
			private static final int YEAR_BEGIN_INDEX = 0;
			private static final int YEAR_END_INDEX = YEAR_BEGIN_INDEX
					+ YEAR_LENGTH;
			private static final int MONTH_BEGIN_INDEX = YEAR_END_INDEX + 1;
			private static final int MONTH_END_INDEX = MONTH_BEGIN_INDEX
					+ MONTH_LENGTH;
			private int year;
			private int month;

			public CommentQualifier() {
				super();
			}

			public CommentQualifier(int year, int month) {
				super();
				this.year = year;
				this.month = month;
			}

			public CommentQualifier(byte[] bytes) {
				super();
				fromBytes(bytes);
			}

			@Override
			public byte[] toBytes() {
				byte[] yearBytes = ByteConvertUtility.toBytes(year, 4);
				byte[] monthBytes = ByteConvertUtility.toBytes(month, 2);
				return ArrayUtility.addAll(yearBytes, SPACE, monthBytes);
			}

			@Override
			public void fromBytes(byte[] bytes) {
				this.year = ByteConvertUtility.getIntFromBytes(bytes,
						YEAR_BEGIN_INDEX, YEAR_END_INDEX);
				this.month = ByteConvertUtility.getIntFromBytes(bytes,
						MONTH_BEGIN_INDEX, MONTH_END_INDEX);
			}

			public int getYear() {
				return year;
			}

			public void setYear(int year) {
				this.year = year;
			}

			public int getMonth() {
				return month;
			}

			public void setMonth(int month) {
				this.month = month;
			}
		}

		public class CommentValue extends HBaseValue {
			private String comment;

			public CommentValue() {
				super();
			}

			public CommentValue(String comment) {
				super();
				this.comment = comment;
			}

			public CommentValue(byte[] bytes) {
				super();
				fromBytes(bytes);
			}

			@Override
			public byte[] toBytes() {
				byte[] commentBytes = ByteConvertUtility.toBytes(comment);
				return ArrayUtility.addAll(commentBytes);
			}

			@Override
			public void fromBytes(byte[] bytes) {
				this.comment = ByteConvertUtility.getStringFromBytes(bytes);
			}

			public String getComment() {
				return comment;
			}

			public void setComment(String comment) {
				this.comment = comment;
			}
		}

		@Override
		protected HBaseColumnQualifier generateColumnQualifier(byte[] bytes) {
			return this.new CommentQualifier(bytes);
		}

		@Override
		protected HBaseValue generateValue(byte[] bytes) {
			return this.new CommentValue(bytes);
		}
	}
}
