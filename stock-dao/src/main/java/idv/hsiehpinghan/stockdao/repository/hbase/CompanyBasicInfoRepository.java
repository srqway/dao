package idv.hsiehpinghan.stockdao.repository.hbase;

import idv.hsiehpinghan.hbaseassistant.abstractclass.HBaseRowKey;
import idv.hsiehpinghan.hbaseassistant.abstractclass.HBaseTable;
import idv.hsiehpinghan.hbaseassistant.assistant.HbaseAssistant;
import idv.hsiehpinghan.hbaseassistant.repository.RepositoryBase;
import idv.hsiehpinghan.stockdao.entity.CompanyBasicInfo;
import idv.hsiehpinghan.stockdao.repository.ICompanyBasicInfoRepository;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class CompanyBasicInfoRepository extends RepositoryBase implements
		ICompanyBasicInfoRepository {
	@Autowired
	private HbaseAssistant hbaseAssistant;

	@Override
	public Class<? extends HBaseTable> getTargetTableClass() {
		return CompanyBasicInfo.class;
	}

	@Override
	public HbaseAssistant getHbaseAssistant() {
		return hbaseAssistant;
	}

	@Override
	public void put(CompanyBasicInfo entity) throws IllegalAccessException {
		hbaseAssistant.put(entity);
	}

	@Override
	public CompanyBasicInfo get(String stockCode)
			throws IllegalAccessException, NoSuchMethodException,
			SecurityException, InstantiationException,
			IllegalArgumentException, InvocationTargetException, IOException {
		HBaseRowKey rowKey = getRowKey(stockCode);
		return (CompanyBasicInfo) hbaseAssistant.get(rowKey);
	}

	@Override
	public void put(List<CompanyBasicInfo> entities)
			throws IllegalAccessException {
		hbaseAssistant.put(entities);
	}

	@Override
	public boolean exists(String stockCode) throws NoSuchFieldException,
			SecurityException, IllegalArgumentException,
			IllegalAccessException, NoSuchMethodException,
			InvocationTargetException, InstantiationException, IOException {
		HBaseRowKey rowKey = getRowKey(stockCode);
		return exists(rowKey);
	}

	// @Override
	// @SuppressWarnings("unchecked")
	// public List<FinancialReportData> scan(Filter filter) {
	// return (List<FinancialReportData>) (Object) hbaseAssistant.scan(
	// getTargetTableClass(), filter);
	// }

	private HBaseRowKey getRowKey(String stockCode) {
		CompanyBasicInfo entity = new CompanyBasicInfo();
		generateRowKey(entity, stockCode);
		return entity.getRowKey();
	}

	private void generateRowKey(CompanyBasicInfo entity, String stockCode) {
		entity.new RowKey(stockCode, entity);
	}
}
