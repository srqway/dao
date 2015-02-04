package idv.hsiehpinghan.stockdao.entity;

import idv.hsiehpinghan.collectionutility.utility.ArrayUtility;
import idv.hsiehpinghan.hbaseassistant.abstractclass.HBaseColumnFamily;
import idv.hsiehpinghan.hbaseassistant.abstractclass.HBaseColumnQualifier;
import idv.hsiehpinghan.hbaseassistant.abstractclass.HBaseRowKey;
import idv.hsiehpinghan.hbaseassistant.abstractclass.HBaseTable;
import idv.hsiehpinghan.hbaseassistant.abstractclass.HBaseValue;
import idv.hsiehpinghan.hbaseassistant.utility.ByteConvertUtility;

import java.util.Date;

public class CompanyBasicInfo extends HBaseTable {
	private RowKey rowKey;
	private CommonFamily commonFamily;
	private RoleFamily roleFamily;
	private CommunicationFamily communicationFamily;
	private FinanceFamily financeFamily;

	@Override
	public HBaseRowKey getRowKey() {
		return rowKey;
	}

	@Override
	public void setRowKey(HBaseRowKey rowKey) {
		this.rowKey = (RowKey) rowKey;
	}

	public CommonFamily getCommonFamily() {
		if (commonFamily == null) {
			commonFamily = this.new CommonFamily(this);
		}
		return commonFamily;
	}

	public RoleFamily getRoleFamily() {
		if (roleFamily == null) {
			roleFamily = this.new RoleFamily(this);
		}
		return roleFamily;
	}

	public CommunicationFamily getCommunicationFamily() {
		if (communicationFamily == null) {
			communicationFamily = this.new CommunicationFamily(this);
		}
		return communicationFamily;
	}

	public FinanceFamily getFinanceFamily() {
		if (financeFamily == null) {
			financeFamily = this.new FinanceFamily(this);
		}
		return financeFamily;
	}

	public class RowKey extends HBaseRowKey {
		private String stockCode;

		public RowKey(CompanyBasicInfo entity) {
			super(entity);
		}

		public RowKey(String stockCode, CompanyBasicInfo entity) {
			super(entity);
			this.stockCode = stockCode;
		}

		public RowKey(byte[] bytes, CompanyBasicInfo entity) {
			super(entity);
			fromBytes(bytes);
		}

		@Override
		public byte[] toBytes() {
			byte[] stockCodeBytes = ByteConvertUtility.toBytes(stockCode, 15);
			return ArrayUtility.addAll(stockCodeBytes);
		}

		@Override
		public void fromBytes(byte[] bytes) {
			this.stockCode = ByteConvertUtility.getStringFromBytes(bytes);
		}

		public String getStockCode() {
			return stockCode;
		}

		public void setStockCode(String stockCode) {
			this.stockCode = stockCode;
		}
	}

	public class CommonFamily extends HBaseColumnFamily {
		private CommonFamily(CompanyBasicInfo entity) {
			super(entity);
		}

		public CommonValue getLatestValue(String name) {
			CommonQualifier qual = this.new CommonQualifier(name);
			return (CommonValue) super.getLatestValue(qual);
		}

		public void add(String name, Date date, String value) {
			HBaseColumnQualifier qualifier = this.new CommonQualifier(name);
			CommonValue val = this.new CommonValue(value);
			add(qualifier, date, val);
		}

		public class CommonQualifier extends HBaseColumnQualifier {
			public static final String MARKET_TYPE = "marketType";
			public static final String INDUSTRY_TYPE = "industryType";
			public static final String CHINESE_NAME = "chineseName";
			public static final String ENGLISH_BRIEF_NAME = "englishBriefName";
			public static final String UNIFIED_BUSINESS_NUMBER = "unifiedBusinessNumber";
			public static final String ESTABLISHMENT_DATE = "establishmentDate";
			public static final String LISTING_DATE = "listingDate";
			
			private String name;

			public CommonQualifier() {
				super();
			}

			public CommonQualifier(String name) {
				super();
				this.name = name;
			}

			public CommonQualifier(byte[] bytes) {
				super();
				fromBytes(bytes);
			}

			@Override
			public byte[] toBytes() {
				byte[] nameBytes = ByteConvertUtility.toBytes(name);
				return ArrayUtility.addAll(nameBytes);
			}

			@Override
			public void fromBytes(byte[] bytes) {
				this.name = ByteConvertUtility.getStringFromBytes(bytes);
			}

			public String getName() {
				return name;
			}

			public void setName(String name) {
				this.name = name;
			}
		}

		public class CommonValue extends HBaseValue {
			private String value;

			public CommonValue() {
				super();
			}

			public CommonValue(String value) {
				super();
				this.value = value;
			}

			public CommonValue(byte[] bytes) {
				super();
				fromBytes(bytes);
			}

			@Override
			public byte[] toBytes() {
				byte[] valueBytes = ByteConvertUtility.toBytes(value);
				return ArrayUtility.addAll(valueBytes);
			}

			@Override
			public void fromBytes(byte[] bytes) {
				this.value = ByteConvertUtility.getStringFromBytes(bytes);
			}

			public String getValue() {
				return value;
			}

			public void setValue(String value) {
				this.value = value;
			}
		}

		@Override
		protected HBaseColumnQualifier generateColumnQualifier(byte[] bytes) {
			return this.new CommonQualifier(bytes);
		}

		@Override
		protected HBaseValue generateValue(byte[] bytes) {
			return this.new CommonValue(bytes);
		}
	}

	public class RoleFamily extends HBaseColumnFamily {
		private RoleFamily(CompanyBasicInfo entity) {
			super(entity);
		}

		public RoleValue getLatestValue(String name) {
			RoleQualifier qual = this.new RoleQualifier(name);
			return (RoleValue) super.getLatestValue(qual);
		}

		public void add(String name, Date date, String value) {
			HBaseColumnQualifier qualifier = this.new RoleQualifier(name);
			RoleValue val = this.new RoleValue(value);
			add(qualifier, date, val);
		}

		public class RoleQualifier extends HBaseColumnQualifier {
			public static final String CHAIRMAN = "chairman";
			public static final String GENERAL_MANAGER = "generalManager";
			public static final String SPOKESPERSON = "spokesperson";
			public static final String JOB_TITLE_OF_SPOKESPERSON_ = "jobTitleOfSpokesperson";
			public static final String ACTING_SPOKESMAN = "actingSpokesman";

			private String name;

			public RoleQualifier() {
				super();
			}

			public RoleQualifier(String name) {
				super();
				this.name = name;
			}

			public RoleQualifier(byte[] bytes) {
				super();
				fromBytes(bytes);
			}

			@Override
			public byte[] toBytes() {
				byte[] nameBytes = ByteConvertUtility.toBytes(name);
				return ArrayUtility.addAll(nameBytes);
			}

			@Override
			public void fromBytes(byte[] bytes) {
				this.name = ByteConvertUtility.getStringFromBytes(bytes);
			}

			public String getName() {
				return name;
			}

			public void setName(String name) {
				this.name = name;
			}
		}

		public class RoleValue extends HBaseValue {
			private String value;

			public RoleValue() {
				super();
			}

			public RoleValue(String value) {
				super();
				this.value = value;
			}

			public RoleValue(byte[] bytes) {
				super();
				fromBytes(bytes);
			}

			@Override
			public byte[] toBytes() {
				byte[] valueBytes = ByteConvertUtility.toBytes(value);
				return ArrayUtility.addAll(valueBytes);
			}

			@Override
			public void fromBytes(byte[] bytes) {
				this.value = ByteConvertUtility.getStringFromBytes(bytes);
			}

			public String getValue() {
				return value;
			}

			public void setValue(String value) {
				this.value = value;
			}
		}

		@Override
		protected HBaseColumnQualifier generateColumnQualifier(byte[] bytes) {
			return this.new RoleQualifier(bytes);
		}

		@Override
		protected HBaseValue generateValue(byte[] bytes) {
			return this.new RoleValue(bytes);
		}
	}

	public class CommunicationFamily extends HBaseColumnFamily {
		private CommunicationFamily(CompanyBasicInfo entity) {
			super(entity);
		}

		public CommunicationValue getLatestValue(String name) {
			CommunicationQualifier qual = this.new CommunicationQualifier(name);
			return (CommunicationValue) super.getLatestValue(qual);
		}

		public void add(String name, Date date, String value) {
			HBaseColumnQualifier qualifier = this.new CommunicationQualifier(
					name);
			CommunicationValue val = this.new CommunicationValue(value);
			add(qualifier, date, val);
		}

		public class CommunicationQualifier extends HBaseColumnQualifier {
			public static final String CHINESE_ADDRESS ="chineseAddress"; 
			public static final String TELEPHONE ="telephone";
			public static final String STOCK_TRANSFER_AGENCY ="stockTransferAgency";
			public static final String TELEPHONE_OF_STOCK_TRANSFER_AGENCY ="telephoneOfStockTransferAgency";
			public static final String ADDRESS_OF_STOCK_TRANSFER_AGENCY ="addressOfStockTransferAgency";
			public static final String ENGLISH_ADDRESS ="englishAddress";
			public static final String FAX_NUMBER ="faxNumber";
			public static final String EMAIL ="email";
			public static final String WEB_SITE ="webSite";

			private String name;

			public CommunicationQualifier() {
				super();
			}

			public CommunicationQualifier(String name) {
				super();
				this.name = name;
			}

			public CommunicationQualifier(byte[] bytes) {
				super();
				fromBytes(bytes);
			}

			@Override
			public byte[] toBytes() {
				byte[] nameBytes = ByteConvertUtility.toBytes(name);
				return ArrayUtility.addAll(nameBytes);
			}

			@Override
			public void fromBytes(byte[] bytes) {
				this.name = ByteConvertUtility.getStringFromBytes(bytes);
			}

			public String getName() {
				return name;
			}

			public void setName(String name) {
				this.name = name;
			}
		}

		public class CommunicationValue extends HBaseValue {
			private String value;

			public CommunicationValue() {
				super();
			}

			public CommunicationValue(String value) {
				super();
				this.value = value;
			}

			public CommunicationValue(byte[] bytes) {
				super();
				fromBytes(bytes);
			}

			@Override
			public byte[] toBytes() {
				byte[] valueBytes = ByteConvertUtility.toBytes(value);
				return ArrayUtility.addAll(valueBytes);
			}

			@Override
			public void fromBytes(byte[] bytes) {
				this.value = ByteConvertUtility.getStringFromBytes(bytes);
			}

			public String getValue() {
				return value;
			}

			public void setValue(String value) {
				this.value = value;
			}
		}

		@Override
		protected HBaseColumnQualifier generateColumnQualifier(byte[] bytes) {
			return this.new CommunicationQualifier(bytes);
		}

		@Override
		protected HBaseValue generateValue(byte[] bytes) {
			return this.new CommunicationValue(bytes);
		}
	}

	public class FinanceFamily extends HBaseColumnFamily {
		private FinanceFamily(CompanyBasicInfo entity) {
			super(entity);
		}

		public FinanceValue getLatestValue(String name) {
			FinanceQualifier qual = this.new FinanceQualifier(name);
			return (FinanceValue) super.getLatestValue(qual);
		}

		public void add(String name, Date date, String value) {
			HBaseColumnQualifier qualifier = this.new FinanceQualifier(name);
			FinanceValue val = this.new FinanceValue(value);
			add(qualifier, date, val);
		}

		public class FinanceQualifier extends HBaseColumnQualifier {
			public static final String FINANCIAL_REPORT_TYPE = "financialReportType";
			public static final String PAR_VALUE_OF_ORDINARY_SHARES = "parValueOfOrdinaryShares";
			public static final String PAID_IN_CAPITAL = "paidInCapital";
			public static final String AMOUNT_OF_ORDINARY_SHARES = "amountOfOrdinaryShares";
			public static final String PRIVATE_PLACEMENT_AMOUNT_OF_ORDINARY_SHARES = "privatePlacementAmountOfOrdinaryShares";
			public static final String AMOUNT_OF_PREFERRED_SHARES = "amountOfPreferredShares";
			public static final String ACCOUNTING_FIRM = "accountingFirm";
			public static final String ACCOUNTANT_1 = "accountant_1";
			public static final String ACCOUNTANT_2 = "accountant_2";

			private String name;

			public FinanceQualifier() {
				super();
			}

			public FinanceQualifier(String name) {
				super();
				this.name = name;
			}

			public FinanceQualifier(byte[] bytes) {
				super();
				fromBytes(bytes);
			}

			@Override
			public byte[] toBytes() {
				byte[] nameBytes = ByteConvertUtility.toBytes(name);
				return ArrayUtility.addAll(nameBytes);
			}

			@Override
			public void fromBytes(byte[] bytes) {
				this.name = ByteConvertUtility.getStringFromBytes(bytes);
			}

			public String getName() {
				return name;
			}

			public void setName(String name) {
				this.name = name;
			}
		}

		public class FinanceValue extends HBaseValue {
			private String value;

			public FinanceValue() {
				super();
			}

			public FinanceValue(String value) {
				super();
				this.value = value;
			}

			public FinanceValue(byte[] bytes) {
				super();
				fromBytes(bytes);
			}

			@Override
			public byte[] toBytes() {
				byte[] valueBytes = ByteConvertUtility.toBytes(value);
				return ArrayUtility.addAll(valueBytes);
			}

			@Override
			public void fromBytes(byte[] bytes) {
				this.value = ByteConvertUtility.getStringFromBytes(bytes);
			}

			public String getValue() {
				return value;
			}

			public void setValue(String value) {
				this.value = value;
			}
		}

		@Override
		protected HBaseColumnQualifier generateColumnQualifier(byte[] bytes) {
			return this.new FinanceQualifier(bytes);
		}

		@Override
		protected HBaseValue generateValue(byte[] bytes) {
			return this.new FinanceValue(bytes);
		}
	}
}
