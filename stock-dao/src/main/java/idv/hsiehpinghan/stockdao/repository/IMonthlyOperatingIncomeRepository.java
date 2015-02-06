package idv.hsiehpinghan.stockdao.repository;

import idv.hsiehpinghan.hbaseassistant.repository.IRepositoryBase;
import idv.hsiehpinghan.stockdao.entity.MonthlyOperatingIncome;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

public interface IMonthlyOperatingIncomeRepository extends IRepositoryBase {
	void put(List<MonthlyOperatingIncome> entities)
			throws IllegalAccessException;

	MonthlyOperatingIncome get(String stockCode) throws IllegalAccessException,
			NoSuchMethodException, SecurityException, InstantiationException,
			IllegalArgumentException, InvocationTargetException, IOException;
}
