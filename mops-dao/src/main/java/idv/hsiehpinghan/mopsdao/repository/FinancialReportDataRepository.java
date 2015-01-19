package idv.hsiehpinghan.mopsdao.repository;

import idv.hsiehpinghan.hbaseassistant.abstractclass.HBaseRowKey;
import idv.hsiehpinghan.hbaseassistant.abstractclass.HBaseTable;
import idv.hsiehpinghan.hbaseassistant.assistant.HbaseAssistant;
import idv.hsiehpinghan.hbaseassistant.repository.RepositoryBase;
import idv.hsiehpinghan.mopsdao.entity.FinancialReportData;
import idv.hsiehpinghan.mopsdao.enumeration.ReportType;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.apache.hadoop.hbase.filter.Filter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class FinancialReportDataRepository extends RepositoryBase {
	@Autowired
	private HbaseAssistant hbaseAssistant;

	@Override
	public Class<? extends HBaseTable> getTargetTableClass() {
		return FinancialReportData.class;
	}

	@Override
	public HbaseAssistant getHbaseAssistant() {
		return hbaseAssistant;
	}

	public void put(FinancialReportData entity) throws IllegalAccessException {
		hbaseAssistant.put(entity);
	}

	public FinancialReportData get(String stockCode, ReportType reportType,
			int year, int season) throws IllegalAccessException,
			NoSuchMethodException, SecurityException, InstantiationException,
			IllegalArgumentException, InvocationTargetException, IOException {
		HBaseRowKey rowKey = getRowKey(stockCode, reportType, year, season);
		return (FinancialReportData) hbaseAssistant.get(rowKey);
	}

	public boolean exists(String stockCode, ReportType reportType, int year,
			int season) throws NoSuchFieldException, SecurityException,
			IllegalArgumentException, IllegalAccessException,
			NoSuchMethodException, InvocationTargetException,
			InstantiationException, IOException {
		HBaseRowKey rowKey = getRowKey(stockCode, reportType, year, season);
		return exists(rowKey);
	}

	@SuppressWarnings("unchecked")
	public List<FinancialReportData> scan(Filter filter) {
		return (List<FinancialReportData>) (Object) hbaseAssistant.scan(
				getTargetTableClass(), filter);
	}

	private HBaseRowKey getRowKey(String stockCode, ReportType reportType,
			int year, int season) {
		FinancialReportData entity = new FinancialReportData();
		generateRowKey(entity, stockCode, reportType, year, season);
		return entity.getRowKey();
	}

	private void generateRowKey(FinancialReportData entity, String stockCode,
			ReportType reportType, int year, int season) {
		entity.new RowKey(stockCode, reportType, year, season, entity);
	}
}
