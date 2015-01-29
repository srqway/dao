package idv.hsiehpinghan.stockdao.repository;

import idv.hsiehpinghan.stockdao.entity.FinancialReportPresentation;
import idv.hsiehpinghan.stockdao.repository.hbase.FinancialReportPresentationRepository;
import idv.hsiehpinghan.stockdao.suit.TestngSuitSetting;
import idv.hsiehpinghan.testutility.utility.SystemResourceUtility;
import idv.hsiehpinghan.xbrlassistant.assistant.TaxonomyAssistant;
import idv.hsiehpinghan.xbrlassistant.enumeration.XbrlTaxonomyVersion;
import idv.hsiehpinghan.xbrlassistant.xbrl.Presentation;

import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import org.springframework.context.ApplicationContext;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class FinancialReportPresentationRepositoryTest {
	private FinancialReportPresentationRepository repository;
	private TaxonomyAssistant taxonomyAssistant;
	private ObjectMapper objectMapper;
	private XbrlTaxonomyVersion version = XbrlTaxonomyVersion.TIFRS_CI_CR_2014_03_31;

	@BeforeClass
	public void beforeClass() throws Exception {
		ApplicationContext applicationContext = TestngSuitSetting
				.getApplicationContext();
		repository = applicationContext
				.getBean(FinancialReportPresentationRepository.class);
		taxonomyAssistant = applicationContext.getBean(TaxonomyAssistant.class);
		objectMapper = applicationContext.getBean(ObjectMapper.class);
	}

	@Test
	public void put() throws Exception {
		dropTable();
		Assert.assertFalse(repository.exists(version));
		List<String> presentIds = generatePresentIds();
		ObjectNode presentNode = taxonomyAssistant.getPresentationJson(version,
				presentIds);
		repository.put(version, presentIds, presentNode);
		Assert.assertTrue(repository.exists(version));
	}

	@Test(dependsOnMethods = { "put" })
	public void exists() throws Exception {
		Assert.assertTrue(repository.exists(version));
	}

	@Test(dependsOnMethods = { "exists" })
	public void get() throws Exception {
		FinancialReportPresentation entity = repository.get(version);
		// Balance sheet test
		JsonNode balanceSheetNode = objectMapper.readTree(entity
				.getJsonFamily().getValue(Presentation.Id.BalanceSheet)
				.getJson());
		JsonNode blanceSheetSample = objectMapper
				.readTree(SystemResourceUtility
						.getFileResource("sample/presentation/TIFRS_CI_CR_2014_03_31_BalanceSheet.json"));
		Assert.assertEquals(balanceSheetNode.toString(),
				blanceSheetSample.toString());
		// Statement of comprehensive income test
		JsonNode incomeNode = objectMapper.readTree(entity.getJsonFamily()
				.getValue(Presentation.Id.StatementOfComprehensiveIncome)
				.getJson());
		JsonNode incomeSample = objectMapper
				.readTree(SystemResourceUtility
						.getFileResource("sample/presentation/TIFRS_CI_CR_2014_03_31_StatementOfComprehensiveIncome.json"));
		Assert.assertEquals(incomeNode.toString(), incomeSample.toString());

		// Statement of cash flows test
		JsonNode cashFlowNode = objectMapper.readTree(entity.getJsonFamily()
				.getValue(Presentation.Id.StatementOfCashFlows).getJson());
		JsonNode cashFlowSample = objectMapper
				.readTree(SystemResourceUtility
						.getFileResource("sample/presentation/TIFRS_CI_CR_2014_03_31_StatementOfCashFlows.json"));
		Assert.assertEquals(cashFlowNode.toString(), cashFlowSample.toString());

		// Statement of changes in equity test
		JsonNode equityChangeNode = objectMapper
				.readTree(entity.getJsonFamily()
						.getValue(Presentation.Id.StatementOfChangesInEquity)
						.getJson());
		JsonNode equityChangeSample = objectMapper
				.readTree(SystemResourceUtility
						.getFileResource("sample/presentation/TIFRS_CI_CR_2014_03_31_StatementOfChangesInEquity.json"));
		Assert.assertEquals(equityChangeNode.toString(),
				equityChangeSample.toString());
	}

	private void dropTable() throws Exception {
		String tableName = repository.getTargetTableName();
		if (repository.isTableExists(tableName)) {
			repository.dropTable(tableName);
			repository.createTable(repository.getTargetTableClass());
		}
	}

	private List<String> generatePresentIds() {
		List<String> presentIds = new ArrayList<String>(4);
		presentIds.add(Presentation.Id.BalanceSheet);
		presentIds.add(Presentation.Id.StatementOfComprehensiveIncome);
		presentIds.add(Presentation.Id.StatementOfCashFlows);
		presentIds.add(Presentation.Id.StatementOfChangesInEquity);
		return presentIds;
	}
}
