package idv.hsiehpinghan.stockdao.entity;

import idv.hsiehpinghan.datetimeutility.utility.DateUtility;
import idv.hsiehpinghan.stockdao.entity.StockClosingCondition.ClosingConditionFamily;
import idv.hsiehpinghan.stockdao.entity.StockClosingCondition.RowKey;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

import org.testng.Assert;
import org.testng.annotations.Test;

public class StockClosingConditionTest {
	private Date ver = DateUtility.getDate(2015, 2, 3);
	private BigDecimal closingPrice = new BigDecimal("1.1");
	private BigInteger transactionAmount = new BigInteger("2");
	private String stockCode = "stockCode";
	private BigDecimal highestPrice = new BigDecimal("4.4");
	private BigDecimal change = new BigDecimal("5.5");
	private BigInteger moneyAmount = new BigInteger("6");
	private BigDecimal finalPurchasePrice = new BigDecimal("7.7");
	private BigInteger stockAmount = new BigInteger("8");
	private BigDecimal finalSellingPrice = new BigDecimal("9.9");
	private BigDecimal openingPrice = new BigDecimal("10.10");
	private Date date = DateUtility.getDate(2015, 2, 3);
	private BigDecimal lowestPrice = new BigDecimal("12.12");

	@Test
	public void bytesConvert() {
		StockClosingCondition entity = new StockClosingCondition();
		testRowKey(entity);
		testClosingConditionFamily(entity);
	}

	private void testRowKey(StockClosingCondition entity) {
		RowKey key = entity.new RowKey(stockCode, date, entity);
		Assert.assertEquals(stockCode, key.getStockCode());
		Assert.assertEquals(date, key.getDate());
	}

	private void testClosingConditionFamily(StockClosingCondition entity) {
		generateClosingConditionFamilyContent(entity);
		assertClosingConditionFamily(entity);
	}

	private void generateClosingConditionFamilyContent(
			StockClosingCondition entity) {
		ClosingConditionFamily fam = entity.getClosingConditionFamily();
		fam.setOpeningPrice(ver, openingPrice);
		fam.setClosingPrice(ver, closingPrice);
		fam.setChange(ver, change);
		fam.setHighestPrice(ver, highestPrice);
		fam.setLowestPrice(ver, lowestPrice);
		fam.setFinalPurchasePrice(ver, finalPurchasePrice);
		fam.setFinalSellingPrice(ver, finalSellingPrice);
		fam.setStockAmount(ver, stockAmount);
		fam.setMoneyAmount(ver, moneyAmount);
		fam.setTransactionAmount(ver, transactionAmount);
	}

	private void assertClosingConditionFamily(StockClosingCondition entity) {
		ClosingConditionFamily fam = entity.getClosingConditionFamily();
		Assert.assertEquals(openingPrice, fam.getOpeningPrice());
		Assert.assertEquals(closingPrice, fam.getClosingPrice());
		Assert.assertEquals(change, fam.getChange());
		Assert.assertEquals(highestPrice, fam.getHighestPrice());
		Assert.assertEquals(lowestPrice, fam.getLowestPrice());
		Assert.assertEquals(finalPurchasePrice, fam.getFinalPurchasePrice());
		Assert.assertEquals(finalSellingPrice, fam.getFinalSellingPrice());
		Assert.assertEquals(stockAmount, fam.getStockAmount());
		Assert.assertEquals(moneyAmount, fam.getMoneyAmount());
		Assert.assertEquals(transactionAmount, fam.getTransactionAmount());
	}
}
