package idv.hsiehpinghan.stockdao.repository.hbase;

import idv.hsiehpinghan.hbaseassistant.abstractclass.HBaseRowKey;
import idv.hsiehpinghan.hbaseassistant.abstractclass.HBaseTable;
import idv.hsiehpinghan.hbaseassistant.assistant.HbaseAssistant;
import idv.hsiehpinghan.hbaseassistant.repository.RepositoryBase;
import idv.hsiehpinghan.stockdao.entity.MonthlyOperatingIncome;
import idv.hsiehpinghan.stockdao.repository.IMonthlyOperatingIncomeRepository;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class MonthlyOperatingIncomeRepository extends RepositoryBase implements
		IMonthlyOperatingIncomeRepository {

	@Autowired
	private HbaseAssistant hbaseAssistant;

	@Override
	public HbaseAssistant getHbaseAssistant() {
		return hbaseAssistant;
	}

	@Override
	public Class<? extends HBaseTable> getTargetTableClass() {
		return MonthlyOperatingIncome.class;
	}

	@Override
	public void put(List<MonthlyOperatingIncome> entities)
			throws IllegalAccessException {
		hbaseAssistant.put(entities);
	}

	@Override
	public MonthlyOperatingIncome get(String stockCode)
			throws IllegalAccessException, NoSuchMethodException,
			SecurityException, InstantiationException,
			IllegalArgumentException, InvocationTargetException, IOException {
		HBaseRowKey rowKey = getRowKey(stockCode);
		return (MonthlyOperatingIncome) hbaseAssistant.get(rowKey);
	}

	private HBaseRowKey getRowKey(String stockCode) {
		MonthlyOperatingIncome entity = new MonthlyOperatingIncome();
		generateRowKey(entity, stockCode);
		return entity.getRowKey();
	}

	private void generateRowKey(MonthlyOperatingIncome entity, String stockCode) {
		entity.new RowKey(stockCode, entity);
	}
}
