package idv.hsiehpinghan.mopsdao.entity;

import idv.hsiehpinghan.hbaseassistant.abstractclass.HBaseColumnFamily;
import idv.hsiehpinghan.hbaseassistant.abstractclass.HBaseColumnQualifier;
import idv.hsiehpinghan.hbaseassistant.abstractclass.HBaseRowKey;
import idv.hsiehpinghan.hbaseassistant.abstractclass.HBaseTable;
import idv.hsiehpinghan.hbaseassistant.abstractclass.HBaseValue;
import idv.hsiehpinghan.hbaseassistant.utility.ByteConvertUtility;

import java.util.Date;
import java.util.Map.Entry;

public class FinancialReportPresentation extends HBaseTable {
	private JsonFamily jsonFamily;

	public FinancialReportPresentation() {
		super();
	}

	public FinancialReportPresentation(Key rowKey, JsonFamily jsonFamily) {
		super(rowKey);
		this.jsonFamily = jsonFamily;
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

	public class Key extends HBaseRowKey {
		private String taxonomyVersion;

		public Key(FinancialReportPresentation table) {
			super(table);
		}

		public Key(String taxonomyVersion, FinancialReportPresentation table) {
			super(table);
			this.taxonomyVersion = taxonomyVersion;
		}

		public Key(byte[] rowKey, FinancialReportPresentation table) {
			super(table);
			fromBytes(rowKey);
		}

		@Override
		public byte[] toBytes() {
			return ByteConvertUtility.toBytes(taxonomyVersion);
		}

		@Override
		public void fromBytes(byte[] bytes) {
			this.taxonomyVersion = ByteConvertUtility.getStringFromBytes(bytes);
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

		public JsonValue getValue(String presentationId) {
			IdQualifier qual = this.new IdQualifier(presentationId);
			for (Entry<Date, HBaseValue> verEnt : getVersionValueSet(qual)) {
				return (JsonValue) verEnt.getValue();
			}
			return null;
		}

		public class IdQualifier extends HBaseColumnQualifier {
			private String presentationId;

			public IdQualifier() {
				super();
			}

			public IdQualifier(String presentationId) {
				super();
				this.presentationId = presentationId;
			}

			public IdQualifier(byte[] presentationIdBytes) {
				super();
				fromBytes(presentationIdBytes);
			}

			@Override
			public byte[] toBytes() {
				return ByteConvertUtility.toBytes(presentationId);
			}

			@Override
			public void fromBytes(byte[] bytes) {
				this.presentationId = ByteConvertUtility
						.getStringFromBytes(bytes);
			}

			public String getPresentationId() {
				return presentationId;
			}
		}

		public class JsonValue extends HBaseValue {
			private String json;

			public JsonValue() {
				super();
			}

			public JsonValue(String json) {
				super();
				this.json = json;
			}

			public JsonValue(byte[] jsonBytes) {
				super();
				fromBytes(jsonBytes);
			}

			public String getJson() {
				return json;
			}

			@Override
			public byte[] toBytes() {
				return ByteConvertUtility.toBytes(json);
			}

			@Override
			public void fromBytes(byte[] bytes) {
				this.json = ByteConvertUtility.getStringFromBytes(bytes);
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
