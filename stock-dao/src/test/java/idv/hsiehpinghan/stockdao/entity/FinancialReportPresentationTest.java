package idv.hsiehpinghan.stockdao.entity;

import idv.hsiehpinghan.hbaseassistant.utility.HbaseEntityTestUtility;
import idv.hsiehpinghan.stockdao.entity.FinancialReportPresentation.JsonFamily.IdQualifier;
import idv.hsiehpinghan.stockdao.entity.FinancialReportPresentation.JsonFamily.JsonValue;
import idv.hsiehpinghan.stockdao.entity.FinancialReportPresentation.RowKey;
import idv.hsiehpinghan.testutility.utility.SystemResourceUtility;
import idv.hsiehpinghan.xbrlassistant.enumeration.XbrlTaxonomyVersion;
import idv.hsiehpinghan.xbrlassistant.xbrl.Presentation;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class FinancialReportPresentationTest {
	private String version = XbrlTaxonomyVersion.TIFRS_CI_CR_2014_03_31.name();

	@BeforeClass
	public void beforeClass() throws IOException {
	}

	@Test
	public void toBytesFromBytes() throws Exception {
		FinancialReportPresentation entity = new FinancialReportPresentation();
		// Test row key.
		RowKey key = entity.new RowKey(version, entity);
		HbaseEntityTestUtility.toBytesFromBytes(key);
		// Test idQualifier.
		IdQualifier idQualifier = entity.getJsonFamily().new IdQualifier(
				Presentation.Id.BalanceSheet);
		HbaseEntityTestUtility.toBytesFromBytes(idQualifier);
		// Test JsonValue.

		File file = SystemResourceUtility
				.getFileResource("sample/presentation/TIFRS_CI_CR_2014_03_31_BalanceSheet.json");
		String json = FileUtils.readFileToString(file);
		JsonValue jsonValue = entity.getJsonFamily().new JsonValue(json);
		HbaseEntityTestUtility.toBytesFromBytes(jsonValue);
	}
}
