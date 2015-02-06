package idv.hsiehpinghan.stockdao.entity;

import idv.hsiehpinghan.datetimeutility.utility.DateUtility;
import idv.hsiehpinghan.hbaseassistant.utility.HbaseEntityTestUtility;
import idv.hsiehpinghan.stockdao.entity.StockClosingCondition.PriceFamily;
import idv.hsiehpinghan.stockdao.entity.StockClosingCondition.PriceFamily.PriceQualifier;
import idv.hsiehpinghan.stockdao.entity.StockClosingCondition.PriceFamily.PriceValue;
import idv.hsiehpinghan.stockdao.entity.StockClosingCondition.RowKey;
import idv.hsiehpinghan.stockdao.entity.StockClosingCondition.VolumeFamily;
import idv.hsiehpinghan.stockdao.entity.StockClosingCondition.VolumeFamily.VolumeQualifier;
import idv.hsiehpinghan.stockdao.entity.StockClosingCondition.VolumeFamily.VolumeValue;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class StockClosingConditionTest {
	private String stockCode = "1101";
	private Date date = DateUtility.getDate(2015, 2, 3);
	private BigDecimal highestPrice = new BigDecimal("1.1");
	private BigInteger moneyAmount = new BigInteger("2");

	@BeforeClass
	public void beforeClass() throws IOException {
	}

	@Test
	public void toBytesFromBytes() {
		StockClosingCondition entity = new StockClosingCondition();
		testRowKey(entity);
		testPriceFamily(entity);
		testVolumeFamily(entity);
	}

	private void testRowKey(StockClosingCondition entity) {
		RowKey key = entity.new RowKey(stockCode, date, entity);
		HbaseEntityTestUtility.toBytesFromBytes(key);
	}

	private void testPriceFamily(StockClosingCondition entity) {
		PriceFamily priceFamily = entity.getPriceFamily();
		testPriceQualifier(priceFamily);
		testPriceValue(priceFamily);
	}

	private void testPriceQualifier(PriceFamily priceFamily) {
		PriceQualifier priceQualifier = priceFamily.new PriceQualifier(
				PriceQualifier.HIGHEST_PRICE);
		HbaseEntityTestUtility.toBytesFromBytes(priceQualifier);
	}

	private void testPriceValue(PriceFamily priceFamily) {
		PriceValue priceValue = priceFamily.new PriceValue(highestPrice);
		HbaseEntityTestUtility.toBytesFromBytes(priceValue);
	}

	private void testVolumeFamily(StockClosingCondition entity) {
		VolumeFamily volumeFamily = entity.getVolumeFamily();
		testVolumeQualifier(volumeFamily);
		testVolumeValue(volumeFamily);
	}

	private void testVolumeQualifier(VolumeFamily volumeFamily) {
		VolumeQualifier volumeQualifier = volumeFamily.new VolumeQualifier(
				VolumeQualifier.MONEY_AMOUNT);
		HbaseEntityTestUtility.toBytesFromBytes(volumeQualifier);
	}

	private void testVolumeValue(VolumeFamily volumeFamily) {
		VolumeValue volumeValue = volumeFamily.new VolumeValue(moneyAmount);
		HbaseEntityTestUtility.toBytesFromBytes(volumeValue);
	}
}
