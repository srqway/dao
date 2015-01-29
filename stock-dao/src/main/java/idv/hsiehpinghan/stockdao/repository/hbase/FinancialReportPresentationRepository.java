package idv.hsiehpinghan.stockdao.repository.hbase;

import idv.hsiehpinghan.hbaseassistant.abstractclass.HBaseRowKey;
import idv.hsiehpinghan.hbaseassistant.abstractclass.HBaseTable;
import idv.hsiehpinghan.hbaseassistant.assistant.HbaseAssistant;
import idv.hsiehpinghan.hbaseassistant.repository.RepositoryBase;
import idv.hsiehpinghan.stockdao.entity.FinancialReportPresentation;
import idv.hsiehpinghan.stockdao.entity.FinancialReportPresentation.JsonFamily;
import idv.hsiehpinghan.stockdao.repository.IFinancialReportPresentationRepository;
import idv.hsiehpinghan.xbrlassistant.enumeration.XbrlTaxonomyVersion;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

@Repository
public class FinancialReportPresentationRepository extends RepositoryBase
		implements IFinancialReportPresentationRepository {
	@Autowired
	private HbaseAssistant hbaseAssistant;
	@Autowired
	private ObjectMapper objectMapper;

	@Override
	public Class<? extends HBaseTable> getTargetTableClass() {
		return FinancialReportPresentation.class;
	}

	@Override
	public HbaseAssistant getHbaseAssistant() {
		return hbaseAssistant;
	}

	@Override
	public FinancialReportPresentation put(XbrlTaxonomyVersion version,
			List<String> presentationIds, ObjectNode presentationNode)
			throws IllegalAccessException {
		FinancialReportPresentation entity = generateEntity(version,
				presentationIds, presentationNode);
		hbaseAssistant.put(entity);
		return entity;
	}

	@Override
	public FinancialReportPresentation get(XbrlTaxonomyVersion version)
			throws IllegalAccessException, NoSuchMethodException,
			SecurityException, InstantiationException,
			IllegalArgumentException, InvocationTargetException, IOException {
		return get(version, null);
	}

	@Override
	public FinancialReportPresentation get(XbrlTaxonomyVersion version,
			List<String> presentationIds) throws IllegalAccessException,
			NoSuchMethodException, SecurityException, InstantiationException,
			IllegalArgumentException, InvocationTargetException, IOException {
		HBaseRowKey rowKey = getRowKey(version);
		return (FinancialReportPresentation) hbaseAssistant.get(rowKey);
	}

	@Override
	public boolean exists(XbrlTaxonomyVersion rowKey)
			throws NoSuchFieldException, SecurityException,
			IllegalArgumentException, IllegalAccessException,
			NoSuchMethodException, InvocationTargetException,
			InstantiationException, IOException {
		HBaseRowKey key = getRowKey(rowKey);
		return super.exists(key);
	}

	private HBaseRowKey getRowKey(XbrlTaxonomyVersion version) {
		FinancialReportPresentation entity = new FinancialReportPresentation();
		generateRowKey(entity, version);
		return entity.getRowKey();
	}

	private FinancialReportPresentation generateEntity(
			XbrlTaxonomyVersion version, List<String> presentationIds,
			ObjectNode presentNode) {
		FinancialReportPresentation entity = new FinancialReportPresentation();
		generateRowKey(entity, version);
		generateColumnFamilies(entity, presentationIds, presentNode);

		return entity;
	}

	private void generateRowKey(FinancialReportPresentation entity,
			XbrlTaxonomyVersion version) {
		entity.new RowKey(version.toString(), entity);
	}

	private void generateColumnFamilies(FinancialReportPresentation entity,
			List<String> presentationIds, ObjectNode presentNode) {
		JsonFamily family = entity.getJsonFamily();
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
