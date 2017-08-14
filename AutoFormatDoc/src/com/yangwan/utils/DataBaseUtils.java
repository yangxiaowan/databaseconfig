package com.yangwan.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.junit.Test;


public class DataBaseUtils {

	/**
	 * 数据库地址
	 */
	private static final String databasePath = "jdbc:mysql://172.25.103.231:3306/autoformat";  
	
    private static final String driverPath = "com.mysql.jdbc.Driver";  
    
    /**
     * 数据库用户名
     */
    private String userName = "root";  
    
    /**
     * 数据库密码
     */
    private String password = "beyonddream";  
    
    /**
     * 数据库链接
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
    
    @Test
    public void testDataBase(){
    	DataBaseUtils dataBaseUtils = new DataBaseUtils();
    	if(dataBaseUtils.getConnectionToSQL()){
    		System.out.println("获得数据库连接");
    	}
    }
}
