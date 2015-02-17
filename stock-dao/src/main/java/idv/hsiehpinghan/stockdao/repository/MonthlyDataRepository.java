package idv.hsiehpinghan.stockdao.repository;

import idv.hsiehpinghan.hbaseassistant.abstractclass.HBaseRowKey;
import idv.hsiehpinghan.hbaseassistant.abstractclass.HBaseTable;
import idv.hsiehpinghan.hbaseassistant.assistant.HbaseAssistant;
import idv.hsiehpinghan.hbaseassistant.repository.RepositoryBase;
import idv.hsiehpinghan.stockdao.entity.MonthlyData;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class MonthlyDataRepository extends RepositoryBase {

	@Autowired
	private HbaseAssistant hbaseAssistant;

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

	@Override
	public Class<? extends HBaseTable> getTargetTableClass() {
		return MonthlyData.class;
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
