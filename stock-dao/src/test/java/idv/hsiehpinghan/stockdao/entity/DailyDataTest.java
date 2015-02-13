package idv.hsiehpinghan.stockdao.entity;

import idv.hsiehpinghan.stockdao.entity.DailyData.ClosingConditionFamily;
import idv.hsiehpinghan.stockdao.entity.DailyData.RowKey;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

import org.apache.commons.lang3.time.DateUtils;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class DailyDataTest {
	private Date ver;
	private String stockCode = "2330";
	private Date date;
	private BigDecimal change = new BigDecimal("1.11");
	private BigDecimal closingPrice = new BigDecimal("2.22");
	private BigDecimal finalPurchasePrice = new BigDecimal("3.33");
	private BigDecimal finalSellingPrice = new BigDecimal("4.44");
	private BigDecimal highestPrice = new BigDecimal("5.55");
	private BigDecimal lowestPrice = new BigDecimal("6.66");
	private BigInteger moneyAmount = new BigInteger("7");
	private BigDecimal openingPrice = new BigDecimal("8");
	private BigInteger stockAmount = new BigInteger("9");
	private BigInteger transactionAmount = new BigInteger("10");

	@BeforeClass
	public void beforeClass() throws Exception {
		ver = DateUtils.parseDate("2015/01/01", "yyyy/MM/dd");
		date = DateUtils.parseDate("2015/05/22", "yyyy/MM/dd");
	}

	@Test
	public void bytesConvert() {
		DailyData entity = new DailyData();
		testRowKey(entity);
		testClosingConditionFamily(entity);
	}

	private void testClosingConditionFamily(DailyData entity) {
		generateClosingConditionFamilyContent(entity);
		assertClosingConditionFamily(entity);
	}

	private void generateClosingConditionFamilyContent(DailyData entity) {
		entity.getClosingConditionFamily();
		ClosingConditionFamily fam = entity.getClosingConditionFamily();
		fam.setChange(ver, change);
		fam.setClosingPrice(ver, closingPrice);
		fam.setFinalPurchasePrice(ver, finalPurchasePrice);
		fam.setFinalSellingPrice(ver, finalSellingPrice);
		fam.setHighestPrice(ver, highestPrice);
		fam.setLowestPrice(ver, lowestPrice);
		fam.setMoneyAmount(ver, moneyAmount);
		fam.setOpeningPrice(ver, openingPrice);
		fam.setStockAmount(ver, stockAmount);
		fam.setTransactionAmount(ver, transactionAmount);

	}

	private void assertClosingConditionFamily(DailyData entity) {
		ClosingConditionFamily fam = entity.getClosingConditionFamily();
		Assert.assertEquals(change, fam.getChange());
		Assert.assertEquals(closingPrice, fam.getClosingPrice());
		Assert.assertEquals(finalPurchasePrice, fam.getFinalPurchasePrice());
		Assert.assertEquals(finalSellingPrice, fam.getFinalSellingPrice());
		Assert.assertEquals(highestPrice, fam.getHighestPrice());
		Assert.assertEquals(lowestPrice, fam.getLowestPrice());
		Assert.assertEquals(moneyAmount, fam.getMoneyAmount());
		Assert.assertEquals(openingPrice, fam.getOpeningPrice());
		Assert.assertEquals(stockAmount, fam.getStockAmount());
		Assert.assertEquals(transactionAmount, fam.getTransactionAmount());

	}

	private void testRowKey(DailyData entity) {
		RowKey key = entity.new RowKey(stockCode, date, entity);
		Assert.assertEquals(stockCode, key.getStockCode());
		Assert.assertEquals(date, key.getDate());
	}
}
