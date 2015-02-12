package idv.hsiehpinghan.stockdao.repository;

import idv.hsiehpinghan.hbaseassistant.abstractclass.HBaseTable;
import idv.hsiehpinghan.hbaseassistant.assistant.HbaseAssistant;
import idv.hsiehpinghan.hbaseassistant.repository.RepositoryBase;
import idv.hsiehpinghan.stockdao.entity.Xbrl;
import idv.hsiehpinghan.stockdao.enumeration.ReportType;

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

	@Override
	protected HbaseAssistant getHbaseAssistant() {
		return hbaseAssistant;
	}

	public Xbrl generateEntity(String stockCode, ReportType reportType,
			int year, int season) {
		Xbrl entity = new Xbrl();
		entity.new RowKey(stockCode, reportType, year, season, entity);
		return entity;
	}

	// private HBaseRowKey getRowKey(XbrlTaxonomyVersion version) {
	// Taxonomy entity = new Taxonomy();
	// generateRowKey(entity, version);
	// return entity.getRowKey();
	// }
	//
	// private void generateRowKey(Taxonomy entity, XbrlTaxonomyVersion version)
	// {
	// entity.new RowKey(version, entity);
	// }
}
