package idv.hsiehpinghan.stockdao.repository;

import idv.hsiehpinghan.hbaseassistant.abstractclass.HBaseRowKey;
import idv.hsiehpinghan.hbaseassistant.abstractclass.HBaseTable;
import idv.hsiehpinghan.hbaseassistant.assistant.HbaseAssistant;
import idv.hsiehpinghan.hbaseassistant.repository.RepositoryBase;
import idv.hsiehpinghan.stockdao.entity.Xbrl;
import idv.hsiehpinghan.stockdao.enumeration.ReportType;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

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

	/**
	 * Generate entity.
	 * 
	 * @param stockCode
	 * @param reportType
	 * @param year
	 * @param season
	 * @return
	 */
	public Xbrl generateEntity(String stockCode, ReportType reportType,
			int year, int season) {
		Xbrl entity = new Xbrl();
		generateRowKey(stockCode, reportType, year, season, entity);
		return entity;
	}

	/**
	 * Get entity.
	 * 
	 * @param stockCode
	 * @param reportType
	 * @param year
	 * @param season
	 * @return
	 * @throws IllegalAccessException
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 * @throws InstantiationException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 * @throws IOException
	 */
	public Xbrl get(String stockCode, ReportType reportType, int year,
			int season) throws IllegalAccessException, NoSuchMethodException,
			SecurityException, InstantiationException,
			IllegalArgumentException, InvocationTargetException, IOException {
		HBaseRowKey rowKey = getRowKey(stockCode, reportType, year, season);
		return (Xbrl) hbaseAssistant.get(rowKey);
	}

	public boolean exists(String stockCode, ReportType reportType, int year,
			int season) throws NoSuchFieldException, SecurityException,
			IllegalArgumentException, IllegalAccessException,
			NoSuchMethodException, InvocationTargetException,
			InstantiationException, IOException {
		return super.exists(getRowKey(stockCode, reportType, year, season));
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
