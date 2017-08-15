package com.yangwan.utils;


public class MyUtils {

	/**
	 * 根据服务名和接口名形成唯一的uniqueCode
	 * @param serviceName  服务名
	 * @param interfaceName  接口名
	 * @return
	 */
	public static Integer generateUniqueCode(String value){
		return value.hashCode();
	}
	
}
