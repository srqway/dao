package idv.hsiehpinghan.stockdao.repository;

import idv.hsiehpinghan.hbaseassistant.abstractclass.HBaseRowKey;
import idv.hsiehpinghan.hbaseassistant.abstractclass.HBaseTable;
import idv.hsiehpinghan.hbaseassistant.assistant.HbaseAssistant;
import idv.hsiehpinghan.hbaseassistant.repository.RepositoryBase;
import idv.hsiehpinghan.stockdao.entity.DailyData;
import idv.hsiehpinghan.stockdao.entity.DailyData.RowKey;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.hadoop.hbase.filter.FuzzyRowFilter;
import org.apache.hadoop.hbase.filter.KeyOnlyFilter;
import org.apache.hadoop.hbase.util.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class DailyDataRepository extends RepositoryBase {
	@Autowired
	private HbaseAssistant hbaseAssistant;

	@Override
	public Class<? extends HBaseTable> getTargetTableClass() {
		return DailyData.class;
	}

	public DailyData generateEntity(String stockCode, Date date) {
		DailyData entity = new DailyData();
		generateRowKey(stockCode, date, entity);
		return entity;
	}

	public DailyData get(String stockCode, Date date)
			throws IllegalAccessException, NoSuchMethodException,
			SecurityException, InstantiationException,
			IllegalArgumentException, InvocationTargetException, IOException {
		HBaseRowKey rowKey = getRowKey(stockCode, date);
		return (DailyData) hbaseAssistant.get(rowKey);
	}

	public List<DailyData> fuzzyGet(String stockCode, Date date) {
		DailyData.RowKey rowKey = (DailyData.RowKey) getRowKey(stockCode, date);
		List<Pair<byte[], byte[]>> fuzzyKeysData = new ArrayList<Pair<byte[], byte[]>>();
		Pair<byte[], byte[]> pair = new Pair<byte[], byte[]>(rowKey.getBytes(),
				rowKey.getFuzzyBytes(stockCode, date));
		fuzzyKeysData.add(pair);
		FuzzyRowFilter fuzzyRowFilter = new org.apache.hadoop.hbase.filter.FuzzyRowFilter(
				fuzzyKeysData);
		@SuppressWarnings("unchecked")
		List<DailyData> dailyDatas = (List<DailyData>) (Object) hbaseAssistant
				.scan(getTargetTableClass(), fuzzyRowFilter);
		return dailyDatas;
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

	public boolean exists(String stockCode, Date date)
			throws NoSuchFieldException, SecurityException,
			IllegalArgumentException, IllegalAccessException,
			NoSuchMethodException, InvocationTargetException,
			InstantiationException, IOException {
		HBaseRowKey key = getRowKey(stockCode, date);
		return super.exists(key);
	}

	@Override
	protected HbaseAssistant getHbaseAssistant() {
		return hbaseAssistant;
	}

	private HBaseRowKey getRowKey(String stockCode, Date date) {
		DailyData entity = new DailyData();
		generateRowKey(stockCode, date, entity);
		return entity.getRowKey();
	}

	private void generateRowKey(String stockCode, Date date, DailyData entity) {
		entity.new RowKey(stockCode, date, entity);
	}
}
