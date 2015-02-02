package idv.hsiehpinghan.stockdao.repository.hbase;

import idv.hsiehpinghan.hbaseassistant.abstractclass.HBaseRowKey;
import idv.hsiehpinghan.hbaseassistant.abstractclass.HBaseTable;
import idv.hsiehpinghan.hbaseassistant.assistant.HbaseAssistant;
import idv.hsiehpinghan.hbaseassistant.repository.RepositoryBase;
import idv.hsiehpinghan.stockdao.entity.StockClosingCondition;
import idv.hsiehpinghan.stockdao.repository.IStockClosingConditionRepository;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class StockClosingConditionRepository extends RepositoryBase implements
		IStockClosingConditionRepository {

	@Autowired
	private HbaseAssistant hbaseAssistant;

	@Override
	public Class<? extends HBaseTable> getTargetTableClass() {
		return StockClosingCondition.class;
	}

	@Override
	public HbaseAssistant getHbaseAssistant() {
		return hbaseAssistant;
	}

	@Override
	public void put(List<StockClosingCondition> entities)
			throws IllegalAccessException {
		hbaseAssistant.put(entities);
	}

	@Override
	public StockClosingCondition get(String stockCode, Date date)
			throws IllegalAccessException, NoSuchMethodException,
			SecurityException, InstantiationException,
			IllegalArgumentException, InvocationTargetException, IOException {
		HBaseRowKey rowKey = getRowKey(stockCode, date);
		return (StockClosingCondition) hbaseAssistant.get(rowKey);
	}

	@Override
	public boolean exists(String stockCode, Date date)
			throws NoSuchFieldException, SecurityException,
			IllegalArgumentException, IllegalAccessException,
			NoSuchMethodException, InvocationTargetException,
			InstantiationException, IOException {
		HBaseRowKey key = getRowKey(stockCode, date);
		return super.exists(key);
	}

	private HBaseRowKey getRowKey(String stockCode, Date date) {
		StockClosingCondition entity = new StockClosingCondition();
		generateRowKey(entity, stockCode, date);
		return entity.getRowKey();
	}

	private void generateRowKey(StockClosingCondition entity, String stockCode,
			Date date) {
		entity.new RowKey(stockCode, date, entity);
	}

	// private StockClosingCondition generateEntity(String stockCode, Date date,
	// Map<PriceQualifier, PriceValue> priceMap,
	// Map<VolumeQualifier, VolumeValue> volumeMap) {
	// StockClosingCondition entity = new StockClosingCondition();
	// generateRowKey(entity, stockCode, date);
	// generateColumnFamilies(entity, priceMap, volumeMap);
	// return entity;
	// }

	// private void generateColumnFamilies(StockClosingCondition entity,
	// Map<PriceQualifier, PriceValue> priceMap,
	// Map<VolumeQualifier, VolumeValue> volumeMap) {
	// Date date = Calendar.getInstance().getTime();
	// // Generate priceFamily.
	// generatePriceFamily(entity, date, priceMap);
	// // Generate volumeFamily.
	// generateVolumeFamily(entity, date, volumeMap);
	// }

	// private void generatePriceFamily(StockClosingCondition entity, Date date,
	// Map<PriceQualifier, PriceValue> priceMap) {
	// PriceFamily priceFamily = entity.getPriceFamily();
	// for (Entry<PriceQualifier, PriceValue> ent : priceMap.entrySet()) {
	// priceFamily.add(ent.getKey(), date, ent.getValue());
	// }
	// }
	//
	// private void generateVolumeFamily(StockClosingCondition entity, Date
	// date,
	// Map<VolumeQualifier, VolumeValue> volumeMap) {
	// VolumeFamily volumeFamily = entity.getVolumeFamily();
	// for (Entry<VolumeQualifier, VolumeValue> ent : volumeMap.entrySet()) {
	// volumeFamily.add(ent.getKey(), date, ent.getValue());
	// }
	// }
}
