package com.yangwan.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;

import com.yangwan.bean.AutoFormatBean;


public class DataBaseUtils {

	/**
	 * 数据库地址
	 */
	private static final String databasePath = "jdbc:mysql://172.25.103.236:3306/autoformat";  
	
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
			tempBean.outputBeanInfo();
			String insertStr = "insert into interface_info(moduleName,serviceName,interfaceName,mdValue,jsName,htmlName,pageName,uniqueCode) values('"
					+tempBean.getModuleName()+"','"+tempBean.getServiceName()+"','"+tempBean.getInterfaceName()+"','"+
					tempBean.getMdValue()+"','"+tempBean.getJsName()+"','"+tempBean.getHtmlName()+"','"+tempBean.getPageName()+"',"+tempBean.getUniqueCode()+")";
			statement.executeUpdate(insertStr);
		}
	 }
}
