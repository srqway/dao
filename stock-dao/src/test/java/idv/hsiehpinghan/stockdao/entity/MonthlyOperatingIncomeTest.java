package idv.hsiehpinghan.stockdao.entity;

import idv.hsiehpinghan.hbaseassistant.utility.HbaseEntityTestUtility;
import idv.hsiehpinghan.stockdao.entity.MonthlyOperatingIncome.DataFamily;
import idv.hsiehpinghan.stockdao.entity.MonthlyOperatingIncome.DataFamily.DataQualifier;
import idv.hsiehpinghan.stockdao.entity.MonthlyOperatingIncome.DataFamily.DataValue;
import idv.hsiehpinghan.stockdao.entity.MonthlyOperatingIncome.RowKey;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class MonthlyOperatingIncomeTest {

	@BeforeClass
	public void beforeClass() throws IOException {
	}

	@Test
	public void toBytesFromBytes() throws Exception {
		MonthlyOperatingIncome entity = new MonthlyOperatingIncome();
		testRowKey(entity);
		testDataFamily(entity);
	}

	private void testRowKey(MonthlyOperatingIncome entity) {
		RowKey key = entity.new RowKey("2330", entity);
		HbaseEntityTestUtility.toBytesFromBytes(key);
	}

	private void testDataFamily(MonthlyOperatingIncome entity) {
		DataFamily dataFamily = entity.getDataFamily();
		testDataQualifier(dataFamily);
		testDataValue(dataFamily);
	}
	
	private void testDataQualifier(DataFamily dataFamily) {
		DataQualifier dataQualifier = dataFamily.new DataQualifier(
				2015, 1);
		HbaseEntityTestUtility.toBytesFromBytes(dataQualifier);
	}
	
	private void testDataValue(DataFamily dataFamily) {
		BigInteger currentMonth = new BigInteger("1");
		BigInteger currentMonthOfLastYear = new BigInteger("2");
		BigInteger differentAmount = new BigInteger("3");
		BigDecimal differentPercent = new BigDecimal("4.4");
		BigInteger cumulativeAmountOfThisYear = new BigInteger("5");
		BigInteger cumulativeAmountOfLastYear = new BigInteger("6");
		BigInteger cumulativeDifferentAmount = new BigInteger("7");
		BigDecimal cumulativeDifferentPercent = new BigDecimal("8.8");
		String comment = "comment";
		DataValue dataValue = dataFamily.new DataValue(
				currentMonth, currentMonthOfLastYear, differentAmount,
				differentPercent, cumulativeAmountOfThisYear,
				cumulativeAmountOfLastYear, cumulativeDifferentAmount,
				cumulativeDifferentPercent, comment);
		HbaseEntityTestUtility.toBytesFromBytes(dataValue);
	}
}
