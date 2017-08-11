package com.yangwan.bean;

import java.io.Serializable;

public class AutoFormatBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6812628377097840538L;

	/**
	 * 记录编号
	 */
	private Integer id;
	
	/**
	 * 模块名 例如:cash, cash-special
	 */
	private String moduleName;
	
	/**
	 * 隶属页面名
	 */
	private String pageName;
	
	/**
	 * 功能名
	 */
	private String functionName;
	
	/**
	 * MD5值
	 */
	private String mdValue;
	
	/**
	 * 服务名
	 */
	private String service;
	
	/**
	 * 方法名
	 */
	private String interfaceName;

	/**
	 * 隶属html页面名称
	 */
	private String htmlName;
	
	/**
	 * 隶属js文件名称
	 */
	private String jsName;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getModuleName() {
		return moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	public String getPageName() {
		return pageName;
	}

	public void setPageName(String pageName) {
		this.pageName = pageName;
	}

	public String getFunctionName() {
		return functionName;
	}

	public void setFunctionName(String functionName) {
		this.functionName = functionName;
	}

	public String getMdValue() {
		return mdValue;
	}

	public void setMdValue(String mdValue) {
		this.mdValue = mdValue;
	}

	public String getService() {
		return service;
	}

	public void setService(String service) {
		this.service = service;
	}

	public String getInterfaceName() {
		return interfaceName;
	}

	public void setInterfaceName(String interfaceName) {
		this.interfaceName = interfaceName;
	}

	public String getHtmlName() {
		return htmlName;
	}

	public void setHtmlName(String htmlName) {
		this.htmlName = htmlName;
	}

	public String getJsName() {
		return jsName;
	}

	public void setJsName(String jsName) {
		this.jsName = jsName;
	}
	
}
