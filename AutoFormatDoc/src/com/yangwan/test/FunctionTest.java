package com.yangwan.test;

import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Test;

import com.yangwan.bean.AutoFormatBean;
import com.yangwan.utils.DataBaseUtils;
import com.yangwan.utils.MDValueFormatUtil;
import com.yangwan.utils.MyUtils;
import com.yangwan.utils.PageNameFormatUtil;

public class FunctionTest {

	private PageNameFormatUtil pageNameFormatUtil;
	
	private MDValueFormatUtil mdValueFormatUtil;
	
	@Test
	public void test(){
		System.out.println("JUnit is OK!");
	}
	
	@Test
	public void autoAnalysisProcess(){
		Map<Integer, AutoFormatBean> autoFormatList = new HashMap<Integer, AutoFormatBean>();
		mdValueFormatUtil = new MDValueFormatUtil(autoFormatList,
				"C:\\cash-spec-page\\src\\site\\js\\bwaerpc-i.js");
		mdValueFormatUtil.autoAnalysisProcess();
		for(Integer key : autoFormatList.keySet()){
			autoFormatList.get(key).outputBeanInfo();
		}
	}
	
	@Test
	public void scanJsFile(){
		Map<Integer, AutoFormatBean> autoFormatList = new HashMap<Integer, AutoFormatBean>();
		mdValueFormatUtil = new MDValueFormatUtil(autoFormatList,
				"C:\\cash-spec-page\\src\\site\\js\\bwaerpc-i.js");
		mdValueFormatUtil.autoAnalysisProcess();
		String workPath = "C:\\cash-spec-page\\src\\site";
		pageNameFormatUtil = new PageNameFormatUtil(autoFormatList, workPath);
		pageNameFormatUtil.setModuleName("cash-special");
		pageNameFormatUtil.scanJsFile();
		pageNameFormatUtil.scanHtmlFile();
		for(Integer key : autoFormatList.keySet()){
			autoFormatList.get(key).outputBeanInfo();
		}
	}
	
	
	@Test
	public void regTest(){
		Pattern pattern = Pattern.compile("^BWAE(\\.)(\\w+)(\\.)(\\w+)(\\.)(\\w+)");
		String testStr = "		BWAE.cash.ScoreLiveService.getEvent({";
		Matcher m = pattern.matcher(testStr.trim());
		if(m.find()){
			System.out.println(m.group(0));
			System.out.println(m.group(1));
			System.out.println(m.group(2));
			System.out.println(m.group(3));
			System.out.println(m.group(4));
			System.out.println(m.group(5));
			System.out.println(m.group(6));
		}
	}
	
	@Test
	public void regTest2(){
		Pattern pattern = Pattern.compile("^<script(.*)(../js/)(.*.js)");
		String testStr = "<script type=\"text/javascript\" src=\"../js/whiteListBetConfiReplace.js\"></script>";
		Matcher m = pattern.matcher(testStr.trim());
		System.out.println("info:");
		if(m.find()){
			System.out.println(m.group(0));
			System.out.println(m.group(1));
			System.out.println(m.group(2));
			System.out.println(m.group(3));
		}
	}
	
	@Test
	public void getUniqueCode(){
		System.out.println(MyUtils.generateUniqueCode("cashwhiteListSpecialTradeChannelConfiguration.js").toString());
	}
	
	@Test
	public void testIdentityHashMap(){
		IdentityHashMap<Integer, String> map = new IdentityHashMap<>();
		map.put(1, "yangwan");
		map.put(2, "yangwan2");
		map.put(3, "yangwan3");
		map.put(1, "yangwan4");
		map.put(2, "yangwan5");
	}
	
	@Test
    public void testDataBase(){
    	DataBaseUtils dataBaseUtils = new DataBaseUtils();
    	if(dataBaseUtils.getConnectionToSQL()){
    		System.out.println("连接数据库成功");
    	}
    }
	
	@Test
	public void cashSpecialAutoFormatData(){  //cash-special项目解析
		Map<Integer, AutoFormatBean> autoFormatList = new HashMap<Integer, AutoFormatBean>();
		mdValueFormatUtil = new MDValueFormatUtil(autoFormatList,
				"C:\\Users\\yangwan\\git\\qmc-mgr_cash-spec-page\\src\\site\\js\\bwaerpc-i.js");
		mdValueFormatUtil.autoAnalysisProcess();
		String workPath = "C:\\Users\\yangwan\\git\\qmc-mgr_cash-spec-page\\src\\site";
		pageNameFormatUtil = new PageNameFormatUtil(autoFormatList, workPath);
		pageNameFormatUtil.setModuleName("cash-special");
		pageNameFormatUtil.scanJsFile();
		pageNameFormatUtil.scanHtmlFile();
//		for(Integer key : autoFormatList.keySet()){
//			autoFormatList.get(key).outputBeanInfo();
//		}
		DataBaseUtils dataBaseUtils = new DataBaseUtils();
		try{
			dataBaseUtils.saveAutoFormatData(autoFormatList);
		}catch(Exception e){
			System.out.println("ERROR!!!");
			e.printStackTrace();
		}
	}
	
	@Test
	public void cashAutoFormatData(){  //cash项目解析
		Map<Integer, AutoFormatBean> autoFormatList = new HashMap<Integer, AutoFormatBean>();
		mdValueFormatUtil = new MDValueFormatUtil(autoFormatList,
				"C:\\Users\\yangwan\\git\\qmc-mgr_cash-page\\src\\site\\js\\bwaerpc-i.js");
		mdValueFormatUtil.autoAnalysisProcess();
		String workPath = "C:\\Users\\yangwan\\git\\qmc-mgr_cash-page\\src\\site";
		pageNameFormatUtil = new PageNameFormatUtil(autoFormatList, workPath);
		pageNameFormatUtil.setModuleName("cash");
		pageNameFormatUtil.scanJsFile();
		pageNameFormatUtil.scanHtmlFile();
//		for(Integer key : autoFormatList.keySet()){
//			autoFormatList.get(key).outputBeanInfo();
//		}
		DataBaseUtils dataBaseUtils = new DataBaseUtils();
		try{
			dataBaseUtils.saveAutoFormatData(autoFormatList);
		}catch(Exception e){
			System.out.println("ERROR!!!");
			e.printStackTrace();
		}
	}
}
