package idv.hsiehpinghan.mopsdao.repository;

import idv.hsiehpinghan.hbaseassistant.abstractclass.HBaseRowKey;
import idv.hsiehpinghan.hbaseassistant.assistant.HbaseAssistant;
import idv.hsiehpinghan.mopsdao.entity.FinancialReportInstance;
import idv.hsiehpinghan.mopsdao.entity.FinancialReportInstance.InfoFamily;
import idv.hsiehpinghan.mopsdao.entity.FinancialReportInstance.InstanceFamily;
import idv.hsiehpinghan.mopsdao.entity.FinancialReportInstance.Key;
import idv.hsiehpinghan.mopsdao.enumeration.ReportType;
import idv.hsiehpinghan.xbrlassistant.assistant.InstanceAssistant;
import idv.hsiehpinghan.xbrlassistant.enumeration.XbrlTaxonomyVersion;
import idv.hsiehpinghan.xbrlassistant.xbrl.Instance;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

@Repository
public class FinancialReportInstanceRepository extends MopsDaoRepositoryBase {
	private final String DATE_PATTERN = "yyyyMMdd";
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
	 * @throws ParseException
	 */
	public FinancialReportInstance put(String stockCode, ReportType reportType,
			int year, int season, XbrlTaxonomyVersion taxonymyVersion,
			ObjectNode objNode, List<String> PresentationIds)
			throws IllegalAccessException, ParseException {
		FinancialReportInstance entity = generateEntity(stockCode, reportType,
				year, season, objNode, PresentationIds);
		hbaseAssistant.put(entity);
		return entity;
	}

	public FinancialReportInstance get(String stockCode, ReportType reportType,
			int year, int season) throws NoSuchFieldException,
			SecurityException, IllegalArgumentException,
			IllegalAccessException, NoSuchMethodException,
			InvocationTargetException, InstantiationException, IOException {
		HBaseRowKey rowKey = getRowKey(stockCode, reportType, year, season);
		return (FinancialReportInstance) hbaseAssistant.get(rowKey);
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
			List<String> PresentationIds) throws ParseException {
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
			ObjectNode objNode, List<String> PresentationIds)
			throws ParseException {
		Date date = Calendar.getInstance().getTime();
		// Generate infoFamily.
		generateInfoFamily(entity, objNode, PresentationIds, date);
		// Generate instanceFamily.
		generateInstanceFamily(entity, objNode, date);
	}

	private void generateInstanceFamily(FinancialReportInstance entity,
			ObjectNode objNode, Date date) throws ParseException {
		InstanceFamily instanceFamily = entity.getInstanceFamily();
		JsonNode instanceNode = objNode.get(InstanceAssistant.INSTANCE);
		Iterator<Entry<String, JsonNode>> fields = instanceNode.fields();
		while (fields.hasNext()) {
			Entry<String, JsonNode> eleIdEnt = fields.next();
			String eleId = eleIdEnt.getKey();
			ArrayNode arrNode = (ArrayNode) eleIdEnt.getValue();
			for (JsonNode dataNode : arrNode) {
				String periodType = dataNode.get("periodType").textValue();
				String unit = dataNode.get("unit").textValue();
				BigDecimal value = new BigDecimal(dataNode.get("value")
						.textValue());
				if (Instance.Attribute.DURATION.equals(periodType)) {
					Date startDate = DateUtils
							.parseDate(dataNode.get("startDate").textValue(),
									DATE_PATTERN);
					Date endDate = DateUtils.parseDate(dataNode.get("endDate")
							.textValue(), DATE_PATTERN);
					instanceFamily.add(eleId, date, periodType, startDate,
							endDate, unit, value);
				} else if (Instance.Attribute.INSTANT.equals(periodType)) {
					Date instant = DateUtils.parseDate(dataNode.get("instant")
							.textValue(), DATE_PATTERN);
					instanceFamily.add(eleId, date, periodType, instant, unit,
							value);
				} else {
					throw new RuntimeException("PeriodType(" + periodType
							+ ") not implements !!!");
				}
			}
		}
	}

	private void generateInfoFamily(FinancialReportInstance entity,
			ObjectNode objNode, List<String> PresentationIds, Date date) {
		InfoFamily infoFamily = entity.getInfoFamily();
		JsonNode infoNode = objNode.get(InstanceAssistant.INFO);
		// Generate infoFamily version.
		String version = infoNode.get(InstanceAssistant.VERSION).textValue();
		infoFamily.add(InstanceAssistant.VERSION, date, version);
		// Generate infoFamily context.
		JsonNode contextNode = infoNode.get(InstanceAssistant.CONTEXT);
		StringBuilder sb = new StringBuilder();
		for (String presentId : PresentationIds) {
			JsonNode presentIdNode = contextNode.get(presentId);
			ArrayNode instantArrNode = (ArrayNode) presentIdNode
					.get(Instance.Attribute.INSTANT);
			if (instantArrNode != null) {
				sb.setLength(0);
				for (JsonNode context : instantArrNode) {
					sb.append(context.textValue() + ",");
				}
				infoFamily.add(presentId + Instance.Attribute.INSTANT, date,
						sb.toString());
			}
			ArrayNode durationArrNode = (ArrayNode) presentIdNode
					.get(Instance.Attribute.DURATION);
			if (durationArrNode != null) {
				sb.setLength(0);
				for (JsonNode context : durationArrNode) {
					sb.append(context.textValue() + ",");
				}
				infoFamily.add(presentId + Instance.Attribute.DURATION, date,
						sb.toString());
			}
		}
	}
}
