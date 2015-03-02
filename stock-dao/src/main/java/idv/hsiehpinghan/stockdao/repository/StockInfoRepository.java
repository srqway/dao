package idv.hsiehpinghan.stockdao.repository;

import idv.hsiehpinghan.hbaseassistant.abstractclass.HBaseRowKey;
import idv.hsiehpinghan.hbaseassistant.abstractclass.HBaseTable;
import idv.hsiehpinghan.hbaseassistant.assistant.HbaseAssistant;
import idv.hsiehpinghan.hbaseassistant.repository.RepositoryBase;
import idv.hsiehpinghan.stockdao.entity.StockInfo;
import idv.hsiehpinghan.stockdao.entity.StockInfo.RowKey;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.apache.hadoop.hbase.filter.KeyOnlyFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class StockInfoRepository extends RepositoryBase {
	@Autowired
	private HbaseAssistant hbaseAssistant;

	@Override
	public Class<? extends HBaseTable> getTargetTableClass() {
		return StockInfo.class;
	}

	public StockInfo generateEntity(String stockCode) {
		StockInfo entity = new StockInfo();
		generateRowKey(stockCode, entity);
		return entity;
	}

	public StockInfo get(String stockCode) throws IllegalAccessException,
			NoSuchMethodException, SecurityException, InstantiationException,
			IllegalArgumentException, InvocationTargetException, IOException {
		HBaseRowKey rowKey = getRowKey(stockCode);
		return (StockInfo) hbaseAssistant.get(rowKey);
	}

	public int getRowAmount() {
		return hbaseAssistant.getRowAmount(getTargetTableClass());
	}

	public List<RowKey> getRowKeys() {
		List<HBaseTable> entities = hbaseAssistant.scan(getTargetTableClass(),
				new KeyOnlyFilter());
		List<RowKey> rowKeys = new ArrayList<RowKey>(entities.size());
		for (HBaseTable entity : entities) {
			RowKey rowKey = (RowKey) entity.getRowKey();
			rowKeys.add(rowKey);
		}
		return rowKeys;
	}

	public boolean exists(String stockCode) throws NoSuchFieldException,
			SecurityException, IllegalArgumentException,
			IllegalAccessException, NoSuchMethodException,
			InvocationTargetException, InstantiationException, IOException {
		HBaseRowKey key = getRowKey(stockCode);
		return super.exists(key);
	}

	@Override
	protected HbaseAssistant getHbaseAssistant() {
		return hbaseAssistant;
	}

	private HBaseRowKey getRowKey(String stockCode) {
		StockInfo entity = new StockInfo();
		generateRowKey(stockCode, entity);
		return entity.getRowKey();
	}

	private void generateRowKey(String stockCode, StockInfo entity) {
		entity.new RowKey(stockCode, entity);
	}
}
