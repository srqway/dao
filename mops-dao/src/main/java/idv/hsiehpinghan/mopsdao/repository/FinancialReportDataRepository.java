package idv.hsiehpinghan.mopsdao.repository;

import idv.hsiehpinghan.hbaseassistant.abstractclass.HBaseRowKey;
import idv.hsiehpinghan.hbaseassistant.assistant.HbaseAssistant;
import idv.hsiehpinghan.mopsdao.entity.FinancialReportData;
import idv.hsiehpinghan.mopsdao.entity.FinancialReportData.RowKey;
import idv.hsiehpinghan.mopsdao.enumeration.ReportType;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class FinancialReportDataRepository extends MopsDaoRepositoryBase {
	@Autowired
	private HbaseAssistant hbaseAssistant;

	@Override
	public Class<?> getTargetTableClass() {
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

	private HBaseRowKey getRowKey(String stockCode, ReportType reportType,
			int year, int season) {
		FinancialReportData entity = new FinancialReportData();
		generateRowKey(entity, stockCode, reportType, year, season);
		return entity.getRowKey();
	}

	private void generateRowKey(FinancialReportData entity, String stockCode,
			ReportType reportType, int year, int season) {
		RowKey key = entity.new RowKey(stockCode, reportType, year, season,
				entity);
		entity.setRowKey(key);
	}
}
