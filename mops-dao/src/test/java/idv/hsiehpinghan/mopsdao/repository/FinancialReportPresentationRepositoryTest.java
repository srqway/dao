package idv.hsiehpinghan.mopsdao.repository;

import idv.hsiehpinghan.mopsdao.entity.FinancialReportPresentation;
import idv.hsiehpinghan.mopsdao.entity.FinancialReportPresentation.Key;
import idv.hsiehpinghan.mopsdao.suit.TestngSuitSetting;
import idv.hsiehpinghan.xbrlassistant.assistant.TaxonomyAssistant;
import idv.hsiehpinghan.xbrlassistant.enumeration.XbrlTaxonomyVersion;
import idv.hsiehpinghan.xbrlassistant.xbrl.Presentation;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import org.springframework.context.ApplicationContext;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.fasterxml.jackson.databind.node.ObjectNode;

public class FinancialReportPresentationRepositoryTest {
	private FinancialReportPresentationRepository repository;
	private TaxonomyAssistant taxonomyAssistant;
	private XbrlTaxonomyVersion version = XbrlTaxonomyVersion.TIFRS_BASI_CR_2013_03_31;
	
	@BeforeClass
	public void beforeClass() throws IOException {
		ApplicationContext applicationContext = TestngSuitSetting
				.getApplicationContext();
		repository = applicationContext.getBean(FinancialReportPresentationRepository.class);
		taxonomyAssistant = applicationContext.getBean(TaxonomyAssistant.class);
	}

	@Test
	public void save() throws Exception {
		Key key = generateKey();
		Assert.assertFalse(repository.exists(key));
		List<String> presentIds = generatePresentIds();
		ObjectNode presentNode = taxonomyAssistant.getPresentationJson(version, presentIds);
		repository.save(version, presentIds, presentNode);
		Assert.assertTrue(repository.exists(key));
	}

	private Key generateKey() {
		FinancialReportPresentation entity = new FinancialReportPresentation();
		return entity.new Key(version.toString());
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
