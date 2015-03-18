package idv.hsiehpinghan.stockdao.repository;

import idv.hsiehpinghan.hbaseassistant.abstractclass.HBaseRowKey;
import idv.hsiehpinghan.hbaseassistant.abstractclass.HBaseTable;
import idv.hsiehpinghan.hbaseassistant.assistant.HbaseAssistant;
import idv.hsiehpinghan.hbaseassistant.repository.RepositoryBase;
import idv.hsiehpinghan.stockdao.entity.RatioDifference;
import idv.hsiehpinghan.stockdao.entity.RatioDifference.RowKey;
import idv.hsiehpinghan.stockdao.enumeration.ReportType;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

import org.apache.hadoop.hbase.filter.FuzzyRowFilter;
import org.apache.hadoop.hbase.filter.KeyOnlyFilter;
import org.apache.hadoop.hbase.util.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class RatioDifferenceRepository extends RepositoryBase {
	@Autowired
	private HbaseAssistant hbaseAssistant;

	@Override
	public Class<? extends HBaseTable> getTargetTableClass() {
		return RatioDifference.class;
	}

	public RatioDifference generateEntity(String stockCode,
			ReportType reportType, int year, int season) {
		RatioDifference entity = new RatioDifference();
		generateRowKey(stockCode, reportType, year, season, entity);
		return entity;
	}

	public RatioDifference get(String stockCode, ReportType reportType,
			int year, int season) throws IllegalAccessException,
			NoSuchMethodException, SecurityException, InstantiationException,
			IllegalArgumentException, InvocationTargetException, IOException {
		HBaseRowKey rowKey = getRowKey(stockCode, reportType, year, season);
		return (RatioDifference) hbaseAssistant.get(rowKey);
	}

	public TreeSet<RatioDifference> fuzzyScan(String stockCode,
			ReportType reportType, Integer year, Integer season) {
		RatioDifference.RowKey rowKey = (RatioDifference.RowKey) getRowKey(
				stockCode, reportType, year == null ? 0 : year,
				season == null ? 0 : season);
		List<Pair<byte[], byte[]>> fuzzyKeysData = new ArrayList<Pair<byte[], byte[]>>();
		Pair<byte[], byte[]> pair = new Pair<byte[], byte[]>(rowKey.getBytes(),
				rowKey.getFuzzyBytes(stockCode, reportType, year, season));
		fuzzyKeysData.add(pair);
		FuzzyRowFilter fuzzyRowFilter = new FuzzyRowFilter(fuzzyKeysData);
		@SuppressWarnings("unchecked")
		TreeSet<RatioDifference> ratioDifferences = (TreeSet<RatioDifference>) (Object) hbaseAssistant
				.scan(getTargetTableClass(), fuzzyRowFilter);
		return ratioDifferences;
	}

	public int getRowAmount() {
		return hbaseAssistant.getRowAmount(getTargetTableClass());
	}

	public TreeSet<RowKey> getRowKeys() {
		TreeSet<HBaseTable> entities = hbaseAssistant.scan(
				getTargetTableClass(), new KeyOnlyFilter());
		TreeSet<RowKey> rowKeys = new TreeSet<RowKey>();
		for (HBaseTable entity : entities) {
			RowKey rowKey = (RowKey) entity.getRowKey();
			rowKeys.add(rowKey);
		}
		return rowKeys;
	}

	public boolean exists(String stockCode, ReportType reportType, int year,
			int season) throws NoSuchFieldException, SecurityException,
			IllegalArgumentException, IllegalAccessException,
			NoSuchMethodException, InvocationTargetException,
			InstantiationException, IOException {
		HBaseRowKey key = getRowKey(stockCode, reportType, year, season);
		return super.exists(key);
	}

	@Override
	protected HbaseAssistant getHbaseAssistant() {
		return hbaseAssistant;
	}

	private HBaseRowKey getRowKey(String stockCode, ReportType reportType,
			int year, int season) {
		RatioDifference entity = new RatioDifference();
		generateRowKey(stockCode, reportType, year, season, entity);
		return entity.getRowKey();
	}

	private void generateRowKey(String stockCode, ReportType reportType,
			int year, int season, RatioDifference entity) {
		entity.new RowKey(stockCode, reportType, year, season, entity);
	}
}
