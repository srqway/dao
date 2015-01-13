package idv.hsiehpinghan.mopsdao.repository;

import idv.hsiehpinghan.hbaseassistant.abstractclass.HBaseColumnQualifier;
import idv.hsiehpinghan.hbaseassistant.abstractclass.HBaseRowKey;
import idv.hsiehpinghan.hbaseassistant.abstractclass.HBaseValue;
import idv.hsiehpinghan.hbaseassistant.assistant.HbaseAssistant;
import idv.hsiehpinghan.mopsdao.entity.FinancialReportInstance;
import idv.hsiehpinghan.mopsdao.entity.FinancialReportInstance.InfoFamily;
import idv.hsiehpinghan.mopsdao.entity.FinancialReportInstance.InfoFamily.InfoQualifier;
import idv.hsiehpinghan.mopsdao.entity.FinancialReportInstance.InfoFamily.InfoValue;
import idv.hsiehpinghan.mopsdao.entity.FinancialReportInstance.InstanceFamily;
import idv.hsiehpinghan.mopsdao.entity.FinancialReportInstance.Key;
import idv.hsiehpinghan.mopsdao.enumeration.ReportType;
import idv.hsiehpinghan.xbrlassistant.assistant.InstanceAssistant;
import idv.hsiehpinghan.xbrlassistant.enumeration.XbrlTaxonomyVersion;
import idv.hsiehpinghan.xbrlassistant.xbrl.Instance;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.NavigableMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

@Repository
public class FinancialReportInstanceRepository extends MopsDaoRepositoryBase {
	@Autowired
	private HbaseAssistant hbaseAssistant;
	@Autowired
	private ObjectMapper objectMapper;

	@Override
	public Class<?> getTargetTableClass() {
		return FinancialReportInstance.class;
	}

	@Override
	public HbaseAssistant getHbaseAssistant() {
		return hbaseAssistant;
	}

	/**
	 * Put financialReportInstance to hbase.
	 * 
	 * @param stockCode
	 * @param reportType
	 * @param year
	 * @param season
	 * @param taxonymyVersion
	 * @param instanceNode
	 * @return
	 * @throws IllegalAccessException
	 */
	public FinancialReportInstance put(String stockCode, ReportType reportType,
			int year, int season, XbrlTaxonomyVersion taxonymyVersion,
			ObjectNode objNode, List<String> PresentationIds)
			throws IllegalAccessException {
		FinancialReportInstance entity = generateEntity(stockCode, reportType,
				year, season, objNode, PresentationIds);
		hbaseAssistant.put(entity);
		return entity;
	}

	public ObjectNode getAsJson(String stockCode, ReportType reportType,
			int year, int season) throws NoSuchFieldException,
			SecurityException, IllegalArgumentException,
			IllegalAccessException, NoSuchMethodException,
			InvocationTargetException, InstantiationException, IOException {
		HBaseRowKey rowKey = getRowKey(stockCode, reportType, year, season);
		FinancialReportInstance entity = (FinancialReportInstance) hbaseAssistant
				.get(rowKey);
		NavigableMap<HBaseColumnQualifier, NavigableMap<Date, HBaseValue>> qualMap = entity
				.getInfoFamily().getQualifierVersionValueMap();
		ObjectNode instanceNode = objectMapper.createObjectNode();
		for (Map.Entry<HBaseColumnQualifier, NavigableMap<Date, HBaseValue>> qualEntry : qualMap
				.entrySet()) {
			String infoTitle = ((InfoQualifier) qualEntry.getKey())
					.getInfoTitle();
			NavigableMap<Date, HBaseValue> verMap = qualEntry.getValue();
			for (Map.Entry<Date, HBaseValue> verEntry : verMap.descendingMap()
					.entrySet()) {
				Date date = verEntry.getKey();
				InfoValue infoVal = (InfoValue) verEntry.getValue();
			}
		}
		return instanceNode;
	}

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
	public boolean exists(String stockCode, ReportType reportType, int year,
			int season) throws NoSuchFieldException, SecurityException,
			IllegalArgumentException, IllegalAccessException,
			NoSuchMethodException, InvocationTargetException,
			InstantiationException, IOException {
		HBaseRowKey rowKey = getRowKey(stockCode, reportType, year, season);
		return super.exists(rowKey);
	}

	private HBaseRowKey getRowKey(String stockCode, ReportType reportType,
			int year, int season) {
		FinancialReportInstance entity = new FinancialReportInstance();
		generateRowKey(entity, stockCode, reportType, year, season);
		return entity.getRowKey();
	}

	private FinancialReportInstance generateEntity(String stockCode,
			ReportType reportType, int year, int season, ObjectNode objNode,
			List<String> PresentationIds) {
		FinancialReportInstance entity = new FinancialReportInstance();
		generateRowKey(entity, stockCode, reportType, year, season);
		generateColumnFamily(entity, objNode, PresentationIds);
		return entity;
	}

	private void generateRowKey(FinancialReportInstance entity,
			String stockCode, ReportType reportType, int year, int season) {
		Key key = entity.new Key(stockCode, reportType, year, season, entity);
		entity.setRowKey(key);
	}

	private void generateColumnFamily(FinancialReportInstance entity,
			ObjectNode objNode, List<String> PresentationIds) {
		Date date = Calendar.getInstance().getTime();
		// Generate infoFamily.
		InfoFamily infoFamily = entity.getInfoFamily();
		JsonNode infoNode = objNode.get(InstanceAssistant.INFO);
		// Generate infoFamily version.
		String version = infoNode.get(InstanceAssistant.VERSION).textValue();
		infoFamily.add(InstanceAssistant.VERSION, date, version);
		// Generate infoFamily context.
		JsonNode contextNode = infoNode.get(InstanceAssistant.CONTEXT);
		StringBuilder sb = new StringBuilder();
		for(String presentId : PresentationIds) {
			JsonNode presentIdNode = contextNode.get(presentId);
			ArrayNode instantArrNode = (ArrayNode)presentIdNode.get(Instance.Attribute.INSTANT);
			if(instantArrNode != null) {
				sb.setLength(0);
				for(JsonNode context : instantArrNode) {
					sb.append(context.textValue() + ",");
				}
				infoFamily.add(presentId + Instance.Attribute.INSTANT, date, sb.toString());
			}
			
			ArrayNode durationArrNode = (ArrayNode)presentIdNode.get(Instance.Attribute.DURATION);
			if(durationArrNode != null) {
				sb.setLength(0);
				for(JsonNode context : durationArrNode) {
					sb.append(context.textValue() + ",");
				}
				infoFamily.add(presentId + Instance.Attribute.DURATION, date, sb.toString());
			}
		}
		// Generate instanceFamily.
		InstanceFamily instanceFamily = entity.getInstanceFamily();
		JsonNode instanceNode = objNode.get(InstanceAssistant.INSTANCE);
		// TODO
	}
}
