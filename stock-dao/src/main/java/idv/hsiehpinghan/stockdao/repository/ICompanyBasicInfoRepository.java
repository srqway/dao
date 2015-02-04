package idv.hsiehpinghan.stockdao.repository;

import idv.hsiehpinghan.hbaseassistant.repository.IRepositoryBase;
import idv.hsiehpinghan.stockdao.entity.CompanyBasicInfo;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

public interface ICompanyBasicInfoRepository extends IRepositoryBase {
	void put(CompanyBasicInfo entity) throws IllegalAccessException;

	CompanyBasicInfo get(String stockCode) throws IllegalAccessException,
			NoSuchMethodException, SecurityException, InstantiationException,
			IllegalArgumentException, InvocationTargetException, IOException;

	void put(List<CompanyBasicInfo> entities) throws IllegalAccessException;

	boolean exists(String stockCode) throws NoSuchFieldException,
			SecurityException, IllegalArgumentException,
			IllegalAccessException, NoSuchMethodException,
			InvocationTargetException, InstantiationException, IOException;
}
