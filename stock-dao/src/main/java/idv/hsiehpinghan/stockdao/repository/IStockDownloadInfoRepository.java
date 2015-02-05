package idv.hsiehpinghan.stockdao.repository;

import idv.hsiehpinghan.hbaseassistant.repository.IRepositoryBase;
import idv.hsiehpinghan.stockdao.entity.StockDownloadInfo;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

public interface IStockDownloadInfoRepository extends IRepositoryBase {
	StockDownloadInfo put(StockDownloadInfo entity)
			throws IllegalAccessException;

	StockDownloadInfo get(String tableName) throws IllegalAccessException,
			NoSuchMethodException, SecurityException, InstantiationException,
			IllegalArgumentException, InvocationTargetException, IOException;

	StockDownloadInfo getOrCreateEntity(String tableName)
			throws IllegalAccessException, NoSuchMethodException,
			SecurityException, InstantiationException,
			IllegalArgumentException, InvocationTargetException, IOException;
}
