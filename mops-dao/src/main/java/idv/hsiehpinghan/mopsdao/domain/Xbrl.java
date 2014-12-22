package idv.hsiehpinghan.mopsdao.domain;

import idv.hsiehpinghan.hbaseassistant.abstractclass.HBaseColumnFamily;
import idv.hsiehpinghan.hbaseassistant.abstractclass.HBaseRowKey;
import idv.hsiehpinghan.hbaseassistant.abstractclass.HBaseTable;
import idv.hsiehpinghan.mopsdao.enumeration.ReportType;

import java.util.NavigableMap;

public class Xbrl extends HBaseTable {

	private BalanceSheet balanceSheet;
	
	/**
	 * Row key.
	 * 
	 * @author thank.hsiehpinghan
	 *
	 */
	public class RowKey extends HBaseRowKey {
		private String companyCode;
		private int year;
		private int season;
		private ReportType reportType;
		private int order;

		@Override
		public byte[] toBytes() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public void fromBytes(byte[] bytes) {
			// TODO Auto-generated method stub

		}
	}

	public class BalanceSheet extends HBaseColumnFamily {

		@Override
		public void fromMap(
				NavigableMap<byte[], NavigableMap<Long, byte[]>> valueMap) {
			// TODO Auto-generated method stub
			
		}	
	}
	
//	public class StatementOfComprehensiveIncome extends HBaseColumnFamily {	
//	}
//	
//	public class StatementOfChangesInEquity extends HBaseColumnFamily{
//	}
//	
//	public class StatementOfCashFlowsAbstract extends HBaseColumnFamily{
//	}
}
