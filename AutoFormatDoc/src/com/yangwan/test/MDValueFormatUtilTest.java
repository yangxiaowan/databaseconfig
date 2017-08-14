package com.yangwan.test;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.yangwan.bean.AutoFormatBean;
import com.yangwan.utils.MDValueFormatUtil;

public class MDValueFormatUtilTest {

	@Test
	public void test(){
		System.out.println("JUnit is OK!");
	}
	
	@Test
	public void autoAnalysisProcess(){
		Map<String, AutoFormatBean> autoFormatList = new HashMap<String, AutoFormatBean>();
		MDValueFormatUtil mdValueFormatUtil = new MDValueFormatUtil(autoFormatList,
				"C:\\Users\\yangwan\\git\\qmc-mgr_cash\\target\\bwae\\srv-api\\META-INF\\js\\bwaerpc-i.js");
		mdValueFormatUtil.autoAnalysisProcess();
		
	}
}
