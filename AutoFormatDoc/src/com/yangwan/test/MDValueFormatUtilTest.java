package com.yangwan.test;

import java.util.ArrayList;
import java.util.List;

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
		List<AutoFormatBean> autoFormatList = new ArrayList<AutoFormatBean>();
		MDValueFormatUtil mdValueFormatUtil = new MDValueFormatUtil(autoFormatList,
				"C:\\Users\\yangwan\\git\\qmc-mgr_cash\\target\\bwae\\srv-api\\META-INF\\js\\bwaerpc-i.js");
		mdValueFormatUtil.autoAnalysisProcess();
		
	}
}
