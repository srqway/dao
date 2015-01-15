package idv.hsiehpinghan.mopsdao.repository;

import idv.hsiehpinghan.mopsdao.entity.FinancialReportInstance;
import idv.hsiehpinghan.mopsdao.entity.FinancialReportInstance.InfoFamily.InfoValue;
import idv.hsiehpinghan.mopsdao.entity.FinancialReportInstance.InstanceFamily.InstanceValue;
import idv.hsiehpinghan.mopsdao.enumeration.ReportType;
import idv.hsiehpinghan.mopsdao.suit.TestngSuitSetting;
import idv.hsiehpinghan.mopsdao.utility.ResourceUtility;
import idv.hsiehpinghan.xbrlassistant.assistant.InstanceAssistant;
import idv.hsiehpinghan.xbrlassistant.enumeration.XbrlTaxonomyVersion;
import idv.hsiehpinghan.xbrlassistant.xbrl.Instance;
import idv.hsiehpinghan.xbrlassistant.xbrl.Presentation;

import java.io.File;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import junit.framework.Assert;

import org.apache.commons.lang3.time.DateUtils;
import org.springframework.context.ApplicationContext;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.fasterxml.jackson.databind.node.ObjectNode;

public class FinancialReportInstanceRepositoryTest {
	private FinancialReportInstanceRepository repository;
	private InstanceAssistant instanceAssistant;
	private XbrlTaxonomyVersion version;
	private List<String> presentIds;
	private String stockCode = "1101";
	private ReportType reportType = ReportType.CONSOLIDATED_STATEMENT;
	private int year = 2013;
	private int season = 1;

	@BeforeClass
	public void beforeClass() {
		ApplicationContext applicationContext = TestngSuitSetting
				.getApplicationContext();
		presentIds = getPresentIds();
		repository = applicationContext
				.getBean(FinancialReportInstanceRepository.class);
		instanceAssistant = applicationContext.getBean(InstanceAssistant.class);
	}

	// @AfterClass
	public void afterClass() throws Exception {
		repository.dropTable(repository.getTargetTableName());
	}

	@Test
	public void put() throws Exception {
		File instanceFile = ResourceUtility
				.getFileResource("xbrl-instance/2013-01-sii-01-C/tifrs-fr0-m1-ci-cr-1101-2013Q1.xml");
		ObjectNode instanceNode = instanceAssistant.getInstanceJson(
				instanceFile, presentIds);
		repository.put(stockCode, reportType, year, season, instanceNode,
				presentIds);
		Assert.assertTrue(repository
				.exists(stockCode, reportType, year, season));
	}

	@Test(dependsOnMethods = { "put" })
	public void get() throws Exception {
		FinancialReportInstance entity = repository.get(stockCode, reportType,
				year, season);
		testInfoFamily(entity);
		testInstanceFamily(entity);
	}

	private void testInfoFamily(FinancialReportInstance entity) {
		InfoValue infoValue;
		// Test version.
		infoValue = entity.getInfoFamily().getValue(InstanceAssistant.VERSION);
		Assert.assertEquals(version.name(), infoValue.getInfoContent());
		// Test context.
		infoValue = entity.getInfoFamily().getValue(
				Presentation.Id.StatementOfCashFlows,
				Instance.Attribute.INSTANT);
		Assert.assertEquals("20120101,20120331,20121231,20130331,",
				infoValue.getInfoContent());
		infoValue = entity.getInfoFamily().getValue(
				Presentation.Id.StatementOfCashFlows,
				Instance.Attribute.DURATION);
		Assert.assertEquals("20120101~20120331,20130101~20130331,",
				infoValue.getInfoContent());
	}

	private void testInstanceFamily(FinancialReportInstance entity)
			throws ParseException {
		testInstant(entity);
		testDuration(entity);
	}

	private void testDuration(FinancialReportInstance entity)
			throws ParseException {
		String elementId = "tifrs-SCF_ShareOfLossProfitOfAssociatesAndJointVenturesAccountedForUsingEquityMethod";
		Date startDate = DateUtils.parseDate("20130101", "yyyyMMdd");
		Date endDate = DateUtils.parseDate("20130331", "yyyyMMdd");
		InstanceValue instanceValue = entity.getInstanceFamily().getValue(
				elementId, Instance.Attribute.DURATION, startDate, endDate);
		Assert.assertEquals("TWD", instanceValue.getUnit());
		Assert.assertEquals("-151905000", instanceValue.getValue().toString());
	}

	private void testInstant(FinancialReportInstance entity)
			throws ParseException {
		String elementId = "tifrs-bsci-ci_GainsLossesOnEffectivePortionOfCashFlowHedges";
		Date instant = DateUtils.parseDate("20120101", "yyyyMMdd");
		InstanceValue instanceValue = entity.getInstanceFamily().getValue(
				elementId, Instance.Attribute.INSTANT, instant);
		Assert.assertEquals("TWD", instanceValue.getUnit());
		Assert.assertEquals("-34373000", instanceValue.getValue().toString());
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
