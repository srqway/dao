package idv.hsiehpinghan.stockdao.enumeration;

public enum UnitType {
	TWD, SHARES;

	public static UnitType getUnitType(String code) {
		switch (code) {
		case "TWD":
			return TWD;
		case "Shares":
			return SHARES;
		default:
			throw new RuntimeException("Unit type(" + code + ") undefined !!!");
		}
	}
}