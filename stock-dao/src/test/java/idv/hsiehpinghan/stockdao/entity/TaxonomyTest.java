package idv.hsiehpinghan.stockdao.entity;

import idv.hsiehpinghan.stockdao.entity.Taxonomy.PresentationFamily;
import idv.hsiehpinghan.stockdao.entity.Taxonomy.RowKey;
import idv.hsiehpinghan.xbrlassistant.enumeration.XbrlTaxonomyVersion;

import java.util.Date;

import junit.framework.Assert;

import org.apache.commons.lang3.time.DateUtils;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class TaxonomyTest {
	private XbrlTaxonomyVersion taxonomyVersion = XbrlTaxonomyVersion.TIFRS_BASI_CR_2013_03_31;
	private Date ver;
	private String balanceSheet = "balanceSheet";
	private String statementOfCashFlows = "statementOfCashFlows";
	private String statementOfChangesInEquity = "statementOfChangesInEquity";
	private String statementOfComprehensiveIncome = "statementOfComprehensiveIncome";

	@BeforeClass
	public void beforeClass() throws Exception {
		ver = DateUtils.parseDate("2015/01/01", "yyyy/MM/dd");
	}

	@Test
	public void bytesConvert() {
		Taxonomy entity = new Taxonomy();
		testRowKey(entity);
		testPresentationFamily(entity);
	}

	private void testRowKey(Taxonomy entity) {
		RowKey key = entity.new RowKey(taxonomyVersion, entity);
		Assert.assertEquals(taxonomyVersion, key.getTaxonomyVersion());
	}

	private void testPresentationFamily(Taxonomy entity) {
		generatePresentationFamilyContent(entity);
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
