package idv.hsiehpinghan.stockservice.entity;

import idv.hsiehpinghan.stockdao.entity.ExchangeRate;
import idv.hsiehpinghan.stockdao.enumeration.Dollar;

import java.util.Date;

import junit.framework.Assert;

import org.testng.annotations.Test;

public class ExchangeRateTest {

	@Test
	public void rowKey() {
		ExchangeRate rate = new ExchangeRate();
		ExchangeRate.RowKey key_1 = rate.new RowKey(Dollar.AUD, new Date(),
				rate);
		ExchangeRate.RowKey key_2 = rate.new RowKey(rate);
		byte[] bs = key_1.toBytes();
		key_2.fromBytes(bs);
		Assert.assertEquals(key_1, key_2);
	}
}
