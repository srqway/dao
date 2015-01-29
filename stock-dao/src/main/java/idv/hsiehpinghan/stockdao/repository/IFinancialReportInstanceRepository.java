package idv.hsiehpinghan.stockdao.repository;

import idv.hsiehpinghan.hbaseassistant.repository.IRepositoryBase;
import idv.hsiehpinghan.stockdao.entity.FinancialReportInstance;
import idv.hsiehpinghan.stockdao.enumeration.ReportType;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.util.List;

import com.fasterxml.jackson.databind.node.ObjectNode;

public interface IFinancialReportInstanceRepository extends IRepositoryBase {
	/**
	 * Put financialReportInstance to hbase.
	 * 
	 * @param stockCode
	 * @param reportType
	 * @param year
	 * @param season
	 * @param instanceNode
	 * @return
	 * @throws IllegalAccessException
	 * @throws ParseException
	 */
	public FinancialReportInstance put(String stockCode, ReportType reportType,
			int year, int season, ObjectNode objNode,
			List<String> PresentationIds) throws IllegalAccessException,
			ParseException;

	public FinancialReportInstance get(String stockCode, ReportType reportType,
			int year, int season) throws NoSuchFieldException,
			SecurityException, IllegalArgumentException,
			IllegalAccessException, NoSuchMethodException,
			InvocationTargetException, InstantiationException, IOException;

	/**
	 * Check if row exists.
	 * 
	 * @param stockCode
	 * @param reportType
	 * @param year
	 * @param season
	 * @return
	 * @throws NoSuchFieldException
	 * @throws SecurityException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws NoSuchMethodException
	 * @throws InvocationTargetException
	 * @throws InstantiationException
	 * @throws IOException
	 */
	boolean exists(String stockCode, ReportType reportType, int year, int season)
			throws NoSuchFieldException, SecurityException,
			IllegalArgumentException, IllegalAccessException,
			NoSuchMethodException, InvocationTargetException,
			InstantiationException, IOException;
}
