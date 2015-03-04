package idv.hsiehpinghan.stockdao.repository;

import idv.hsiehpinghan.datetimeutility.utility.DateUtility;
import idv.hsiehpinghan.stockdao.entity.StockClosingCondition;
import idv.hsiehpinghan.stockdao.entity.StockClosingCondition.ClosingConditionFamily;
import idv.hsiehpinghan.stockdao.suit.TestngSuitSetting;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

import org.springframework.context.ApplicationContext;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class StockClosingConditionRepositoryTest {
	private Date ver = DateUtility.getDate(2015, 2, 3);
	private BigDecimal closingPrice = new BigDecimal("13.13");
	private BigInteger transactionAmount = new BigInteger("14");
	private String stockCode = "stockCode";
	private BigDecimal highestPrice = new BigDecimal("16.16");
	private BigDecimal change = new BigDecimal("17.17");
	private BigInteger moneyAmount = new BigInteger("18");
	private BigDecimal finalPurchasePrice = new BigDecimal("19.19");
	private BigInteger stockAmount = new BigInteger("20");
	private BigDecimal finalSellingPrice = new BigDecimal("21.21");
	private BigDecimal openingPrice = new BigDecimal("22.22");
	private Date date = DateUtility.getDate(2015, 2, 3);
	private BigDecimal lowestPrice = new BigDecimal("24.24");
	private StockClosingConditionRepository repository;

	@BeforeClass
	public void beforeClass() throws Exception {
		ApplicationContext applicationContext = TestngSuitSetting
				.getApplicationContext();
		repository = applicationContext
				.getBean(StockClosingConditionRepository.class);
	}

	@Test
	public void put() throws Exception {
		StockClosingCondition entity = repository.generateEntity(stockCode,
				date);
		generateClosingConditionFamilyContent(entity);
		repository.put(entity);
		Assert.assertTrue(repository.exists(entity.getRowKey()));
	}

	@Test(dependsOnMethods = { "put" })
	public void get() throws Exception {
		StockClosingCondition entity = repository.get(stockCode, date);
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
