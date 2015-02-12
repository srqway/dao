package idv.hsiehpinghan.stockdao.entity;

import idv.hsiehpinghan.stockdao.entity.MonthlyData.OperatingIncomeFamily;
import idv.hsiehpinghan.stockdao.entity.MonthlyData.RowKey;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

import org.apache.commons.lang3.time.DateUtils;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class MonthlyDataTest {
	private Date ver;
	private String stockCode = "2330";
	private int year = 2015;
	private int month = 1;
	private String comment = "Comment";
	private BigInteger cumulativeAmountOfLastYear = new BigInteger("1");
	private BigInteger cumulativeAmountOfThisYear = new BigInteger("2");
	private BigInteger cumulativeDifferentAmount = new BigInteger("3");
	private BigDecimal cumulativeDifferentPercent = new BigDecimal("4.4");
	private BigInteger currentMonth = new BigInteger("5");
	private BigInteger currentMonthOfLastYear = new BigInteger("6");
	private BigInteger differentAmount = new BigInteger("7");
	private BigDecimal differentPercent = new BigDecimal("8.8");

	@BeforeClass
	public void beforeClass() throws Exception {
		ver = DateUtils.parseDate("2015/01/01", "yyyy/MM/dd");
	}

	@Test
	public void bytesConvert() {
		MonthlyData entity = new MonthlyData();
		testRowKey(entity);
		testOperatingIncomeFamily(entity);
	}

	private void testOperatingIncomeFamily(MonthlyData entity) {
		generateOperatingIncomeFamilyContent(entity);
		assertOperatingIncomeFamily(entity);
	}

	private void generateOperatingIncomeFamilyContent(MonthlyData entity) {
		OperatingIncomeFamily fam = entity.getOperatingIncomeFamily();
		fam.setComment(ver, comment);
		fam.setCumulativeAmountOfLastYear(ver, cumulativeAmountOfLastYear);
		fam.setCumulativeAmountOfThisYear(ver, cumulativeAmountOfThisYear);
		fam.setCumulativeDifferentAmount(ver, cumulativeDifferentAmount);
		fam.setCumulativeDifferentPercent(ver, cumulativeDifferentPercent);
		fam.setCurrentMonth(ver, currentMonth);
		fam.setCurrentMonthOfLastYear(ver, currentMonthOfLastYear);
		fam.setDifferentAmount(ver, differentAmount);
		fam.setDifferentPercent(ver, differentPercent);
	}

	private void assertOperatingIncomeFamily(MonthlyData entity) {
		OperatingIncomeFamily fam = entity.getOperatingIncomeFamily();
		Assert.assertEquals(comment, fam.getComment());
		Assert.assertEquals(cumulativeAmountOfLastYear,
				fam.getCumulativeAmountOfLastYear());
		Assert.assertEquals(cumulativeAmountOfThisYear,
				fam.getCumulativeAmountOfThisYear());
		Assert.assertEquals(cumulativeDifferentAmount,
				fam.getCumulativeDifferentAmount());
		Assert.assertEquals(cumulativeDifferentPercent,
				fam.getCumulativeDifferentPercent());
		Assert.assertEquals(currentMonth, fam.getCurrentMonth());
		Assert.assertEquals(currentMonthOfLastYear,
				fam.getCurrentMonthOfLastYear());
		Assert.assertEquals(differentAmount, fam.getDifferentAmount());
		Assert.assertEquals(differentPercent, fam.getDifferentPercent());
	}

	private void testRowKey(MonthlyData entity) {
		RowKey key = entity.new RowKey(stockCode, year, month, entity);
		Assert.assertEquals(stockCode, key.getStockCode());
		Assert.assertEquals(year, key.getYear());
		Assert.assertEquals(month, key.getMonth());
	}
}
