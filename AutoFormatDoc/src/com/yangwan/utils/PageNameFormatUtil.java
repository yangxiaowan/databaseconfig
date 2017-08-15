package com.yangwan.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.io.InputStreamReader;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.yangwan.bean.AutoFormatBean;

/**
 * 
 * 
 * page 项目解析程序
 * @author yangwan
 * 自动解析接口对应的hmtl文件 js文件
 */
public class PageNameFormatUtil {

	/**
	 * 模块名
	 */
	private String moduleName = "cash";
	/**
	 * 前端项目路径，精确到**page。
	 * 例如C:\Users\yangwan\WebstormProjects\qmc-mgr_cash-page
	 */
	private String pageWorkPath;

	/**
	 * 存储接口信息的列表
	 */
	private Map<Integer,AutoFormatBean> autoFormatMap;
	
	
	private Map<Integer, List<AutoFormatBean>> htmlMap;
	
	public PageNameFormatUtil(Map<Integer,AutoFormatBean> autoFormatMap, String pageWorkPath){
		this.autoFormatMap = autoFormatMap;
		this.pageWorkPath = pageWorkPath;
	}
	
	/**
	 * 扫描js文件，为接口找到隶属的js文件路径
	 * @param jsFilePath js文件路径
	 */
	public void scanJsFile(){
		htmlMap = new HashMap<Integer, List<AutoFormatBean>>();
		File[] fileList = null;
		String fileListPath = pageWorkPath + "\\js";
		try{
			File file = new File(fileListPath);
			FilenameFilter filter = new FilenameFilter() {
				@Override
				public boolean accept(File dir, String name) {
					if(name.endsWith(".js")){
						return true;
					}
					return false;
				}
			};
			fileList = file.listFiles(filter);
			for(File tempFile : fileList){
				List<AutoFormatBean> jsListBean = new ArrayList<AutoFormatBean>();
				if(tempFile.getName().equals("bwaerpc-i.js") || tempFile.getName().equals("common.js")){
					continue;
				}
				FileInputStream fis = new FileInputStream(tempFile);
				BufferedReader br = new BufferedReader(new InputStreamReader(fis));
				String lineContent = "";
				while((lineContent = br.readLine()) != null){ //扫描每个js文件
					lineContent = lineContent.trim();
					Pattern pattern = Pattern.compile("^BWAE(\\.)(\\w+)(\\.)(\\w+)(\\.)(\\w+)");
					Matcher m = pattern.matcher(lineContent);
					if(m.find()){
						String moduleName = m.group(2);
						String serviceName = m.group(4);
						String interfaceName = m.group(6);
						Integer uniqueCode = MyUtils.generateUniqueCode(moduleName + serviceName + interfaceName);
						AutoFormatBean autoFormatBean = autoFormatMap.get(uniqueCode);
						if(autoFormatBean != null){
							autoFormatBean.setJsName(tempFile.getName());
							jsListBean.add(autoFormatBean);
						}else{
							System.out.println(">>>>>>>>"+"模块名"+moduleName+" 服务名"+serviceName+" 接口名"+interfaceName + " 隶属js文件" + tempFile.getName());
						}
					}
					 
				}
				if(jsListBean.size() > 0){
					Integer htmlCode = MyUtils.generateUniqueCode(moduleName + tempFile.getName());
					htmlMap.put(htmlCode, jsListBean);
				}
			}
		}catch(Exception e){
			e.printStackTrace();
			System.out.print("获取js文件列表失败");
		}
	}
	
	public void scanHtmlFile(){
		if(htmlMap == null){
			System.out.println("htmlMap为空，请先扫描js文件夹!!!!!!");
			return ;
		}
		File[] fileList = null;
		String fileListPath = pageWorkPath + "\\page";
		try{
			File file = new File(fileListPath);
			FilenameFilter filter = new FilenameFilter() {
				@Override
				public boolean accept(File dir, String name) {
					if(name.endsWith(".html")){
						return true;
					}
					return false;
				}
			};
			fileList = file.listFiles(filter);
			for(File tempFile : fileList){
				FileInputStream fis = new FileInputStream(tempFile);
				BufferedReader br = new BufferedReader(new InputStreamReader(fis));
				String lineContent = "";
				String title = "";
				while((lineContent = br.readLine()) != null){ //扫描html文件夹
					lineContent = lineContent.trim();
					Pattern pattern = Pattern.compile("^<script(.*)(../js/)(.*.js)"); //获得html页面引用的js文件
					Matcher m = pattern.matcher(lineContent);
					List<AutoFormatBean> jsListBean = null;
					if(m.find()){
						Integer htmlCode = MyUtils.generateUniqueCode(moduleName + m.group(3));
						jsListBean = htmlMap.get(htmlCode);
						if(jsListBean != null){
							for(AutoFormatBean tempBean : jsListBean){
								tempBean.setHtmlName(tempFile.getName());
								if(title != null & title != ""){
									tempBean.setPageName(URLDecoder.decode(title,"utf-8"));
								}
							}
						}
					}
					Pattern ptitle = Pattern.compile("^<title>(.*)</title>"); //获取html的页面名称
					Matcher mtitle = ptitle.matcher(lineContent);
					if(mtitle.find()){
						title = mtitle.group(1);
						if(jsListBean != null){
							for(AutoFormatBean tempBean : jsListBean){
								tempBean.setHtmlName(tempFile.getName());
								if(title != null & title != ""){
									tempBean.setPageName(URLDecoder.decode(title,"utf-8"));
								}
							}
						}
					}
				}
			}
		}catch(Exception e){
			e.printStackTrace();
			System.out.print("获取html文件列表失败");
		}
	}

	public Map<Integer, AutoFormatBean> getAutoFormatMap() {
		return autoFormatMap;
	}

	public void setAutoFormatMap(Map<Integer, AutoFormatBean> autoFormatMap) {
		this.autoFormatMap = autoFormatMap;
	}

}
