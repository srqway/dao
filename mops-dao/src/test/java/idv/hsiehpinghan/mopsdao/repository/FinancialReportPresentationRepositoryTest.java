package idv.hsiehpinghan.mopsdao.repository;

import idv.hsiehpinghan.mopsdao.suit.TestngSuitSetting;
import idv.hsiehpinghan.mopsdao.utility.ResourceUtility;
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
	public void getAsJson() throws Exception {
		ObjectNode objNode = repository.getAsJson(version);

		// Balance sheet test
		JsonNode balanceSheetNode = objNode.get(Presentation.Id.BalanceSheet);
		JsonNode blanceSheetSample = objectMapper
				.readTree(ResourceUtility
						.getFileResource("sample/presentation/TIFRS_CI_CR_2014_03_31_BalanceSheet.json"));
		Assert.assertEquals(balanceSheetNode.toString(),
				blanceSheetSample.toString());

		// Statement of comprehensive income test
		JsonNode incomeNode = objNode
				.get(Presentation.Id.StatementOfComprehensiveIncome);
		JsonNode incomeSample = objectMapper
				.readTree(ResourceUtility
						.getFileResource("sample/presentation/TIFRS_CI_CR_2014_03_31_StatementOfComprehensiveIncome.json"));
		Assert.assertEquals(incomeNode.toString(), incomeSample.toString());

		// Statement of cash flows test
		JsonNode cashFlowNode = objNode
				.get(Presentation.Id.StatementOfCashFlows);
		JsonNode cashFlowSample = objectMapper
				.readTree(ResourceUtility
						.getFileResource("sample/presentation/TIFRS_CI_CR_2014_03_31_StatementOfCashFlows.json"));
		Assert.assertEquals(cashFlowNode.toString(), cashFlowSample.toString());

		// Statement of changes in equity test
		JsonNode equityChangeNode = objNode
				.get(Presentation.Id.StatementOfChangesInEquity);
		JsonNode equityChangeSample = objectMapper
				.readTree(ResourceUtility
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
