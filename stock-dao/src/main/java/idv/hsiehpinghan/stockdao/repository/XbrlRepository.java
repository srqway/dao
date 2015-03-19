package idv.hsiehpinghan.stockdao.repository;

import idv.hsiehpinghan.hbaseassistant.abstractclass.HBaseRowKey;
import idv.hsiehpinghan.hbaseassistant.abstractclass.HBaseTable;
import idv.hsiehpinghan.hbaseassistant.assistant.HbaseAssistant;
import idv.hsiehpinghan.hbaseassistant.repository.RepositoryBase;
import idv.hsiehpinghan.hbaseassistant.utility.ByteConvertUtility;
import idv.hsiehpinghan.stockdao.entity.Xbrl;
import idv.hsiehpinghan.stockdao.entity.Xbrl.RowKey;
import idv.hsiehpinghan.stockdao.enumeration.ReportType;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

import org.apache.hadoop.hbase.filter.BinaryComparator;
import org.apache.hadoop.hbase.filter.CompareFilter;
import org.apache.hadoop.hbase.filter.FamilyFilter;
import org.apache.hadoop.hbase.filter.FilterList;
import org.apache.hadoop.hbase.filter.FuzzyRowFilter;
import org.apache.hadoop.hbase.filter.KeyOnlyFilter;
import org.apache.hadoop.hbase.filter.RowFilter;
import org.apache.hadoop.hbase.util.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class XbrlRepository extends RepositoryBase {
	@Autowired
	private HbaseAssistant hbaseAssistant;

	@Override
	public Class<? extends HBaseTable> getTargetTableClass() {
		return Xbrl.class;
	}

	public Xbrl generateEntity(String stockCode, ReportType reportType,
			int year, int season) {
		Xbrl entity = new Xbrl();
		generateRowKey(stockCode, reportType, year, season, entity);
		return entity;
	}

	public Xbrl get(String stockCode, ReportType reportType, int year,
			int season) throws IllegalAccessException, NoSuchMethodException,
			SecurityException, InstantiationException,
			IllegalArgumentException, InvocationTargetException, IOException {
		HBaseRowKey rowKey = getRowKey(stockCode, reportType, year, season);
		return (Xbrl) hbaseAssistant.get(rowKey);
	}

	public TreeSet<Xbrl> fuzzyScan(String stockCode, ReportType reportType,
			Integer year, Integer season) {
		Xbrl.RowKey rowKey = (Xbrl.RowKey) getRowKey(stockCode, reportType,
				year == null ? 0 : year, season == null ? 0 : season);
		List<Pair<byte[], byte[]>> fuzzyKeysData = new ArrayList<Pair<byte[], byte[]>>();
		Pair<byte[], byte[]> pair = new Pair<byte[], byte[]>(rowKey.getBytes(),
				rowKey.getFuzzyBytes(stockCode, reportType, year, season));
		fuzzyKeysData.add(pair);
		FuzzyRowFilter fuzzyRowFilter = new FuzzyRowFilter(fuzzyKeysData);
		@SuppressWarnings("unchecked")
		TreeSet<Xbrl> xbrls = (TreeSet<Xbrl>) (Object) hbaseAssistant.scan(
				getTargetTableClass(), fuzzyRowFilter);
		return xbrls;
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

	public Xbrl getWithInfoFamilyOnly(String stockCode, ReportType reportType,
			int year, int season) {
		Xbrl entity = generateEntity(stockCode, reportType, year, season);
		RowFilter rowFilter = getRowFilter(entity);
		FamilyFilter familyFilter = getFamilyFilter("infoFamily");
		FilterList filterList = new FilterList(rowFilter, familyFilter);
		TreeSet<HBaseTable> entities = hbaseAssistant.scan(Xbrl.class,
				filterList);
		if (entities.size() <= 0) {
			return null;
		}
		return (Xbrl) entities.first();
	}

	public TreeSet<Xbrl> scanWithInfoFamilyOnly() {
		FamilyFilter familyFilter = getFamilyFilter("infoFamily");
		@SuppressWarnings("unchecked")
		TreeSet<Xbrl> entities = (TreeSet<Xbrl>) (Object) hbaseAssistant.scan(
				Xbrl.class, familyFilter);
		return entities;
	}

	public Xbrl getWithInstanceFamilyOnly(String stockCode,
			ReportType reportType, int year, int season) {
		Xbrl entity = generateEntity(stockCode, reportType, year, season);
		RowFilter rowFilter = getRowFilter(entity);
		FamilyFilter familyFilter = getFamilyFilter("instanceFamily");
		FilterList filterList = new FilterList(rowFilter, familyFilter);
		TreeSet<HBaseTable> entities = hbaseAssistant.scan(Xbrl.class,
				filterList);
		if (entities.size() <= 0) {
			return null;
		}
		return (Xbrl) entities.first();
	}

	public TreeSet<Xbrl> scanWithInstanceFamilyOnly() {
		FamilyFilter familyFilter = getFamilyFilter("instanceFamily");
		@SuppressWarnings("unchecked")
		TreeSet<Xbrl> entities = (TreeSet<Xbrl>) (Object) hbaseAssistant.scan(
				Xbrl.class, familyFilter);
		return entities;
	}

	public Xbrl getWithItemFamilyOnly(String stockCode, ReportType reportType,
			int year, int season) {
		Xbrl entity = generateEntity(stockCode, reportType, year, season);
		RowFilter rowFilter = getRowFilter(entity);
		FamilyFilter familyFilter = getFamilyFilter("itemFamily");
		FilterList filterList = new FilterList(rowFilter, familyFilter);
		TreeSet<HBaseTable> entities = hbaseAssistant.scan(Xbrl.class,
				filterList);
		if (entities.size() <= 0) {
			return null;
		}
		return (Xbrl) entities.first();
	}

	public TreeSet<Xbrl> scanWithItemFamilyOnly() {
		FamilyFilter familyFilter = getFamilyFilter("itemFamily");
		@SuppressWarnings("unchecked")
		TreeSet<Xbrl> entities = (TreeSet<Xbrl>) (Object) hbaseAssistant.scan(
				Xbrl.class, familyFilter);
		return entities;
	}

	public Xbrl getWithGrowthFamilyOnly(String stockCode,
			ReportType reportType, int year, int season) {
		Xbrl entity = generateEntity(stockCode, reportType, year, season);
		RowFilter rowFilter = getRowFilter(entity);
		FamilyFilter familyFilter = getFamilyFilter("growthFamily");
		FilterList filterList = new FilterList(rowFilter, familyFilter);
		TreeSet<HBaseTable> entities = hbaseAssistant.scan(Xbrl.class,
				filterList);
		if (entities.size() <= 0) {
			return null;
		}
		return (Xbrl) entities.first();
	}

	public TreeSet<Xbrl> scanWithGrowthFamilyOnly() {
		FamilyFilter familyFilter = getFamilyFilter("growthFamily");
		@SuppressWarnings("unchecked")
		TreeSet<Xbrl> entities = (TreeSet<Xbrl>) (Object) hbaseAssistant.scan(
				Xbrl.class, familyFilter);
		return entities;
	}

	public Xbrl getWithRatioFamilyOnly(String stockCode, ReportType reportType,
			int year, int season) {
		Xbrl entity = generateEntity(stockCode, reportType, year, season);
		RowFilter rowFilter = getRowFilter(entity);
		FamilyFilter familyFilter = getFamilyFilter("ratioFamily");
		FilterList filterList = new FilterList(rowFilter, familyFilter);
		TreeSet<HBaseTable> entities = hbaseAssistant.scan(Xbrl.class,
				filterList);
		if (entities.size() <= 0) {
			return null;
		}
		return (Xbrl) entities.first();
	}

	public TreeSet<Xbrl> scanWithRatioFamilyOnly() {
		FamilyFilter familyFilter = getFamilyFilter("ratioFamily");
		@SuppressWarnings("unchecked")
		TreeSet<Xbrl> entities = (TreeSet<Xbrl>) (Object) hbaseAssistant.scan(
				Xbrl.class, familyFilter);
		return entities;
	}

	public Xbrl getWithRatioDifferenceFamilyOnly(String stockCode,
			ReportType reportType, int year, int season) {
		Xbrl entity = generateEntity(stockCode, reportType, year, season);
		RowFilter rowFilter = getRowFilter(entity);
		FamilyFilter familyFilter = getFamilyFilter("ratioDifferenceFamily");
		FilterList filterList = new FilterList(rowFilter, familyFilter);
		TreeSet<HBaseTable> entities = hbaseAssistant.scan(Xbrl.class,
				filterList);
		if (entities.size() <= 0) {
			return null;
		}
		return (Xbrl) entities.first();
	}

	public TreeSet<Xbrl> scanWithRatioDifferenceFamilyOnly() {
		FamilyFilter familyFilter = getFamilyFilter("ratioDifferenceFamily");
		@SuppressWarnings("unchecked")
		TreeSet<Xbrl> entities = (TreeSet<Xbrl>) (Object) hbaseAssistant.scan(
				Xbrl.class, familyFilter);
		return entities;
	}

	private RowFilter getRowFilter(Xbrl entity) {
		return new RowFilter(CompareFilter.CompareOp.EQUAL,
				new BinaryComparator(entity.getRowKey().getBytes()));
	}

	private FamilyFilter getFamilyFilter(String columnFamilyName) {
		return new FamilyFilter(CompareFilter.CompareOp.EQUAL,
				new BinaryComparator(
						ByteConvertUtility.toBytes(columnFamilyName)));
	}

	@Override
	protected HbaseAssistant getHbaseAssistant() {
		return hbaseAssistant;
	}

	private HBaseRowKey getRowKey(String stockCode, ReportType reportType,
			int year, int season) {
		Xbrl entity = new Xbrl();
		generateRowKey(stockCode, reportType, year, season, entity);
		return entity.getRowKey();
	}

	private void generateRowKey(String stockCode, ReportType reportType,
			int year, int season, Xbrl entity) {
		entity.new RowKey(stockCode, reportType, year, season, entity);
	}
}
