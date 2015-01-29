package idv.hsiehpinghan.stockdao.repository.hbase;

import idv.hsiehpinghan.hbaseassistant.abstractclass.HBaseRowKey;
import idv.hsiehpinghan.hbaseassistant.abstractclass.HBaseTable;
import idv.hsiehpinghan.hbaseassistant.assistant.HbaseAssistant;
import idv.hsiehpinghan.hbaseassistant.repository.RepositoryBase;
import idv.hsiehpinghan.stockdao.entity.StockClosingCondition;
import idv.hsiehpinghan.stockdao.entity.StockClosingCondition.PriceFamily;
import idv.hsiehpinghan.stockdao.entity.StockClosingCondition.PriceFamily.PriceQualifier;
import idv.hsiehpinghan.stockdao.entity.StockClosingCondition.PriceFamily.PriceValue;
import idv.hsiehpinghan.stockdao.entity.StockClosingCondition.VolumeFamily;
import idv.hsiehpinghan.stockdao.entity.StockClosingCondition.VolumeFamily.VolumeQualifier;
import idv.hsiehpinghan.stockdao.entity.StockClosingCondition.VolumeFamily.VolumeValue;
import idv.hsiehpinghan.stockdao.repository.IStockClosingConditionRepository;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.Map.Entry;

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
	public StockClosingCondition put(String stockCode,
			Map<PriceQualifier, PriceValue> priceMap,
			Map<VolumeQualifier, VolumeValue> volumeMap)
			throws IllegalAccessException {
		StockClosingCondition entity = generateEntity(stockCode, priceMap,
				volumeMap);
		hbaseAssistant.put(entity);
		return entity;
	}

	@Override
	public StockClosingCondition get(String stockCode)
			throws IllegalAccessException, NoSuchMethodException,
			SecurityException, InstantiationException,
			IllegalArgumentException, InvocationTargetException, IOException {
		HBaseRowKey rowKey = getRowKey(stockCode);
		return (StockClosingCondition) hbaseAssistant.get(rowKey);
	}

	private HBaseRowKey getRowKey(String stockCode) {
		StockClosingCondition entity = new StockClosingCondition();
		generateRowKey(entity, stockCode);
		return entity.getRowKey();
	}

	private StockClosingCondition generateEntity(String stockCode,
			Map<PriceQualifier, PriceValue> priceMap,
			Map<VolumeQualifier, VolumeValue> volumeMap) {
		StockClosingCondition entity = new StockClosingCondition();
		generateRowKey(entity, stockCode);
		generateColumnFamilies(entity, priceMap, volumeMap);
		return entity;
	}

	private void generateRowKey(StockClosingCondition entity, String stockCode) {
		entity.new RowKey(stockCode, entity);
	}

	private void generateColumnFamilies(StockClosingCondition entity,
			Map<PriceQualifier, PriceValue> priceMap,
			Map<VolumeQualifier, VolumeValue> volumeMap) {
		Date date = Calendar.getInstance().getTime();
		// Generate priceFamily.
		generatePriceFamily(entity, date, priceMap);
		// Generate volumeFamily.
		generateVolumeFamily(entity, date, volumeMap);
	}

	private void generatePriceFamily(StockClosingCondition entity, Date date,
			Map<PriceQualifier, PriceValue> priceMap) {
		PriceFamily priceFamily = entity.getPriceFamily();
		for (Entry<PriceQualifier, PriceValue> ent : priceMap.entrySet()) {
			priceFamily.add(ent.getKey(), date, ent.getValue());
		}
	}

	private void generateVolumeFamily(StockClosingCondition entity, Date date,
			Map<VolumeQualifier, VolumeValue> volumeMap) {
		VolumeFamily volumeFamily = entity.getVolumeFamily();
		for (Entry<VolumeQualifier, VolumeValue> ent : volumeMap.entrySet()) {
			volumeFamily.add(ent.getKey(), date, ent.getValue());
		}
	}
}
