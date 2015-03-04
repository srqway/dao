package idv.hsiehpinghan.stockdao.entity;

import idv.hsiehpinghan.datetimeutility.utility.DateUtility;
import idv.hsiehpinghan.stockdao.entity.MonthlyOperatingIncome.IncomeFamily;
import idv.hsiehpinghan.stockdao.entity.MonthlyOperatingIncome.RowKey;
import idv.hsiehpinghan.stockdao.enumeration.CurrencyType;

import java.math.BigDecimal;
import java.util.Date;

import org.testng.Assert;
import org.testng.annotations.Test;

public class MonthlyOperatingIncomeTest {
	private Date ver = DateUtility.getDate(2015, 2, 3);
	private BigDecimal differentAmount = new BigDecimal("1.1");
	private String stockCode = "stockCode";
	private BigDecimal cumulativeDifferentAmount = new BigDecimal("3.3");
	private BigDecimal currentMonth = new BigDecimal("4.4");
	private boolean isFunctionalCurrency = false;
	private CurrencyType currency = CurrencyType.AUD;
	private BigDecimal cumulativeAmountOfLastYear = new BigDecimal("7.7");
	private BigDecimal exchangeRateOfCurrentMonth = new BigDecimal("8.8");
	private BigDecimal cumulativeDifferentPercent = new BigDecimal("9.9");
	private BigDecimal currentMonthOfLastYear = new BigDecimal("10.10");
	private BigDecimal cumulativeExchangeRateOfThisYear = new BigDecimal(
			"11.11");
	private BigDecimal cumulativeAmountOfThisYear = new BigDecimal("12.12");
	private int month = 13;
	private int year = 14;
	private String comment = "comment";
	private BigDecimal differentPercent = new BigDecimal("16.16");

	@Test
	public void bytesConvert() {
		MonthlyOperatingIncome entity = new MonthlyOperatingIncome();
		testRowKey(entity);
		testIncomeFamily(entity);
	}

	private void testRowKey(MonthlyOperatingIncome entity) {
		RowKey key = entity.new RowKey(stockCode, isFunctionalCurrency,
				currency, year, month, entity);
		Assert.assertEquals(stockCode, key.getStockCode());
		Assert.assertEquals(isFunctionalCurrency, key.getIsFunctionalCurrency());
		Assert.assertEquals(currency, key.getCurrency());
		Assert.assertEquals(year, key.getYear());
		Assert.assertEquals(month, key.getMonth());
	}

	private void testIncomeFamily(MonthlyOperatingIncome entity) {
		generateIncomeFamilyContent(entity);
		assertIncomeFamily(entity);
	}

	private void generateIncomeFamilyContent(MonthlyOperatingIncome entity) {
		IncomeFamily fam = entity.getIncomeFamily();
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

	private void assertIncomeFamily(MonthlyOperatingIncome entity) {
		IncomeFamily fam = entity.getIncomeFamily();
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
