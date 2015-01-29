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
		private String stockCode;

		public RowKey(StockClosingCondition entity) {
			super(entity);
		}

		public RowKey(String stockCode, StockClosingCondition entity) {
			super(entity);
			this.stockCode = stockCode;
		}

		public RowKey(byte[] rowKey, StockClosingCondition entity) {
			super(entity);
			fromBytes(rowKey);
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

	public class PriceFamily extends HBaseColumnFamily {
		private PriceFamily(StockClosingCondition entity) {
			super(entity);
		}

		public PriceValue getLatestValue(Date date) {
			PriceQualifier qual = this.new PriceQualifier(date);
			return (PriceValue) super.getLatestValue(qual);
		}

		public void add(Date date, Date version, BigDecimal openingPrice,
				BigDecimal closingPrice, BigDecimal change,
				BigDecimal highestPrice, BigDecimal lowestPrice,
				BigDecimal finalPurchasePrice, BigDecimal finalSellingPrice) {
			HBaseColumnQualifier qual = this.new PriceQualifier(date);
			add(qual, version, openingPrice, closingPrice, change,
					highestPrice, lowestPrice, finalPurchasePrice,
					finalSellingPrice);
		}

		private void add(HBaseColumnQualifier qualifier, Date date,
				BigDecimal openingPrice, BigDecimal closingPrice,
				BigDecimal change, BigDecimal highestPrice,
				BigDecimal lowestPrice, BigDecimal finalPurchasePrice,
				BigDecimal finalSellingPrice) {
			PriceValue val = this.new PriceValue(openingPrice, closingPrice,
					change, highestPrice, lowestPrice, finalPurchasePrice,
					finalSellingPrice);
			add(qualifier, date, val);
		}

		public class PriceQualifier extends HBaseColumnQualifier {
			private Date date;

			public PriceQualifier() {
				super();
			}

			public PriceQualifier(Date date) {
				super();
				this.date = date;
			}

			public PriceQualifier(byte[] bytes) {
				super();
				fromBytes(bytes);
			}

			@Override
			public byte[] toBytes() {
				return ByteConvertUtility.toBytes(date);
			}

			@Override
			public void fromBytes(byte[] bytes) {
				try {
					this.date = ByteConvertUtility.getDateFromBytes(bytes);
				} catch (ParseException e) {
					throw new RuntimeException(e);
				}
			}

			public Date getDate() {
				return date;
			}

			public void setDate(Date date) {
				this.date = date;
			}
		}

		public class PriceValue extends HBaseValue {
			private static final int OPENING_PRICE_LENGTH = 5;
			private static final int CLOSING_PRICE_LENGTH = 5;
			private static final int CHANGE_LENGTH = 5;
			private static final int HIGHEST_PRICE_LENGTH = 5;
			private static final int LOWEST_PRICE_LENGTH = 5;
			private static final int FINAL_PURCHASE_PRICE_LENGTH = 5;
			private static final int FINAL_SELLING_PRICE_LENGTH = 5;

			private static final int OPENING_PRICE_BEGIN_INDEX = 0;
			private static final int OPENING_PRICE_END_INDEX = OPENING_PRICE_BEGIN_INDEX
					+ OPENING_PRICE_LENGTH;
			private static final int CLOSING_PRICE_BEGIN_INDEX = OPENING_PRICE_END_INDEX + 1;
			private static final int CLOSING_PRICE_END_INDEX = CLOSING_PRICE_BEGIN_INDEX
					+ CLOSING_PRICE_LENGTH;
			private static final int CHANGE_BEGIN_INDEX = CLOSING_PRICE_END_INDEX + 1;
			private static final int CHANGE_END_INDEX = CHANGE_BEGIN_INDEX
					+ CHANGE_LENGTH;
			private static final int HIGHEST_PRICE_BEGIN_INDEX = CHANGE_END_INDEX + 1;
			private static final int HIGHEST_PRICE_END_INDEX = HIGHEST_PRICE_BEGIN_INDEX
					+ HIGHEST_PRICE_LENGTH;
			private static final int LOWEST_PRICE_BEGIN_INDEX = HIGHEST_PRICE_END_INDEX + 1;
			private static final int LOWEST_PRICE_END_INDEX = LOWEST_PRICE_BEGIN_INDEX
					+ LOWEST_PRICE_LENGTH;
			private static final int FINAL_PURCHASE_PRICE_BEGIN_INDEX = LOWEST_PRICE_END_INDEX + 1;
			private static final int FINAL_PURCHASE_PRICE_END_INDEX = FINAL_PURCHASE_PRICE_BEGIN_INDEX
					+ FINAL_PURCHASE_PRICE_LENGTH;
			private static final int FINAL_SELLING_PRICE_BEGIN_INDEX = FINAL_PURCHASE_PRICE_END_INDEX + 1;
			private static final int FINAL_SELLING_PRICE_END_INDEX = FINAL_SELLING_PRICE_BEGIN_INDEX
					+ FINAL_SELLING_PRICE_LENGTH;

			private BigDecimal openingPrice;
			private BigDecimal closingPrice;
			private BigDecimal change;
			private BigDecimal highestPrice;
			private BigDecimal lowestPrice;
			private BigDecimal finalPurchasePrice;
			private BigDecimal finalSellingPrice;

			public PriceValue() {
				super();
			}

			public PriceValue(BigDecimal openingPrice, BigDecimal closingPrice,
					BigDecimal change, BigDecimal highestPrice,
					BigDecimal lowestPrice, BigDecimal finalPurchasePrice,
					BigDecimal finalSellingPrice) {
				super();
				this.openingPrice = openingPrice;
				this.closingPrice = closingPrice;
				this.change = change;
				this.highestPrice = change;
				this.lowestPrice = change;
				this.finalPurchasePrice = change;
				this.finalSellingPrice = change;
			}

			public PriceValue(byte[] bytes) {
				super();
				fromBytes(bytes);
			}

			public BigDecimal getOpeningPrice() {
				return openingPrice;
			}

			public void setOpeningPrice(BigDecimal openingPrice) {
				this.openingPrice = openingPrice;
			}

			public BigDecimal getClosingPrice() {
				return closingPrice;
			}

			public void setClosingPrice(BigDecimal closingPrice) {
				this.closingPrice = closingPrice;
			}

			public BigDecimal getChange() {
				return change;
			}

			public void setChange(BigDecimal change) {
				this.change = change;
			}

			public BigDecimal getHighestPrice() {
				return highestPrice;
			}

			public void setHighestPrice(BigDecimal highestPrice) {
				this.highestPrice = highestPrice;
			}

			public BigDecimal getLowestPrice() {
				return lowestPrice;
			}

			public void setLowestPrice(BigDecimal lowestPrice) {
				this.lowestPrice = lowestPrice;
			}

			public BigDecimal getFinalPurchasePrice() {
				return finalPurchasePrice;
			}

			public void setFinalPurchasePrice(BigDecimal finalPurchasePrice) {
				this.finalPurchasePrice = finalPurchasePrice;
			}

			public BigDecimal getFinalSellingPrice() {
				return finalSellingPrice;
			}

			public void setFinalSellingPrice(BigDecimal finalSellingPrice) {
				this.finalSellingPrice = finalSellingPrice;
			}

			@Override
			public byte[] toBytes() {
				byte[] openingPriceBytes = ByteConvertUtility.toBytes(
						openingPrice, OPENING_PRICE_LENGTH);
				byte[] closingPriceBytes = ByteConvertUtility.toBytes(
						closingPrice, CLOSING_PRICE_LENGTH);
				byte[] changeBytes = ByteConvertUtility.toBytes(change,
						CHANGE_LENGTH);
				byte[] highestPriceBytes = ByteConvertUtility.toBytes(
						highestPrice, HIGHEST_PRICE_LENGTH);
				byte[] lowestPriceBytes = ByteConvertUtility.toBytes(
						lowestPrice, LOWEST_PRICE_LENGTH);
				byte[] finalPurchasePriceBytes = ByteConvertUtility.toBytes(
						finalPurchasePrice, FINAL_PURCHASE_PRICE_LENGTH);
				byte[] finalSellingPriceBytes = ByteConvertUtility.toBytes(
						finalSellingPrice, FINAL_SELLING_PRICE_LENGTH);
				return ArrayUtility.addAll(openingPriceBytes, SPACE,
						closingPriceBytes, SPACE, changeBytes, SPACE,
						highestPriceBytes, SPACE, lowestPriceBytes, SPACE,
						finalPurchasePriceBytes, SPACE, finalSellingPriceBytes);
			}

			@Override
			public void fromBytes(byte[] bytes) {
				this.openingPrice = ByteConvertUtility.getBigDecimalFromBytes(
						bytes, OPENING_PRICE_BEGIN_INDEX,
						OPENING_PRICE_END_INDEX);
				this.closingPrice = ByteConvertUtility.getBigDecimalFromBytes(
						bytes, CLOSING_PRICE_BEGIN_INDEX,
						CLOSING_PRICE_END_INDEX);
				this.change = ByteConvertUtility.getBigDecimalFromBytes(bytes,
						CHANGE_BEGIN_INDEX, CHANGE_END_INDEX);
				this.highestPrice = ByteConvertUtility.getBigDecimalFromBytes(
						bytes, HIGHEST_PRICE_BEGIN_INDEX,
						HIGHEST_PRICE_END_INDEX);
				this.lowestPrice = ByteConvertUtility
						.getBigDecimalFromBytes(bytes,
								LOWEST_PRICE_BEGIN_INDEX,
								LOWEST_PRICE_END_INDEX);
				this.finalPurchasePrice = ByteConvertUtility
						.getBigDecimalFromBytes(bytes,
								FINAL_PURCHASE_PRICE_BEGIN_INDEX,
								FINAL_PURCHASE_PRICE_END_INDEX);
				this.finalSellingPrice = ByteConvertUtility
						.getBigDecimalFromBytes(bytes,
								FINAL_SELLING_PRICE_BEGIN_INDEX,
								FINAL_SELLING_PRICE_END_INDEX);
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

		public VolumeValue getLatestValue(Date date) {
			VolumeQualifier qual = this.new VolumeQualifier(date);
			return (VolumeValue) super.getLatestValue(qual);
		}

		public void add(Date date, Date version, Integer stockAmount,
				Integer moneyAmount, Integer transactionAmount) {
			HBaseColumnQualifier qual = this.new VolumeQualifier(date);
			add(qual, date, stockAmount, moneyAmount, transactionAmount);
		}

		private void add(HBaseColumnQualifier qualifier, Date date,
				Integer stockAmount, Integer moneyAmount,
				Integer transactionAmount) {
			VolumeValue val = this.new VolumeValue(stockAmount, moneyAmount,
					transactionAmount);
			add(qualifier, date, val);
		}

		public class VolumeQualifier extends HBaseColumnQualifier {
			private Date date;

			public VolumeQualifier() {
				super();
			}

			public VolumeQualifier(Date date) {
				super();
				this.date = date;
			}

			public VolumeQualifier(byte[] bytes) {
				super();
				fromBytes(bytes);
			}

			@Override
			public byte[] toBytes() {
				return ByteConvertUtility.toBytes(date);
			}

			@Override
			public void fromBytes(byte[] bytes) {
				try {
					this.date = ByteConvertUtility.getDateFromBytes(bytes);
				} catch (ParseException e) {
					throw new RuntimeException(e);
				}
			}

			public Date getDate() {
				return date;
			}

			public void setDate(Date date) {
				this.date = date;
			}
		}

		public class VolumeValue extends HBaseValue {
			private static final int STOCK_AMOUNT_LENGTH = 15;
			private static final int MONEY_AMOUNT_LENGTH = 15;
			private static final int TRANSACTION_AMOUNT_LENGTH = 15;

			private static final int STOCK_AMOUNT_BEGIN_INDEX = 0;
			private static final int STOCK_AMOUNT_END_INDEX = STOCK_AMOUNT_BEGIN_INDEX
					+ STOCK_AMOUNT_LENGTH;
			private static final int MONEY_AMOUNT_BEGIN_INDEX = STOCK_AMOUNT_END_INDEX + 1;
			private static final int MONEY_AMOUNT_END_INDEX = MONEY_AMOUNT_BEGIN_INDEX
					+ MONEY_AMOUNT_LENGTH;
			private static final int TRANSACTION_AMOUNT_BEGIN_INDEX = MONEY_AMOUNT_END_INDEX + 1;
			private static final int TRANSACTION_AMOUNT_END_INDEX = TRANSACTION_AMOUNT_BEGIN_INDEX
					+ TRANSACTION_AMOUNT_LENGTH;

			private Integer stockAmount;
			private Integer moneyAmount;
			private Integer transactionAmount;

			public VolumeValue() {
				super();
			}

			public VolumeValue(Integer stockAmount, Integer moneyAmount,
					Integer transactionAmount) {
				super();
				this.stockAmount = stockAmount;
				this.moneyAmount = moneyAmount;
				this.transactionAmount = transactionAmount;
			}

			public VolumeValue(byte[] bytes) {
				super();
				fromBytes(bytes);
			}

			public Integer getStockAmount() {
				return stockAmount;
			}

			public void setStockAmount(Integer stockAmount) {
				this.stockAmount = stockAmount;
			}

			public Integer getMoneyAmount() {
				return moneyAmount;
			}

			public void setMoneyAmount(Integer moneyAmount) {
				this.moneyAmount = moneyAmount;
			}

			public Integer getTransactionAmount() {
				return transactionAmount;
			}

			public void setTransactionAmount(Integer transactionAmount) {
				this.transactionAmount = transactionAmount;
			}

			@Override
			public byte[] toBytes() {
				byte[] stockAmountBytes = ByteConvertUtility.toBytes(
						stockAmount, STOCK_AMOUNT_LENGTH);
				byte[] moneyAmountBytes = ByteConvertUtility.toBytes(
						moneyAmount, MONEY_AMOUNT_LENGTH);
				byte[] transactionAmountBytes = ByteConvertUtility.toBytes(
						transactionAmount, TRANSACTION_AMOUNT_LENGTH);
				return ArrayUtility.addAll(stockAmountBytes, SPACE,
						moneyAmountBytes, SPACE, transactionAmountBytes);
			}

			@Override
			public void fromBytes(byte[] bytes) {
				this.stockAmount = ByteConvertUtility
						.getIntegerFromBytes(bytes, STOCK_AMOUNT_BEGIN_INDEX,
								STOCK_AMOUNT_END_INDEX);
				this.moneyAmount = ByteConvertUtility
						.getIntegerFromBytes(bytes, MONEY_AMOUNT_BEGIN_INDEX,
								MONEY_AMOUNT_END_INDEX);
				this.transactionAmount = ByteConvertUtility
						.getIntegerFromBytes(bytes,
								TRANSACTION_AMOUNT_BEGIN_INDEX,
								TRANSACTION_AMOUNT_END_INDEX);
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
