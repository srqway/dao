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

import org.apache.hadoop.hbase.filter.BinaryComparator;
import org.apache.hadoop.hbase.filter.CompareFilter;
import org.apache.hadoop.hbase.filter.FamilyFilter;
import org.apache.hadoop.hbase.filter.Filter;
import org.apache.hadoop.hbase.filter.FilterList;
import org.apache.hadoop.hbase.filter.KeyOnlyFilter;
import org.apache.hadoop.hbase.filter.RowFilter;
import org.apache.hadoop.hbase.util.Bytes;
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

//	public StockInfo getWithCompanyFamilyOnly(String stockCode) {
//		FilterList filters = new FilterList();
//		filters.addFilter(generateRowFilter(stockCode));
//		filters.addFilter(generateFamilyFilter("companyFamily"));
//		List<HBaseTable> entities = getHbaseAssistant().scan(
//				getTargetTableClass(), filters);
//		if (entities.size() > 0) {
//			return (StockInfo) entities.get(0);
//		}
//		return null;
//	}

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

//	private Filter generateRowFilter(String stockCode) {
//		return new RowFilter(CompareFilter.CompareOp.EQUAL,
//				new BinaryComparator(Bytes.toBytes(stockCode)));
//	}
//
//	private Filter generateFamilyFilter(String familyName) {
//		return new FamilyFilter(CompareFilter.CompareOp.EQUAL,
//				new BinaryComparator(Bytes.toBytes(familyName)));
//	}
}
