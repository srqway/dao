package idv.hsiehpinghan.stockdao.repository;

import idv.hsiehpinghan.hbaseassistant.abstractclass.HBaseRowKey;
import idv.hsiehpinghan.hbaseassistant.abstractclass.HBaseTable;
import idv.hsiehpinghan.hbaseassistant.assistant.HbaseAssistant;
import idv.hsiehpinghan.hbaseassistant.repository.RepositoryBase;
import idv.hsiehpinghan.stockdao.entity.MonthlyData;
import idv.hsiehpinghan.stockdao.entity.MonthlyData.RowKey;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.apache.hadoop.hbase.filter.FuzzyRowFilter;
import org.apache.hadoop.hbase.filter.KeyOnlyFilter;
import org.apache.hadoop.hbase.util.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class MonthlyDataRepository extends RepositoryBase {
	@Autowired
	private HbaseAssistant hbaseAssistant;

	@Override
	public Class<? extends HBaseTable> getTargetTableClass() {
		return MonthlyData.class;
	}

	public MonthlyData generateEntity(String stockCode, int year, int month) {
		MonthlyData entity = new MonthlyData();
		generateRowKey(stockCode, year, month, entity);
		return entity;
	}

	public MonthlyData get(String stockCode, int year, int month)
			throws IllegalAccessException, NoSuchMethodException,
			SecurityException, InstantiationException,
			IllegalArgumentException, InvocationTargetException, IOException {
		HBaseRowKey rowKey = getRowKey(stockCode, year, month);
		return (MonthlyData) hbaseAssistant.get(rowKey);
	}

	public List<MonthlyData> fuzzyScan(String stockCode, Integer year,
			Integer month) {
		MonthlyData.RowKey rowKey = (MonthlyData.RowKey) getRowKey(stockCode,
				year == null ? 0 : year, month == null ? 0 : month);
		List<Pair<byte[], byte[]>> fuzzyKeysData = new ArrayList<Pair<byte[], byte[]>>();
		Pair<byte[], byte[]> pair = new Pair<byte[], byte[]>(rowKey.getBytes(),
				rowKey.getFuzzyBytes(stockCode, year, month));
		fuzzyKeysData.add(pair);
		FuzzyRowFilter fuzzyRowFilter = new FuzzyRowFilter(fuzzyKeysData);
		@SuppressWarnings("unchecked")
		List<MonthlyData> monthlyDatas = (List<MonthlyData>) (Object) hbaseAssistant
				.scan(getTargetTableClass(), fuzzyRowFilter);
		return monthlyDatas;
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

	public boolean exists(String stockCode, int year, int month)
			throws NoSuchFieldException, SecurityException,
			IllegalArgumentException, IllegalAccessException,
			NoSuchMethodException, InvocationTargetException,
			InstantiationException, IOException {
		HBaseRowKey key = getRowKey(stockCode, year, month);
		return super.exists(key);
	}

	@Override
	protected HbaseAssistant getHbaseAssistant() {
		return hbaseAssistant;
	}

	private HBaseRowKey getRowKey(String stockCode, int year, int month) {
		MonthlyData entity = new MonthlyData();
		generateRowKey(stockCode, year, month, entity);
		return entity.getRowKey();
	}

	private void generateRowKey(String stockCode, int year, int month,
			MonthlyData entity) {
		entity.new RowKey(stockCode, year, month, entity);
	}
}
