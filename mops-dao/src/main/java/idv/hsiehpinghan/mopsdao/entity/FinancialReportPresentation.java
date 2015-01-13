package idv.hsiehpinghan.mopsdao.entity;

import idv.hsiehpinghan.hbaseassistant.abstractclass.HBaseColumnFamily;
import idv.hsiehpinghan.hbaseassistant.abstractclass.HBaseColumnQualifier;
import idv.hsiehpinghan.hbaseassistant.abstractclass.HBaseRowKey;
import idv.hsiehpinghan.hbaseassistant.abstractclass.HBaseTable;
import idv.hsiehpinghan.hbaseassistant.abstractclass.HBaseValue;

import java.util.Date;
import java.util.NavigableMap;

import org.apache.hadoop.hbase.util.Bytes;

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
			return Bytes.toBytes(taxonomyVersion);
		}

		@Override
		public void fromBytes(byte[] bytes) {
			this.taxonomyVersion = Bytes.toString(bytes);
		}
	}

	public class JsonFamily extends HBaseColumnFamily {
		private JsonFamily(FinancialReportPresentation table) {
			super(table);
		}

		public void add(String presentationId, Date date, String json) {
			HBaseColumnQualifier qualifier = this.new IdQualifier(
					presentationId);
			NavigableMap<Date, HBaseValue> verMap = getVersionValueMap(qualifier);
			JsonValue val = new JsonValue(json);
			verMap.put(date, val);
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
				return Bytes.toBytes(presentationId);
			}

			@Override
			public void fromBytes(byte[] presentationIdBytes) {
				this.presentationId = Bytes.toString(presentationIdBytes);
			}

			public String getPresentationId() {
				return presentationId;
			}

			@Override
			public int compareTo(HBaseColumnQualifier o) {
				String presentationId = this.getClass().cast(o)
						.getPresentationId();
				return this.getPresentationId().compareTo(presentationId);
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
				return Bytes.toBytes(json);
			}

			@Override
			public void fromBytes(byte[] bytes) {
				this.json = Bytes.toString(bytes);
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
