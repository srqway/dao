package idv.hsiehpinghan.mopsdao.repository;

import idv.hsiehpinghan.hbaseassistant.utility.HbaseAssistant;

import java.io.IOException;

public abstract class MopsDaoRepositoryBase {
	/**
	 * Create table.
	 * 
	 * @param clazz
	 * @throws IOException
	 */
	public void createTable(Class<?> clazz) throws IOException {
		getHbaseAssistant().createTable(clazz);
	}
	
	/**
	 * Check if table exists.
	 * 
	 * @param tableName
	 * @return
	 * @throws IOException
	 */
	public boolean isTableExists(String tableName) throws IOException {
		return getHbaseAssistant().isTableExists(tableName);
	}
	
	/**
	 * Drop table.
	 * 
	 * @param tableName
	 * @throws IOException
	 */
	public void dropTable(String tableName) throws IOException {
		getHbaseAssistant().dropTable(tableName);
	}
	
	public abstract HbaseAssistant getHbaseAssistant();
	
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
