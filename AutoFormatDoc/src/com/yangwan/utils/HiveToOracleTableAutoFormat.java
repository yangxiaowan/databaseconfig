package com.yangwan.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.yangwan.bean.HiveToOracleTableBean;

public class HiveToOracleTableAutoFormat {

	private DataBaseUtils dataBaseUtils;
	
	private String filePath;
	
	public HiveToOracleTableAutoFormat(String filePath) {
		this.filePath = filePath;
	}
	
	private FileInputStream getFileOperateStream() throws Exception {
		FileInputStream fis = null;
		if(filePath != null){
			File file = new File(filePath);
			fis = new FileInputStream(file);
		}else{
			System.out.println("read the relationship of the hive and oracle table failed!!!");
		}
		return fis;
	}
	
	public void autoHiveToOracleAnalysisProcess(){
		StringBuffer hiveBuffer = new StringBuffer();
		StringBuffer oracleBuffer = new StringBuffer();
		dataBaseUtils = new DataBaseUtils();
		FileInputStream fis = null;
		BufferedReader br = null;
		try{
			fis = getFileOperateStream();
			InputStreamReader isr = new InputStreamReader(fis);
			br = new BufferedReader(isr);
		}catch(Exception e){  
			System.out.println("读取文件失败:"+this.filePath);
			e.getStackTrace();
		}
		try {
			dataBaseUtils.clearTableData("hivetooracle");
		} catch (Exception e1) {
			e1.printStackTrace();
			System.out.println("清除数据库失败!!!!!!!");
		}
		try{
			String contentLine = "";
			Pattern pattern = Pattern.compile("(qmc_\\w+)(\\s+)(QMC_\\w+)(\\s+)(.+)");
			Matcher matcher = null;
			while((contentLine = br.readLine()) != null){
				matcher = pattern.matcher(contentLine);
				if(matcher.find()){
					HiveToOracleTableBean hiveToOracleTableBean = new HiveToOracleTableBean();
					hiveBuffer.append(matcher.group(1)).append(",");
					oracleBuffer.append(matcher.group(3)).append(",");
					hiveToOracleTableBean.setHiveTableName(matcher.group(1));
					hiveToOracleTableBean.setOracleTableName(matcher.group(3));
					hiveToOracleTableBean.setTableMore(matcher.group(5));
					dataBaseUtils.saveHiveToOracleTable(hiveToOracleTableBean);
				}
			}
		}catch(Exception e){
			e.printStackTrace();
			System.out.println("readLine Error@!!!!:"+this.filePath);
		}
		System.out.println("hive to oracle table autoformat successed!!!!!!!!");
		System.out.println("hive info:" + hiveBuffer.toString());
		System.out.println("oracle info:" + oracleBuffer.toString());
	}	
}
