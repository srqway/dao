package idv.hsiehpinghan.mopsdao.repository;

import idv.hsiehpinghan.hbaseassistant.utility.HbaseAssistant;
import idv.hsiehpinghan.mopsdao.entity.FinancialReportPresentation;
import idv.hsiehpinghan.mopsdao.entity.FinancialReportPresentation.JsonFamily;
import idv.hsiehpinghan.mopsdao.entity.FinancialReportPresentation.Key;
import idv.hsiehpinghan.xbrlassistant.enumeration.XbrlTaxonomyVersion;

import java.lang.reflect.InvocationTargetException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

@Repository
public class FinancialReportPresentationRepository {
	@Autowired
	private HbaseAssistant hbaseAssistant;

	/**
	 * Save entity.
	 * 
	 * @param version
	 * @param presentationIds
	 * @param presentNode
	 * @throws IllegalAccessException
	 */
	public void save(XbrlTaxonomyVersion version, List<String> presentationIds,
			ObjectNode presentNode) throws IllegalAccessException {
		FinancialReportPresentation entity = createEntity(version,
				presentationIds, presentNode);
		hbaseAssistant.put(entity);
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
	public boolean exists(Key rowKey) throws NoSuchFieldException,
			SecurityException, IllegalArgumentException,
			IllegalAccessException, NoSuchMethodException,
			InvocationTargetException, InstantiationException {
		return hbaseAssistant.get(rowKey) != null;
	}

//	public void delete(Key rowKey) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
//		hbaseAssistant.delete(rowKey);
//	}
	
	private FinancialReportPresentation createEntity(
			XbrlTaxonomyVersion version, List<String> presentationIds,
			ObjectNode presentNode) {
		FinancialReportPresentation entity = new FinancialReportPresentation();
		generateRowKey(entity, version);
		generateColumnFamily(entity, presentationIds, presentNode);

		return entity;
	}

	private void generateRowKey(FinancialReportPresentation entity,
			XbrlTaxonomyVersion version) {
		Key key = entity.new Key(version.toString());
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
			family.add(presentId, date, presentNode.toString());
		}
	}
}
