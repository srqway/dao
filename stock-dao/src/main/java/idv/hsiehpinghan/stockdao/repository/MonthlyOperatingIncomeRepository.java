package idv.hsiehpinghan.stockdao.repository;

import idv.hsiehpinghan.hbaseassistant.abstractclass.HBaseRowKey;
import idv.hsiehpinghan.hbaseassistant.abstractclass.HBaseTable;
import idv.hsiehpinghan.hbaseassistant.assistant.HbaseAssistant;
import idv.hsiehpinghan.hbaseassistant.repository.RepositoryBase;
import idv.hsiehpinghan.stockdao.entity.MonthlyOperatingIncome;
import idv.hsiehpinghan.stockdao.entity.MonthlyOperatingIncome.RowKey;
import idv.hsiehpinghan.stockdao.enumeration.CurrencyType;

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
public class MonthlyOperatingIncomeRepository extends RepositoryBase {
	@Autowired
	private HbaseAssistant hbaseAssistant;

	@Override
	public Class<? extends HBaseTable> getTargetTableClass() {
		return MonthlyOperatingIncome.class;
	}

	public MonthlyOperatingIncome generateEntity(String stockCode,
			boolean isFunctionalCurrency, CurrencyType currency, int year,
			int month) {
		MonthlyOperatingIncome entity = new MonthlyOperatingIncome();
		generateRowKey(stockCode, isFunctionalCurrency, currency, year, month,
				entity);
		return entity;
	}

	public MonthlyOperatingIncome get(String stockCode,
			boolean isFunctionalCurrency, CurrencyType currency, int year,
			int month) throws IllegalAccessException, NoSuchMethodException,
			SecurityException, InstantiationException,
			IllegalArgumentException, InvocationTargetException, IOException {
		HBaseRowKey rowKey = getRowKey(stockCode, isFunctionalCurrency,
				currency, year, month);
		return (MonthlyOperatingIncome) hbaseAssistant.get(rowKey);
	}

	public List<MonthlyOperatingIncome> fuzzyScan(String stockCode,
			Boolean isFunctionalCurrency, CurrencyType currency, Integer year,
			Integer month) {
		MonthlyOperatingIncome.RowKey rowKey = (MonthlyOperatingIncome.RowKey) getRowKey(
				stockCode, isFunctionalCurrency == null ? false
						: isFunctionalCurrency, currency, year == null ? 0
						: year, month == null ? 0 : month);
		List<Pair<byte[], byte[]>> fuzzyKeysData = new ArrayList<Pair<byte[], byte[]>>();
		Pair<byte[], byte[]> pair = new Pair<byte[], byte[]>(rowKey.getBytes(),
				rowKey.getFuzzyBytes(stockCode, isFunctionalCurrency, currency,
						year, month));
		fuzzyKeysData.add(pair);
		FuzzyRowFilter fuzzyRowFilter = new FuzzyRowFilter(fuzzyKeysData);
		@SuppressWarnings("unchecked")
		List<MonthlyOperatingIncome> monthlyOperatingIncomes = (List<MonthlyOperatingIncome>) (Object) hbaseAssistant
				.scan(getTargetTableClass(), fuzzyRowFilter);
		return monthlyOperatingIncomes;
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

	public boolean exists(String stockCode, boolean isFunctionalCurrency,
			CurrencyType currency, int year, int month)
			throws NoSuchFieldException, SecurityException,
			IllegalArgumentException, IllegalAccessException,
			NoSuchMethodException, InvocationTargetException,
			InstantiationException, IOException {
		HBaseRowKey key = getRowKey(stockCode, isFunctionalCurrency, currency,
				year, month);
		return super.exists(key);
	}

	@Override
	protected HbaseAssistant getHbaseAssistant() {
		return hbaseAssistant;
	}

	private HBaseRowKey getRowKey(String stockCode,
			boolean isFunctionalCurrency, CurrencyType currency, int year,
			int month) {
		MonthlyOperatingIncome entity = new MonthlyOperatingIncome();
		generateRowKey(stockCode, isFunctionalCurrency, currency, year, month,
				entity);
		return entity.getRowKey();
	}

	private void generateRowKey(String stockCode, boolean isFunctionalCurrency,
			CurrencyType currency, int year, int month,
			MonthlyOperatingIncome entity) {
		entity.new RowKey(stockCode, isFunctionalCurrency, currency, year,
				month, entity);
	}
}
