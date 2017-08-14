package com.yangwan.utils;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;

import com.yangwan.bean.AutoFormatBean;

/**
 * page 项目解析程序
 * @author yangwan
 * 自动解析接口对应的hmtl文件 js文件
 */
public class PageNameFormatUtil {

	/**
	 * 前端项目路径，精确到**page。
	 * 例如C:\Users\yangwan\WebstormProjects\qmc-mgr_cash-page
	 */
	private String pageWorkPath;

	/**
	 * 存储接口信息的列表
	 */
	private List<AutoFormatBean> autoFormatList;
	
	public PageNameFormatUtil(List<AutoFormatBean> autoFormatList, String pageWorkPath){
		this.autoFormatList = autoFormatList;
		this.pageWorkPath = pageWorkPath;
	}
	
	
}
