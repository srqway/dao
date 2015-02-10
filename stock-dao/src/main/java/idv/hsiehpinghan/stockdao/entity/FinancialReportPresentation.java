package idv.hsiehpinghan.stockdao.entity;

import idv.hsiehpinghan.hbaseassistant.abstractclass.HBaseColumnFamily;
import idv.hsiehpinghan.hbaseassistant.abstractclass.HBaseColumnQualifier;
import idv.hsiehpinghan.hbaseassistant.abstractclass.HBaseRowKey;
import idv.hsiehpinghan.hbaseassistant.abstractclass.HBaseTable;
import idv.hsiehpinghan.hbaseassistant.abstractclass.HBaseValue;
import idv.hsiehpinghan.hbaseassistant.utility.ByteConvertUtility;

import java.util.Date;

public class FinancialReportPresentation extends HBaseTable {
	private RowKey rowKey;
	private JsonFamily jsonFamily;

	public FinancialReportPresentation() {
		super();
	}

	@Override
	public HBaseRowKey getRowKey() {
		return rowKey;
	}

	@Override
	public void setRowKey(HBaseRowKey rowKey) {
		this.rowKey = (RowKey) rowKey;
	}

	public JsonFamily getJsonFamily() {
		if (jsonFamily == null) {
			jsonFamily = this.new JsonFamily(this);
		}
		return jsonFamily;
	}

	public void setJsonFamily(JsonFamily jsonFamily) {
		this.jsonFamily = jsonFamily;
	}

	public class RowKey extends HBaseRowKey {
		public RowKey(FinancialReportPresentation table) {
			super(table);
		}

		public RowKey(String taxonomyVersion, FinancialReportPresentation entity) {
			super(entity);
			setBytes(ByteConvertUtility.toBytes(taxonomyVersion));
		}

		public RowKey(byte[] rowKey, FinancialReportPresentation entity) {
			super(entity);
			setBytes(rowKey);
		}
	}

	public class JsonFamily extends HBaseColumnFamily {
		private JsonFamily(FinancialReportPresentation table) {
			super(table);
		}

		public void add(String presentationId, Date date, String json) {
			HBaseColumnQualifier qualifier = this.new IdQualifier(
					presentationId);
			JsonValue val = new JsonValue(json);
			add(qualifier, date, val);
		}

		public JsonValue getLatestValue(String presentationId) {
			IdQualifier qual = this.new IdQualifier(presentationId);
			return (JsonValue) super.getLatestValue(qual);
		}

		public class IdQualifier extends HBaseColumnQualifier {
			public IdQualifier() {
				super();
			}

			public IdQualifier(String presentationId) {
				super();
				setBytes(ByteConvertUtility.toBytes(presentationId));
			}

			public IdQualifier(byte[] bytes) {
				super();
				setBytes(bytes);
			}

			public String getPresentationId() {
				return ByteConvertUtility.getStringFromBytes(getBytes());
			}
		}

		public class JsonValue extends HBaseValue {
			public JsonValue() {
				super();
			}

			public JsonValue(String json) {
				super();
				setBytes(ByteConvertUtility.toBytes(json));
			}

			public JsonValue(byte[] jsonBytes) {
				super();
				setBytes(jsonBytes);
			}

			public String getJson() {
				return ByteConvertUtility.getStringFromBytes(getBytes());
			}
		}

		@Override
		protected HBaseColumnQualifier generateColumnQualifier(
				byte[] qualifierBytes) {
			return this.new IdQualifier(qualifierBytes);
		}

		@Override
		protected HBaseValue generateValue(byte[] valueBytes) {
			return this.new JsonValue(valueBytes);
		}
	}
}
