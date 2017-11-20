package com.yangwan.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Test;

import com.yangwan.bean.AutoFormatBean;
import com.yangwan.utils.AutoFormatTable;
import com.yangwan.utils.DataBaseUtils;
import com.yangwan.utils.HiveToOracleTableAutoFormat;
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
//	Pattern pattern = Pattern.compile("(qmc_\\w+)(\\s+)(QMC_\\w+)(\\s+)(.+)");
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
		Pattern pattern = Pattern.compile("(qmc_\\w+)(\\s+)(QMC_\\w+)(\\s+)(.+)");
		String testStr = " qmc_rs_activeusernobyday	QMC_ACTIVEUSERNOBYDAY_YM	活跃账号--天数据统计表";
		Matcher m = pattern.matcher(testStr.trim());
		if(m.find()){
			System.out.println(m.group(0));
			System.out.println(m.group(1));
			System.out.println(m.group(2));
			System.out.println(m.group(3));
			System.out.println(m.group(4));
			System.out.println(m.group(5));
		}
	}
	
	@Test
	public void autoHiveToOracleAnalysisProcess(){
		String filePath = "C:\\BigData\\Spark_QMC_HIVE2ORACLE\\doc\\20171020-Umeng-Hive-to-Oracle.txt";
		HiveToOracleTableAutoFormat hiveToOracleTableAutoFormat = new HiveToOracleTableAutoFormat(filePath);
		hiveToOracleTableAutoFormat.autoHiveToOracleAnalysisProcess();
	}
	
	
	@Test
	public void autoTableAnalysisProcess(){
		String filePath = "C:\\BigData\\Spark_QMC_HIVE2ORACLE\\src\\main\\scala\\com\\bw\\oracle\\友盟oracle.sql";
		AutoFormatTable autoFormatTable = new AutoFormatTable(filePath);
		autoFormatTable.autoFormatCreateTable();
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
	public void containStr(){
		String tempStr = "sfasfe ad  ,..43435 daytime324 asfdg";
		if(tempStr.contains("daytime")){
			System.out.println("11111111111");
		}
	}
	
	@Test
	public void regTest3(){
		Pattern pattern = Pattern.compile("^(\\s*)(\\w+)(.+)(\\,)");
		Matcher m = pattern.matcher("  startupcount NUMBER DEFAULT 0 not null,");
		System.out.println("info:");
		if(m.find()){
			System.out.println("0"+m.group(0));
			System.out.println(m.group(1));
			System.out.println(m.group(2));
		}else{
			
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
	public void goldAutoFormatData(){  //gold项目解析
		Map<Integer, AutoFormatBean> autoFormatList = new HashMap<Integer, AutoFormatBean>();
		mdValueFormatUtil = new MDValueFormatUtil(autoFormatList,
				"C:\\Users\\yangwan\\git\\qmc-mgr_gold-page\\src\\site\\js\\bwaerpc-i.js");
		mdValueFormatUtil.autoAnalysisProcess();
		String workPath = "C:\\Users\\yangwan\\git\\qmc-mgr_gold-page\\src\\site";
		pageNameFormatUtil = new PageNameFormatUtil(autoFormatList, workPath);
		pageNameFormatUtil.setModuleName("gold");
		pageNameFormatUtil.scanJsFile();
		pageNameFormatUtil.scanHtmlFile();
//		for(Integer key : autoFormatList.keySet()){
//			autoFormatList.get(key).outputBeanInfo();
//		}
		DataBaseUtils dataBaseUtils = new DataBaseUtils();
		try{
			dataBaseUtils.saveAutoFormatData(autoFormatList); //存储数据到数据库
			dataBaseUtils.setModuleName("gold");
			ArrayList<String> list = new ArrayList<String>();
			list.add("明星榜");
//			list.add("NBA球员资料");
//			list.add("奖金回滚");
//			list.add("数据统计");
			dataBaseUtils.setPageNameList(list);
			dataBaseUtils.formatDoc();
		}catch(Exception e){
			System.out.println("ERROR!!!");
			e.printStackTrace();
		}
	}
	
	@Test
	public void dcAutoFormatData(){  //dc项目解析
		Map<Integer, AutoFormatBean> autoFormatList = new HashMap<Integer, AutoFormatBean>();
		mdValueFormatUtil = new MDValueFormatUtil(autoFormatList,
				"C:\\Users\\yangwan\\git\\qmc-mgr_dc-page\\src\\site\\js\\bwaerpc-i.js");
		mdValueFormatUtil.autoAnalysisProcess();
		String workPath = "C:\\Users\\yangwan\\git\\qmc-mgr_dc-page\\src\\site";
		pageNameFormatUtil = new PageNameFormatUtil(autoFormatList, workPath);
		pageNameFormatUtil.setModuleName("datacenter");
		pageNameFormatUtil.scanJsFile();
		pageNameFormatUtil.scanHtmlFile();
//		for(Integer key : autoFormatList.keySet()){
//			autoFormatList.get(key).outputBeanInfo();
//		}
		DataBaseUtils dataBaseUtils = new DataBaseUtils();
		try{
			dataBaseUtils.saveAutoFormatData(autoFormatList); //存储数据到数据库
			dataBaseUtils.setModuleName("datacenter");
			ArrayList<String> list = new ArrayList<String>();
			list.add("充值券使用统计");
			list.add("充值券用户统计");
//			list.add("奖金回滚");
//			list.add("数据统计");
			dataBaseUtils.setPageNameList(list);
			dataBaseUtils.formatDoc();
		}catch(Exception e){
			System.out.println("ERROR!!!");
			e.printStackTrace();
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
			dataBaseUtils.saveAutoFormatData(autoFormatList); //存储数据到数据库
			dataBaseUtils.setModuleName("cash-special");
			ArrayList<String> list = new ArrayList<String>();
			list.add("特殊版充值配置");
//			list.add("出票商余额查询");
//			list.add("滚动条设置替换");
//			list.add("余额管理");
//			list.add("上传图片");
//			list.add("首页图标配置");
//			list.add("提现大客户管理");
//			list.add("提现处理");
			dataBaseUtils.setPageNameList(list);
//			dataBaseUtils.formatHtmlDoc();
			dataBaseUtils.formatHtmlDoc();
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
			dataBaseUtils.setModuleName("cash");
			ArrayList<String> list = new ArrayList<String>();
			String pageContent = "";
			list.add("二次交易订单查询");
			list.add("二次交易账户");
			list.add("二次交易数据统计");
//			list.add("荐单查询（运营）");
//			数字彩信息 竞彩篮球赛程 竞彩篮球赛果 竞彩足球赛程 竞彩足球赛果 北京单彩赛程  北京单彩赛果
			dataBaseUtils.setPageNameList(list);
			dataBaseUtils.formatHtmlDoc();
		}catch(Exception e){
			System.out.println("ERROR!!!");
			e.printStackTrace();
		}
	}
}
