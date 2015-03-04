package idv.hsiehpinghan.stockdao.repository;

import idv.hsiehpinghan.datetimeutility.utility.DateUtility;
import idv.hsiehpinghan.stockdao.entity.MonthlyOperatingIncome;
import idv.hsiehpinghan.stockdao.entity.MonthlyOperatingIncome.IncomeFamily;
import idv.hsiehpinghan.stockdao.enumeration.CurrencyType;
import idv.hsiehpinghan.stockdao.suit.TestngSuitSetting;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.context.ApplicationContext;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class MonthlyOperatingIncomeRepositoryTest {
	private Date ver = DateUtility.getDate(2015, 2, 3);
	private BigDecimal differentAmount = new BigDecimal("17.17");
	private String stockCode = "stockCode";
	private BigDecimal cumulativeDifferentAmount = new BigDecimal("19.19");
	private BigDecimal currentMonth = new BigDecimal("20.20");
	private boolean isFunctionalCurrency = false;
	private CurrencyType currency = CurrencyType.AUD;
	private BigDecimal cumulativeAmountOfLastYear = new BigDecimal("23.23");
	private BigDecimal exchangeRateOfCurrentMonth = new BigDecimal("24.24");
	private BigDecimal cumulativeDifferentPercent = new BigDecimal("25.25");
	private BigDecimal currentMonthOfLastYear = new BigDecimal("26.26");
	private BigDecimal cumulativeExchangeRateOfThisYear = new BigDecimal(
			"27.27");
	private BigDecimal cumulativeAmountOfThisYear = new BigDecimal("28.28");
	private int month = 29;
	private int year = 30;
	private String comment = "comment";
	private BigDecimal differentPercent = new BigDecimal("32.32");
	private MonthlyOperatingIncomeRepository repository;

	@BeforeClass
	public void beforeClass() throws Exception {
		ApplicationContext applicationContext = TestngSuitSetting
				.getApplicationContext();
		repository = applicationContext
				.getBean(MonthlyOperatingIncomeRepository.class);
	}

	@Test
	public void put() throws Exception {
		MonthlyOperatingIncome entity = repository.generateEntity(stockCode,
				isFunctionalCurrency, currency, year, month);
		generateIncomeFamilyContent(entity);
		repository.put(entity);
		Assert.assertTrue(repository.exists(entity.getRowKey()));
	}

	@Test(dependsOnMethods = { "put" })
	public void get() throws Exception {
		MonthlyOperatingIncome entity = repository.get(stockCode,
				isFunctionalCurrency, currency, year, month);
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
