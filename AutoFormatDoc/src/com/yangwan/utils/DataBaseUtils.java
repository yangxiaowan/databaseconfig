package com.yangwan.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Map;

import com.yangwan.bean.AutoFormatBean;
import com.yangwan.bean.AutoTableBean;
import com.yangwan.bean.HiveToOracleTableBean;


public class DataBaseUtils {

	/**
	 * 数据库地址
	 */
	private static final String databasePath = "jdbc:mysql://192.168.190.1:3306/autoformat";  
	
    private static final String driverPath = "com.mysql.jdbc.Driver";  
    
    /**
     * 用户名
     */
    private String userName = "root";  
    
    /**
     * 密码
     */
    private String password = "beyonddream";
    
    /**
     * 链接
     */
    private Connection connection = null;  
    
    private String moduleName = null;
    
    public void setModuleName(String moduleName){
    	this.moduleName = moduleName;
    }
    
    private ArrayList<String> pageNameList = null;
    
    public void setPageNameList(ArrayList<String> list){
    	this.pageNameList = list;
    }
    
    public DataBaseUtils(){  
        try {  
            Class.forName(driverPath);  
        } catch (ClassNotFoundException e) {  
            e.printStackTrace();  
        }  
        System.out.println("加载数据库驱动成功");  
    }  
    
    public boolean getConnectionToSQL(){  
        try {  
        	connection = DriverManager.getConnection(databasePath,userName,password);  
             return true;  
        } catch (SQLException e) {  
            e.printStackTrace();  
        }  
        return false;  
    }
    
    public Connection getConnection(){
    	return this.connection;
    }
    
    public void saveAutoFormatData(Map<Integer, AutoFormatBean> autoFormatMap) throws Exception{
    	if(connection == null){
    		getConnectionToSQL();
    	}
		Statement statement = connection.createStatement();
		String deleteStr = "delete from interface_info where 1=1";
		statement.executeUpdate(deleteStr);
		System.out.println("清除数据库成功"); //清除原有数据
		for(AutoFormatBean tempBean : autoFormatMap.values()){
//			tempBean.outputBeanInfo();
			String insertStr = "insert into interface_info(moduleName,serviceName,interfaceName,mdValue,jsName,htmlName,pageName,uniqueCode) values('"
					+tempBean.getModuleName()+"','"+tempBean.getServiceName()+"','"+tempBean.getInterfaceName()+"','"+
					tempBean.getMdValue()+"','"+tempBean.getJsName()+"','"+tempBean.getHtmlName()+"','"+tempBean.getPageName()+"',"+tempBean.getUniqueCode()+")";
			statement.executeUpdate(insertStr);
		}
	 }
    
    public String findHiveTableByOracleTable(String oracleName) throws Exception{
    	if(connection == null){
    		getConnectionToSQL();
    	}
		Statement statement = connection.createStatement();
		String selectStr = "select DISTINCT hiveTableName, tableMore from hivetooracle where oracleTableName='"+oracleName+"'";
		ResultSet result = statement.executeQuery(selectStr);
		if(result.next()){
			return result.getString("hiveTableName")+","+result.getString("tableMore");
		}else{
			return null;
		}
    }
    
    public void saveHiveToOracleTable(HiveToOracleTableBean hiveToOracleTableBean) throws Exception{
    	if(connection == null){
    		getConnectionToSQL();
    	}
		Statement statement = connection.createStatement();
		String insertStr = "insert into hivetooracle(hiveTableName,oracleTableName,tableMore) values('"
				+hiveToOracleTableBean.getHiveTableName().trim()+"','"+hiveToOracleTableBean.getOracleTableName().trim()
				+"','"+hiveToOracleTableBean.getTableMore().trim()+"')";
		statement.executeUpdate(insertStr);
	 }
    
    public void saveHiveTableInfo(AutoTableBean autoTableBean) throws Exception{
    	if(connection == null){
    		getConnectionToSQL();
    	}
		Statement statement = connection.createStatement();
		String insertStr = "insert into hivetableinfo(hiveTableAttr,hiveTableName,oracleTableName,tableMore) values('"
				+autoTableBean.getHiveTableAttr().trim()+"','"+autoTableBean.getHiveTableName().trim()
				+"','"+autoTableBean.getOracleTableName().trim()+"','"+autoTableBean.getTableMore()+"')";
		statement.executeUpdate(insertStr);
    }
    
    public void clearTableData(String tableName)throws Exception{
    	if(connection == null){
    		getConnectionToSQL();
    	}
		Statement statement = connection.createStatement();
    	String deleteStr =  "truncate table "+tableName;
		statement.executeUpdate(deleteStr);
		System.out.println("清除数据库成功"); //清除原有数据
    }
    
    public void formatDoc() throws Exception{
    	if(pageNameList != null){
    		for(String temp : pageNameList){
    			StringBuffer buffer = new StringBuffer();
    			if(connection == null){
    	    		getConnectionToSQL();
    	    	}
    			Statement statement = connection.createStatement();
    			String selectStr = "select DISTINCT moduleName, serviceName, interfaceName, pageName, mdValue from interface_info WHERE pageName='"
    			+ temp+"'";
    			buffer.append(temp+" :\n");
    			buffer.append("INSERT INTO `permission` (module,description,path) VALUES ");
    			ResultSet result = statement.executeQuery(selectStr);
    			while(result.next()){
    				buffer.append("('").append(moduleName).append("','").
    				append(result.getString("serviceName")).append(".").
    				append(result.getString("interfaceName")).append("','/").append(result.getString("mdValue")).
    				append("'),");
    			}
    			buffer.deleteCharAt(buffer.length()-1);
    			System.out.println(buffer.toString());
    		}
    		
    	}else{
    		System.out.println("请输入页面名称");
    	}
    }
    
    public void formatHtmlDoc() throws Exception{
    	if(pageNameList != null){
    		for(String temp : pageNameList){
    			if(connection == null){
    	    		getConnectionToSQL();
    	    	}
    			StringBuffer buffer = new StringBuffer();
    			Statement statement = connection.createStatement();
    			String selectStr = "select DISTINCT moduleName, serviceName, interfaceName, pageName, mdValue, htmlName from interface_info WHERE pageName='"
    			+ temp+"'";
    			buffer.append(temp+" :\n");
    			ResultSet result = statement.executeQuery(selectStr);
    			while(result.next()){
//    				buffer.append("('").append(moduleName).append("','").
//    				append(result.getString("serviceName")).append(".").
//    				append(result.getString("interfaceName")).append("','/").append(result.getString("mdValue")).append(",")
//    				append("')\n");
    				buffer.append("接口名: " + result.getString("serviceName") + "." + result.getString("interfaceName") + "   ");
    				buffer.append("md5值 : " +"/"+ result.getString("mdValue")+"   ");
    				buffer.append("隶属页面名称: " + result.getString("pageName") + "  ");
    				buffer.append("隶属html名称: ");
    				buffer.append("/").append(result.getString("moduleName")).append("/page/").append(result.getString("htmlName")).append("\n");
    			}
    			buffer.deleteCharAt(buffer.length()-1);
    			System.out.println(buffer.toString());
    		}
    		
    	}else{
    		System.out.println("请输入页面名称");
    	}
    }
    
    
}
