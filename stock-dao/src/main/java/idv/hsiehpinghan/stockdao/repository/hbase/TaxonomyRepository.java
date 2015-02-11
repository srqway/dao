package idv.hsiehpinghan.stockdao.repository.hbase;

import idv.hsiehpinghan.hbaseassistant.abstractclass.HBaseRowKey;
import idv.hsiehpinghan.hbaseassistant.abstractclass.HBaseTable;
import idv.hsiehpinghan.hbaseassistant.assistant.HbaseAssistant;
import idv.hsiehpinghan.hbaseassistant.repository.RepositoryBase;
import idv.hsiehpinghan.stockdao.entity.Taxonomy;
import idv.hsiehpinghan.stockdao.repository.ITaxonomyRepository;
import idv.hsiehpinghan.xbrlassistant.enumeration.XbrlTaxonomyVersion;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class TaxonomyRepository extends RepositoryBase implements
		ITaxonomyRepository {

	@Autowired
	private HbaseAssistant hbaseAssistant;

	@Override
	public Class<? extends HBaseTable> getTargetTableClass() {
		return Taxonomy.class;
	}

	@Override
	protected HbaseAssistant getHbaseAssistant() {
		return hbaseAssistant;
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

	@Override
	public Taxonomy get(XbrlTaxonomyVersion version)
			throws IllegalAccessException, NoSuchMethodException,
			SecurityException, InstantiationException,
			IllegalArgumentException, InvocationTargetException, IOException {
		HBaseRowKey rowKey = getRowKey(version);
		return (Taxonomy) hbaseAssistant.get(rowKey);
	}

	@Override
	public Taxonomy generateEntity(XbrlTaxonomyVersion version) {
		Taxonomy entity = new Taxonomy();
		entity.new RowKey(version, entity);
		return entity;
	}

	private HBaseRowKey getRowKey(XbrlTaxonomyVersion version) {
		Taxonomy entity = new Taxonomy();
		generateRowKey(entity, version);
		return entity.getRowKey();
	}

	private void generateRowKey(Taxonomy entity, XbrlTaxonomyVersion version) {
		entity.new RowKey(version, entity);
	}
}
