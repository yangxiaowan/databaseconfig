package com.yangwan.bean;

import java.io.Serializable;

public class AutoFormatBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6812628377097840538L;

	/**
	 * 编号
	 */
	private Integer id;
	
	/**
	 * 特殊标识Code
	 */
	private Integer uniqueCode;
	
	/**
	 * 模块名 例如:cash, cash-special
	 */
	private String moduleName;
	
	/**
	 * 页面名
	 */
	private String pageName;
	
	/**
	 * MD5ֵ
	 */
	private String mdValue;
	
	/**
	 * 服务名
	 */
	private String serviceName;
	
	/**
	 * 接口名
	 */
	private String interfaceName;

	/**
	 * html页面文件
	 */
	private String htmlName;
	
	/**
	 * 隶属js文件名
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

	public String getMdValue() {
		return mdValue;
	}

	public void setMdValue(String mdValue) {
		this.mdValue = mdValue;
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
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
	
	public Integer getUniqueCode() {
		return uniqueCode;
	}

	public void setUniqueCode(Integer uniqueCode) {
		this.uniqueCode = uniqueCode;
	}

	public void outputBeanInfo(){
		System.out.println("特殊标识Code :"+uniqueCode+"  模块名: "+moduleName + "  服务名: " + serviceName + 
				"  接口名:"+interfaceName + " MD5ֵ :" + mdValue+
				"  js文件名: "+jsName + "  html文件名 "+htmlName + "  隶属页面名称: "+pageName);
	}
}
