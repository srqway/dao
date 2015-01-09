package idv.hsiehpinghan.mopsdao.repository;

import idv.hsiehpinghan.hbaseassistant.utility.HbaseAssistant;
import idv.hsiehpinghan.mopsdao.entity.FinancialReportInstance;
import idv.hsiehpinghan.mopsdao.entity.FinancialReportInstance.InfoFamily;
import idv.hsiehpinghan.mopsdao.entity.FinancialReportInstance.Key;
import idv.hsiehpinghan.mopsdao.enumeration.ReportType;
import idv.hsiehpinghan.xbrlassistant.enumeration.XbrlTaxonomyVersion;

import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

@Repository
public class FinancialReportInstanceRepository extends MopsDaoRepositoryBase {
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
		return FinancialReportInstance.class;
	}

	@Override
	public HbaseAssistant getHbaseAssistant() {
		return hbaseAssistant;
	}

	public void put(String stockCode, ReportType reportType, int year,
			int season, XbrlTaxonomyVersion taxonymyVersion,
			ObjectNode instanceNode) throws IllegalAccessException {
		FinancialReportInstance entity = generateEntity(stockCode, reportType,
				year, season, taxonymyVersion, instanceNode);
		hbaseAssistant.put(entity);
	}

	private FinancialReportInstance generateEntity(String stockCode,
			ReportType reportType, int year, int season,
			XbrlTaxonomyVersion taxonymyVersion, ObjectNode instanceNode) {
		FinancialReportInstance entity = new FinancialReportInstance();
		generateRowKey(entity, stockCode, reportType, year, season);
		generateColumnFamily(entity, taxonymyVersion, instanceNode);
		return entity;
	}

	private void generateRowKey(FinancialReportInstance entity,
			String stockCode, ReportType reportType, int year, int season) {
		Key key = entity.new Key(stockCode, reportType, year, season, entity);
		entity.setRowKey(key);
	}

	private void generateColumnFamily(FinancialReportInstance entity,
			XbrlTaxonomyVersion taxonymyVersion, ObjectNode instanceNode) {
		InfoFamily family = entity.getInfoFamily();
		Date date = Calendar.getInstance().getTime();
		family.add(
				FinancialReportInstance.InfoFamily.InfoQualifier.XBRL_TAXONOMY_VERSION,
				date, taxonymyVersion.name());
	}
}
