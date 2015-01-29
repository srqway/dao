package idv.hsiehpinghan.stockdao.repository;

import idv.hsiehpinghan.hbaseassistant.repository.IRepositoryBase;
import idv.hsiehpinghan.stockdao.entity.FinancialReportPresentation;
import idv.hsiehpinghan.xbrlassistant.enumeration.XbrlTaxonomyVersion;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import com.fasterxml.jackson.databind.node.ObjectNode;

public interface IFinancialReportPresentationRepository extends IRepositoryBase {
	/**
	 * Put presentationNode to hbase.
	 * 
	 * @param version
	 * @param presentationIds
	 * @param presentationNode
	 * @return
	 * @throws IllegalAccessException
	 */
	FinancialReportPresentation put(XbrlTaxonomyVersion version,
			List<String> presentationIds, ObjectNode presentationNode)
			throws IllegalAccessException;

	/**
	 * Get all presentationNodes from hbase.(Include BalanceSheet,
	 * StatementOfComprehensiveIncome, StatementOfCashFlows,
	 * StatementOfChangesInEquity)
	 * 
	 * @param version
	 * @return
	 * @throws IllegalAccessException
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 * @throws InstantiationException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 * @throws IOException
	 */
	FinancialReportPresentation get(XbrlTaxonomyVersion version)
			throws IllegalAccessException, NoSuchMethodException,
			SecurityException, InstantiationException,
			IllegalArgumentException, InvocationTargetException, IOException;

	/**
	 * Get entity of presentationIds from hbase.
	 * 
	 * @param version
	 * @param presentationIds
	 * @return
	 * @throws IllegalAccessException
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 * @throws InstantiationException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 * @throws IOException
	 */
	FinancialReportPresentation get(XbrlTaxonomyVersion version,
			List<String> presentationIds) throws IllegalAccessException,
			NoSuchMethodException, SecurityException, InstantiationException,
			IllegalArgumentException, InvocationTargetException, IOException;

	/**
	 * Check if entity exists.
	 * 
	 * @param rowKey
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
	boolean exists(XbrlTaxonomyVersion rowKey) throws NoSuchFieldException,
			SecurityException, IllegalArgumentException,
			IllegalAccessException, NoSuchMethodException,
			InvocationTargetException, InstantiationException, IOException;
}
