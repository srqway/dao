package idv.hsiehpinghan.twsedao.entity;

import idv.hsiehpinghan.datetimeutility.utility.DateUtility;
import idv.hsiehpinghan.hbaseassistant.utility.HbaseEntityTestUtility;
import idv.hsiehpinghan.twsedao.entity.StockClosingCondition.PriceFamily.PriceQualifier;
import idv.hsiehpinghan.twsedao.entity.StockClosingCondition.PriceFamily.PriceValue;
import idv.hsiehpinghan.twsedao.entity.StockClosingCondition.RowKey;
import idv.hsiehpinghan.twsedao.entity.StockClosingCondition.VolumeFamily.VolumeQualifier;
import idv.hsiehpinghan.twsedao.entity.StockClosingCondition.VolumeFamily.VolumeValue;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class StockClosingConditionTest {
	private String stockCode = "1101";
	private Date date = DateUtility.getDate(2015, 2, 3);
	private BigDecimal openingPrice = new BigDecimal("1.1");
	private BigDecimal closingPrice = new BigDecimal("2.2");
	private BigDecimal change = new BigDecimal("3.3");
	private BigDecimal highestPrice = new BigDecimal("4.4");
	private BigDecimal lowestPrice = new BigDecimal("5.5");
	private BigDecimal finalPurchasePrice = new BigDecimal("6.6");
	private BigDecimal finalSellingPrice = new BigDecimal("7.7");
	private Integer stockAmount = new Integer("1");
	private Integer moneyAmount = new Integer("2");
	private Integer transactionAmount = new Integer("3");

	@BeforeClass
	public void beforeClass() throws IOException {
	}

	@Test
	public void toBytesFromBytes() {
		StockClosingCondition entity = new StockClosingCondition();
		// Test row key.
		RowKey key = entity.new RowKey(stockCode, entity);
		HbaseEntityTestUtility.toBytesFromBytes(key);
		// Test priceQualifier.
		PriceQualifier priceQualifier = entity.getPriceFamily().new PriceQualifier(
				date);
		HbaseEntityTestUtility.toBytesFromBytes(priceQualifier);
		// Test priceValue.
		PriceValue priceValue = entity.getPriceFamily().new PriceValue(
				openingPrice, closingPrice, change, highestPrice, lowestPrice,
				finalPurchasePrice, finalSellingPrice);
		HbaseEntityTestUtility.toBytesFromBytes(priceValue);
		// Test volumeQualifier.
		VolumeQualifier volumeQualifier = entity.getVolumeFamily().new VolumeQualifier(
				date);
		HbaseEntityTestUtility.toBytesFromBytes(volumeQualifier);
		// Test volumeValue.
		VolumeValue volumeValue = entity.getVolumeFamily().new VolumeValue(
				stockAmount, moneyAmount, transactionAmount);
		HbaseEntityTestUtility.toBytesFromBytes(volumeValue);
	}
}
