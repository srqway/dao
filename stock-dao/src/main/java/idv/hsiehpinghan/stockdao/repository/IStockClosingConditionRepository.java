package idv.hsiehpinghan.stockdao.repository;

import idv.hsiehpinghan.hbaseassistant.repository.IRepositoryBase;
import idv.hsiehpinghan.stockdao.entity.StockClosingCondition;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.List;

public interface IStockClosingConditionRepository extends IRepositoryBase {
	void put(List<StockClosingCondition> entities)
			throws IllegalAccessException;

	StockClosingCondition get(String stockCode, Date date)
			throws IllegalAccessException, NoSuchMethodException,
			SecurityException, InstantiationException,
			IllegalArgumentException, InvocationTargetException, IOException;

	boolean exists(String stockCode, Date date) throws NoSuchFieldException,
			SecurityException, IllegalArgumentException,
			IllegalAccessException, NoSuchMethodException,
			InvocationTargetException, InstantiationException, IOException;
}
