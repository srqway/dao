package idv.hsiehpinghan.mopsdao.repository;

import idv.hsiehpinghan.mopsdao.enumeration.ReportType;
import idv.hsiehpinghan.mopsdao.suit.TestngSuitSetting;
import idv.hsiehpinghan.mopsdao.utility.ResourceUtility;
import idv.hsiehpinghan.xbrlassistant.assistant.InstanceAssistant;
import idv.hsiehpinghan.xbrlassistant.assistant.TaxonomyAssistant;
import idv.hsiehpinghan.xbrlassistant.enumeration.XbrlTaxonomyVersion;
import idv.hsiehpinghan.xbrlassistant.xbrl.Presentation;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.springframework.context.ApplicationContext;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class FinancialReportInstanceRepositoryTest {
	private FinancialReportInstanceRepository repository;
	private ObjectMapper objectMapper;
	private TaxonomyAssistant taxonomyAssistant;
	private InstanceAssistant instanceAssistant;
	private String stockCode = "1101";
	private ReportType reportType = ReportType.CONSOLIDATED_STATEMENT;
	private int year = 2013;
	private int season = 1;

	@BeforeClass
	public void beforeClass() {
		ApplicationContext applicationContext = TestngSuitSetting
				.getApplicationContext();
		repository = applicationContext
				.getBean(FinancialReportInstanceRepository.class);
		objectMapper = applicationContext.getBean(ObjectMapper.class);
		taxonomyAssistant = applicationContext.getBean(TaxonomyAssistant.class);
		instanceAssistant = applicationContext.getBean(InstanceAssistant.class);
	}

	@Test
	public void put() throws Exception {
		File instanceFile = ResourceUtility
				.getFileResource("xbrl-instance/2013-01-sii-01-C/tifrs-fr0-m1-ci-cr-1101-2013Q1.xml");
		XbrlTaxonomyVersion version = taxonomyAssistant
				.getXbrlTaxonomyVersion(instanceFile);
		ObjectNode instanceNode = instanceAssistant.getInstanceJson(
				instanceFile, getPresentIds());
		repository.put(stockCode, reportType, year, season, version,
				instanceNode);
	}

	private List<String> getPresentIds() {
		List<String> ids = new ArrayList<String>(4);
		ids.add(Presentation.Id.BalanceSheet);
		ids.add(Presentation.Id.StatementOfComprehensiveIncome);
		ids.add(Presentation.Id.StatementOfCashFlows);
		ids.add(Presentation.Id.StatementOfChangesInEquity);
		return ids;
	}
	// private JsonNode getInstanceNode() {
	// return objectMapper
	// .readTree(ResourceUtility
	// .getFileResource("sample/instance/tifrs-fr0-m1-ci-cr-1101-2013Q1_Instance.json"));
	// }
}
