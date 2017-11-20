package com.yangwan.bean;

import java.io.Serializable;

public class HiveToOracleTableBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3841122973729957121L;

	/**
	 * 记录编号
	 */
	private Integer id;
	
	private String hiveTableName;
	
	private String oracleTableName;

	private String tableMore;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getHiveTableName() {
		return hiveTableName;
	}

	public void setHiveTableName(String hiveTableName) {
		this.hiveTableName = hiveTableName;
	}

	public String getOracleTableName() {
		return oracleTableName;
	}

	public void setOracleTableName(String oracleTableName) {
		this.oracleTableName = oracleTableName;
	}

	public String getTableMore() {
		return tableMore;
	}

	public void setTableMore(String tableMore) {
		this.tableMore = tableMore;
	}
	
}
