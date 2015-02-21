package idv.hsiehpinghan.stockdao.repository;

import idv.hsiehpinghan.datetimeutility.utility.DateUtility;
import idv.hsiehpinghan.stockdao.entity.MonthlyData;
import idv.hsiehpinghan.stockdao.entity.MonthlyData.OperatingIncomeFamily;
import idv.hsiehpinghan.stockdao.enumeration.CurrencyType;
import idv.hsiehpinghan.stockdao.suit.TestngSuitSetting;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.context.ApplicationContext;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class MonthlyDataRepositoryTest {
	private Date ver = DateUtility.getDate(2015, 2, 3);
	private BigDecimal differentAmount = new BigDecimal("16.16");
	private String stockCode = "stockCode";
	private BigDecimal cumulativeDifferentAmount = new BigDecimal("18.18");
	private BigDecimal currentMonth = new BigDecimal("19.19");
	private CurrencyType currency = CurrencyType.AUD;
	private BigDecimal cumulativeAmountOfLastYear = new BigDecimal("21.21");
	private BigDecimal exchangeRateOfCurrentMonth = new BigDecimal("22.22");
	private BigDecimal cumulativeDifferentPercent = new BigDecimal("23.23");
	private BigDecimal currentMonthOfLastYear = new BigDecimal("24.24");
	private BigDecimal cumulativeExchangeRateOfThisYear = new BigDecimal(
			"25.25");
	private BigDecimal cumulativeAmountOfThisYear = new BigDecimal("26.26");
	private int month = 27;
	private int year = 28;
	private String comment = "comment";
	private BigDecimal differentPercent = new BigDecimal("30.30");
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
