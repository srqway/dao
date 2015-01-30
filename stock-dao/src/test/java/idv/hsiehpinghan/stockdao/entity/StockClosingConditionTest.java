package idv.hsiehpinghan.stockdao.entity;

import idv.hsiehpinghan.datetimeutility.utility.DateUtility;
import idv.hsiehpinghan.hbaseassistant.utility.HbaseEntityTestUtility;
import idv.hsiehpinghan.stockdao.entity.StockClosingCondition.PriceFamily.PriceQualifier;
import idv.hsiehpinghan.stockdao.entity.StockClosingCondition.PriceFamily.PriceValue;
import idv.hsiehpinghan.stockdao.entity.StockClosingCondition.RowKey;
import idv.hsiehpinghan.stockdao.entity.StockClosingCondition.VolumeFamily.VolumeQualifier;
import idv.hsiehpinghan.stockdao.entity.StockClosingCondition.VolumeFamily.VolumeValue;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class StockClosingConditionTest {
	private String stockCode = "1101";
	private Date date = DateUtility.getDate(2015, 2, 3);
	private BigDecimal highestPrice = new BigDecimal("1.1");
	private Integer moneyAmount = new Integer("2");

	@BeforeClass
	public void beforeClass() throws IOException {
	}

	@Test
	public void toBytesFromBytes() {
		StockClosingCondition entity = new StockClosingCondition();
		// Test row key.
		RowKey key = entity.new RowKey(stockCode, date, entity);
		HbaseEntityTestUtility.toBytesFromBytes(key);
		// Test priceQualifier.
		PriceQualifier priceQualifier = entity.getPriceFamily().new PriceQualifier(
				PriceQualifier.HIGHEST_PRICE);
		HbaseEntityTestUtility.toBytesFromBytes(priceQualifier);
		// Test priceValue.
		PriceValue priceValue = entity.getPriceFamily().new PriceValue(
				highestPrice);
		HbaseEntityTestUtility.toBytesFromBytes(priceValue);
		// Test volumeQualifier.
		VolumeQualifier volumeQualifier = entity.getVolumeFamily().new VolumeQualifier(
				VolumeQualifier.MONEY_AMOUNT);
		HbaseEntityTestUtility.toBytesFromBytes(volumeQualifier);
		// Test volumeValue.
		VolumeValue volumeValue = entity.getVolumeFamily().new VolumeValue(
				moneyAmount);
		HbaseEntityTestUtility.toBytesFromBytes(volumeValue);
	}
}
