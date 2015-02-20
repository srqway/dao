package idv.hsiehpinghan.stockdao.entity;

import idv.hsiehpinghan.datetimeutility.utility.DateUtility;
import idv.hsiehpinghan.stockdao.entity.Taxonomy.PresentationFamily;
import idv.hsiehpinghan.stockdao.entity.Taxonomy.RowKey;
import idv.hsiehpinghan.xbrlassistant.enumeration.XbrlTaxonomyVersion;

import java.util.Date;

import org.testng.Assert;
import org.testng.annotations.Test;

public class TaxonomyTest {
	private Date ver = DateUtility.getDate(2015, 2, 3);
	private XbrlTaxonomyVersion taxonomyVersion = XbrlTaxonomyVersion.TIFRS_BASI_IR_2014_03_31;
	private String balanceSheet = "balanceSheet";
	private String statementOfCashFlows = "statementOfCashFlows";
	private String statementOfChangesInEquity = "statementOfChangesInEquity";
	private String statementOfComprehensiveIncome = "statementOfComprehensiveIncome";

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
		fam.setStatementOfComprehensiveIncome(ver,
				statementOfComprehensiveIncome);
		fam.setStatementOfCashFlows(ver, statementOfCashFlows);
		fam.setStatementOfChangesInEquity(ver, statementOfChangesInEquity);
	}

	private void assertPresentationFamily(Taxonomy entity) {
		PresentationFamily fam = entity.getPresentationFamily();
		Assert.assertEquals(balanceSheet, fam.getBalanceSheet());
		Assert.assertEquals(statementOfComprehensiveIncome,
				fam.getStatementOfComprehensiveIncome());
		Assert.assertEquals(statementOfCashFlows, fam.getStatementOfCashFlows());
		Assert.assertEquals(statementOfChangesInEquity,
				fam.getStatementOfChangesInEquity());
	}
}
