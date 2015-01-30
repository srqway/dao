package idv.hsiehpinghan.stockdao.repository;

import idv.hsiehpinghan.hbaseassistant.repository.IRepositoryBase;
import idv.hsiehpinghan.stockdao.entity.StockClosingCondition;
import idv.hsiehpinghan.stockdao.entity.StockClosingCondition.PriceFamily.PriceQualifier;
import idv.hsiehpinghan.stockdao.entity.StockClosingCondition.PriceFamily.PriceValue;
import idv.hsiehpinghan.stockdao.entity.StockClosingCondition.VolumeFamily.VolumeQualifier;
import idv.hsiehpinghan.stockdao.entity.StockClosingCondition.VolumeFamily.VolumeValue;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.Map;

public interface IStockClosingConditionRepository extends IRepositoryBase {
	StockClosingCondition put(String stockCode, Date date,
			Map<PriceQualifier, PriceValue> priceMap,
			Map<VolumeQualifier, VolumeValue> volumeMap)
			throws IllegalAccessException;

	StockClosingCondition get(String stockCode, Date date)
			throws IllegalAccessException, NoSuchMethodException,
			SecurityException, InstantiationException,
			IllegalArgumentException, InvocationTargetException, IOException;
}
