package com.yangwan.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.yangwan.bean.AutoTableBean;

public class AutoFormatTable {

private DataBaseUtils dataBaseUtils;
	
	private String filePath;
	
	public static final int CREATE_TABLE_LINE = 1; //用于识别creat table语句
	
	public static final int LEFT_BRACKET = 2; //生成左括号
	
	public static final int RIGHT_BRACKET = 3; //生成右括号
	
	public static final int ATT_LINE = 4; //属性行
	
	public static final int ERROR_LINE = 5; //错误行
	
	public String tempAtt = "";
	
	public String tempOracleTableName = "";
	
	public AutoFormatTable(String filePath) {
		this.filePath = filePath;
	}
	
	private FileInputStream getFileOperateStream() throws Exception {
		FileInputStream fis = null;
		if(filePath != null){
			File file = new File(filePath);
			fis = new FileInputStream(file);
		}else{
			System.out.println("read the oracle create table sql Failed!!!");
		}
		return fis;
	}
	
	public void autoFormatCreateTable(){
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
			dataBaseUtils.clearTableData("hivetableinfo");
		} catch (Exception e1) {
			e1.printStackTrace();
			System.out.println("清除数据库hivetableinfo失败!!!!!!!");
		}
		String contentStr = "";
		String flag = "line_start";
		String lineAtt = "";
		AutoTableBean autoTableBean = null;
		try{
			while((contentStr = br.readLine()) != null){
				switch(differLine(contentStr)){
					case CREATE_TABLE_LINE:
						autoTableBean = new AutoTableBean();
						autoTableBean.setOracleTableName(tempOracleTableName);
						flag = "create_table";
						break;
					case LEFT_BRACKET: //属性开始
						if(flag.equals("create_table")){
							flag = "att_start";
						}
						break;
					case RIGHT_BRACKET:
						if(flag.equals("att_create")){ //属性结束
							flag = "att_end";
							autoTableBean.setHiveTableAttr(lineAtt);
							lineAtt = "";
							String hivetableName = dataBaseUtils.findHiveTableByOracleTable(autoTableBean.getOracleTableName());
							if(hivetableName != null){
								String splits[] = hivetableName.split(",");
								autoTableBean.setHiveTableName(splits[0]);
								autoTableBean.setTableMore(splits[1]);
//								System.out.println("hivetablename: " + hivetableName + " oracletableName: "+autoTableBean.getOracleTableName());
								dataBaseUtils.saveHiveTableInfo(autoTableBean);
								autoFormatHiveCreateTableStr(autoTableBean);
							}else{
								System.out.println("oracle表名: " + autoTableBean.getOracleTableName());
								System.out.println("Don't have the hivetablename, null!!!!!!!!!!!");
							}
						}
						break;
					case ATT_LINE:
						if(flag.equals("att_start") || flag.equals("att_create")){
							if(!tempAtt.equals("lastupdate")){
								flag = "att_create";
								lineAtt += tempAtt;
								lineAtt += ",";
							}
						}
						break;
					case ERROR_LINE:
						break;
				}
			}
		}catch(Exception e){
			e.printStackTrace();
			System.out.println("");
		}
	}
	
	public int differLine(String lineContent){
		Pattern pattern1 = Pattern.compile("^(\\s*)(create\\s*table\\s*)(QMC_\\w+)");
		Pattern pattern2 = Pattern.compile("^(\\s*)(\\()");
		Pattern pattern3 = Pattern.compile("^(\\s*)(\\))");
		Pattern pattern4 = Pattern.compile("^(\\s*)(\\w+)(.+)(\\,)");
		Matcher matcher = null;
		if((matcher = pattern1.matcher(lineContent)).find()){
			tempOracleTableName = matcher.group(3).trim();
			return CREATE_TABLE_LINE;
		}else if(pattern2.matcher(lineContent).find()){
			return LEFT_BRACKET;
		}else if(pattern3.matcher(lineContent).find()){
			return RIGHT_BRACKET;
		}else if((matcher = pattern4.matcher(lineContent)).find()){
			tempAtt = matcher.group(2).trim();
			return ATT_LINE;
		}else{
			return ERROR_LINE;
		}
	}
	
	public void autoFormatHiveCreateTableStr(AutoTableBean autoTableBean){
		StringBuffer createStrBuffer = new StringBuffer();
		createStrBuffer.append("--").append(autoTableBean.getTableMore()).append("\r\n");
		createStrBuffer.append("drop TABLE ").append(autoTableBean.getHiveTableName()).append(";\r\n");
		createStrBuffer.append("create table ").append(autoTableBean.getHiveTableName()).append("\r\n");
		createStrBuffer.append("(\r\n");
		if((autoTableBean.getHiveTableName().contains("bymonth") || autoTableBean.getHiveTableName().contains("byweek")) 
			&& !autoTableBean.getHiveTableAttr().contains("daytime")){
			createStrBuffer.append("  ").append("daytime").append("  ").append("string,\r\n");
		}
		String splits[] = autoTableBean.getHiveTableAttr().split(",");
		int i = 0;
		for(i=0; i < splits.length - 1; i++){
			createStrBuffer.append("  ").append(splits[i]).append("  ").append("string,\r\n");
		}
		createStrBuffer.append("  ").append(splits[i]).append("  ").append("string\r\n");
		createStrBuffer.append(") STORED AS PARQUET;\r\n\r\n");
		System.out.println(createStrBuffer.toString());
	}
}
