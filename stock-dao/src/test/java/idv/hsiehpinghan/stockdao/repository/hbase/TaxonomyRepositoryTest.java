package idv.hsiehpinghan.stockdao.repository.hbase;

import idv.hsiehpinghan.stockdao.entity.Taxonomy;
import idv.hsiehpinghan.stockdao.entity.Taxonomy.PresentationFamily;
import idv.hsiehpinghan.stockdao.suit.TestngSuitSetting;
import idv.hsiehpinghan.xbrlassistant.enumeration.XbrlTaxonomyVersion;

import java.util.Date;

import junit.framework.Assert;

import org.apache.commons.lang3.time.DateUtils;
import org.springframework.context.ApplicationContext;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class TaxonomyRepositoryTest {
	private TaxonomyRepository repository;
	private XbrlTaxonomyVersion taxonomyVersion = XbrlTaxonomyVersion.TIFRS_BASI_CR_2013_03_31;
	private Date ver;
	private String balanceSheet = "balanceSheet";
	private String statementOfCashFlows = "statementOfCashFlows";
	private String statementOfChangesInEquity = "statementOfChangesInEquity";
	private String statementOfComprehensiveIncome = "statementOfComprehensiveIncome";

	@BeforeClass
	public void beforeClass() throws Exception {
		ApplicationContext applicationContext = TestngSuitSetting
				.getApplicationContext();
		repository = applicationContext.getBean(TaxonomyRepository.class);
		ver = DateUtils.parseDate("2015/09/09", "yyyy/MM/dd");
	}

	@Test
	public void put() throws Exception {
		Taxonomy entity = repository.generateEntity(taxonomyVersion);
		generatePresentationFamilyContent(entity);
		repository.put(entity);
		Assert.assertTrue(repository.exists(entity.getRowKey()));
	}

	@Test(dependsOnMethods = { "put" })
	public void get() throws Exception {
		Taxonomy entity = repository.get(taxonomyVersion);
		assertPresentationFamily(entity);
	}

	private void generatePresentationFamilyContent(Taxonomy entity) {
		PresentationFamily fam = entity.getPresentationFamily();
		fam.setBalanceSheet(ver, balanceSheet);
		fam.setStatementOfCashFlows(ver, statementOfCashFlows);
		fam.setStatementOfChangesInEquity(ver, statementOfChangesInEquity);
		fam.setStatementOfComprehensiveIncome(ver,
				statementOfComprehensiveIncome);
	}

	private void assertPresentationFamily(Taxonomy entity) {
		PresentationFamily fam = entity.getPresentationFamily();
		Assert.assertEquals(balanceSheet, fam.getBalanceSheet());
		Assert.assertEquals(statementOfCashFlows, fam.getStatementOfCashFlows());
		Assert.assertEquals(statementOfChangesInEquity,
				fam.getStatementOfChangesInEquity());
		Assert.assertEquals(statementOfComprehensiveIncome,
				fam.getStatementOfComprehensiveIncome());
	}

}
