package idv.hsiehpinghan.stockdao.entity;

import idv.hsiehpinghan.datetimeutility.utility.DateUtility;
import idv.hsiehpinghan.stockdao.entity.MonthlyData.OperatingIncomeFamily;
import idv.hsiehpinghan.stockdao.entity.MonthlyData.RowKey;
import idv.hsiehpinghan.stockdao.enumeration.CurrencyType;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

import org.testng.Assert;
import org.testng.annotations.Test;

public class MonthlyDataTest {
	private Date ver = DateUtility.getDate(2015, 2, 3);
	private BigInteger differentAmount = new BigInteger("1");
	private String stockCode = "stockCode";
	private BigInteger cumulativeDifferentAmount = new BigInteger("3");
	private BigInteger currentMonth = new BigInteger("4");
	private CurrencyType currency = CurrencyType.CNY;
	private BigInteger cumulativeAmountOfLastYear = new BigInteger("6");
	private BigDecimal exchangeRateOfCurrentMonth = new BigDecimal("7.7");
	private BigDecimal cumulativeDifferentPercent = new BigDecimal("8.8");
	private BigInteger currentMonthOfLastYear = new BigInteger("9");
	private BigDecimal cumulativeExchangeRateOfThisYear = new BigDecimal(
			"10.10");
	private BigInteger cumulativeAmountOfThisYear = new BigInteger("11");
	private int month = 12;
	private int year = 13;
	private String comment = "comment";
	private BigDecimal differentPercent = new BigDecimal("15.15");

	@Test
	public void bytesConvert() {
		MonthlyData entity = new MonthlyData();
		testRowKey(entity);
		testOperatingIncomeFamily(entity);
	}

	private void testRowKey(MonthlyData entity) {
		RowKey key = entity.new RowKey(stockCode, year, month, entity);
		Assert.assertEquals(stockCode, key.getStockCode());
		Assert.assertEquals(year, key.getYear());
		Assert.assertEquals(month, key.getMonth());
	}

	private void testOperatingIncomeFamily(MonthlyData entity) {
		generateOperatingIncomeFamilyContent(entity);
		assertOperatingIncomeFamily(entity);
	}

	private void generateOperatingIncomeFamilyContent(MonthlyData entity) {
		OperatingIncomeFamily fam = entity.getOperatingIncomeFamily();
		fam.setCurrency(ver, currency);
		fam.setCurrentMonth(ver, currentMonth);
		fam.setCurrentMonthOfLastYear(ver, currentMonthOfLastYear);
		fam.setDifferentAmount(ver, differentAmount);
		fam.setDifferentPercent(ver, differentPercent);
		fam.setCumulativeAmountOfThisYear(ver, cumulativeAmountOfThisYear);
		fam.setCumulativeAmountOfLastYear(ver, cumulativeAmountOfLastYear);
		fam.setCumulativeDifferentAmount(ver, cumulativeDifferentAmount);
		fam.setCumulativeDifferentPercent(ver, cumulativeDifferentPercent);
		fam.setExchangeRateOfCurrentMonth(ver, exchangeRateOfCurrentMonth);
		fam.setCumulativeExchangeRateOfThisYear(ver,
				cumulativeExchangeRateOfThisYear);
		fam.setComment(ver, comment);
	}

	private void assertOperatingIncomeFamily(MonthlyData entity) {
		OperatingIncomeFamily fam = entity.getOperatingIncomeFamily();
		Assert.assertEquals(currency, fam.getCurrency());
		Assert.assertEquals(currentMonth, fam.getCurrentMonth());
		Assert.assertEquals(currentMonthOfLastYear,
				fam.getCurrentMonthOfLastYear());
		Assert.assertEquals(differentAmount, fam.getDifferentAmount());
		Assert.assertEquals(differentPercent, fam.getDifferentPercent());
		Assert.assertEquals(cumulativeAmountOfThisYear,
				fam.getCumulativeAmountOfThisYear());
		Assert.assertEquals(cumulativeAmountOfLastYear,
				fam.getCumulativeAmountOfLastYear());
		Assert.assertEquals(cumulativeDifferentAmount,
				fam.getCumulativeDifferentAmount());
		Assert.assertEquals(cumulativeDifferentPercent,
				fam.getCumulativeDifferentPercent());
		Assert.assertEquals(exchangeRateOfCurrentMonth,
				fam.getExchangeRateOfCurrentMonth());
		Assert.assertEquals(cumulativeExchangeRateOfThisYear,
				fam.getCumulativeExchangeRateOfThisYear());
		Assert.assertEquals(comment, fam.getComment());
	}
}
