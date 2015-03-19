package idv.hsiehpinghan.stockdao.entity;

import idv.hsiehpinghan.collectionutility.utility.ArrayUtility;
import idv.hsiehpinghan.datatypeutility.utility.ByteUtility;
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
	private static final byte[] SPACE = ByteUtility.SINGLE_SPACE_BYTE_ARRAY;
	private RowKey rowKey;
	private PresentationFamily presentationFamily;
	private NameFamily nameFamily;

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

	public NameFamily getNameFamily() {
		if (nameFamily == null) {
			nameFamily = this.new NameFamily(this);
		}
		return nameFamily;
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
			byte[] bytes = ByteConvertUtility
					.toBytes(taxonomyVersion == null ? null : taxonomyVersion
							.name());
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

	public class NameFamily extends HBaseColumnFamily {
		public static final String CHINESE_NAME = "chineseName";
		public static final String ENGLISH_NAME = "englishName";

		private NameFamily(Taxonomy entity) {
			super(entity);
		}

		@SuppressWarnings("unchecked")
		public Set<NameQualifier> getQualifiers() {
			return (Set<NameQualifier>) (Object) super
					.getQualifierVersionValueMap().keySet();
		}

		public String getChineseName(String elementId) {
			HBaseColumnQualifier qual = new NameQualifier(CHINESE_NAME,
					elementId);
			NameValue val = (NameValue) super.getLatestValue(qual);
			if (val == null) {
				return null;
			}
			return val.getAsString();
		}

		public void setChineseName(String elementId, Date ver,
				String chineseName) {
			NameQualifier qual = new NameQualifier(CHINESE_NAME, elementId);
			NameValue val = new NameValue();
			val.set(chineseName);
			add(qual, ver, val);
		}

		public String getEnglishName(String elementId) {
			HBaseColumnQualifier qual = new NameQualifier(ENGLISH_NAME,
					elementId);
			NameValue val = (NameValue) super.getLatestValue(qual);
			if (val == null) {
				return null;
			}
			return val.getAsString();
		}

		public void setEnglishName(String elementId, Date ver,
				String englishName) {
			NameQualifier qual = new NameQualifier(ENGLISH_NAME, elementId);
			NameValue val = new NameValue();
			val.set(englishName);
			add(qual, ver, val);
		}

		@Override
		protected HBaseColumnQualifier generateColumnQualifier(byte[] bytes) {
			return this.new NameQualifier(bytes);
		}

		@Override
		protected HBaseValue generateValue(byte[] bytes) {
			return this.new NameValue(bytes);
		}

		public class NameQualifier extends HBaseColumnQualifier {
			private static final int COLUMN_NAME_LENGTH = 200;
			private static final int ELEMENT_ID_LENGTH = 300;
			private static final int COLUMN_NAME_BEGIN_INDEX = 0;
			private static final int COLUMN_NAME_END_INDEX = COLUMN_NAME_BEGIN_INDEX
					+ COLUMN_NAME_LENGTH;
			private static final int ELEMENT_ID_BEGIN_INDEX = COLUMN_NAME_END_INDEX + 1;
			private static final int ELEMENT_ID_END_INDEX = ELEMENT_ID_BEGIN_INDEX
					+ ELEMENT_ID_LENGTH;

			public NameQualifier() {
				super();
			}

			public NameQualifier(byte[] bytes) {
				super();
				setBytes(bytes);
			}

			public NameQualifier(String columnName, String elementId) {
				super();
				byte[] columnNameBytes = ByteConvertUtility.toBytes(columnName,
						COLUMN_NAME_LENGTH);
				byte[] elementIdBytes = ByteConvertUtility.toBytes(elementId,
						ELEMENT_ID_LENGTH);
				super.setBytes(ArrayUtility.addAll(columnNameBytes, SPACE,
						elementIdBytes));
			}

			public String getColumnName() {
				return ByteConvertUtility.getStringFromBytes(getBytes(),
						COLUMN_NAME_BEGIN_INDEX, COLUMN_NAME_END_INDEX);
			}

			public void setColumnName(String columnName) {
				byte[] bytes = getBytes();
				byte[] subBytes = ByteConvertUtility.toBytes(columnName,
						COLUMN_NAME_LENGTH);
				ArrayUtility.replace(bytes, subBytes, COLUMN_NAME_BEGIN_INDEX,
						COLUMN_NAME_END_INDEX);
			}

			public String getElementId() {
				return ByteConvertUtility.getStringFromBytes(getBytes(),
						ELEMENT_ID_BEGIN_INDEX, ELEMENT_ID_END_INDEX);
			}

			public void setElementId(String elementId) {
				byte[] bytes = getBytes();
				byte[] subBytes = ByteConvertUtility.toBytes(elementId,
						ELEMENT_ID_LENGTH);
				ArrayUtility.replace(bytes, subBytes, ELEMENT_ID_BEGIN_INDEX,
						ELEMENT_ID_END_INDEX);
			}
		}

		public class NameValue extends HBaseValue {
			public NameValue() {
				super();
			}

			public NameValue(byte[] bytes) {
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
