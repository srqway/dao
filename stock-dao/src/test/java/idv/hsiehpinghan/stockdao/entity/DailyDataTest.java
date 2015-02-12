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
	private BigDecimal Change = new BigDecimal("1.11");
	private BigDecimal ClosingPrice = new BigDecimal("2.22");
	private BigDecimal FinalPurchasePrice = new BigDecimal("3.33");
	private BigDecimal FinalSellingPrice = new BigDecimal("4.44");
	private BigDecimal HighestPrice = new BigDecimal("5.55");
	private BigDecimal LowestPrice = new BigDecimal("6.66");
	private BigInteger MoneyAmount = new BigInteger("7");
	private BigDecimal OpeningPrice = new BigDecimal("8");
	private BigInteger StockAmount = new BigInteger("9");
	private BigInteger TransactionAmount = new BigInteger("10");

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
		fam.setChange(ver, Change);
		fam.setClosingPrice(ver, ClosingPrice);
		fam.setFinalPurchasePrice(ver, FinalPurchasePrice);
		fam.setFinalSellingPrice(ver, FinalSellingPrice);
		fam.setHighestPrice(ver, HighestPrice);
		fam.setLowestPrice(ver, LowestPrice);
		fam.setMoneyAmount(ver, MoneyAmount);
		fam.setOpeningPrice(ver, OpeningPrice);
		fam.setStockAmount(ver, StockAmount);
		fam.setTransactionAmount(ver, TransactionAmount);

	}

	private void assertClosingConditionFamily(DailyData entity) {
		ClosingConditionFamily fam = entity.getClosingConditionFamily();
		Assert.assertEquals(Change, fam.getChange());
		Assert.assertEquals(ClosingPrice, fam.getClosingPrice());
		Assert.assertEquals(FinalPurchasePrice, fam.getFinalPurchasePrice());
		Assert.assertEquals(FinalSellingPrice, fam.getFinalSellingPrice());
		Assert.assertEquals(HighestPrice, fam.getHighestPrice());
		Assert.assertEquals(LowestPrice, fam.getLowestPrice());
		Assert.assertEquals(MoneyAmount, fam.getMoneyAmount());
		Assert.assertEquals(OpeningPrice, fam.getOpeningPrice());
		Assert.assertEquals(StockAmount, fam.getStockAmount());
		Assert.assertEquals(TransactionAmount, fam.getTransactionAmount());

	}

	private void testRowKey(DailyData entity) {
		RowKey key = entity.new RowKey(stockCode, date, entity);
		Assert.assertEquals(stockCode, key.getStockCode());
		Assert.assertEquals(date, key.getDate());
	}
}
