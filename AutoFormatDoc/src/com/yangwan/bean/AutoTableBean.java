package com.yangwan.bean;

import java.io.Serializable;

public class AutoTableBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6183811095733227367L;

	/**
	 * 记录编号
	 */
	private Integer id;
	
	/**
	 * hive字段列表 字段以逗号分隔，存储类型为varchar
	 */
	private String hiveTableAttr;

	private String hiveTableName;
	
	private String oracleTableName;
	
	private String tableMore;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getHiveTableAttr() {
		return hiveTableAttr;
	}

	public void setHiveTableAttr(String hiveTableAttr) {
		this.hiveTableAttr = hiveTableAttr;
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
