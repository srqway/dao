package idv.hsiehpinghan.mopsdao.repository;

public abstract class MopsDaoRepositoryBase {
	/**
	 * Get this repository's target table name.
	 * 
	 * @return
	 */
	public abstract String getTargetTableName();

	/**
	 * Get this repository's target table class.
	 * 
	 * @return
	 */
	public abstract Class<?> getTargetTableClass();
}
