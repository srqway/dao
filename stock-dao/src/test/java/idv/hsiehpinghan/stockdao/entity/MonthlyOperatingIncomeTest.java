package idv.hsiehpinghan.stockdao.entity;

import idv.hsiehpinghan.hbaseassistant.utility.HbaseEntityTestUtility;
import idv.hsiehpinghan.stockdao.entity.MonthlyOperatingIncome.CommentFamily;
import idv.hsiehpinghan.stockdao.entity.MonthlyOperatingIncome.CommentFamily.CommentQualifier;
import idv.hsiehpinghan.stockdao.entity.MonthlyOperatingIncome.CommentFamily.CommentValue;
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
		testCommentFamily(entity);
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
		DataQualifier dataQualifier = dataFamily.new DataQualifier(2015, 1);
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
		DataValue dataValue = dataFamily.new DataValue(currentMonth,
				currentMonthOfLastYear, differentAmount, differentPercent,
				cumulativeAmountOfThisYear, cumulativeAmountOfLastYear,
				cumulativeDifferentAmount, cumulativeDifferentPercent);
		HbaseEntityTestUtility.toBytesFromBytes(dataValue);
	}

	private void testCommentFamily(MonthlyOperatingIncome entity) {
		CommentFamily commentFamily = entity.getCommentFamily();
		testCommentQualifier(commentFamily);
		testCommentValue(commentFamily);
	}

	private void testCommentQualifier(CommentFamily commentFamily) {
		CommentQualifier commentQualifier = commentFamily.new CommentQualifier(
				2015, 1);
		HbaseEntityTestUtility.toBytesFromBytes(commentQualifier);
	}

	private void testCommentValue(CommentFamily commentFamily) {
		String comment = "comment";
		CommentValue commentValue = commentFamily.new CommentValue(comment);
		HbaseEntityTestUtility.toBytesFromBytes(commentValue);
	}
}
