package idv.hsiehpinghan.stockdao.repository;

import idv.hsiehpinghan.datetimeutility.utility.DateUtility;
import idv.hsiehpinghan.stockdao.entity.MonthlyData;
import idv.hsiehpinghan.stockdao.entity.MonthlyData.OperatingIncomeFamily;
import idv.hsiehpinghan.stockdao.enumeration.CurrencyType;
import idv.hsiehpinghan.stockdao.suit.TestngSuitSetting;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

import org.springframework.context.ApplicationContext;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class MonthlyDataRepositoryTest {
	private Date ver = DateUtility.getDate(2015, 2, 3);
	private String stockCode = "stockCode";
	private int year = 17;
	private int month = 18;
	private CurrencyType currency = CurrencyType.TWD;
	private BigInteger currentMonth = new BigInteger("20");
	private BigInteger currentMonthOfLastYear = new BigInteger("21");
	private BigInteger differentAmount = new BigInteger("22");
	private BigDecimal differentPercent = new BigDecimal("23.23");
	private BigInteger cumulativeAmountOfThisYear = new BigInteger("24");
	private BigInteger cumulativeAmountOfLastYear = new BigInteger("25");
	private BigInteger cumulativeDifferentAmount = new BigInteger("26");
	private BigDecimal cumulativeDifferentPercent = new BigDecimal("27.27");
	private BigDecimal exchangeRateOfCurrentMonth = new BigDecimal("28.28");
	private BigDecimal cumulativeExchangeRateOfThisYear = new BigDecimal(
			"29.29");
	private String comment = "comment";
	private MonthlyDataRepository repository;

	@BeforeClass
	public void beforeClass() throws Exception {
		ApplicationContext applicationContext = TestngSuitSetting
				.getApplicationContext();
		repository = applicationContext.getBean(MonthlyDataRepository.class);
	}

	@Test
	public void put() throws Exception {
		MonthlyData entity = repository.generateEntity(stockCode, year, month);
		generateOperatingIncomeFamilyContent(entity);
		repository.put(entity);
		Assert.assertTrue(repository.exists(entity.getRowKey()));
	}

	@Test(dependsOnMethods = { "put" })
	public void get() throws Exception {
		MonthlyData entity = repository.get(stockCode, year, month);
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
