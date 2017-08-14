package com.yangwan.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.yangwan.bean.AutoFormatBean;

/**
 * @author yangwan
 * 自动解析MD5值(主要解析对象为bwaerpc-i.js文件)
 */
public class MDValueFormatUtil{

	/**
	 * 数据库操作工具
	 */
	private DataBaseUtils dataBaseUtils;
	
	/**
	 * 存储接口信息的列表
	 */
	private Map<String, AutoFormatBean> autoFormatMap;
	
	/**
	 * 解析文件路径
	 */
	private String filePath;
	
	/**
	 * 文件名
	 */
	private String fileName;
	
	public static final int ERROR_LINE = 0;
	
	public static final int FUNCTION_LINE = 1;
	
	public static final int VALUE_LINE = 2;
	
	public MDValueFormatUtil(Map<String, AutoFormatBean> autoFormatMap, String filePath) {
		this.autoFormatMap = autoFormatMap;
		this.filePath = filePath;
	}
	
	public MDValueFormatUtil(Map<String, AutoFormatBean> autoFormatMap) {
		this.autoFormatMap = autoFormatMap;
	}
	
	/**
	 * 返回文件操作流
	 * @return
	 * @throws Exception
	 */
	private FileInputStream getFileOperateStream() throws Exception {
		FileInputStream fis = null;
		if(filePath != null){
			File file = new File(filePath);
			fis = new FileInputStream(file);
		}else{
			System.out.println("MDValueFormatUtil文件路径为空");
		}
		return fis;
	}

	private int mathLine(String lineContent){
		if(lineContent.contains("function(params,resultCallback)")){
			return FUNCTION_LINE;
		}else if(lineContent.contains("new BWAERpc")){
			return VALUE_LINE;
		}else{
			return ERROR_LINE;
		}
	}
	
	/**
	 * 自动解析程序 获取MD5 moduleName interfaceName service
	 * @return
	 */
	public void autoAnalysisProcess(){
		if(autoFormatMap == null){
			autoFormatMap = new HashMap<String, AutoFormatBean>();
		}
		FileInputStream fis = null;
		BufferedReader br = null;
		try{
			fis = getFileOperateStream();
			InputStreamReader isr = new InputStreamReader(fis);
			br = new BufferedReader(isr);
		}catch(Exception e){  //文件流操作异常
			System.out.println("获取文件读取流失败,文件路径 :"+this.filePath);
			e.getStackTrace();
		}
		String lineContent = "";
		String pattern = "";
		Pattern r = null;
		Matcher m = null;
		AutoFormatBean tempBean = new AutoFormatBean();
		try{
			while((lineContent = br.readLine()) != null){
				switch(mathLine(lineContent)){
				case FUNCTION_LINE:
					 pattern = "BWAE(\\.)(\\w+)(\\.)(\\w+)(\\.)(\\w+)(\\D+)";
					 r = Pattern.compile(pattern);
					 m = r.matcher(lineContent);
					 if (m.find()) {
						 tempBean.setModuleName(m.group(2)); //设置模块名
						 tempBean.setServiceName(m.group(4));   //设置服务名
						 tempBean.setInterfaceName(m.group(6));  //设置接口名
					 }
					break;
				case VALUE_LINE:
					 pattern = "new(.*)(\\+\\'\\/)(\\w+)(\\'.*)";
					 r = Pattern.compile(pattern);
					 m = r.matcher(lineContent);
					 if (m.find()) {
						 tempBean.setMdValue(m.group(3)); //设置md5值
						 
						 autoFormatMap.put("", tempBean);
						 tempBean = new AutoFormatBean();
					 }
					 break;
				case ERROR_LINE:
					break;
				}
			}
		}catch(Exception e){
			e.printStackTrace();
			System.out.println("文件读取错误 >>>>>>>>Utils:MDValueFormatUtil");
		}
	}
	
	public void saveToDataBase() throws Exception{
		if(dataBaseUtils != null){
			dataBaseUtils.getConnectionToSQL();
			Connection connection = dataBaseUtils.getConnection();
			
		}else{
			System.out.print(MDValueFormatUtil.class.toString() + "DataBaseUtils为空");
		}
	}
	
	public DataBaseUtils getDataBaseUtils() {
		return dataBaseUtils;
	}

	public void setDataBaseUtils(DataBaseUtils dataBaseUtils) {
		this.dataBaseUtils = dataBaseUtils;
	}
	
	public Map<String, AutoFormatBean> getAutoFormatList() {
		return autoFormatMap;
	}

	public void setAutoFormatList(Map<String, AutoFormatBean> autoFormatMap) {
		this.autoFormatMap = autoFormatMap;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
}
