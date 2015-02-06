package idv.hsiehpinghan.stockdao.repository.hbase;

import idv.hsiehpinghan.hbaseassistant.abstractclass.HBaseRowKey;
import idv.hsiehpinghan.hbaseassistant.abstractclass.HBaseTable;
import idv.hsiehpinghan.hbaseassistant.assistant.HbaseAssistant;
import idv.hsiehpinghan.hbaseassistant.repository.RepositoryBase;
import idv.hsiehpinghan.stockdao.entity.StockDownloadInfo;
import idv.hsiehpinghan.stockdao.repository.IStockDownloadInfoRepository;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class StockDownloadInfoRepository extends RepositoryBase implements
		IStockDownloadInfoRepository {
	@Autowired
	private HbaseAssistant hbaseAssistant;

	@Override
	public Class<? extends HBaseTable> getTargetTableClass() {
		return StockDownloadInfo.class;
	}

	@Override
	public HbaseAssistant getHbaseAssistant() {
		return hbaseAssistant;
	}

	@Override
	public void put(StockDownloadInfo entity)
			throws IllegalAccessException {
		hbaseAssistant.put(entity);
	}

	@Override
	public StockDownloadInfo get(String tableName)
			throws IllegalAccessException, NoSuchMethodException,
			SecurityException, InstantiationException,
			IllegalArgumentException, InvocationTargetException, IOException {
		HBaseRowKey rowKey = getRowKey(tableName);
		return (StockDownloadInfo) hbaseAssistant.get(rowKey);
	}

	@Override
	public StockDownloadInfo getOrCreateEntity(String tableName)
			throws IllegalAccessException, NoSuchMethodException,
			SecurityException, InstantiationException,
			IllegalArgumentException, InvocationTargetException, IOException {
		StockDownloadInfo entity = get(tableName);
		if (entity == null) {
			entity = new StockDownloadInfo();
			entity.new RowKey(tableName, entity);
		}
		return entity;
	}

	private HBaseRowKey getRowKey(String tableName) {
		StockDownloadInfo entity = new StockDownloadInfo();
		generateRowKey(entity, tableName);
		return entity.getRowKey();
	}

	private void generateRowKey(StockDownloadInfo entity, String tableName) {
		entity.new RowKey(tableName, entity);
	}

}
