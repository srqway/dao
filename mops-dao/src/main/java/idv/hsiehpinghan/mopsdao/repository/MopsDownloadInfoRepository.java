package idv.hsiehpinghan.mopsdao.repository;

import idv.hsiehpinghan.hbaseassistant.abstractclass.HBaseRowKey;
import idv.hsiehpinghan.hbaseassistant.abstractclass.HBaseTable;
import idv.hsiehpinghan.hbaseassistant.assistant.HbaseAssistant;
import idv.hsiehpinghan.hbaseassistant.repository.RepositoryBase;
import idv.hsiehpinghan.mopsdao.entity.MopsDownloadInfo;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class MopsDownloadInfoRepository extends RepositoryBase {
	@Autowired
	private HbaseAssistant hbaseAssistant;

	@Override
	public Class<? extends HBaseTable> getTargetTableClass() {
		return MopsDownloadInfo.class;
	}

	@Override
	public HbaseAssistant getHbaseAssistant() {
		return hbaseAssistant;
	}

	public MopsDownloadInfo put(MopsDownloadInfo entity)
			throws IllegalAccessException {
		hbaseAssistant.put(entity);
		return entity;
	}

	public MopsDownloadInfo get(String tableName)
			throws IllegalAccessException, NoSuchMethodException,
			SecurityException, InstantiationException,
			IllegalArgumentException, InvocationTargetException, IOException {
		HBaseRowKey rowKey = getRowKey(tableName);
		return (MopsDownloadInfo) hbaseAssistant.get(rowKey);
	}

	private HBaseRowKey getRowKey(String tableName) {
		MopsDownloadInfo entity = new MopsDownloadInfo();
		generateRowKey(entity, tableName);
		return entity.getRowKey();
	}

	private void generateRowKey(MopsDownloadInfo entity, String tableName) {
		entity.new RowKey(tableName, entity);
	}

}
