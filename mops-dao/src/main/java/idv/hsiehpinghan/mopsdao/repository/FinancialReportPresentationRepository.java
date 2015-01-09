package idv.hsiehpinghan.mopsdao.repository;

import idv.hsiehpinghan.hbaseassistant.abstractclass.HBaseColumnQualifier;
import idv.hsiehpinghan.hbaseassistant.abstractclass.HBaseRowKey;
import idv.hsiehpinghan.hbaseassistant.abstractclass.HBaseValue;
import idv.hsiehpinghan.hbaseassistant.utility.HbaseAssistant;
import idv.hsiehpinghan.mopsdao.entity.FinancialReportPresentation;
import idv.hsiehpinghan.mopsdao.entity.FinancialReportPresentation.JsonFamily;
import idv.hsiehpinghan.mopsdao.entity.FinancialReportPresentation.JsonFamily.IdQualifier;
import idv.hsiehpinghan.mopsdao.entity.FinancialReportPresentation.JsonFamily.JsonValue;
import idv.hsiehpinghan.mopsdao.entity.FinancialReportPresentation.Key;
import idv.hsiehpinghan.xbrlassistant.enumeration.XbrlTaxonomyVersion;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.NavigableMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

@Repository
public class FinancialReportPresentationRepository extends
		MopsDaoRepositoryBase {
	@Autowired
	private HbaseAssistant hbaseAssistant;
	@Autowired
	private ObjectMapper objectMapper;

	@Override
	public String getTargetTableName() {
		return getTargetTableClass().getSimpleName();
	}

	@Override
	public Class<?> getTargetTableClass() {
		return FinancialReportPresentation.class;
	}

	/**
	 * Put presentationNode to hbase.
	 * 
	 * @param version
	 * @param presentationIds
	 * @param presentNode
	 * @throws IllegalAccessException
	 */
	public void put(XbrlTaxonomyVersion version, List<String> presentationIds,
			ObjectNode presentationNode) throws IllegalAccessException {
		FinancialReportPresentation entity = getEntity(version,
				presentationIds, presentationNode);
		hbaseAssistant.put(entity);
	}

	/**
	 * Get all presentationNodes from hbase.(Include BalanceSheet,
	 * StatementOfComprehensiveIncome, StatementOfCashFlows,
	 * StatementOfChangesInEquity)
	 * 
	 * @param version
	 * @return
	 * @throws NoSuchFieldException
	 * @throws SecurityException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws NoSuchMethodException
	 * @throws InvocationTargetException
	 * @throws InstantiationException
	 * @throws JsonProcessingException
	 * @throws IOException
	 */
	public ObjectNode get(XbrlTaxonomyVersion version)
			throws NoSuchFieldException, SecurityException,
			IllegalArgumentException, IllegalAccessException,
			NoSuchMethodException, InvocationTargetException,
			InstantiationException, JsonProcessingException, IOException {
		return get(version ,null);
	}

	/**
	 * Get presentationNodes of presentationIds from hbase.
	 * @param version
	 * @param presentationIds
	 * @return
	 * @throws NoSuchFieldException
	 * @throws SecurityException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws NoSuchMethodException
	 * @throws InvocationTargetException
	 * @throws InstantiationException
	 * @throws JsonProcessingException
	 * @throws IOException
	 */
	public ObjectNode get(XbrlTaxonomyVersion version, List<String> presentationIds)
			throws NoSuchFieldException, SecurityException,
			IllegalArgumentException, IllegalAccessException,
			NoSuchMethodException, InvocationTargetException,
			InstantiationException, JsonProcessingException, IOException {
		HBaseRowKey rowKey = getRowKey(version);
		FinancialReportPresentation entity = (FinancialReportPresentation) hbaseAssistant
				.get(rowKey);
		NavigableMap<HBaseColumnQualifier, NavigableMap<Date, HBaseValue>> qualMap = entity
				.getJsonFamily().getQualifierVersionValueMap();
		ObjectNode presentNode = objectMapper.createObjectNode();
		for (Map.Entry<HBaseColumnQualifier, NavigableMap<Date, HBaseValue>> qualEntry : qualMap
				.entrySet()) {
			String presentId = ((IdQualifier) qualEntry.getKey())
					.getPresentationId();
			if(presentationIds != null && presentationIds.contains(presentId) == false) {
				continue;
			}
			NavigableMap<Date, HBaseValue> verMap = qualEntry.getValue();
			for (Map.Entry<Date, HBaseValue> verEntry : verMap.entrySet()) {
				JsonValue jsonVal = (JsonValue) verEntry.getValue();
				JsonNode subPresentNode = objectMapper.readTree(jsonVal
						.getJson());
				presentNode.set(presentId, subPresentNode);
				// Get the newest record.
				break;
			}
		}
		return presentNode;
	}
	/**
	 * Drop table.
	 * 
	 * @param tableName
	 * @throws IOException
	 */
	public void dropTable(String tableName) throws IOException {
		hbaseAssistant.dropTable(tableName);
	}

	/**
	 * Create table.
	 * 
	 * @param clazz
	 * @throws IOException
	 */
	public void createTable(Class<?> clazz) throws IOException {
		hbaseAssistant.createTable(clazz);
	}

	/**
	 * Check if table exists.
	 * 
	 * @param tableName
	 * @return
	 * @throws IOException
	 */
	public boolean isTableExists(String tableName) throws IOException {
		return hbaseAssistant.isTableExists(tableName);
	}

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
	 */
	public boolean exists(XbrlTaxonomyVersion rowKey)
			throws NoSuchFieldException, SecurityException,
			IllegalArgumentException, IllegalAccessException,
			NoSuchMethodException, InvocationTargetException,
			InstantiationException {
		HBaseRowKey key = getRowKey(rowKey);
		return hbaseAssistant.get(key) != null;
	}

	private HBaseRowKey getRowKey(XbrlTaxonomyVersion version) {
		FinancialReportPresentation entity = new FinancialReportPresentation();
		generateRowKey(entity, version);
		return entity.getRowKey();
	}

	private FinancialReportPresentation getEntity(XbrlTaxonomyVersion version,
			List<String> presentationIds, ObjectNode presentNode) {
		FinancialReportPresentation entity = new FinancialReportPresentation();
		generateRowKey(entity, version);
		generateColumnFamily(entity, presentationIds, presentNode);

		return entity;
	}

	private void generateRowKey(FinancialReportPresentation entity,
			XbrlTaxonomyVersion version) {
		Key key = entity.new Key(version.toString(), entity);
		entity.setRowKey(key);
	}

	private void generateColumnFamily(FinancialReportPresentation entity,
			List<String> presentationIds, ObjectNode presentNode) {
		JsonFamily family = entity.new JsonFamily();
		entity.setJsonFamily(family);
		Date date = Calendar.getInstance().getTime();
		for (String presentId : presentationIds) {
			JsonNode node = presentNode.get(presentId);
			if (node == null) {
				continue;
			}
			family.add(presentId, date, node.toString());
		}
	}

}
