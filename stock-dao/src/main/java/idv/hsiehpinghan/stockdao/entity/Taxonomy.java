package idv.hsiehpinghan.stockdao.entity;

import idv.hsiehpinghan.hbaseassistant.abstractclass.HBaseColumnFamily;
import idv.hsiehpinghan.hbaseassistant.abstractclass.HBaseColumnQualifier;
import idv.hsiehpinghan.hbaseassistant.abstractclass.HBaseRowKey;
import idv.hsiehpinghan.hbaseassistant.abstractclass.HBaseTable;
import idv.hsiehpinghan.hbaseassistant.abstractclass.HBaseValue;
import idv.hsiehpinghan.hbaseassistant.utility.ByteConvertUtility;
import idv.hsiehpinghan.xbrlassistant.enumeration.XbrlTaxonomyVersion;

import java.util.Date;
import java.util.Set;

public class Taxonomy extends HBaseTable {
	private RowKey rowKey;
	private PresentationFamily presentationFamily;

	@Override
	public HBaseRowKey getRowKey() {
		return rowKey;
	}

	@Override
	public void setRowKey(HBaseRowKey rowKey) {
		this.rowKey = (RowKey) rowKey;
	}

	public PresentationFamily getPresentationFamily() {
		if (presentationFamily == null) {
			presentationFamily = this.new PresentationFamily(this);
		}
		return presentationFamily;
	}

	public class RowKey extends HBaseRowKey {
		public RowKey(Taxonomy entity) {
			super(entity);
		}

		public RowKey(byte[] bytes, Taxonomy entity) {
			super(entity);
			setBytes(bytes);
		}

		public RowKey(XbrlTaxonomyVersion taxonomyVersion, Taxonomy entity) {
			super(entity);
			setTaxonomyVersion(taxonomyVersion);
		}

		public XbrlTaxonomyVersion getTaxonomyVersion() {
			return XbrlTaxonomyVersion.valueOf(ByteConvertUtility
					.getStringFromBytes(getBytes()));
		}

		public void setTaxonomyVersion(XbrlTaxonomyVersion taxonomyVersion) {
			byte[] bytes = ByteConvertUtility.toBytes(taxonomyVersion.name());
			setBytes(bytes);
		}
	}

	public class PresentationFamily extends HBaseColumnFamily {
		public static final String BALANCE_SHEET = "balanceSheet";
		public static final String STATEMENT_OF_COMPREHENSIVE_INCOME = "statementOfComprehensiveIncome";
		public static final String STATEMENT_OF_CASH_FLOWS = "statementOfCashFlows";
		public static final String STATEMENT_OF_CHANGES_IN_EQUITY = "statementOfChangesInEquity";

		private PresentationFamily(Taxonomy entity) {
			super(entity);
		}

		@SuppressWarnings("unchecked")
		public Set<PresentationQualifier> getQualifiers() {
			return (Set<PresentationQualifier>) (Object) super
					.getQualifierVersionValueMap().keySet();
		}

		public String getBalanceSheet() {
			HBaseColumnQualifier qual = new PresentationQualifier(BALANCE_SHEET);
			PresentationValue val = (PresentationValue) super
					.getLatestValue(qual);
			if (val == null) {
				return null;
			}
			return val.getAsString();
		}

		public void setBalanceSheet(Date ver, String balanceSheet) {
			PresentationQualifier qual = new PresentationQualifier(
					BALANCE_SHEET);
			PresentationValue val = new PresentationValue();
			val.set(balanceSheet);
			add(qual, ver, val);
		}

		public String getStatementOfComprehensiveIncome() {
			HBaseColumnQualifier qual = new PresentationQualifier(
					STATEMENT_OF_COMPREHENSIVE_INCOME);
			PresentationValue val = (PresentationValue) super
					.getLatestValue(qual);
			if (val == null) {
				return null;
			}
			return val.getAsString();
		}

		public void setStatementOfComprehensiveIncome(Date ver,
				String statementOfComprehensiveIncome) {
			PresentationQualifier qual = new PresentationQualifier(
					STATEMENT_OF_COMPREHENSIVE_INCOME);
			PresentationValue val = new PresentationValue();
			val.set(statementOfComprehensiveIncome);
			add(qual, ver, val);
		}

		public String getStatementOfCashFlows() {
			HBaseColumnQualifier qual = new PresentationQualifier(
					STATEMENT_OF_CASH_FLOWS);
			PresentationValue val = (PresentationValue) super
					.getLatestValue(qual);
			if (val == null) {
				return null;
			}
			return val.getAsString();
		}

		public void setStatementOfCashFlows(Date ver,
				String statementOfCashFlows) {
			PresentationQualifier qual = new PresentationQualifier(
					STATEMENT_OF_CASH_FLOWS);
			PresentationValue val = new PresentationValue();
			val.set(statementOfCashFlows);
			add(qual, ver, val);
		}

		public String getStatementOfChangesInEquity() {
			HBaseColumnQualifier qual = new PresentationQualifier(
					STATEMENT_OF_CHANGES_IN_EQUITY);
			PresentationValue val = (PresentationValue) super
					.getLatestValue(qual);
			if (val == null) {
				return null;
			}
			return val.getAsString();
		}

		public void setStatementOfChangesInEquity(Date ver,
				String statementOfChangesInEquity) {
			PresentationQualifier qual = new PresentationQualifier(
					STATEMENT_OF_CHANGES_IN_EQUITY);
			PresentationValue val = new PresentationValue();
			val.set(statementOfChangesInEquity);
			add(qual, ver, val);
		}

		@Override
		protected HBaseColumnQualifier generateColumnQualifier(byte[] bytes) {
			return this.new PresentationQualifier(bytes);
		}

		@Override
		protected HBaseValue generateValue(byte[] bytes) {
			return this.new PresentationValue(bytes);
		}

		public class PresentationQualifier extends HBaseColumnQualifier {
			public PresentationQualifier() {
				super();
			}

			public PresentationQualifier(byte[] bytes) {
				super();
				setBytes(bytes);
			}

			public PresentationQualifier(String columnName) {
				super();
				setColumnName(columnName);
			}

			public String getColumnName() {
				return ByteConvertUtility.getStringFromBytes(getBytes());
			}

			public void setColumnName(String columnName) {
				byte[] bytes = ByteConvertUtility.toBytes(columnName);
				setBytes(bytes);
			}
		}

		public class PresentationValue extends HBaseValue {
			public PresentationValue() {
				super();
			}

			public PresentationValue(byte[] bytes) {
				super();
				setBytes(bytes);
			}

			public String getAsString() {
				return ByteConvertUtility.getStringFromBytes(getBytes());
			}

			public void set(String value) {
				setBytes(ByteConvertUtility.toBytes(value));
			}
		}
	}
}
